package com.logistics.servlet;

import com.logistics.bean.ProductBean;
import com.logistics.bean.CustomerBean;
import com.logistics.bean.LocationBean;
import com.logistics.service.ProductService;
import com.logistics.service.CustomerService;
import com.logistics.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * 仪表板Servlet
 * 处理首页仪表板相关的HTTP请求
 */
public class DashboardServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
    private ProductService productService = new ProductService();
    private CustomerService customerService = new CustomerService();
    private LocationService locationService = new LocationService();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // 获取统计数据
            long totalProducts = productService.getTotalCount(null);
            long totalCustomers = customerService.getTotalCount(null);
            long totalLocations = locationService.getTotalCount(null);
            
            // 获取最近创建的商品（前5个）
            List<ProductBean> recentProducts = productService.getProductsWithPagination(
                0, 5, "CREATED_AT", "DESC", null);
            
            // 获取最近创建的客户（前5个）
            List<CustomerBean> recentCustomers = customerService.getCustomersWithPagination(
                0, 5, "CREATED_AT", "DESC", null);
            
            // 获取最近创建的库位（前5个）
            List<LocationBean> recentLocations = locationService.getLocationsWithPagination(
                0, 5, "CREATED_AT", "DESC", null);
            
            // 设置JSP属性
            request.setAttribute("totalProducts", totalProducts);
            request.setAttribute("totalCustomers", totalCustomers);
            request.setAttribute("totalLocations", totalLocations);
            request.setAttribute("recentProducts", recentProducts);
            request.setAttribute("recentCustomers", recentCustomers);
            request.setAttribute("recentLocations", recentLocations);
            
            // 转发到JSP
            request.getRequestDispatcher("/jsp/dashboard.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("获取仪表板数据失败", e);
            request.setAttribute("error", "获取仪表板数据失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}
