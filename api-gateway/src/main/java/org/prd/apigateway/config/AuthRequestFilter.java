package org.prd.apigateway.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.prd.apigateway.dto.JwtResponse;
import org.prd.apigateway.dto.UriRequest;
import org.prd.apigateway.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


@Component
public class AuthRequestFilter extends AbstractGatewayFilterFactory<AuthRequestFilter.Config> {

    private final WebClient.Builder webClient;

    private final Logger log = LoggerFactory.getLogger(AuthRequestFilter.class);
    private final JwtUtil jwtUtil;

    public AuthRequestFilter(WebClient.Builder webClient, JwtUtil jwtUtil) {
        super(Config.class);
        this.webClient = webClient;
        this.jwtUtil = jwtUtil;

    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isPreLogger()) {
                log.info("Pre filter: {}", config.getMessage());

                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                    return onError(exchange, HttpStatus.BAD_REQUEST, "Authorization header is missing.");
                }

                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                String token = jwtUtil.resolve(authHeader);
                if (token == null) {
                    return onError(exchange, HttpStatus.BAD_REQUEST, "Invalid token.");
                }

                // Llamada de validación del token con WebClient
                return webClient.build()
                        .post()
                        .uri("http://micro-auth-service/auth/open/validate?token=" + token)
                        .bodyValue(new UriRequest(exchange.getRequest().getPath().toString(), exchange.getRequest().getMethod().toString()))
                        .retrieve()
                        .onStatus(httpStatusCode -> {
                            log.info("Status code: {}", httpStatusCode);
                            return httpStatusCode.isError();
                            }, response -> response.bodyToMono(String.class)
                                        .flatMap(errorBody -> {
                                            log.error("Authentication failed with status: {}", response.statusCode());
                                            log.error("Error body: {}", errorBody);
                                            // Lanzar una excepción con el estado de error y el cuerpo del mensaje
                                            return Mono.error(new CustomAuthException((HttpStatus) response.statusCode(), errorBody));
                                        })
                        )
                        .bodyToMono(JwtResponse.class)
                        .flatMap(jwtResponse -> {
                            log.info("Token validated successfully: {}", jwtResponse);
                            return chain.filter(exchange);
                        })
                        //Si se produce un error durante la validación del token.
                        .onErrorResume(CustomAuthException.class, e -> {
                            log.error("Error during token validation: {}", e.getMessage());
                            return onError(exchange, e.getStatus(), e.getMessage());
                        })
                        //Error inesperado en el servicio gateway mientras se valida el token.
                        .onErrorResume(e -> {
                            log.error("Unexpected error during token validation: {}", e.getMessage());
                            return onError(exchange, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error during token validation.");
                        });
            }

            // Post-filtro
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if (config.isPostLogger()) {
                    log.info("Post filter: {}", config.getMessage());
                }
            }));
        };
    }

    // Método para responder con un error y enviar un cuerpo de error personalizado
    public Mono<Void> onError(ServerWebExchange exchange, HttpStatus status, String errorBody) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        byte[] bytes = errorBody.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }



    public static class CustomAuthException extends RuntimeException {
        private final HttpStatus status;

        public CustomAuthException(HttpStatus status, String message) {
            super(message);
            this.status = status;
        }

        public HttpStatus getStatus() {
            return status;
        }
    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Config {
        private String message;
        private boolean preLogger;
        private boolean postLogger;
    }
}