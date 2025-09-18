package com.logistics.servlet;

import com.logistics.bean.LocationBean;
import com.logistics.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * 库位管理Servlet
 * 处理库位相关的HTTP请求
 */
public class LocationServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LocationServlet.class);
    private LocationService locationService = new LocationService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("detail".equals(action)) {
            showDetail(request, response);
        } else if ("edit".equals(action)) {
            showEdit(request, response);
        } else {
            showList(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("create".equals(action)) {
            createLocation(request, response);
        } else if ("update".equals(action)) {
            updateLocation(request, response);
        } else if ("delete".equals(action)) {
            deleteLocation(request, response);
        } else {
            showList(request, response);
        }
    }
    
    /**
     * 显示库位列表
     */
    private void showList(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 获取参数
        int page = Integer.parseInt(request.getParameter("page") != null ? 
                request.getParameter("page") : "0");
        int size = Integer.parseInt(request.getParameter("size") != null ? 
                request.getParameter("size") : "10");
        String sortBy = request.getParameter("sortBy") != null ? 
                request.getParameter("sortBy") : "CREATED_AT";
        String sortDir = request.getParameter("sortDir") != null ? 
                request.getParameter("sortDir") : "DESC";
        String keyword = request.getParameter("keyword");
        
        try {
            // 调用业务逻辑
            List<LocationBean> locations = locationService.getLocationsWithPagination(
                page, size, sortBy, sortDir, keyword);
            long totalCount = locationService.getTotalCount(keyword);
            int totalPages = locationService.getTotalPages(totalCount, size);
            
            // 设置JSP属性
            request.setAttribute("locations", locations);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", size);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("sortDir", sortDir);
            request.setAttribute("keyword", keyword);
            
            // 转发到JSP
            request.getRequestDispatcher("/jsp/location/list.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("获取库位列表失败", e);
            request.setAttribute("error", "获取库位数据失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 显示库位详情
     */
    private void showDetail(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String locationId = request.getParameter("id");
        
        try {
            LocationBean location = locationService.getLocationById(locationId);
            if (location != null) {
                request.setAttribute("location", location);
                request.getRequestDispatcher("/jsp/location/detail.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "库位不存在");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("获取库位详情失败", e);
            request.setAttribute("error", "获取库位详情失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 显示库位编辑页面
     */
    private void showEdit(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String locationId = request.getParameter("id");
        
        try {
            LocationBean location = locationService.getLocationById(locationId);
            if (location != null) {
                request.setAttribute("location", location);
                request.getRequestDispatcher("/jsp/location/edit.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "库位不存在");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("获取库位编辑页面失败", e);
            request.setAttribute("error", "获取库位编辑页面失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 创建库位
     */
    private void createLocation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            LocationBean location = new LocationBean();
            location.setLocationId(request.getParameter("locationId"));
            location.setWarehouseCode(request.getParameter("warehouseCode"));
            location.setZone(request.getParameter("zone"));
            location.setRack(request.getParameter("rack"));
            location.setLevelNo(request.getParameter("levelNo"));
            location.setPosition(request.getParameter("position"));
            location.setMaxCapacity(Integer.parseInt(request.getParameter("maxCapacity")));
            
            boolean success = locationService.createLocation(location);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/location?message=create_success");
            } else {
                request.setAttribute("error", "创建库位失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("创建库位失败", e);
            request.setAttribute("error", "创建库位失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 更新库位
     */
    private void updateLocation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            LocationBean location = new LocationBean();
            location.setLocationId(request.getParameter("locationId"));
            location.setWarehouseCode(request.getParameter("warehouseCode"));
            location.setZone(request.getParameter("zone"));
            location.setRack(request.getParameter("rack"));
            location.setLevelNo(request.getParameter("levelNo"));
            location.setPosition(request.getParameter("position"));
            location.setMaxCapacity(Integer.parseInt(request.getParameter("maxCapacity")));
            
            boolean success = locationService.updateLocation(location);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/location?message=update_success");
            } else {
                request.setAttribute("error", "更新库位失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("更新库位失败", e);
            request.setAttribute("error", "更新库位失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 删除库位
     */
    private void deleteLocation(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String locationId = request.getParameter("id");
        
        try {
            boolean success = locationService.deleteLocation(locationId);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/location?message=delete_success");
            } else {
                request.setAttribute("error", "删除库位失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("删除库位失败", e);
            request.setAttribute("error", "删除库位失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}
