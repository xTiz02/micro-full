package org.prd.bookservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopic {
    @Value("${spring-book-topic-name}")
    private String prodRevertTopic;

    @Bean
    public NewTopic generateTopic() {

        Map<String, String> configurations = new HashMap<>();
        configurations.put(TopicConfig.CLEANUP_POLICY_CONFIG, TopicConfig.CLEANUP_POLICY_DELETE); // delete (borra mensaje) - compact (Mantiene el mas actual)
        configurations.put(TopicConfig.RETENTION_MS_CONFIG, "86400000"); // Tiempo de retencion de mensajes, defecto -1 (indefinido)
        configurations.put(TopicConfig.SEGMENT_BYTES_CONFIG, "1073741824"); // Tamanio maximo del segmento - defecto 1073741824 bytes - 1GB
        configurations.put(TopicConfig.MAX_MESSAGE_BYTES_CONFIG, "1000012"); // Tamanio maximo de cada mensaje - defecto 1000000 - 1 MB

        NewTopic newTopic = TopicBuilder.name(prodRevertTopic)
                .partitions(2) // Numero de particiones
                //.replicas(2) // Numero de replicas
                .configs(configurations)
                .build();
        return newTopic;
    }
}