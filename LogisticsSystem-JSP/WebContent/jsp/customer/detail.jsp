<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.CustomerBean" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <h1>客户详情</h1>
    
    <%
        CustomerBean customer = (CustomerBean) request.getAttribute("customer");
        if (customer != null) {
    %>
    <div class="form-container">
        <div class="form-group">
            <label>客户ID:</label>
            <input type="text" value="<%= customer.getCustomerId() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>客户名称:</label>
            <input type="text" value="<%= customer.getCustomerName() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>地址:</label>
            <input type="text" value="<%= customer.getAddress() != null ? customer.getAddress() : "" %>" readonly>
        </div>
        
        <div class="form-group">
            <label>电话:</label>
            <input type="text" value="<%= customer.getPhone() != null ? customer.getPhone() : "" %>" readonly>
        </div>
        
        <div class="form-group">
            <label>创建时间:</label>
            <input type="text" value="<%= customer.getCreatedAt() %>" readonly>
        </div>
        
        <div class="form-group">
            <label>更新时间:</label>
            <input type="text" value="<%= customer.getUpdatedAt() %>" readonly>
        </div>
        
        <div class="form-actions">
            <a href="${pageContext.request.contextPath}/customer?action=edit&id=<%= customer.getCustomerId() %>" class="btn btn-warning">编辑</a>
            <a href="${pageContext.request.contextPath}/customer" class="btn btn-primary">返回列表</a>
        </div>
    </div>
    <%
        } else {
    %>
    <div class="error-container">
        <h1>客户不存在</h1>
        <p>请求的客户不存在或已被删除</p>
        <a href="${pageContext.request.contextPath}/customer" class="btn btn-primary">返回列表</a>
    </div>
    <%
        }
    %>
</div>

<%@ include file="../common/footer.jsp" %>
