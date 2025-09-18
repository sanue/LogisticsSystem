package com.logistics.dao;

import com.logistics.bean.CustomerBean;
import com.logistics.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户数据访问对象
 * 负责与数据库交互，执行客户相关的SQL操作
 */
public class CustomerDAO {
    private static final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);
    
    /**
     * 分页查询客户列表
     * @param page 页码（从0开始）
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @param keyword 搜索关键词
     * @return 客户列表
     */
    public List<CustomerBean> findWithPagination(int page, int size, String sortBy, 
                                               String sortDir, String keyword) {
        List<CustomerBean> customers = new ArrayList<>();
        String sql = "SELECT * FROM CUSTOMER_MASTER " +
                    "WHERE (CUSTOMER_NAME LIKE ? OR CUSTOMER_ID LIKE ?) " +
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
                CustomerBean customer = new CustomerBean();
                customer.setCustomerId(rs.getString("CUSTOMER_ID"));
                customer.setCustomerName(rs.getString("CUSTOMER_NAME"));
                customer.setAddress(rs.getString("ADDRESS"));
                customer.setPhone(rs.getString("PHONE"));
                customer.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                customer.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                customers.add(customer);
            }
            
            logger.info("查询到 {} 条客户记录", customers.size());
        } catch (SQLException e) {
            logger.error("查询客户数据失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return customers;
    }
    
    /**
     * 统计客户总数
     * @param keyword 搜索关键词
     * @return 客户总数
     */
    public long countWithPagination(String keyword) {
        String sql = "SELECT COUNT(*) FROM CUSTOMER_MASTER " +
                    "WHERE (CUSTOMER_NAME LIKE ? OR CUSTOMER_ID LIKE ?)";
        
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
            logger.error("统计客户数量失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return 0;
    }
    
    /**
     * 根据ID查询客户
     * @param customerId 客户ID
     * @return 客户对象
     */
    public CustomerBean findById(String customerId) {
        String sql = "SELECT * FROM CUSTOMER_MASTER WHERE CUSTOMER_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                CustomerBean customer = new CustomerBean();
                customer.setCustomerId(rs.getString("CUSTOMER_ID"));
                customer.setCustomerName(rs.getString("CUSTOMER_NAME"));
                customer.setAddress(rs.getString("ADDRESS"));
                customer.setPhone(rs.getString("PHONE"));
                customer.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                customer.setUpdatedAt(rs.getTimestamp("UPDATED_AT").toLocalDateTime());
                return customer;
            }
        } catch (SQLException e) {
            logger.error("根据ID查询客户失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return null;
    }
    
    /**
     * 插入新客户
     * @param customer 客户对象
     * @return 是否成功
     */
    public boolean insert(CustomerBean customer) {
        String sql = "INSERT INTO CUSTOMER_MASTER (CUSTOMER_ID, CUSTOMER_NAME, ADDRESS, " +
                    "PHONE, CREATED_AT, UPDATED_AT) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.getCustomerId());
            stmt.setString(2, customer.getCustomerName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhone());
            stmt.setTimestamp(5, Timestamp.valueOf(customer.getCreatedAt()));
            stmt.setTimestamp(6, Timestamp.valueOf(customer.getUpdatedAt()));
            
            int result = stmt.executeUpdate();
            logger.info("插入客户记录: {}", customer.getCustomerId());
            return result > 0;
        } catch (SQLException e) {
            logger.error("插入客户失败", e);
            throw new RuntimeException("数据库插入失败", e);
        }
    }
    
    /**
     * 更新客户信息
     * @param customer 客户对象
     * @return 是否成功
     */
    public boolean update(CustomerBean customer) {
        String sql = "UPDATE CUSTOMER_MASTER SET CUSTOMER_NAME = ?, ADDRESS = ?, " +
                    "PHONE = ?, UPDATED_AT = ? WHERE CUSTOMER_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customer.getCustomerName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.setTimestamp(4, Timestamp.valueOf(customer.getUpdatedAt()));
            stmt.setString(5, customer.getCustomerId());
            
            int result = stmt.executeUpdate();
            logger.info("更新客户记录: {}", customer.getCustomerId());
            return result > 0;
        } catch (SQLException e) {
            logger.error("更新客户失败", e);
            throw new RuntimeException("数据库更新失败", e);
        }
    }
    
    /**
     * 根据ID删除客户
     * @param customerId 客户ID
     * @return 是否成功
     */
    public boolean deleteById(String customerId) {
        String sql = "DELETE FROM CUSTOMER_MASTER WHERE CUSTOMER_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, customerId);
            int result = stmt.executeUpdate();
            logger.info("删除客户记录: {}", customerId);
            return result > 0;
        } catch (SQLException e) {
            logger.error("删除客户失败", e);
            throw new RuntimeException("数据库删除失败", e);
        }
    }
}
