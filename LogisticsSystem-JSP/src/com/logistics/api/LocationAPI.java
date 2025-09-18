package com.logistics.api;

import com.logistics.bean.LocationBean;
import com.logistics.service.LocationService;
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
 * 库位REST API
 * 提供库位相关的REST接口
 */
@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationAPI {
    private static final Logger logger = LoggerFactory.getLogger(LocationAPI.class);
    private LocationService locationService = new LocationService();
    
    /**
     * 获取所有库位（分页）
     */
    @GET
    public Response getAllLocations(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("10") int size,
            @QueryParam("sortBy") @DefaultValue("CREATED_AT") String sortBy,
            @QueryParam("sortDir") @DefaultValue("DESC") String sortDir,
            @QueryParam("keyword") String keyword) {
        
        try {
            logger.info("REST API - 获取库位列表 - page: {}, size: {}, sortBy: {}, sortDir: {}, keyword: {}", 
                       page, size, sortBy, sortDir, keyword);
            
            List<LocationBean> locations = locationService.getLocationsWithPagination(
                page, size, sortBy, sortDir, keyword);
            long totalCount = locationService.getTotalCount(keyword);
            int totalPages = locationService.getTotalPages(totalCount, size);
            
            Map<String, Object> response = new HashMap<>();
            response.put("code", 200);
            response.put("message", "Success");
            response.put("data", locations);
            response.put("page", page);
            response.put("size", size);
            response.put("totalPages", totalPages);
            response.put("totalElements", totalCount);
            
            return Response.ok(JsonUtil.toJson(response)).build();
            
        } catch (Exception e) {
            logger.error("REST API - 获取库位列表失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 根据ID获取库位
     */
    @GET
    @Path("/{id}")
    public Response getLocationById(@PathParam("id") String locationId) {
        try {
            logger.info("REST API - 获取库位详情 - ID: {}", locationId);
            
            LocationBean location = locationService.getLocationById(locationId);
            
            if (location != null) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Success");
                response.put("data", location);
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 404);
                errorResponse.put("message", "Location not found");
                
                return Response.status(404).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 获取库位详情失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 创建库位
     */
    @POST
    public Response createLocation(LocationBean location) {
        try {
            logger.info("REST API - 创建库位 - ID: {}", location.getLocationId());
            
            boolean success = locationService.createLocation(location);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 201);
                response.put("message", "Location created successfully");
                response.put("data", location);
                
                return Response.status(201).entity(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to create location");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 创建库位失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 更新库位
     */
    @PUT
    @Path("/{id}")
    public Response updateLocation(@PathParam("id") String locationId, LocationBean location) {
        try {
            logger.info("REST API - 更新库位 - ID: {}", locationId);
            
            location.setLocationId(locationId);
            boolean success = locationService.updateLocation(location);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Location updated successfully");
                response.put("data", location);
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to update location");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 更新库位失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
    
    /**
     * 删除库位
     */
    @DELETE
    @Path("/{id}")
    public Response deleteLocation(@PathParam("id") String locationId) {
        try {
            logger.info("REST API - 删除库位 - ID: {}", locationId);
            
            boolean success = locationService.deleteLocation(locationId);
            
            if (success) {
                Map<String, Object> response = new HashMap<>();
                response.put("code", 200);
                response.put("message", "Location deleted successfully");
                
                return Response.ok(JsonUtil.toJson(response)).build();
            } else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("code", 400);
                errorResponse.put("message", "Failed to delete location");
                
                return Response.status(400).entity(JsonUtil.toJson(errorResponse)).build();
            }
            
        } catch (Exception e) {
            logger.error("REST API - 删除库位失败", e);
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("code", 500);
            errorResponse.put("message", "Internal Server Error");
            errorResponse.put("error", e.getMessage());
            
            return Response.status(500).entity(JsonUtil.toJson(errorResponse)).build();
        }
    }
}
