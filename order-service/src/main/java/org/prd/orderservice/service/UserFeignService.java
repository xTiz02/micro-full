package org.prd.orderservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "micro-auth-service")
public interface UserFeignService {

    @GetMapping("/user/restrict/id/{uuid}")
    ResponseEntity<?> getUserByUUID(@PathVariable(name = "uuid") UUID uuid);
}