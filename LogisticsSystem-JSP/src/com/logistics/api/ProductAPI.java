package com.logistics.api;

import com.logistics.bean.ProductBean;
import com.logistics.service.ProductService;
import com.logistics.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品REST API
 * 提供商品相关的REST接口
 */
@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductAPI {
    private static final Logger logger = LoggerFactory.getLogger(ProductAPI.class);
    private ProductService productService = new ProductService();
    
    /**
     * 获取所有商品（分页）
     */
    @GET
    public Response getAllProducts(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sortBy") @DefaultValue("CREATED_AT") String sortBy,
            @QueryParam("sortDir") @DefaultValue("DESC") String sortDir,
            @QueryParam("keyword") String keyword) {
        
        try {
            logger.info("REST API - 获取商品列表 - page: {}, size: {}, sortBy: {}, sortDir: {}, keyword: {}", 
                       page, size, sortBy, sortDir, keyword);
            
            List<ProductBean> products = productService.getProductsWithPagination(
                page, size, sortBy, sortDir, keyword);
            long totalCount = productService.getTotalCount(keyword);
            int totalPages = productService.getTotalPages(totalCount, size);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", products);
            response.put("page", page);
            response.put("size", size);
            response.put("totalPages", totalPages);
            response.put("totalElements", totalCount);
            
            return Response.ok(JsonUtil.toJson(response)).build();
            
        } catch (Exception e) {
            logger.error("REST API - 获取商品列表失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 根据ID获取商品
     */
    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") String productId) {
        try {
            logger.info("REST API - 获取商品详情 - ID: {}", productId);
            
            ProductBean product = productService.getProductById(productId);
            
            if (product != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Success");
                response.put("data", product);
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 404);
                errorResponse.put("message", "Product not found");
                
                return Response.status(404).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 获取商品详情失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 创建商品
     */
    @POST
    public Response createProduct(ProductBean product) {
        try {
            logger.info("REST API - 创建商品 - 名称: {}", product.getProductName());
            
            boolean success = productService.createProduct(product);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 201);
                response.put("message", "Product created successfully");
                response.put("data", product);
                
                return Response.status(201).entity(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to create product");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 创建商品失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 更新商品
     */
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") String productId, ProductBean product) {
        try {
            logger.info("REST API - 更新商品 - ID: {}", productId);
            
            product.setProductId(productId);
            boolean success = productService.updateProduct(product);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Product updated successfully");
                response.put("data", product);
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to update product");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 更新商品失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 删除商品
     */
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") String productId) {
        try {
            logger.info("REST API - 删除商品 - ID: {}", productId);
            
            boolean success = productService.deleteProduct(productId);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Product deleted successfully");
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to delete product");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 删除商品失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
}
