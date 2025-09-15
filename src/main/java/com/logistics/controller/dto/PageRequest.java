package com.logistics.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 分页请求DTO
 * Page Request DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageRequest {
    /**
     * 页码（从0开始）
     * Page number (starting from 0)
     */
    private int page = 0;
    
    /**
     * 每页大小
     * Page size
     */
    private int size = 10;
    
    /**
     * 排序字段
     * Sort field
     */
    private String sortBy = "createdAt";
    
    /**
     * 排序方向（ASC/DESC）
     * Sort direction (ASC/DESC)
     */
    private String sortDir = "DESC";
    
    /**
     * 搜索关键词
     * Search keyword
     */
    private String keyword;
}

