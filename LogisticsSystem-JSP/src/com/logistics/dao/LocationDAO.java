package com.logistics.dao;

import com.logistics.bean.LocationBean;
import com.logistics.util.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 库位数据访问对象
 * 负责与数据库交互，执行库位相关的SQL操作
 */
public class LocationDAO {
    private static final Logger logger = LoggerFactory.getLogger(LocationDAO.class);
    
    /**
     * 分页查询库位列表
     * @param page 页码（从0开始）
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @param keyword 搜索关键词
     * @return 库位列表
     */
    public List<LocationBean> findWithPagination(int page, int size, String sortBy, 
                                               String sortDir, String keyword) {
        List<LocationBean> locations = new ArrayList<>();
        String sql = "SELECT * FROM LOCATION_MASTER " +
                    "WHERE (WAREHOUSE_CODE LIKE ? OR LOCATION_ID LIKE ?) " +
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
                LocationBean location = new LocationBean();
                location.setLocationId(rs.getString("LOCATION_ID"));
                location.setWarehouseCode(rs.getString("WAREHOUSE_CODE"));
                location.setZone(rs.getString("ZONE"));
                location.setRack(rs.getString("RACK"));
                location.setLevelNo(rs.getString("LEVEL_NO"));
                location.setPosition(rs.getString("POSITION"));
                location.setMaxCapacity(rs.getInt("MAX_CAPACITY"));
                location.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                locations.add(location);
            }
            
            logger.info("查询到 {} 条库位记录", locations.size());
        } catch (SQLException e) {
            logger.error("查询库位数据失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return locations;
    }
    
    /**
     * 统计库位总数
     * @param keyword 搜索关键词
     * @return 库位总数
     */
    public long countWithPagination(String keyword) {
        String sql = "SELECT COUNT(*) FROM LOCATION_MASTER " +
                    "WHERE (WAREHOUSE_CODE LIKE ? OR LOCATION_ID LIKE ?)";
        
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
            logger.error("统计库位数量失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return 0;
    }
    
    /**
     * 根据ID查询库位
     * @param locationId 库位ID
     * @return 库位对象
     */
    public LocationBean findById(String locationId) {
        String sql = "SELECT * FROM LOCATION_MASTER WHERE LOCATION_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, locationId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                LocationBean location = new LocationBean();
                location.setLocationId(rs.getString("LOCATION_ID"));
                location.setWarehouseCode(rs.getString("WAREHOUSE_CODE"));
                location.setZone(rs.getString("ZONE"));
                location.setRack(rs.getString("RACK"));
                location.setLevelNo(rs.getString("LEVEL_NO"));
                location.setPosition(rs.getString("POSITION"));
                location.setMaxCapacity(rs.getInt("MAX_CAPACITY"));
                location.setCreatedAt(rs.getTimestamp("CREATED_AT").toLocalDateTime());
                return location;
            }
        } catch (SQLException e) {
            logger.error("根据ID查询库位失败", e);
            throw new RuntimeException("数据库查询失败", e);
        }
        
        return null;
    }
    
    /**
     * 插入新库位
     * @param location 库位对象
     * @return 是否成功
     */
    public boolean insert(LocationBean location) {
        String sql = "INSERT INTO LOCATION_MASTER (LOCATION_ID, WAREHOUSE_CODE, ZONE, " +
                    "RACK, LEVEL_NO, POSITION, MAX_CAPACITY, CREATED_AT) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, location.getLocationId());
            stmt.setString(2, location.getWarehouseCode());
            stmt.setString(3, location.getZone());
            stmt.setString(4, location.getRack());
            stmt.setString(5, location.getLevelNo());
            stmt.setString(6, location.getPosition());
            stmt.setInt(7, location.getMaxCapacity());
            stmt.setTimestamp(8, Timestamp.valueOf(location.getCreatedAt()));
            
            int result = stmt.executeUpdate();
            logger.info("插入库位记录: {}", location.getLocationId());
            return result > 0;
        } catch (SQLException e) {
            logger.error("插入库位失败", e);
            throw new RuntimeException("数据库插入失败", e);
        }
    }
    
    /**
     * 更新库位信息
     * @param location 库位对象
     * @return 是否成功
     */
    public boolean update(LocationBean location) {
        String sql = "UPDATE LOCATION_MASTER SET WAREHOUSE_CODE = ?, ZONE = ?, " +
                    "RACK = ?, LEVEL_NO = ?, POSITION = ?, MAX_CAPACITY = ? " +
                    "WHERE LOCATION_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, location.getWarehouseCode());
            stmt.setString(2, location.getZone());
            stmt.setString(3, location.getRack());
            stmt.setString(4, location.getLevelNo());
            stmt.setString(5, location.getPosition());
            stmt.setInt(6, location.getMaxCapacity());
            stmt.setString(7, location.getLocationId());
            
            int result = stmt.executeUpdate();
            logger.info("更新库位记录: {}", location.getLocationId());
            return result > 0;
        } catch (SQLException e) {
            logger.error("更新库位失败", e);
            throw new RuntimeException("数据库更新失败", e);
        }
    }
    
    /**
     * 根据ID删除库位
     * @param locationId 库位ID
     * @return 是否成功
     */
    public boolean deleteById(String locationId) {
        String sql = "DELETE FROM LOCATION_MASTER WHERE LOCATION_ID = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, locationId);
            int result = stmt.executeUpdate();
            logger.info("删除库位记录: {}", locationId);
            return result > 0;
        } catch (SQLException e) {
            logger.error("删除库位失败", e);
            throw new RuntimeException("数据库删除失败", e);
        }
    }
}
