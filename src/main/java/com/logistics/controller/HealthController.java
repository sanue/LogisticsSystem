package com.logistics.controller;

import com.logistics.controller.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * ヘルスチェックコントローラー
 * Health Check Controller
 * 
 * アプリケーションの状態を確認
 * Check application status
 */
@RestController
@RequestMapping("/api/health")
@CrossOrigin(origins = "*")
public class HealthController {
    
    /**
     * ヘルスチェック
     * Health check
     * 
     * @return アプリケーション状態
     * @return Application status
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> health() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now());
        status.put("application", "Logistics System");
        status.put("version", "1.0.0");
        
        return ResponseEntity.ok(ApiResponse.success(status, "Application is running"));
    }
}
