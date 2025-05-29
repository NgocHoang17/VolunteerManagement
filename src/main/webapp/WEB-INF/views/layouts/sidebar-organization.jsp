<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar-content">
    <a href="<c:url value='/organization/dashboard'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/organization/dashboard' ? 'active' : ''}">
        <i class="fas fa-tachometer-alt"></i> Tổng quan
    </a>
    <a href="<c:url value='/organization/activities'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/organization/activities' ? 'active' : ''}">
        <i class="fas fa-calendar-alt"></i> Quản lý hoạt động
    </a>
    <a href="<c:url value='/organization/volunteers'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/organization/volunteers' ? 'active' : ''}">
        <i class="fas fa-users"></i> Quản lý TNV
    </a>
    <a href="<c:url value='/organization/certificates'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/organization/certificates' ? 'active' : ''}">
        <i class="fas fa-certificate"></i> Quản lý chứng nhận
    </a>
    <a href="<c:url value='/organization/reports'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/organization/reports' ? 'active' : ''}">
        <i class="fas fa-chart-bar"></i> Báo cáo thống kê
    </a>
    <a href="<c:url value='/organization/profile'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/organization/profile' ? 'active' : ''}">
        <i class="fas fa-building"></i> Thông tin tổ chức
    </a>
</div> 