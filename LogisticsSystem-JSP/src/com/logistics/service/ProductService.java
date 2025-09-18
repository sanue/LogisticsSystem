package com.logistics.service;

import com.logistics.bean.ProductBean;
import com.logistics.dao.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 商品业务逻辑服务类
 * 处理商品相关的业务逻辑
 */
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private ProductDAO productDAO = new ProductDAO();
    
    /**
     * 获取商品分页数据
     * @param page 页码
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @param keyword 搜索关键词
     * @return 商品列表
     */
    public List<ProductBean> getProductsWithPagination(int page, int size, String sortBy, 
                                                      String sortDir, String keyword) {
        logger.info("获取商品分页数据 - page: {}, size: {}, sortBy: {}, sortDir: {}, keyword: {}", 
                   page, size, sortBy, sortDir, keyword);
        
        return productDAO.findWithPagination(page, size, sortBy, sortDir, keyword);
    }
    
    /**
     * 获取商品总数
     * @param keyword 搜索关键词
     * @return 商品总数
     */
    public long getTotalCount(String keyword) {
        return productDAO.countWithPagination(keyword);
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
     * 根据ID获取商品
     * @param productId 商品ID
     * @return 商品对象
     */
    public ProductBean getProductById(String productId) {
        logger.info("根据ID获取商品: {}", productId);
        return productDAO.findById(productId);
    }
    
    /**
     * 创建商品
     * @param product 商品对象
     * @return 是否成功
     */
    public boolean createProduct(ProductBean product) {
        logger.info("创建商品: {}", product.getProductName());
        return productDAO.insert(product);
    }
    
    /**
     * 更新商品
     * @param product 商品对象
     * @return 是否成功
     */
    public boolean updateProduct(ProductBean product) {
        logger.info("更新商品: {}", product.getProductId());
        return productDAO.update(product);
    }
    
    /**
     * 删除商品
     * @param productId 商品ID
     * @return 是否成功
     */
    public boolean deleteProduct(String productId) {
        logger.info("删除商品: {}", productId);
        return productDAO.deleteById(productId);
    }
}
