package com.logistics.service.impl;

import com.logistics.entity.CustomerMaster;
import com.logistics.mapper.CustomerMasterMapper;
import com.logistics.service.CustomerMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 顧客マスタサービス実装クラス
 * Customer Master Service Implementation
 * 
 * 顧客管理のビジネスロジックを実装
 * Implements business logic for customer management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CustomerMasterServiceImpl implements CustomerMasterService {
    
    private final CustomerMasterMapper customerMasterMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<CustomerMaster> getAllCustomers() {
        log.info("Getting all customers");
        return customerMasterMapper.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public CustomerMaster getCustomerById(String customerId) {
        log.info("Getting customer by ID: {}", customerId);
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        return customerMasterMapper.findById(customerId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<CustomerMaster> searchCustomersByName(String customerName) {
        log.info("Searching customers by name: {}", customerName);
        if (customerName == null || customerName.trim().isEmpty()) {
            return getAllCustomers();
        }
        return customerMasterMapper.findByName(customerName);
    }
    
    @Override
    public CustomerMaster createCustomer(CustomerMaster customer) {
        log.info("Creating new customer: {}", customer.getCustomerName());
        
        // バリデーション
        validateCustomer(customer);
        
        // 顧客IDの重複チェック
        if (customerMasterMapper.existsById(customer.getCustomerId())) {
            throw new IllegalArgumentException("Customer with ID " + customer.getCustomerId() + " already exists");
        }
        
        // 作成日時を設定
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        
        int result = customerMasterMapper.insert(customer);
        if (result == 0) {
            throw new RuntimeException("Failed to create customer");
        }
        
        log.info("Customer created successfully: {}", customer.getCustomerId());
        return customer;
    }
    
    @Override
    public CustomerMaster updateCustomer(CustomerMaster customer) {
        log.info("Updating customer: {}", customer.getCustomerId());
        
        // バリデーション
        validateCustomer(customer);
        
        // 顧客の存在チェック
        if (!customerMasterMapper.existsById(customer.getCustomerId())) {
            throw new IllegalArgumentException("Customer with ID " + customer.getCustomerId() + " does not exist");
        }
        
        // 更新日時を設定
        customer.setUpdatedAt(LocalDateTime.now());
        
        int result = customerMasterMapper.update(customer);
        if (result == 0) {
            throw new RuntimeException("Failed to update customer");
        }
        
        log.info("Customer updated successfully: {}", customer.getCustomerId());
        return customer;
    }
    
    @Override
    public boolean deleteCustomer(String customerId) {
        log.info("Deleting customer: {}", customerId);
        
        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be null or empty");
        }
        
        // 顧客の存在チェック
        if (!customerMasterMapper.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist");
        }
        
        int result = customerMasterMapper.deleteById(customerId);
        boolean success = result > 0;
        
        if (success) {
            log.info("Customer deleted successfully: {}", customerId);
        } else {
            log.warn("Failed to delete customer: {}", customerId);
        }
        
        return success;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsCustomer(String customerId) {
        if (customerId == null || customerId.trim().isEmpty()) {
            return false;
        }
        return customerMasterMapper.existsById(customerId);
    }
    
    /**
     * 顧客情報のバリデーション
     * Validate customer information
     * 
     * @param customer 顧客情報
     */
    private void validateCustomer(CustomerMaster customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        if (customer.getCustomerId() == null || customer.getCustomerId().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        
        if (customer.getCustomerName() == null || customer.getCustomerName().trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name is required");
        }
    }
}
