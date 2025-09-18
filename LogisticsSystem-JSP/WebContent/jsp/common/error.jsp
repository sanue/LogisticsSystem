<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<div class="container">
    <div class="error-container">
        <h1>エラー</h1>
        <p>${error}</p>
        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-primary">ホームに戻る</a>
    </div>
</div>

<%@ include file="footer.jsp" %>
