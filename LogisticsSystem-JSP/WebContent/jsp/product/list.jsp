<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.ProductBean" %>
<%@ page import="java.util.List" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <h1>商品管理</h1>
    
    <!-- 操作按钮 -->
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/jsp/product/edit.jsp" class="btn btn-primary">新增商品</a>
    </div>
    
    <!-- 搜索表单 -->
    <form method="get" action="${pageContext.request.contextPath}/product">
        <div class="search-form">
            <input type="text" name="keyword" value="${keyword}" placeholder="搜索商品...">
            <select name="sortBy">
                <option value="CREATED_AT" ${sortBy == 'CREATED_AT' ? 'selected' : ''}>按创建时间</option>
                <option value="PRODUCT_NAME" ${sortBy == 'PRODUCT_NAME' ? 'selected' : ''}>按商品名</option>
                <option value="SAFETY_STOCK" ${sortBy == 'SAFETY_STOCK' ? 'selected' : ''}>按安全库存</option>
            </select>
            <select name="sortDir">
                <option value="DESC" ${sortDir == 'DESC' ? 'selected' : ''}>降序</option>
                <option value="ASC" ${sortDir == 'ASC' ? 'selected' : ''}>升序</option>
            </select>
            <button type="submit">搜索</button>
        </div>
    </form>
    
    <!-- 商品列表 -->
    <table class="product-table">
        <thead>
            <tr>
                <th>商品ID</th>
                <th>商品名称</th>
                <th>规格</th>
                <th>单位</th>
                <th>安全库存</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<ProductBean> products = (List<ProductBean>) request.getAttribute("products");
                if (products != null && !products.isEmpty()) {
                    for (ProductBean product : products) {
            %>
            <tr>
                <td><%= product.getProductId() %></td>
                <td><%= product.getProductName() %></td>
                <td><%= product.getSpecification() != null ? product.getSpecification() : "" %></td>
                <td><%= product.getUnit() %></td>
                <td><%= product.getSafetyStock() %></td>
                <td><%= product.getCreatedAt() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/product?action=detail&id=<%= product.getProductId() %>" class="btn btn-info">详情</a>
                    <a href="${pageContext.request.contextPath}/product?action=edit&id=<%= product.getProductId() %>" class="btn btn-warning">编辑</a>
                    <a href="javascript:deleteProduct('<%= product.getProductId() %>')" class="btn btn-danger">删除</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="7" class="no-data">暂无数据</td>
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
        
        <a href="product?page=0&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">首页</a>
        <% if (currentPage > 0) { %>
            <a href="product?page=<%= currentPage - 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">上一页</a>
        <% } %>
        
        <span>第 <%= currentPage + 1 %> 页，共 <%= totalPages %> 页</span>
        
        <% if (currentPage < totalPages - 1) { %>
            <a href="product?page=<%= currentPage + 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">下一页</a>
        <% } %>
        <a href="product?page=<%= totalPages - 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">末页</a>
    </div>
</div>

<script>
    function deleteProduct(productId) {
        if (confirm('确定要删除这个商品吗？')) {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/product';
            
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'delete';
            
            var idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'id';
            idInput.value = productId;
            
            form.appendChild(actionInput);
            form.appendChild(idInput);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>

<%@ include file="../common/footer.jsp" %>
