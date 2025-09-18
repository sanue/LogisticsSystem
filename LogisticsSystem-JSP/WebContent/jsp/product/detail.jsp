<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.ProductBean" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <h1>商品详情</h1>
    
    <%
        ProductBean product = (ProductBean) request.getAttribute("product");
        if (product != null) {
    %>
    <div class="form-container">
        <div class="form-group">
            <label>商品ID:</label>
            <input type="text" value="<%= product.getProductId() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>商品名称:</label>
            <input type="text" value="<%= product.getProductName() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>规格:</label>
            <input type="text" value="<%= product.getSpecification() != null ? product.getSpecification() : "" %>" readonly>
        </div>
        
        <div class="form-group">
            <label>单位:</label>
            <input type="text" value="<%= product.getUnit() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>安全库存:</label>
            <input type="text" value="<%= product.getSafetyStock() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>创建时间:</label>
            <input type="text" value="<%= product.getCreatedAt() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>更新时间:</label>
            <input type="text" value="<%= product.getUpdatedAt() %>" readonly>
        </div>
        
        <div class="form-actions">
            <a href="${pageContext.request.contextPath}/product?action=edit&id=<%= product.getProductId() %>" class="btn btn-warning">编辑</a>
            <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">返回列表</a>
        </div>
    </div>
    <%
        } else {
    %>
    <div class="error-container">
        <h1>商品不存在</h1>
        <p>请求的商品不存在或已被删除</p>
        <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">返回列表</a>
    </div>
    <%
        }
    %>
</div>

<%@ include file="../common/footer.jsp" %>
