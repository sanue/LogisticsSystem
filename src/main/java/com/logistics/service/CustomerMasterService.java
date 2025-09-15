package com.logistics.service;

import com.logistics.entity.CustomerMaster;

import java.util.List;

/**
 * 顧客マスタサービスインターフェース
 * Customer Master Service Interface
 * 
 * 顧客管理のビジネスロジックを定義
 * Defines business logic for customer management
 */
public interface CustomerMasterService {
    
    /**
     * 全顧客を取得
     * Get all customers
     * 
     * @return 顧客リスト
     * @return List of customers
     */
    List<CustomerMaster> getAllCustomers();
    
    /**
     * 顧客IDで顧客を取得
     * Get customer by customer ID
     * 
     * @param customerId 顧客ID
     * @return 顧客情報
     * @return Customer information
     */
    CustomerMaster getCustomerById(String customerId);
    
    /**
     * 顧客名で検索
     * Search by customer name
     * 
     * @param customerName 顧客名
     * @return 顧客リスト
     * @return List of customers
     */
    List<CustomerMaster> searchCustomersByName(String customerName);
    
    /**
     * 顧客を新規登録
     * Create new customer
     * 
     * @param customer 顧客情報
     * @return 登録された顧客情報
     * @return Created customer information
     */
    CustomerMaster createCustomer(CustomerMaster customer);
    
    /**
     * 顧客情報を更新
     * Update customer information
     * 
     * @param customer 顧客情報
     * @return 更新された顧客情報
     * @return Updated customer information
     */
    CustomerMaster updateCustomer(CustomerMaster customer);
    
    /**
     * 顧客を削除
     * Delete customer
     * 
     * @param customerId 顧客ID
     * @return 削除成功の場合true
     * @return true if deletion successful
     */
    boolean deleteCustomer(String customerId);
    
    /**
     * 顧客の存在確認
     * Check if customer exists
     * 
     * @param customerId 顧客ID
     * @return 存在する場合true
     * @return true if exists
     */
    boolean existsCustomer(String customerId);
}
