<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>物流管理システム</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="header">
        <h1>物流管理システム</h1>
        <p>Logistics Management System</p>
    </div>
    
    <div class="nav">
        <ul>
            <li><a href="${pageContext.request.contextPath}/dashboard">ダッシュボード</a></li>
            <li><a href="${pageContext.request.contextPath}/product">商品管理</a></li>
            <li><a href="${pageContext.request.contextPath}/customer">客户管理</a></li>
            <li><a href="${pageContext.request.contextPath}/location">库位管理</a></li>
            <li><a href="${pageContext.request.contextPath}/api/test.html">API测试</a></li>
        </ul>
    </div>
