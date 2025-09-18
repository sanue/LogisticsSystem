package com.logistics.dao;

import com.logistics.bean.ProductBean;
import com.logistics.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品数据访问对象
 * 负责与数据库交互，执行商品相关的SQL操作
 */
public class ProductDAO {
    private static final Logger logger = LoggerFactory.getLogger(ProductDAO.class);
    
    /**
     * 分页查询商品列表
     * @param page 页码（从0开始）
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @param keyword 搜索关键词
     * @return 商品列表
     */
    public List<ProductBean> findWithPagination(int page, int size, String sortBy, 
                                               String sortDir, String keyword) {
        List<ProductBean> products = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCT_MASTER " +
                    "WHERE (PRODUCT_NAME LIKE ? OR PRODUCT_ID LIKE ?) " +
                    "ORDER BY " + sortBy + " " + sortDir + " " +
                    "OFFSET ? * ? ROWS FETCH NEXT ? ROWS ONLY";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchKeyword = keyword != null ? "%" + keyword + "%" : "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setInt(3, page);
            stmt.setInt(4, size);
            stmt.setInt(5, size);
            
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProductBean product = new ProductBean();
                product.setProductId(rs.getString("PRODUCT_ID"));
                product.setProductName(rs.getString("PRODUCT_NAME"));
                product.setSpecification(rs.getString("SPECIFICATION"));
                product.setUnit(rs.getString("UNIT"));
                product.setSafetyStock(rs.getInt("SAFETY_STOCK"));
                product.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                products.add(product);
            }
            
            logger.info("查询到 {} 条商品记录", products.size());
        } catch (SQLException e) {
            logger.error("查询商品数据失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return products;
    }
    
    /**
     * 统计商品总数
     * @param keyword 搜索关键词
     * @return 商品总数
     */
    public long countWithPagination(String keyword) {
        String sql = "SELECT COUNT(*) FROM PRODUCT_MASTER " +
                    "WHERE (PRODUCT_NAME LIKE ? OR PRODUCT_ID LIKE ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchKeyword = keyword != null ? "%" + keyword + "%" : "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            logger.error("统计商品数量失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return 0;
    }
    
    /**
     * 根据ID查询商品
     * @param productId 商品ID
     * @return 商品对象
     */
    public ProductBean findById(String productId) {
        String sql = "SELECT * FROM PRODUCT_MASTER WHERE PRODUCT_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                ProductBean product = new ProductBean();
                product.setProductId(rs.getString("PRODUCT_ID"));
                product.setProductName(rs.getString("PRODUCT_NAME"));
                product.setSpecification(rs.getString("SPECIFICATION"));
                product.setUnit(rs.getString("UNIT"));
                product.setSafetyStock(rs.getInt("SAFETY_STOCK"));
                product.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                product.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return product;
            }
        } catch (SQLException e) {
            logger.error("根据ID查询商品失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return null;
    }
    
    /**
     * 插入新商品
     * @param product 商品对象
     * @return 是否成功
     */
    public boolean insert(ProductBean product) {
        String sql = "INSERT INTO PRODUCT_MASTER (PRODUCT_ID, PRODUCT_NAME, SPECIFICATION, " +
                    "UNIT, SAFETY_STOCK, CREATED_AT, UPDATED_AT) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setString(3, product.getSpecification());
            stmt.setString(4, product.getUnit());
            stmt.setInt(5, product.getSafetyStock());
            stmt.setTimestamp(6, Timestamp.valueOf(product.getCreatedAt()));
            stmt.setTimestamp(7, Timestamp.valueOf(product.getUpdatedAt()));
            
            int result = stmt.executeUpdate();
            logger.info("插入商品记录: {}", product.getProductId());
            return result > 0;
        } catch (SQLException e) {
            logger.error("插入商品失败", e);
            throw new RuntimeException("数据库插入失败", e);
        }
    }
    
    /**
     * 更新商品信息
     * @param product 商品对象
     * @return 是否成功
     */
    public boolean update(ProductBean product) {
        String sql = "UPDATE PRODUCT_MASTER SET PRODUCT_NAME = ?, SPECIFICATION = ?, " +
                    "UNIT = ?, SAFETY_STOCK = ?, UPDATED_AT = ? WHERE PRODUCT_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product.getProductName());
            stmt.setString(2, product.getSpecification());
            stmt.setString(3, product.getUnit());
            stmt.setInt(4, product.getSafetyStock());
            stmt.setTimestamp(5, Timestamp.valueOf(product.getUpdatedAt()));
            stmt.setString(6, product.getProductId());
            
            int result = stmt.executeUpdate();
            logger.info("更新商品记录: {}", product.getProductId());
            return result > 0;
        } catch (SQLException e) {
            logger.error("更新商品失败", e);
            throw new RuntimeException("数据库更新失败", e);
        }
    }
    
    /**
     * 根据ID删除商品
     * @param productId 商品ID
     * @return 是否成功
     */
    public boolean deleteById(String productId) {
        String sql = "DELETE FROM PRODUCT_MASTER WHERE PRODUCT_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, productId);
            int result = stmt.executeUpdate();
            logger.info("删除商品记录: {}", productId);
            return result > 0;
        } catch (SQLException e) {
            logger.error("删除商品失败", e);
            throw new RuntimeException("数据库删除失败", e);
        }
    }
}
