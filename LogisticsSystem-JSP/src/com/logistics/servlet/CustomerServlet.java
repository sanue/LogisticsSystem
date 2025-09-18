package com.logistics.servlet;

import com.logistics.bean.CustomerBean;
import com.logistics.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * 客户管理Servlet
 * 处理客户相关的HTTP请求
 */
public class CustomerServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(CustomerServlet.class);
    private CustomerService customerService = new CustomerService();
    
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
            createCustomer(request, response);
        } else if ("update".equals(action)) {
            updateCustomer(request, response);
        } else if ("delete".equals(action)) {
            deleteCustomer(request, response);
        } else {
            showList(request, response);
        }
    }
    
    /**
     * 显示客户列表
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
            List<CustomerBean> customers = customerService.getCustomersWithPagination(
                page, size, sortBy, sortDir, keyword);
            long totalCount = customerService.getTotalCount(keyword);
            int totalPages = customerService.getTotalPages(totalCount, size);
            
            // 设置JSP属性
            request.setAttribute("customers", customers);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", size);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("sortDir", sortDir);
            request.setAttribute("keyword", keyword);
            
            // 转发到JSP
            request.getRequestDispatcher("/jsp/customer/list.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("获取客户列表失败", e);
            request.setAttribute("error", "获取客户数据失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 显示客户详情
     */
    private void showDetail(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String customerId = request.getParameter("id");
        
        try {
            CustomerBean customer = customerService.getCustomerById(customerId);
            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("/jsp/customer/detail.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "客户不存在");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("获取客户详情失败", e);
            request.setAttribute("error", "获取客户详情失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 显示客户编辑页面
     */
    private void showEdit(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String customerId = request.getParameter("id");
        
        try {
            CustomerBean customer = customerService.getCustomerById(customerId);
            if (customer != null) {
                request.setAttribute("customer", customer);
                request.getRequestDispatcher("/jsp/customer/edit.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "客户不存在");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("获取客户编辑页面失败", e);
            request.setAttribute("error", "获取客户编辑页面失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 创建客户
     */
    private void createCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            CustomerBean customer = new CustomerBean();
            customer.setCustomerId(request.getParameter("customerId"));
            customer.setCustomerName(request.getParameter("customerName"));
            customer.setAddress(request.getParameter("address"));
            customer.setPhone(request.getParameter("phone"));
            
            boolean success = customerService.createCustomer(customer);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/customer?message=create_success");
            } else {
                request.setAttribute("error", "创建客户失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("创建客户失败", e);
            request.setAttribute("error", "创建客户失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 更新客户
     */
    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            CustomerBean customer = new CustomerBean();
            customer.setCustomerId(request.getParameter("customerId"));
            customer.setCustomerName(request.getParameter("customerName"));
            customer.setAddress(request.getParameter("address"));
            customer.setPhone(request.getParameter("phone"));
            
            boolean success = customerService.updateCustomer(customer);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/customer?message=update_success");
            } else {
                request.setAttribute("error", "更新客户失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("更新客户失败", e);
            request.setAttribute("error", "更新客户失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 删除客户
     */
    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String customerId = request.getParameter("id");
        
        try {
            boolean success = customerService.deleteCustomer(customerId);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/customer?message=delete_success");
            } else {
                request.setAttribute("error", "删除客户失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("删除客户失败", e);
            request.setAttribute("error", "删除客户失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}
