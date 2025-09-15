package com.logistics.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

/**
 * 分页响应DTO
 * Page Response DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    /**
     * 数据列表
     * Data list
     */
    private List<T> content;
    
    /**
     * 当前页码
     * Current page number
     */
    private int page;
    
    /**
     * 每页大小
     * Page size
     */
    private int size;
    
    /**
     * 总页数
     * Total pages
     */
    private int totalPages;
    
    /**
     * 总记录数
     * Total elements
     */
    private long totalElements;
    
    /**
     * 是否有下一页
     * Has next page
     */
    private boolean hasNext;
    
    /**
     * 是否有上一页
     * Has previous page
     */
    private boolean hasPrevious;
    
    /**
     * 是否为第一页
     * Is first page
     */
    private boolean isFirst;
    
    /**
     * 是否为最后一页
     * Is last page
     */
    private boolean isLast;
}

