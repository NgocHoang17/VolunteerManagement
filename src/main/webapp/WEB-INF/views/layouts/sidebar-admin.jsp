<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar-content">
    <a href="<c:url value='/admin/dashboard'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/dashboard' ? 'active' : ''}">
        <i class="fas fa-tachometer-alt"></i> Tổng quan
    </a>
    <a href="<c:url value='/admin/users'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/users' ? 'active' : ''}">
        <i class="fas fa-users"></i> Quản lý người dùng
    </a>
    <a href="<c:url value='/admin/activities'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/activities' ? 'active' : ''}">
        <i class="fas fa-calendar-alt"></i> Quản lý hoạt động
    </a>
    <a href="<c:url value='/admin/organizations'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/organizations' ? 'active' : ''}">
        <i class="fas fa-building"></i> Quản lý tổ chức
    </a>
    <a href="<c:url value='/admin/reports'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/reports' ? 'active' : ''}">
        <i class="fas fa-chart-bar"></i> Báo cáo thống kê
    </a>
    <a href="<c:url value='/admin/settings'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/admin/settings' ? 'active' : ''}">
        <i class="fas fa-cog"></i> Cài đặt hệ thống
    </a>
</div> 