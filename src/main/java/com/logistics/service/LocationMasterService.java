package com.logistics.service;

import com.logistics.entity.LocationMaster;

import java.util.List;

/**
 * ロケーションマスタサービスインターフェース
 * Location Master Service Interface
 * 
 * ロケーション管理のビジネスロジックを定義
 * Defines business logic for location management
 */
public interface LocationMasterService {
    
    /**
     * 全ロケーションを取得
     * Get all locations
     * 
     * @return ロケーションリスト
     * @return List of locations
     */
    List<LocationMaster> getAllLocations();
    
    /**
     * ロケーションIDでロケーションを取得
     * Get location by location ID
     * 
     * @param locationId ロケーションID
     * @return ロケーション情報
     * @return Location information
     */
    LocationMaster getLocationById(String locationId);
    
    /**
     * 倉庫コードで検索
     * Search by warehouse code
     * 
     * @param warehouseCode 倉庫コード
     * @return ロケーションリスト
     * @return List of locations
     */
    List<LocationMaster> getLocationsByWarehouseCode(String warehouseCode);
    
    /**
     * ゾーンで検索
     * Search by zone
     * 
     * @param zone ゾーン
     * @return ロケーションリスト
     * @return List of locations
     */
    List<LocationMaster> getLocationsByZone(String zone);
    
    /**
     * ロケーションを新規登録
     * Create new location
     * 
     * @param location ロケーション情報
     * @return 登録されたロケーション情報
     * @return Created location information
     */
    LocationMaster createLocation(LocationMaster location);
    
    /**
     * ロケーション情報を更新
     * Update location information
     * 
     * @param location ロケーション情報
     * @return 更新されたロケーション情報
     * @return Updated location information
     */
    LocationMaster updateLocation(LocationMaster location);
    
    /**
     * ロケーションを削除
     * Delete location
     * 
     * @param locationId ロケーションID
     * @return 削除成功の場合true
     * @return true if deletion successful
     */
    boolean deleteLocation(String locationId);
    
    /**
     * ロケーションの存在確認
     * Check if location exists
     * 
     * @param locationId ロケーションID
     * @return 存在する場合true
     * @return true if exists
     */
    boolean existsLocation(String locationId);
}
