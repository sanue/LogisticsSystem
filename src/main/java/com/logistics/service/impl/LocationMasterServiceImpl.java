package com.logistics.service.impl;

import com.logistics.entity.LocationMaster;
import com.logistics.mapper.LocationMasterMapper;
import com.logistics.service.LocationMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ロケーションマスタサービス実装クラス
 * Location Master Service Implementation
 * 
 * ロケーション管理のビジネスロジックを実装
 * Implements business logic for location management
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LocationMasterServiceImpl implements LocationMasterService {
    
    private final LocationMasterMapper locationMasterMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<LocationMaster> getAllLocations() {
        log.info("Getting all locations");
        return locationMasterMapper.findAll();
    }
    
    @Override
    @Transactional(readOnly = true)
    public LocationMaster getLocationById(String locationId) {
        log.info("Getting location by ID: {}", locationId);
        if (locationId == null || locationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Location ID cannot be null or empty");
        }
        return locationMasterMapper.findById(locationId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<LocationMaster> getLocationsByWarehouseCode(String warehouseCode) {
        log.info("Getting locations by warehouse code: {}", warehouseCode);
        if (warehouseCode == null || warehouseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse code cannot be null or empty");
        }
        return locationMasterMapper.findByWarehouseCode(warehouseCode);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<LocationMaster> getLocationsByZone(String zone) {
        log.info("Getting locations by zone: {}", zone);
        if (zone == null || zone.trim().isEmpty()) {
            throw new IllegalArgumentException("Zone cannot be null or empty");
        }
        return locationMasterMapper.findByZone(zone);
    }
    
    @Override
    public LocationMaster createLocation(LocationMaster location) {
        log.info("Creating new location: {}", location.getLocationId());
        
        // バリデーション
        validateLocation(location);
        
        // ロケーションIDの重複チェック
        if (locationMasterMapper.existsById(location.getLocationId())) {
            throw new IllegalArgumentException("Location with ID " + location.getLocationId() + " already exists");
        }
        
        // 作成日時を設定
        location.setCreatedAt(LocalDateTime.now());
        
        int result = locationMasterMapper.insert(location);
        if (result == 0) {
            throw new RuntimeException("Failed to create location");
        }
        
        log.info("Location created successfully: {}", location.getLocationId());
        return location;
    }
    
    @Override
    public LocationMaster updateLocation(LocationMaster location) {
        log.info("Updating location: {}", location.getLocationId());
        
        // バリデーション
        validateLocation(location);
        
        // ロケーションの存在チェック
        if (!locationMasterMapper.existsById(location.getLocationId())) {
            throw new IllegalArgumentException("Location with ID " + location.getLocationId() + " does not exist");
        }
        
        int result = locationMasterMapper.update(location);
        if (result == 0) {
            throw new RuntimeException("Failed to update location");
        }
        
        log.info("Location updated successfully: {}", location.getLocationId());
        return location;
    }
    
    @Override
    public boolean deleteLocation(String locationId) {
        log.info("Deleting location: {}", locationId);
        
        if (locationId == null || locationId.trim().isEmpty()) {
            throw new IllegalArgumentException("Location ID cannot be null or empty");
        }
        
        // ロケーションの存在チェック
        if (!locationMasterMapper.existsById(locationId)) {
            throw new IllegalArgumentException("Location with ID " + locationId + " does not exist");
        }
        
        int result = locationMasterMapper.deleteById(locationId);
        boolean success = result > 0;
        
        if (success) {
            log.info("Location deleted successfully: {}", locationId);
        } else {
            log.warn("Failed to delete location: {}", locationId);
        }
        
        return success;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsLocation(String locationId) {
        if (locationId == null || locationId.trim().isEmpty()) {
            return false;
        }
        return locationMasterMapper.existsById(locationId);
    }
    
    /**
     * ロケーション情報のバリデーション
     * Validate location information
     * 
     * @param location ロケーション情報
     */
    private void validateLocation(LocationMaster location) {
        if (location == null) {
            throw new IllegalArgumentException("Location cannot be null");
        }
        
        if (location.getLocationId() == null || location.getLocationId().trim().isEmpty()) {
            throw new IllegalArgumentException("Location ID is required");
        }
        
        if (location.getWarehouseCode() == null || location.getWarehouseCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Warehouse code is required");
        }
        
        if (location.getMaxCapacity() != null && location.getMaxCapacity() < 0) {
            throw new IllegalArgumentException("Maximum capacity cannot be negative");
        }
    }
}
