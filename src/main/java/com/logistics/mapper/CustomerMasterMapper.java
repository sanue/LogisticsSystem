package com.logistics.mapper;

import com.logistics.entity.CustomerMaster;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 顧客マスタMapperインターフェース
 * Customer Master Mapper Interface
 * 
 * 顧客データのCRUD操作を定義
 * Defines CRUD operations for customer data
 */
@Mapper
public interface CustomerMasterMapper {
    
    /**
     * 全顧客を取得
     * Get all customers
     * 
     * @return 顧客リスト
     * @return List of customers
     */
    @Select("SELECT * FROM CUSTOMER_MASTER ORDER BY CREATED_AT DESC")
    List<CustomerMaster> findAll();
    
    /**
     * 顧客IDで顧客を取得
     * Get customer by customer ID
     * 
     * @param customerId 顧客ID
     * @return 顧客情報
     * @return Customer information
     */
    @Select("SELECT * FROM CUSTOMER_MASTER WHERE CUSTOMER_ID = #{customerId}")
    CustomerMaster findById(@Param("customerId") String customerId);
    
    /**
     * 顧客名で検索
     * Search by customer name
     * 
     * @param customerName 顧客名（部分一致）
     * @return 顧客リスト
     * @return List of customers
     */
    @Select("SELECT * FROM CUSTOMER_MASTER WHERE CUSTOMER_NAME LIKE '%' || #{customerName} || '%' ORDER BY CREATED_AT DESC")
    List<CustomerMaster> findByName(@Param("customerName") String customerName);
    
    /**
     * 顧客を新規登録
     * Insert new customer
     * 
     * @param customer 顧客情報
     * @return 登録件数
     * @return Number of inserted records
     */
    @Insert("INSERT INTO CUSTOMER_MASTER (CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, PHONE, CREATED_AT, UPDATED_AT) " +
            "VALUES (#{customerId}, #{customerName}, #{address}, #{phone}, #{createdAt}, #{updatedAt})")
    int insert(CustomerMaster customer);
    
    /**
     * 顧客情報を更新
     * Update customer information
     * 
     * @param customer 顧客情報
     * @return 更新件数
     * @return Number of updated records
     */
    @Update("UPDATE CUSTOMER_MASTER SET " +
            "CUSTOMER_NAME = #{customerName}, " +
            "ADDRESS = #{address}, " +
            "PHONE = #{phone}, " +
            "UPDATED_AT = #{updatedAt} " +
            "WHERE CUSTOMER_ID = #{customerId}")
    int update(CustomerMaster customer);
    
    /**
     * 顧客を削除
     * Delete customer
     * 
     * @param customerId 顧客ID
     * @return 削除件数
     * @return Number of deleted records
     */
    @Delete("DELETE FROM CUSTOMER_MASTER WHERE CUSTOMER_ID = #{customerId}")
    int deleteById(@Param("customerId") String customerId);
    
    /**
     * 顧客の存在確認
     * Check if customer exists
     * 
     * @param customerId 顧客ID
     * @return 存在する場合true
     * @return true if exists
     */
    @Select("SELECT COUNT(*) FROM CUSTOMER_MASTER WHERE CUSTOMER_ID = #{customerId}")
    boolean existsById(@Param("customerId") String customerId);
}
