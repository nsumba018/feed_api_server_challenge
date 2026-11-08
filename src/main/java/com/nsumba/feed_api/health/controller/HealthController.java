package com.nsumba.feed_api.health.controller;

import com.nsumba.feed_api.dto.HealthErrorResponseDto;
import com.nsumba.feed_api.dto.HealthResponseDto;
import com.nsumba.feed_api.health.service.IHealthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthController {

    private final IHealthService healthService;

    @GetMapping("/health")
    public ResponseEntity<?> getHealth() {
        try {
            healthService.checkDatabase();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new HealthResponseDto("ok", "ok", healthService.getUptimeSeconds()));
        } catch (Exception e) {
            // I return 503 when the database fails, as the spec asks
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new HealthErrorResponseDto("degraded", "unreachable", e.getMessage()));
        }
    }
}
