package com.logistics.bean;

import java.time.LocalDateTime;

/**
 * 库位Bean类
 * 用于在JSP页面和Servlet之间传递数据
 */
public class LocationBean {
    private String locationId;
    private String warehouseCode;
    private String zone;
    private String rack;
    private String levelNo;
    private String position;
    private Integer maxCapacity;
    private LocalDateTime createdAt;
    
    // 构造函数
    public LocationBean() {}
    
    public LocationBean(String locationId, String warehouseCode, String zone, 
                       String rack, String levelNo, String position, Integer maxCapacity) {
        this.locationId = locationId;
        this.warehouseCode = warehouseCode;
        this.zone = zone;
        this.rack = rack;
        this.levelNo = levelNo;
        this.position = position;
        this.maxCapacity = maxCapacity;
        this.createdAt = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public String getLocationId() { 
        return locationId; 
    }
    
    public void setLocationId(String locationId) { 
        this.locationId = locationId; 
    }
    
    public String getWarehouseCode() { 
        return warehouseCode; 
    }
    
    public void setWarehouseCode(String warehouseCode) { 
        this.warehouseCode = warehouseCode; 
    }
    
    public String getZone() { 
        return zone; 
    }
    
    public void setZone(String zone) { 
        this.zone = zone; 
    }
    
    public String getRack() { 
        return rack; 
    }
    
    public void setRack(String rack) { 
        this.rack = rack; 
    }
    
    public String getLevelNo() { 
        return levelNo; 
    }
    
    public void setLevelNo(String levelNo) { 
        this.levelNo = levelNo; 
    }
    
    public String getPosition() { 
        return position; 
    }
    
    public void setPosition(String position) { 
        this.position = position; 
    }
    
    public Integer getMaxCapacity() { 
        return maxCapacity; 
    }
    
    public void setMaxCapacity(Integer maxCapacity) { 
        this.maxCapacity = maxCapacity; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
}
