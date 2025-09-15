package com.logistics.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * ロケーションマスタエンティティ
 * Location Master Entity
 * 
 * 倉庫内の位置情報を管理するテーブルに対応
 * Corresponds to the table that manages warehouse location information
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationMaster {
    
    /**
     * ロケーションID（主キー）
     * Location ID (Primary Key)
     */
    private String locationId;
    
    /**
     * 倉庫コード（必須）
     * Warehouse Code (Required)
     */
    private String warehouseCode;
    
    /**
     * ゾーン
     * Zone
     */
    private String zone;
    
    /**
     * ラック
     * Rack
     */
    private String rack;
    
    /**
     * レベル
     * Level
     */
    private String levelNo;
    
    /**
     * ポジション
     * Position
     */
    private String position;
    
    /**
     * 最大容量
     * Maximum Capacity
     */
    private Integer maxCapacity;
    
    /**
     * 作成日時
     * Created At
     */
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
}
