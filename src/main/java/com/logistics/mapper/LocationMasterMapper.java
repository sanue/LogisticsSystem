package com.logistics.mapper;

import com.logistics.entity.LocationMaster;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * ロケーションマスタMapperインターフェース
 * Location Master Mapper Interface
 * 
 * ロケーションデータのCRUD操作を定義
 * Defines CRUD operations for location data
 */
@Mapper
public interface LocationMasterMapper {
    
    /**
     * 全ロケーションを取得
     * Get all locations
     * 
     * @return ロケーションリスト
     * @return List of locations
     */
    @Select("SELECT * FROM LOCATION_MASTER ORDER BY CREATED_AT DESC")
    List<LocationMaster> findAll();
    
    /**
     * ロケーションIDでロケーションを取得
     * Get location by location ID
     * 
     * @param locationId ロケーションID
     * @return ロケーション情報
     * @return Location information
     */
    @Select("SELECT * FROM LOCATION_MASTER WHERE LOCATION_ID = #{locationId}")
    LocationMaster findById(@Param("locationId") String locationId);
    
    /**
     * 倉庫コードで検索
     * Search by warehouse code
     * 
     * @param warehouseCode 倉庫コード
     * @return ロケーションリスト
     * @return List of locations
     */
    @Select("SELECT * FROM LOCATION_MASTER WHERE WAREHOUSE_CODE = #{warehouseCode} ORDER BY CREATED_AT DESC")
    List<LocationMaster> findByWarehouseCode(@Param("warehouseCode") String warehouseCode);
    
    /**
     * ゾーンで検索
     * Search by zone
     * 
     * @param zone ゾーン
     * @return ロケーションリスト
     * @return List of locations
     */
    @Select("SELECT * FROM LOCATION_MASTER WHERE ZONE = #{zone} ORDER BY CREATED_AT DESC")
    List<LocationMaster> findByZone(@Param("zone") String zone);
    
    /**
     * ロケーションを新規登録
     * Insert new location
     * 
     * @param location ロケーション情報
     * @return 登録件数
     * @return Number of inserted records
     */
    @Insert("INSERT INTO LOCATION_MASTER (LOCATION_ID, WAREHOUSE_CODE, ZONE, RACK, LEVEL_NO, POSITION, MAX_CAPACITY, CREATED_AT) " +
            "VALUES (#{locationId}, #{warehouseCode}, #{zone}, #{rack}, #{levelNo}, #{position}, #{maxCapacity}, #{createdAt})")
    int insert(LocationMaster location);
    
    /**
     * ロケーション情報を更新
     * Update location information
     * 
     * @param location ロケーション情報
     * @return 更新件数
     * @return Number of updated records
     */
    @Update("UPDATE LOCATION_MASTER SET " +
            "WAREHOUSE_CODE = #{warehouseCode}, " +
            "ZONE = #{zone}, " +
            "RACK = #{rack}, " +
            "LEVEL_NO = #{levelNo}, " +
            "POSITION = #{position}, " +
            "MAX_CAPACITY = #{maxCapacity} " +
            "WHERE LOCATION_ID = #{locationId}")
    int update(LocationMaster location);
    
    /**
     * ロケーションを削除
     * Delete location
     * 
     * @param locationId ロケーションID
     * @return 削除件数
     * @return Number of deleted records
     */
    @Delete("DELETE FROM LOCATION_MASTER WHERE LOCATION_ID = #{locationId}")
    int deleteById(@Param("locationId") String locationId);
    
    /**
     * ロケーションの存在確認
     * Check if location exists
     * 
     * @param locationId ロケーションID
     * @return 存在する場合true
     * @return true if exists
     */
    @Select("SELECT COUNT(*) FROM LOCATION_MASTER WHERE LOCATION_ID = #{locationId}")
    boolean existsById(@Param("locationId") String locationId);
}
