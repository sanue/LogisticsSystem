<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.logistics.bean.ProductBean" %>
<%@ page import="com.logistics.bean.CustomerBean" %>
<%@ page import="com.logistics.bean.LocationBean" %>
<%@ page import="java.util.List" %>

<%@ include file="common/header.jsp" %>

<div class="container">
    <h1>ダッシュボード</h1>
    
    <!-- 统计卡片 -->
    <div class="stats-container">
        <div class="stat-card">
            <h3>${totalProducts}</h3>
            <p>商品总数</p>
        </div>
        <div class="stat-card">
            <h3>${totalCustomers}</h3>
            <p>客户总数</p>
        </div>
        <div class="stat-card">
            <h3>${totalLocations}</h3>
            <p>库位总数</p>
        </div>
    </div>
    
    <!-- 最近记录 -->
    <div class="recent-container">
        <!-- 最近商品 -->
        <div class="recent-card">
            <h3>最近创建的商品</h3>
            <%
                List<ProductBean> recentProducts = (List<ProductBean>) request.getAttribute("recentProducts");
                if (recentProducts != null && !recentProducts.isEmpty()) {
                    for (ProductBean product : recentProducts) {
            %>
            <div class="recent-item">
                <strong><%= product.getProductName() %></strong>
                <span><%= product.getCreatedAt() %></span>
            </div>
            <%
                    }
                } else {
            %>
            <div class="recent-item">暂无数据</div>
            <%
                }
            %>
        </div>
        
        <!-- 最近客户 -->
        <div class="recent-card">
            <h3>最近创建的客户</h3>
            <%
                List<CustomerBean> recentCustomers = (List<CustomerBean>) request.getAttribute("recentCustomers");
                if (recentCustomers != null && !recentCustomers.isEmpty()) {
                    for (CustomerBean customer : recentCustomers) {
            %>
            <div class="recent-item">
                <strong><%= customer.getCustomerName() %></strong>
                <span><%= customer.getCreatedAt() %></span>
            </div>
            <%
                    }
                } else {
            %>
            <div class="recent-item">暂无数据</div>
            <%
                }
            %>
        </div>
        
        <!-- 最近库位 -->
        <div class="recent-card">
            <h3>最近创建的库位</h3>
            <%
                List<LocationBean> recentLocations = (List<LocationBean>) request.getAttribute("recentLocations");
                if (recentLocations != null && !recentLocations.isEmpty()) {
                    for (LocationBean location : recentLocations) {
            %>
            <div class="recent-item">
                <strong><%= location.getLocationId() %></strong>
                <span><%= location.getCreatedAt() %></span>
            </div>
            <%
                    }
                } else {
            %>
            <div class="recent-item">暂无数据</div>
            <%
                }
            %>
        </div>
    </div>
</div>

<%@ include file="common/footer.jsp" %>
