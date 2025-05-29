<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar-content">
    <a href="<c:url value='/manager/dashboard'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/manager/dashboard' ? 'active' : ''}">
        <i class="fas fa-tachometer-alt"></i> Tổng quan
    </a>
    <a href="<c:url value='/manager/activities'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/manager/activities' ? 'active' : ''}">
        <i class="fas fa-calendar-alt"></i> Quản lý hoạt động
    </a>
    <a href="<c:url value='/manager/volunteers'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/manager/volunteers' ? 'active' : ''}">
        <i class="fas fa-users"></i> Quản lý tình nguyện viên
    </a>
    <a href="<c:url value='/manager/evaluations'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/manager/evaluations' ? 'active' : ''}">
        <i class="fas fa-star"></i> Đánh giá hoạt động
    </a>
    <a href="<c:url value='/manager/reports'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/manager/reports' ? 'active' : ''}">
        <i class="fas fa-chart-line"></i> Báo cáo
    </a>
</div> 