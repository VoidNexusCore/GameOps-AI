package com.gameops.admin.controller;

import com.gameops.common.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/api/health")
    public ApiResult<Map<String, String>> health() {
        return ApiResult.ok(Map.of("status", "UP", "application", "GameOps AI"));
    }
}
