package com.logistics.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIレスポンス共通クラス
 * Common API Response Class
 * 
 * 全てのAPIレスポンスの統一フォーマット
 * Unified format for all API responses
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    
    /**
     * レスポンスコード
     * Response code
     */
    private int code;
    
    /**
     * メッセージ
     * Message
     */
    private String message;
    
    /**
     * データ
     * Data
     */
    private T data;
    
    /**
     * 成功レスポンスを作成
     * Create success response
     * 
     * @param data データ
     * @return 成功レスポンス
     * @return Success response
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(200)
                .message("Success")
                .data(data)
                .build();
    }
    
    /**
     * 成功レスポンスを作成（メッセージ指定）
     * Create success response with custom message
     * 
     * @param data データ
     * @param message メッセージ
     * @return 成功レスポンス
     * @return Success response
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .code(200)
                .message(message)
                .data(data)
                .build();
    }
    
    /**
     * エラーレスポンスを作成
     * Create error response
     * 
     * @param code エラーコード
     * @param message エラーメッセージ
     * @return エラーレスポンス
     * @return Error response
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
                .code(code)
                .message(message)
                .data(null)
                .build();
    }
    
    /**
     * バリデーションエラーレスポンスを作成
     * Create validation error response
     * 
     * @param message エラーメッセージ
     * @return バリデーションエラーレスポンス
     * @return Validation error response
     */
    public static <T> ApiResponse<T> validationError(String message) {
        return error(400, message);
    }
    
    /**
     * サーバーエラーレスポンスを作成
     * Create server error response
     * 
     * @param message エラーメッセージ
     * @return サーバーエラーレスポンス
     * @return Server error response
     */
    public static <T> ApiResponse<T> serverError(String message) {
        return error(500, message);
    }
}

