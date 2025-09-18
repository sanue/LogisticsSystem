package com.logistics.api;

import com.logistics.bean.CustomerBean;
import com.logistics.service.CustomerService;
import com.logistics.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户REST API
 * 提供客户相关的REST接口
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerAPI {
    private static final Logger logger = LoggerFactory.getLogger(CustomerAPI.class);
    private CustomerService customerService = new CustomerService();
    
    /**
     * 获取所有客户（分页）
     */
    @GET
    public Response getAllCustomers(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sortBy") @DefaultValue("CREATED_AT") String sortBy,
            @QueryParam("sortDir") @DefaultValue("DESC") String sortDir,
            @QueryParam("keyword") String keyword) {
        
        try {
            logger.info("REST API - 获取客户列表 - page: {}, size: {}, sortBy: {}, sortDir: {}, keyword: {}", 
                       page, size, sortBy, sortDir, keyword);
            
            List<CustomerBean> customers = customerService.getCustomersWithPagination(
                page, size, sortBy, sortDir, keyword);
            long totalCount = customerService.getTotalCount(keyword);
            int totalPages = customerService.getTotalPages(totalCount, size);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", customers);
            response.put("page", page);
            response.put("size", size);
            response.put("totalPages", totalPages);
            response.put("totalElements", totalCount);
            
            return Response.ok(JsonUtil.toJson(response)).build();
            
        } catch (Exception e) {
            logger.error("REST API - 获取客户列表失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 根据ID获取客户
     */
    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") String customerId) {
        try {
            logger.info("REST API - 获取客户详情 - ID: {}", customerId);
            
            CustomerBean customer = customerService.getCustomerById(customerId);
            
            if (customer != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Success");
                response.put("data", customer);
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 404);
                errorResponse.put("message", "Customer not found");
                
                return Response.status(404).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 获取客户详情失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 创建客户
     */
    @POST
    public Response createCustomer(CustomerBean customer) {
        try {
            logger.info("REST API - 创建客户 - 名称: {}", customer.getCustomerName());
            
            boolean success = customerService.createCustomer(customer);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 201);
                response.put("message", "Customer created successfully");
                response.put("data", customer);
                
                return Response.status(201).entity(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to create customer");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 创建客户失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 更新客户
     */
    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") String customerId, CustomerBean customer) {
        try {
            logger.info("REST API - 更新客户 - ID: {}", customerId);
            
            customer.setCustomerId(customerId);
            boolean success = customerService.updateCustomer(customer);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Customer updated successfully");
                response.put("data", customer);
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to update customer");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 更新客户失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 删除客户
     */
    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") String customerId) {
        try {
            logger.info("REST API - 删除客户 - ID: {}", customerId);
            
            boolean success = customerService.deleteCustomer(customerId);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Customer deleted successfully");
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to delete customer");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 删除客户失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
}
