package com.logistics.controller;

import com.logistics.controller.dto.ApiResponse;
import com.logistics.entity.CustomerMaster;
import com.logistics.service.CustomerMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 顧客マスタコントローラー
 * Customer Master Controller
 * 
 * 顧客管理のREST APIエンドポイント
 * REST API endpoints for customer management
 */
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class CustomerMasterController {
    
    private final CustomerMasterService customerMasterService;
    
    /**
     * 全顧客を取得
     * Get all customers
     * 
     * @return 顧客リスト
     * @return List of customers
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerMaster>>> getAllCustomers() {
        try {
            log.info("Getting all customers");
            List<CustomerMaster> customers = customerMasterService.getAllCustomers();
            return ResponseEntity.ok(ApiResponse.success(customers, "Customers retrieved successfully"));
        } catch (Exception e) {
            log.error("Error getting all customers", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve customers: " + e.getMessage()));
        }
    }
    
    /**
     * 顧客IDで顧客を取得
     * Get customer by customer ID
     * 
     * @param customerId 顧客ID
     * @return 顧客情報
     * @return Customer information
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerMaster>> getCustomerById(@PathVariable String customerId) {
        try {
            log.info("Getting customer by ID: {}", customerId);
            CustomerMaster customer = customerMasterService.getCustomerById(customerId);
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Customer not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(customer, "Customer retrieved successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid customer ID: {}", customerId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting customer by ID: {}", customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve customer: " + e.getMessage()));
        }
    }
    
    /**
     * 顧客名で検索
     * Search customers by name
     * 
     * @param name 顧客名
     * @return 顧客リスト
     * @return List of customers
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<CustomerMaster>>> searchCustomersByName(@RequestParam String name) {
        try {
            log.info("Searching customers by name: {}", name);
            List<CustomerMaster> customers = customerMasterService.searchCustomersByName(name);
            return ResponseEntity.ok(ApiResponse.success(customers, "Customers searched successfully"));
        } catch (Exception e) {
            log.error("Error searching customers by name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to search customers: " + e.getMessage()));
        }
    }
    
    /**
     * 顧客を新規登録
     * Create new customer
     * 
     * @param customer 顧客情報
     * @return 登録された顧客情報
     * @return Created customer information
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CustomerMaster>> createCustomer(@RequestBody CustomerMaster customer) {
        try {
            log.info("Creating new customer: {}", customer.getCustomerName());
            CustomerMaster createdCustomer = customerMasterService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdCustomer, "Customer created successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating customer", e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating customer", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to create customer: " + e.getMessage()));
        }
    }
    
    /**
     * 顧客情報を更新
     * Update customer information
     * 
     * @param customerId 顧客ID
     * @param customer 顧客情報
     * @return 更新された顧客情報
     * @return Updated customer information
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<ApiResponse<CustomerMaster>> updateCustomer(
            @PathVariable String customerId, 
            @RequestBody CustomerMaster customer) {
        try {
            log.info("Updating customer: {}", customerId);
            customer.setCustomerId(customerId); // パス変数からIDを設定
            CustomerMaster updatedCustomer = customerMasterService.updateCustomer(customer);
            return ResponseEntity.ok(ApiResponse.success(updatedCustomer, "Customer updated successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Validation error updating customer: {}", customerId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating customer: {}", customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to update customer: " + e.getMessage()));
        }
    }
    
    /**
     * 顧客を削除
     * Delete customer
     * 
     * @param customerId 顧客ID
     * @return 削除結果
     * @return Deletion result
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable String customerId) {
        try {
            log.info("Deleting customer: {}", customerId);
            boolean deleted = customerMasterService.deleteCustomer(customerId);
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success(null, "Customer deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Customer not found"));
            }
        } catch (IllegalArgumentException e) {
            log.warn("Validation error deleting customer: {}", customerId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting customer: {}", customerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to delete customer: " + e.getMessage()));
        }
    }
}

