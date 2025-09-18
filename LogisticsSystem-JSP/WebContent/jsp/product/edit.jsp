<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.ProductBean" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <%
        ProductBean product = (ProductBean) request.getAttribute("product");
        boolean isEdit = product != null;
        String action = isEdit ? "update" : "create";
        String title = isEdit ? "编辑商品" : "新增商品";
    %>
    
    <h1><%= title %></h1>
    
    <div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/product">
            <input type="hidden" name="action" value="<%= action %>">
            
            <div class="form-group">
                <label>商品ID:</label>
                <input type="text" name="productId" value="<%= isEdit ? product.getProductId() : "" %>" 
                       <%= isEdit ? "readonly" : "required" %>>
            </div>
            
            <div class="form-group">
                <label>商品名称:</label>
                <input type="text" name="productName" value="<%= isEdit ? product.getProductName() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>规格:</label>
                <input type="text" name="specification" value="<%= isEdit && product.getSpecification() != null ? product.getSpecification() : "" %>">
            </div>
            
            <div class="form-group">
                <label>单位:</label>
                <input type="text" name="unit" value="<%= isEdit ? product.getUnit() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>安全库存:</label>
                <input type="number" name="safetyStock" value="<%= isEdit ? product.getSafetyStock() : "" %>" required min="0">
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-success"><%= isEdit ? "更新" : "创建" %></button>
                <a href="${pageContext.request.contextPath}/product" class="btn btn-primary">取消</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
