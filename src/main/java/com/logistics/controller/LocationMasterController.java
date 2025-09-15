package com.logistics.controller;

import com.logistics.controller.dto.ApiResponse;
import com.logistics.entity.LocationMaster;
import com.logistics.service.LocationMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ロケーションマスタコントローラー
 * Location Master Controller
 * 
 * ロケーション管理のREST APIエンドポイント
 * REST API endpoints for location management
 */
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class LocationMasterController {
    
    private final LocationMasterService locationMasterService;
    
    /**
     * 全ロケーションを取得
     * Get all locations
     * 
     * @return ロケーションリスト
     * @return List of locations
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<LocationMaster>>> getAllLocations() {
        try {
            log.info("Getting all locations");
            List<LocationMaster> locations = locationMasterService.getAllLocations();
            return ResponseEntity.ok(ApiResponse.success(locations, "Locations retrieved successfully"));
        } catch (Exception e) {
            log.error("Error getting all locations", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve locations: " + e.getMessage()));
        }
    }
    
    /**
     * ロケーションIDでロケーションを取得
     * Get location by location ID
     * 
     * @param locationId ロケーションID
     * @return ロケーション情報
     * @return Location information
     */
    @GetMapping("/{locationId}")
    public ResponseEntity<ApiResponse<LocationMaster>> getLocationById(@PathVariable String locationId) {
        try {
            log.info("Getting location by ID: {}", locationId);
            LocationMaster location = locationMasterService.getLocationById(locationId);
            if (location == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Location not found"));
            }
            return ResponseEntity.ok(ApiResponse.success(location, "Location retrieved successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid location ID: {}", locationId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting location by ID: {}", locationId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve location: " + e.getMessage()));
        }
    }
    
    /**
     * 倉庫コードでロケーションを検索
     * Search locations by warehouse code
     * 
     * @param warehouseCode 倉庫コード
     * @return ロケーションリスト
     * @return List of locations
     */
    @GetMapping("/warehouse/{warehouseCode}")
    public ResponseEntity<ApiResponse<List<LocationMaster>>> getLocationsByWarehouseCode(@PathVariable String warehouseCode) {
        try {
            log.info("Getting locations by warehouse code: {}", warehouseCode);
            List<LocationMaster> locations = locationMasterService.getLocationsByWarehouseCode(warehouseCode);
            return ResponseEntity.ok(ApiResponse.success(locations, "Locations retrieved successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid warehouse code: {}", warehouseCode, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting locations by warehouse code: {}", warehouseCode, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve locations: " + e.getMessage()));
        }
    }
    
    /**
     * ゾーンでロケーションを検索
     * Search locations by zone
     * 
     * @param zone ゾーン
     * @return ロケーションリスト
     * @return List of locations
     */
    @GetMapping("/zone/{zone}")
    public ResponseEntity<ApiResponse<List<LocationMaster>>> getLocationsByZone(@PathVariable String zone) {
        try {
            log.info("Getting locations by zone: {}", zone);
            List<LocationMaster> locations = locationMasterService.getLocationsByZone(zone);
            return ResponseEntity.ok(ApiResponse.success(locations, "Locations retrieved successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Invalid zone: {}", zone, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error getting locations by zone: {}", zone, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to retrieve locations: " + e.getMessage()));
        }
    }
    
    /**
     * ロケーションを新規登録
     * Create new location
     * 
     * @param location ロケーション情報
     * @return 登録されたロケーション情報
     * @return Created location information
     */
    @PostMapping
    public ResponseEntity<ApiResponse<LocationMaster>> createLocation(@RequestBody LocationMaster location) {
        try {
            log.info("Creating new location: {}", location.getLocationId());
            LocationMaster createdLocation = locationMasterService.createLocation(location);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(createdLocation, "Location created successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Validation error creating location", e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error creating location", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to create location: " + e.getMessage()));
        }
    }
    
    /**
     * ロケーション情報を更新
     * Update location information
     * 
     * @param locationId ロケーションID
     * @param location ロケーション情報
     * @return 更新されたロケーション情報
     * @return Updated location information
     */
    @PutMapping("/{locationId}")
    public ResponseEntity<ApiResponse<LocationMaster>> updateLocation(
            @PathVariable String locationId, 
            @RequestBody LocationMaster location) {
        try {
            log.info("Updating location: {}", locationId);
            location.setLocationId(locationId); // パス変数からIDを設定
            LocationMaster updatedLocation = locationMasterService.updateLocation(location);
            return ResponseEntity.ok(ApiResponse.success(updatedLocation, "Location updated successfully"));
        } catch (IllegalArgumentException e) {
            log.warn("Validation error updating location: {}", locationId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error updating location: {}", locationId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to update location: " + e.getMessage()));
        }
    }
    
    /**
     * ロケーションを削除
     * Delete location
     * 
     * @param locationId ロケーションID
     * @return 削除結果
     * @return Deletion result
     */
    @DeleteMapping("/{locationId}")
    public ResponseEntity<ApiResponse<Void>> deleteLocation(@PathVariable String locationId) {
        try {
            log.info("Deleting location: {}", locationId);
            boolean deleted = locationMasterService.deleteLocation(locationId);
            if (deleted) {
                return ResponseEntity.ok(ApiResponse.success(null, "Location deleted successfully"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error(404, "Location not found"));
            }
        } catch (IllegalArgumentException e) {
            log.warn("Validation error deleting location: {}", locationId, e);
            return ResponseEntity.badRequest()
                    .body(ApiResponse.validationError(e.getMessage()));
        } catch (Exception e) {
            log.error("Error deleting location: {}", locationId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.serverError("Failed to delete location: " + e.getMessage()));
        }
    }
}

