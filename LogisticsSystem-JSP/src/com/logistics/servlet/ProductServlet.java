package com.logistics.servlet;

import com.logistics.bean.ProductBean;
import com.logistics.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

/**
 * 商品管理Servlet
 * 处理商品相关的HTTP请求
 */
public class ProductServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(ProductServlet.class);
    private ProductService productService = new ProductService();
    
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
            createProduct(request, response);
        } else if ("update".equals(action)) {
            updateProduct(request, response);
        } else if ("delete".equals(action)) {
            deleteProduct(request, response);
        } else {
            showList(request, response);
        }
    }
    
    /**
     * 显示商品列表
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
            List<ProductBean> products = productService.getProductsWithPagination(
                page, size, sortBy, sortDir, keyword);
            long totalCount = productService.getTotalCount(keyword);
            int totalPages = productService.getTotalPages(totalCount, size);
            
            // 设置JSP属性
            request.setAttribute("products", products);
            request.setAttribute("currentPage", page);
            request.setAttribute("pageSize", size);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("sortBy", sortBy);
            request.setAttribute("sortDir", sortDir);
            request.setAttribute("keyword", keyword);
            
            // 转发到JSP
            request.getRequestDispatcher("/jsp/product/list.jsp").forward(request, response);
            
        } catch (Exception e) {
            logger.error("获取商品列表失败", e);
            request.setAttribute("error", "获取商品数据失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 显示商品详情
     */
    private void showDetail(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String productId = request.getParameter("id");
        
        try {
            ProductBean product = productService.getProductById(productId);
            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/jsp/product/detail.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "商品不存在");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("获取商品详情失败", e);
            request.setAttribute("error", "获取商品详情失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 显示商品编辑页面
     */
    private void showEdit(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String productId = request.getParameter("id");
        
        try {
            ProductBean product = productService.getProductById(productId);
            if (product != null) {
                request.setAttribute("product", product);
                request.getRequestDispatcher("/jsp/product/edit.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "商品不存在");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("获取商品编辑页面失败", e);
            request.setAttribute("error", "获取商品编辑页面失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 创建商品
     */
    private void createProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            ProductBean product = new ProductBean();
            product.setProductId(request.getParameter("productId"));
            product.setProductName(request.getParameter("productName"));
            product.setSpecification(request.getParameter("specification"));
            product.setUnit(request.getParameter("unit"));
            product.setSafetyStock(Integer.parseInt(request.getParameter("safetyStock")));
            
            boolean success = productService.createProduct(product);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/product?message=create_success");
            } else {
                request.setAttribute("error", "创建商品失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("创建商品失败", e);
            request.setAttribute("error", "创建商品失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 更新商品
     */
    private void updateProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            ProductBean product = new ProductBean();
            product.setProductId(request.getParameter("productId"));
            product.setProductName(request.getParameter("productName"));
            product.setSpecification(request.getParameter("specification"));
            product.setUnit(request.getParameter("unit"));
            product.setSafetyStock(Integer.parseInt(request.getParameter("safetyStock")));
            
            boolean success = productService.updateProduct(product);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/product?message=update_success");
            } else {
                request.setAttribute("error", "更新商品失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("更新商品失败", e);
            request.setAttribute("error", "更新商品失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
    
    /**
     * 删除商品
     */
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String productId = request.getParameter("id");
        
        try {
            boolean success = productService.deleteProduct(productId);
            
            if (success) {
                response.sendRedirect(request.getContextPath() + "/product?message=delete_success");
            } else {
                request.setAttribute("error", "删除商品失败");
                request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            logger.error("删除商品失败", e);
            request.setAttribute("error", "删除商品失败: " + e.getMessage());
            request.getRequestDispatcher("/jsp/common/error.jsp").forward(request, response);
        }
    }
}
