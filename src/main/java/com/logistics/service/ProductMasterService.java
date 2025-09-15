package com.logistics.service;

import com.logistics.controller.dto.PageRequest;
import com.logistics.controller.dto.PageResponse;
import com.logistics.entity.ProductMaster;

import java.util.List;

/**
 * 商品マスタサービスインターフェース
 * Product Master Service Interface
 * 
 * 商品管理のビジネスロジックを定義
 * Defines business logic for product management
 */
public interface ProductMasterService {
    
    /**
     * 全商品を取得
     * Get all products
     * 
     * @return 商品リスト
     * @return List of products
     */
    List<ProductMaster> getAllProducts();
    
    /**
     * 分页获取商品列表
     * Get products with pagination
     * 
     * @param pageRequest 分页请求
     * @return 分页商品列表
     * @return Paginated product list
     */
    PageResponse<ProductMaster> getProductsWithPagination(PageRequest pageRequest);
    
    /**
     * 商品IDで商品を取得
     * Get product by product ID
     * 
     * @param productId 商品ID
     * @return 商品情報
     * @return Product information
     */
    ProductMaster getProductById(String productId);
    
    /**
     * 商品名で検索
     * Search by product name
     * 
     * @param productName 商品名
     * @return 商品リスト
     * @return List of products
     */
    List<ProductMaster> searchProductsByName(String productName);
    
    /**
     * 商品を新規登録
     * Create new product
     * 
     * @param product 商品情報
     * @return 登録された商品情報
     * @return Created product information
     */
    ProductMaster createProduct(ProductMaster product);
    
    /**
     * 商品情報を更新
     * Update product information
     * 
     * @param product 商品情報
     * @return 更新された商品情報
     * @return Updated product information
     */
    ProductMaster updateProduct(ProductMaster product);
    
    /**
     * 商品を削除
     * Delete product
     * 
     * @param productId 商品ID
     * @return 削除成功の場合true
     * @return true if deletion successful
     */
    boolean deleteProduct(String productId);
    
    /**
     * 商品の存在確認
     * Check if product exists
     * 
     * @param productId 商品ID
     * @return 存在する場合true
     * @return true if exists
     */
    boolean existsProduct(String productId);
}
