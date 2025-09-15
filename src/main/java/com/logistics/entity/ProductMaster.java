package com.logistics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 商品マスタエンティティ
 * Product Master Entity
 * 
 * 商品の基本情報を管理するテーブルに対応
 * Corresponds to the table that manages basic product information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductMaster {
    
    /**
     * 商品ID（主キー）
     * Product ID (Primary Key)
     */
    private String productId;
    
    /**
     * 商品名（必須）
     * Product Name (Required)
     */
    private String productName;
    
    /**
     * 仕様
     * Specification
     */
    private String specification;
    
    /**
     * 単位（必須）
     * Unit (Required)
     */
    private String unit;
    
    /**
     * 安全在庫
     * Safety Stock
     */
    @Builder.Default
    private Integer safetyStock = 0;
    
    /**
     * 作成日時
     * Created At
     */
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    /**
     * 更新日時
     * Updated At
     */
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    /**
     * 更新時に自動的に更新日時を設定
     * Automatically set updated time when updating
     */
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
