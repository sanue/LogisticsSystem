package com.logistics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * 顧客マスタエンティティ
 * Customer Master Entity
 * 
 * 顧客の基本情報を管理するテーブルに対応
 * Corresponds to the table that manages basic customer information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerMaster {
    
    /**
     * 顧客ID（主キー）
     * Customer ID (Primary Key)
     */
    private String customerId;
    
    /**
     * 顧客名（必須）
     * Customer Name (Required)
     */
    private String customerName;
    
    /**
     * 住所
     * Address
     */
    private String address;
    
    /**
     * 電話番号
     * Phone Number
     */
    private String phone;
    
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
