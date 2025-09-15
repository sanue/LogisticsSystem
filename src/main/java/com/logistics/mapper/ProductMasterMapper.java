package com.logistics.mapper;

import com.logistics.controller.dto.PageRequest;
import com.logistics.entity.ProductMaster;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 商品マスタMapperインターフェース
 * Product Master Mapper Interface
 * 
 * 商品データのCRUD操作を定義
 * Defines CRUD operations for product data
 */
@Mapper
public interface ProductMasterMapper {
    
    /**
     * 全商品を取得
     * Get all products
     * 
     * @return 商品リスト
     * @return List of products
     */
    @Select("SELECT * FROM PRODUCT_MASTER ORDER BY CREATED_AT DESC")
    List<ProductMaster> findAll();
    
    /**
     * 商品IDで商品を取得
     * Get product by product ID
     * 
     * @param productId 商品ID
     * @return 商品情報
     * @return Product information
     */
    @Select("SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_ID = #{productId}")
    ProductMaster findById(@Param("productId") String productId);
    
    /**
     * 商品名で検索
     * Search by product name
     * 
     * @param productName 商品名（部分一致）
     * @return 商品リスト
     * @return List of products
     */
    @Select("SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_NAME LIKE '%' || #{productName} || '%' ORDER BY CREATED_AT DESC")
    List<ProductMaster> findByName(@Param("productName") String productName);
    
    /**
     * 商品を新規登録
     * Insert new product
     * 
     * @param product 商品情報
     * @return 登録件数
     * @return Number of inserted records
     */
    @Insert("INSERT INTO PRODUCT_MASTER (PRODUCT_ID, PRODUCT_NAME, SPECIFICATION, UNIT, SAFETY_STOCK, CREATED_AT, UPDATED_AT) " +
            "VALUES (#{productId}, #{productName}, #{specification}, #{unit}, #{safetyStock}, #{createdAt}, #{updatedAt})")
    int insert(ProductMaster product);
    
    /**
     * 商品情報を更新
     * Update product information
     * 
     * @param product 商品情報
     * @return 更新件数
     * @return Number of updated records
     */
    @Update("UPDATE PRODUCT_MASTER SET " +
            "PRODUCT_NAME = #{productName}, " +
            "SPECIFICATION = #{specification}, " +
            "UNIT = #{unit}, " +
            "SAFETY_STOCK = #{safetyStock}, " +
            "UPDATED_AT = #{updatedAt} " +
            "WHERE PRODUCT_ID = #{productId}")
    int update(ProductMaster product);
    
    /**
     * 商品を削除
     * Delete product
     * 
     * @param productId 商品ID
     * @return 削除件数
     * @return Number of deleted records
     */
    @Delete("DELETE FROM PRODUCT_MASTER WHERE PRODUCT_ID = #{productId}")
    int deleteById(@Param("productId") String productId);
    
    /**
     * 商品の存在確認
     * Check if product exists
     * 
     * @param productId 商品ID
     * @return 存在する場合true
     * @return true if exists
     */
    @Select("SELECT COUNT(*) FROM PRODUCT_MASTER WHERE PRODUCT_ID = #{productId}")
    boolean existsById(@Param("productId") String productId);
    
    /**
     * 分页查询商品列表
     * Find products with pagination
     * 
     * @param pageRequest 分页请求
     * @return 商品列表
     * @return List of products
     */
    @Select("<script>" +
            "SELECT * FROM PRODUCT_MASTER " +
            "<where>" +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (PRODUCT_NAME LIKE '%' || #{keyword} || '%' OR PRODUCT_ID LIKE '%' || #{keyword} || '%')" +
            "</if>" +
            "</where>" +
            "ORDER BY " +
            "<choose>" +
            "<when test='sortBy == \"productName\"'>PRODUCT_NAME</when>" +
            "<when test='sortBy == \"productId\"'>PRODUCT_ID</when>" +
            "<when test='sortBy == \"safetyStock\"'>SAFETY_STOCK</when>" +
            "<otherwise>CREATED_AT</otherwise>" +
            "</choose>" +
            "<choose>" +
            "<when test='sortDir == \"ASC\"'>ASC</when>" +
            "<otherwise>DESC</otherwise>" +
            "</choose>" +
            " OFFSET #{page} * #{size} ROWS FETCH NEXT #{size} ROWS ONLY" +
            "</script>")
    List<ProductMaster> findWithPagination(PageRequest pageRequest);
    
    /**
     * 统计商品总数
     * Count total products
     * 
     * @param pageRequest 分页请求
     * @return 总数
     * @return Total count
     */
    @Select("<script>" +
            "SELECT COUNT(*) FROM PRODUCT_MASTER " +
            "<where>" +
            "<if test='keyword != null and keyword != \"\"'>" +
            "AND (PRODUCT_NAME LIKE '%' || #{keyword} || '%' OR PRODUCT_ID LIKE '%' || #{keyword} || '%')" +
            "</if>" +
            "</where>" +
            "</script>")
    long countWithPagination(PageRequest pageRequest);
}
