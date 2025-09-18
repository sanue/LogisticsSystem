package com.logistics.service;

import com.logistics.bean.LocationBean;
import com.logistics.dao.LocationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 库位业务逻辑服务类
 * 处理库位相关的业务逻辑
 */
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    private LocationDAO locationDAO = new LocationDAO();
    
    /**
     * 获取库位分页数据
     * @param page 页码
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @param keyword 搜索关键词
     * @return 库位列表
     */
    public List<LocationBean> getLocationsWithPagination(int page, int size, String sortBy, 
                                                        String sortDir, String keyword) {
        logger.info("获取库位分页数据 - page: {}, size: {}, sortBy: {}, sortDir: {}, keyword: {}", 
                   page, size, sortBy, sortDir, keyword);
        
        return locationDAO.findWithPagination(page, size, sortBy, sortDir, keyword);
    }
    
    /**
     * 获取库位总数
     * @param keyword 搜索关键词
     * @return 库位总数
     */
    public long getTotalCount(String keyword) {
        return locationDAO.countWithPagination(keyword);
    }
    
    /**
     * 计算总页数
     * @param totalCount 总记录数
     * @param size 每页大小
     * @return 总页数
     */
    public int getTotalPages(long totalCount, int size) {
        return (int) Math.ceil((double) totalCount / size);
    }
    
    /**
     * 根据ID获取库位
     * @param locationId 库位ID
     * @return 库位对象
     */
    public LocationBean getLocationById(String locationId) {
        logger.info("根据ID获取库位: {}", locationId);
        return locationDAO.findById(locationId);
    }
    
    /**
     * 创建库位
     * @param location 库位对象
     * @return 是否成功
     */
    public boolean createLocation(LocationBean location) {
        logger.info("创建库位: {}", location.getLocationId());
        return locationDAO.insert(location);
    }
    
    /**
     * 更新库位
     * @param location 库位对象
     * @return 是否成功
     */
    public boolean updateLocation(LocationBean location) {
        logger.info("更新库位: {}", location.getLocationId());
        return locationDAO.update(location);
    }
    
    /**
     * 删除库位
     * @param locationId 库位ID
     * @return 是否成功
     */
    public boolean deleteLocation(String locationId) {
        logger.info("删除库位: {}", locationId);
        return locationDAO.deleteById(locationId);
    }
}
