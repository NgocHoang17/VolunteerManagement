<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/layouts/dashboard.jsp">
    <jsp:param name="title" value="Trang quản lý"/>
    <jsp:param name="role" value="manager"/>
    <jsp:param name="content" value="/WEB-INF/views/manager/dashboard-content.jsp"/>
</jsp:include> 