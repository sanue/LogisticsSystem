<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.LocationBean" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <h1>库位详情</h1>
    
    <%
        LocationBean location = (LocationBean) request.getAttribute("location");
        if (location != null) {
    %>
    <div class="form-container">
        <div class="form-group">
            <label>库位ID:</label>
            <input type="text" value="<%= location.getLocationId() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>仓库代码:</label>
            <input type="text" value="<%= location.getWarehouseCode() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>区域:</label>
            <input type="text" value="<%= location.getZone() != null ? location.getZone() : "" %>" readonly>
        </div>
        
        <div class="form-group">
            <label>货架:</label>
            <input type="text" value="<%= location.getRack() != null ? location.getRack() : "" %>" readonly>
        </div>
        
        <div class="form-group">
            <label>层号:</label>
            <input type="text" value="<%= location.getLevelNo() != null ? location.getLevelNo() : "" %>" readonly>
        </div>
        
        <div class="form-group">
            <label>位置:</label>
            <input type="text" value="<%= location.getPosition() != null ? location.getPosition() : "" %>" readonly>
        </div>
        
        <div class="form-group">
            <label>最大容量:</label>
            <input type="text" value="<%= location.getMaxCapacity() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>创建时间:</label>
            <input type="text" value="<%= location.getCreatedAt() %>" readonly>
        </div>
        
        <div class="form-actions">
            <a href="${pageContext.request.contextPath}/location?action=edit&id=<%= location.getLocationId() %>" class="btn btn-warning">编辑</a>
            <a href="${pageContext.request.contextPath}/location" class="btn btn-primary">返回列表</a>
        </div>
    </div>
    <%
        } else {
    %>
    <div class="error-container">
        <h1>库位不存在</h1>
        <p>请求的库位不存在或已被删除</p>
        <a href="${pageContext.request.contextPath}/location" class="btn btn-primary">返回列表</a>
    </div>
    <%
        }
    %>
</div>

<%@ include file="../common/footer.jsp" %>
