<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.LocationBean" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <%
        LocationBean location = (LocationBean) request.getAttribute("location");
        boolean isEdit = location != null;
        String action = isEdit ? "update" : "create";
        String title = isEdit ? "编辑库位" : "新增库位";
    %>
    
    <h1><%= title %></h1>
    
    <div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/location">
            <input type="hidden" name="action" value="<%= action %>">
            
            <div class="form-group">
                <label>库位ID:</label>
                <input type="text" name="locationId" value="<%= isEdit ? location.getLocationId() : "" %>" 
                       <%= isEdit ? "readonly" : "required" %>>
            </div>
            
            <div class="form-group">
                <label>仓库代码:</label>
                <input type="text" name="warehouseCode" value="<%= isEdit ? location.getWarehouseCode() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>区域:</label>
                <input type="text" name="zone" value="<%= isEdit && location.getZone() != null ? location.getZone() : "" %>">
            </div>
            
            <div class="form-group">
                <label>货架:</label>
                <input type="text" name="rack" value="<%= isEdit && location.getRack() != null ? location.getRack() : "" %>">
            </div>
            
            <div class="form-group">
                <label>层号:</label>
                <input type="text" name="levelNo" value="<%= isEdit && location.getLevelNo() != null ? location.getLevelNo() : "" %>">
            </div>
            
            <div class="form-group">
                <label>位置:</label>
                <input type="text" name="position" value="<%= isEdit && location.getPosition() != null ? location.getPosition() : "" %>">
            </div>
            
            <div class="form-group">
                <label>最大容量:</label>
                <input type="number" name="maxCapacity" value="<%= isEdit ? location.getMaxCapacity() : "" %>" required min="1">
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-success"><%= isEdit ? "更新" : "创建" %></button>
                <a href="${pageContext.request.contextPath}/location" class="btn btn-primary">取消</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
