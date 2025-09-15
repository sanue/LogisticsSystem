package com.logistics.service.impl;

import com.logistics.controller.dto.PageRequest;
import com.logistics.controller.dto.PageResponse;
import com.logistics.entity.ProductMaster;
import com.logistics.mapper.ProductMasterMapper;
import com.logistics.service.ProductMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品マスタサービス実装クラス
 * Product Master Service Implementation
 * 
 * 商品管理のビジネスロジックを実装
 * Implements business logic for product management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class ProductMasterServiceImpl implements ProductMasterService {
    
    private final ProductMasterMapper productMasterMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductMaster> getAllProducts() {
        log.info("Getting all products");
        return productMasterMapper.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public PageResponse<ProductMaster> getProductsWithPagination(PageRequest pageRequest) {
        log.info("Getting products with pagination: {}", pageRequest);
        
        // 获取总数
        long totalElements = productMasterMapper.countWithPagination(pageRequest);
        
        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalElements / pageRequest.getSize());
        
        // 获取分页数据
        List<ProductMaster> content = productMasterMapper.findWithPagination(pageRequest);
        
        // 构建分页响应
        return PageResponse.<ProductMaster>builder()
                .content(content)
                .page(pageRequest.getPage())
                .size(pageRequest.getSize())
                .totalPages(totalPages)
                .totalElements(totalElements)
                .hasNext(pageRequest.getPage() < totalPages - 1)
                .hasPrevious(pageRequest.getPage() > 0)
                .isFirst(pageRequest.getPage() == 0)
                .isLast(pageRequest.getPage() >= totalPages - 1)
                .build();
    }
    
    @Override
    @Transactional(readOnly = true)
    public ProductMaster getProductById(String productId) {
        log.info("Getting product by ID: {}", productId);
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        return productMasterMapper.findById(productId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductMaster> searchProductsByName(String productName) {
        log.info("Searching products by name: {}", productName);
        if (productName == null || productName.trim().isEmpty()) {
            return getAllProducts();
        }
        return productMasterMapper.findByName(productName);
    }
    
    @Override
    public ProductMaster createProduct(ProductMaster product) {
        log.info("Creating new product: {}", product.getProductName());
        
        // バリデーション
        validateProduct(product);
        
        // 商品IDの重複チェック
        if (productMasterMapper.existsById(product.getProductId())) {
            throw new IllegalArgumentException("Product with ID " + product.getProductId() + " already exists");
        }
        
        // 作成日時を設定
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        
        int result = productMasterMapper.insert(product);
        if (result == 0) {
            throw new RuntimeException("Failed to create product");
        }
        
        log.info("Product created successfully: {}", product.getProductId());
        return product;
    }
    
    @Override
    public ProductMaster updateProduct(ProductMaster product) {
        log.info("Updating product: {}", product.getProductId());
        
        // バリデーション
        validateProduct(product);
        
        // 商品の存在チェック
        if (!productMasterMapper.existsById(product.getProductId())) {
            throw new IllegalArgumentException("Product with ID " + product.getProductId() + " does not exist");
        }
        
        // 更新日時を設定
        product.setUpdatedAt(LocalDateTime.now());
        
        int result = productMasterMapper.update(product);
        if (result == 0) {
            throw new RuntimeException("Failed to update product");
        }
        
        log.info("Product updated successfully: {}", product.getProductId());
        return product;
    }
    
    @Override
    public boolean deleteProduct(String productId) {
        log.info("Deleting product: {}", productId);
        
        if (productId == null || productId.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be null or empty");
        }
        
        // 商品の存在チェック
        if (!productMasterMapper.existsById(productId)) {
            throw new IllegalArgumentException("Product with ID " + productId + " does not exist");
        }
        
        int result = productMasterMapper.deleteById(productId);
        boolean success = result > 0;
        
        if (success) {
            log.info("Product deleted successfully: {}", productId);
        } else {
            log.warn("Failed to delete product: {}", productId);
        }
        
        return success;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsProduct(String productId) {
        if (productId == null || productId.trim().isEmpty()) {
            return false;
        }
        return productMasterMapper.existsById(productId);
    }
    
    /**
     * 商品情報のバリデーション
     * Validate product information
     * 
     * @param product 商品情報
     */
    private void validateProduct(ProductMaster product) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        
        if (product.getProductId() == null || product.getProductId().trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID is required");
        }
        
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        
        if (product.getUnit() == null || product.getUnit().trim().isEmpty()) {
            throw new IllegalArgumentException("Product unit is required");
        }
        
        if (product.getSafetyStock() == null) {
            product.setSafetyStock(0);
        }
        
        if (product.getSafetyStock() < 0) {
            throw new IllegalArgumentException("Safety stock cannot be negative");
        }
    }
}
