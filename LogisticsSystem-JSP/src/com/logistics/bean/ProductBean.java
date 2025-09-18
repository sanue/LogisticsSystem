package com.logistics.bean;

import java.time.LocalDateTime;

/**
 * 商品Bean类
 * 用于在JSP页面和Servlet之间传递数据
 */
public class ProductBean {
    private String productId;
    private String productName;
    private String specification;
    private String unit;
    private Integer safetyStock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 构造函数
    public ProductBean() {}
    
    public ProductBean(String productId, String productName, String specification, 
                      String unit, Integer safetyStock) {
        this.productId = productId;
        this.productName = productName;
        this.specification = specification;
        this.unit = unit;
        this.safetyStock = safetyStock;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public String getProductId() { 
        return productId; 
    }
    
    public void setProductId(String productId) { 
        this.productId = productId; 
    }
    
    public String getProductName() { 
        return productName; 
    }
    
    public void setProductName(String productName) { 
        this.productName = productName; 
    }
    
    public String getSpecification() { 
        return specification; 
    }
    
    public void setSpecification(String specification) { 
        this.specification = specification; 
    }
    
    public String getUnit() { 
        return unit; 
    }
    
    public void setUnit(String unit) { 
        this.unit = unit; 
    }
    
    public Integer getSafetyStock() { 
        return safetyStock; 
    }
    
    public void setSafetyStock(Integer safetyStock) { 
        this.safetyStock = safetyStock; 
    }
    
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }
    
    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }
}
