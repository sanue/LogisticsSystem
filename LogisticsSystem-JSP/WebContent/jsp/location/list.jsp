<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.LocationBean" %>
<%@ page import="java.util.List" %>

<%@ include file="../common/header.jsp" %>

<div class="container">
    <h1>库位管理</h1>
    
    <!-- 操作按钮 -->
    <div class="action-buttons">
        <a href="${pageContext.request.contextPath}/jsp/location/edit.jsp" class="btn btn-primary">新增库位</a>
    </div>
    
    <!-- 搜索表单 -->
    <form method="get" action="${pageContext.request.contextPath}/location">
        <div class="search-form">
            <input type="text" name="keyword" value="${keyword}" placeholder="搜索库位...">
            <select name="sortBy">
                <option value="CREATED_AT" ${sortBy == 'CREATED_AT' ? 'selected' : ''}>按创建时间</option>
                <option value="LOCATION_ID" ${sortBy == 'LOCATION_ID' ? 'selected' : ''}>按库位ID</option>
                <option value="WAREHOUSE_CODE" ${sortBy == 'WAREHOUSE_CODE' ? 'selected' : ''}>按仓库代码</option>
            </select>
            <select name="sortDir">
                <option value="DESC" ${sortDir == 'DESC' ? 'selected' : ''}>降序</option>
                <option value="ASC" ${sortDir == 'ASC' ? 'selected' : ''}>升序</option>
            </select>
            <button type="submit">搜索</button>
        </div>
    </form>
    
    <!-- 库位列表 -->
    <table class="location-table">
        <thead>
            <tr>
                <th>库位ID</th>
                <th>仓库代码</th>
                <th>区域</th>
                <th>货架</th>
                <th>层号</th>
                <th>位置</th>
                <th>最大容量</th>
                <th>创建时间</th>
                <th>操作</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<LocationBean> locations = (List<LocationBean>) request.getAttribute("locations");
                if (locations != null && !locations.isEmpty()) {
                    for (LocationBean location : locations) {
            %>
            <tr>
                <td><%= location.getLocationId() %></td>
                <td><%= location.getWarehouseCode() %></td>
                <td><%= location.getZone() != null ? location.getZone() : "" %></td>
                <td><%= location.getRack() != null ? location.getRack() : "" %></td>
                <td><%= location.getLevelNo() != null ? location.getLevelNo() : "" %></td>
                <td><%= location.getPosition() != null ? location.getPosition() : "" %></td>
                <td><%= location.getMaxCapacity() %></td>
                <td><%= location.getCreatedAt() %></td>
                <td>
                    <a href="${pageContext.request.contextPath}/location?action=detail&id=<%= location.getLocationId() %>" class="btn btn-info">详情</a>
                    <a href="${pageContext.request.contextPath}/location?action=edit&id=<%= location.getLocationId() %>" class="btn btn-warning">编辑</a>
                    <a href="javascript:deleteLocation('<%= location.getLocationId() %>')" class="btn btn-danger">删除</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="9" class="no-data">暂无数据</td>
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
        
        <a href="location?page=0&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">首页</a>
        <% if (currentPage > 0) { %>
            <a href="location?page=<%= currentPage - 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">上一页</a>
        <% } %>
        
        <span>第 <%= currentPage + 1 %> 页，共 <%= totalPages %> 页</span>
        
        <% if (currentPage < totalPages - 1) { %>
            <a href="location?page=<%= currentPage + 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">下一页</a>
        <% } %>
        <a href="location?page=<%= totalPages - 1 %>&keyword=${keyword}&sortBy=${sortBy}&sortDir=${sortDir}">末页</a>
    </div>
</div>

<script>
    function deleteLocation(locationId) {
        if (confirm('确定要删除这个库位吗？')) {
            var form = document.createElement('form');
            form.method = 'POST';
            form.action = '${pageContext.request.contextPath}/location';
            
            var actionInput = document.createElement('input');
            actionInput.type = 'hidden';
            actionInput.name = 'action';
            actionInput.value = 'delete';
            
            var idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'id';
            idInput.value = locationId;
            
            form.appendChild(actionInput);
            form.appendChild(idInput);
            document.body.appendChild(form);
            form.submit();
        }
    }
</script>

<%@ include file="../common/footer.jsp" %>
