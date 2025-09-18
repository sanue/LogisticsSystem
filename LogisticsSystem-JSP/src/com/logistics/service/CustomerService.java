package com.logistics.service;

import com.logistics.bean.CustomerBean;
import com.logistics.dao.CustomerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 客户业务逻辑服务类
 * 处理客户相关的业务逻辑
 */
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private CustomerDAO customerDAO = new CustomerDAO();
    
    /**
     * 获取客户分页数据
     * @param page 页码
     * @param size 每页大小
     * @param sortBy 排序字段
     * @param sortDir 排序方向
     * @param keyword 搜索关键词
     * @return 客户列表
     */
    public List<CustomerBean> getCustomersWithPagination(int page, int size, String sortBy, 
                                                        String sortDir, String keyword) {
        logger.info("获取客户分页数据 - page: {}, size: {}, sortBy: {}, sortDir: {}, keyword: {}", 
                   page, size, sortBy, sortDir, keyword);
        
        return customerDAO.findWithPagination(page, size, sortBy, sortDir, keyword);
    }
    
    /**
     * 获取客户总数
     * @param keyword 搜索关键词
     * @return 客户总数
     */
    public long getTotalCount(String keyword) {
        return customerDAO.countWithPagination(keyword);
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
     * 根据ID获取客户
     * @param customerId 客户ID
     * @return 客户对象
     */
    public CustomerBean getCustomerById(String customerId) {
        logger.info("根据ID获取客户: {}", customerId);
        return customerDAO.findById(customerId);
    }
    
    /**
     * 创建客户
     * @param customer 客户对象
     * @return 是否成功
     */
    public boolean createCustomer(CustomerBean customer) {
        logger.info("创建客户: {}", customer.getCustomerName());
        return customerDAO.insert(customer);
    }
    
    /**
     * 更新客户
     * @param customer 客户对象
     * @return 是否成功
     */
    public boolean updateCustomer(CustomerBean customer) {
        logger.info("更新客户: {}", customer.getCustomerId());
        return customerDAO.update(customer);
    }
    
    /**
     * 删除客户
     * @param customerId 客户ID
     * @return 是否成功
     */
    public boolean deleteCustomer(String customerId) {
        logger.info("删除客户: {}", customerId);
        return customerDAO.deleteById(customerId);
    }
}
