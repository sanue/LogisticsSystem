package com.logistics.controller;

import com.logistics.controller.dto.ApiResponse;
import com.logistics.controller.dto.PageRequest;
import com.logistics.controller.dto.PageResponse;
import com.logistics.entity.ProductMaster;
import com.logistics.service.ProductMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品マスタコントローラー
 * Product Master Controller
 * 
 * 商品管理のREST APIエンドポイント
 * REST API endpoints for product management
 */
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class ProductMasterController {
    
    private final ProductMasterService productMasterService;
    
    /**
     * 全商品を取得（分页支持）
     * Get all products with pagination
     * 
     * @param page 页码
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @param keyword 搜索关键词
     * @return 分页商品列表
     * @return Paginated product list
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductMaster>>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir,
            @RequestParam(required = false) String keyword) {
        try {
            log.info("Getting products with pagination - page: {}, size: {}, sortBy: {}, sortDir: {}, keyword: {}", 
                    page, size, sortBy, sortDir, keyword);
            
            PageRequest pageRequest = PageRequest.builder()
                    .page(page)
                    .size(size)
                    .sortBy(sortBy)
                    .sortDir(sortDir)
                    .keyword(keyword)
                    .build();
            
            PageResponse<ProductMaster> pageResponse = productMasterService.getProductsWithPagination(pageRequest);
            return ResponseEntity.ok(ApiResponse.success(pageResponse, "Products retrieved successfully"));
        } catch (Exception e) {
            log.error("Error getting products with pagination", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve products: " + e.getMessage()));
        }
    }
    
    /**
     * 商品IDで商品を取得
     * Get product by product ID
     * 
     * @param productId 商品ID
     * @return 商品情報
     * @return Product information
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductMaster>> getProductById(@PathVariable String productId) {
        try {
            log.info("Getting product by ID: {}", productId);
            ProductMaster product = productMasterService.getProductById(productId);
            if (product == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Product not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(product, "Product retrieved successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid product ID: {}", productId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting product by ID: {}", productId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve product: " + e.getMessage()));
        }
    }
    
    /**
     * 商品名で検索
     * Search products by name
     * 
     * @param name 商品名
     * @return 商品リスト
     * @return List of products
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductMaster>>> searchProductsByName(@RequestParam String name) {
        try {
            log.info("Searching products by name: {}", name);
            List<ProductMaster> products = productMasterService.searchProductsByName(name);
            return ResponseEntity.ok(ApiResponse.success(products, "Products searched successfully"));
        } catch (Exception e) {
            log.error("Error searching products by name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to search products: " + e.getMessage()));
        }
    }
    
    /**
     * 商品を新規登録
     * Create new product
     * 
     * @param product 商品情報
     * @return 登録された商品情報
     * @return Created product information
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ProductMaster>> createProduct(@RequestBody ProductMaster product) {
        try {
            log.info("Creating new product: {}", product.getProductName());
            ProductMaster createdProduct = productMasterService.createProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdProduct, "Product created successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating product", e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating product", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to create product: " + e.getMessage()));
        }
    }
    
    /**
     * 商品情報を更新
     * Update product information
     * 
     * @param productId 商品ID
     * @param product 商品情報
     * @return 更新された商品情報
     * @return Updated product information
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductMaster>> updateProduct(
            @PathVariable String productId, 
            @RequestBody ProductMaster product) {
        try {
            log.info("Updating product: {}", productId);
            product.setProductId(productId); // パス変数からIDを設定
            ProductMaster updatedProduct = productMasterService.updateProduct(product);
            return ResponseEntity.ok(ApiResponse.success(updatedProduct, "Product updated successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Validation error updating product: {}", productId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating product: {}", productId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to update product: " + e.getMessage()));
        }
    }
    
    /**
     * 商品を削除
     * Delete product
     * 
     * @param productId 商品ID
     * @return 削除結果
     * @return Deletion result
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String productId) {
        try {
            log.info("Deleting product: {}", productId);
            boolean deleted = productMasterService.deleteProduct(productId);
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success(null, "Product deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Product not found"));
            }
        } catch (IllegalArgumentException e) {
            log.warn("Validation error deleting product: {}", productId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting product: {}", productId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to delete product: " + e.getMessage()));
        }
    }
}
