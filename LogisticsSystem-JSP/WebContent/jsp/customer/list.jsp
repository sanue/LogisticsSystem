<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.CustomerBean" %>
<%@ page import="java.util.List" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <h1>客户管理</h1>
    
    <!-- 操作按钮 -->
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/jsp/customer/edit.jsp" class="btn btn-primary">新增客户</a>
    </div>
    
    <!-- 搜索表单 -->
    <form method="get" action="${pageContext.request.contextPath}/customer">
        <div class="search-form">
            <input type="text" name="keyword" value="${keyword}" placeholder="搜索客户...">
            <select name="sortBy">
                <option value="CREATED_AT" ${sortBy == 'CREATED_AT' ? 'selected' : ''}>按创建时间</option>
                <option value="CUSTOMER_NAME" ${sortBy == 'CUSTOMER_NAME' ? 'selected' : ''}>按客户名</option>
                <option value="CUSTOMER_ID" ${sortBy == 'CUSTOMER_ID' ? 'selected' : ''}>按客户ID</option>
            </select>
            <select name="sortDir">
                <option value="DESC" ${sortDir == 'DESC' ? 'selected' : ''}>降序</option>
                <option value="ASC" ${sortDir == 'ASC' ? 'selected' : ''}>升序</option>
            </select>
            <button type="submit">搜索</button>
        </div>
    </form>
    
    <!-- 客户列表 -->
    <table class="customer-table">
        <thead>
            <tr>
                <th>客户ID</th>
                <th>客户名称</th>
                <th>地址</th>
                <th>电话</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<CustomerBean> customers = (List<CustomerBean>) request.getAttribute("customers");
                if (customers != null && !customers.isEmpty()) {
                    for (CustomerBean customer : customers) {
            %>
            <tr>
                <td><%= customer.getCustomerId() %></td>
                <td><%= customer.getCustomerName() %></td>
                <td><%= customer.getAddress() != null ? customer.getAddress() : "" %></td>
                <td><%= customer.getPhone() != null ? customer.getPhone() : "" %></td>
                <td><%= customer.getCreatedAt() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/customer?action=detail&id=<%= customer.getCustomerId() %>" class="btn btn-info">详情</a>
                    <a href="${pageContext.request.contextPath}/customer?action=edit&id=<%= customer.getCustomerId() %>" class="btn btn-warning">编辑</a>
                    <a href="javascript:deleteCustomer('<%= customer.getCustomerId() %>')" class="btn btn-danger">删除</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="6" class="no-data">暂无数据</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>
    
    <!-- 分页 -->
    <div class="pagination">
        <%
            int currentPage = (Integer) request.getAttribute("currentPage");
            int totalPages = (Integer) request.getAttribute("totalPages");
        %>
        
        <a href="customer?page=0&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">首页</a>
        <% if (currentPage > 0) { %>
            <a href="customer?page=<%= currentPage - 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">上一页</a>
        <% } %>
        
        <span>第 <%= currentPage + 1 %> 页，共 <%= totalPages %> 页</span>
        
        <% if (currentPage < totalPages - 1) { %>
            <a href="customer?page=<%= currentPage + 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">下一页</a>
        <% } %>
        <a href="customer?page=<%= totalPages - 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">末页</a>
    </div>
</div>

<script>
    function deleteCustomer(customerId) {
        if (confirm('确定要删除这个客户吗？')) {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/customer';
            
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'delete';
            
            var idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'id';
            idInput.value = customerId;
            
            form.appendChild(actionInput);
            form.appendChild(idInput);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>

<%@ include file="../common/footer.jsp" %>
