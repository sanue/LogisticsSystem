<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.CustomerBean" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <%
        CustomerBean customer = (CustomerBean) request.getAttribute("customer");
        boolean isEdit = customer != null;
        String action = isEdit ? "update" : "create";
        String title = isEdit ? "编辑客户" : "新增客户";
    %>
    
    <h1><%= title %></h1>
    
    <div class="form-container">
        <form method="post" action="${pageContext.request.contextPath}/customer">
            <input type="hidden" name="action" value="<%= action %>">
            
            <div class="form-group">
                <label>客户ID:</label>
                <input type="text" name="customerId" value="<%= isEdit ? customer.getCustomerId() : "" %>" 
                       <%= isEdit ? "readonly" : "required" %>>
            </div>
            
            <div class="form-group">
                <label>客户名称:</label>
                <input type="text" name="customerName" value="<%= isEdit ? customer.getCustomerName() : "" %>" required>
            </div>
            
            <div class="form-group">
                <label>地址:</label>
                <input type="text" name="address" value="<%= isEdit && customer.getAddress() != null ? customer.getAddress() : "" %>">
            </div>
            
            <div class="form-group">
                <label>电话:</label>
                <input type="text" name="phone" value="<%= isEdit && customer.getPhone() != null ? customer.getPhone() : "" %>">
            </div>
            
            <div class="form-actions">
                <button type="submit" class="btn btn-success"><%= isEdit ? "更新" : "创建" %></button>
                <a href="${pageContext.request.contextPath}/customer" class="btn btn-primary">取消</a>
            </div>
        </form>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
