<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-md-3 col-lg-2 sidebar">
    <div class="sidebar-content">
        <a href="<c:url value='/student/dashboard'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/student/dashboard' ? 'active' : ''}">
            <i class="fas fa-tachometer-alt"></i> Tổng quan
        </a>
        <a href="<c:url value='/student/activities'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/student/activities' ? 'active' : ''}">
            <i class="fas fa-calendar-alt"></i> Hoạt động tình nguyện
        </a>
        <a href="<c:url value='/student/registrations'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/student/registrations' ? 'active' : ''}">
            <i class="fas fa-clipboard-list"></i> Đăng ký tham gia
        </a>
        <a href="<c:url value='/student/history'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/student/history' ? 'active' : ''}">
            <i class="fas fa-history"></i> Lịch sử hoạt động
        </a>
        <a href="<c:url value='/student/certificates'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/student/certificates' ? 'active' : ''}">
            <i class="fas fa-certificate"></i> Chứng nhận
        </a>
        <a href="<c:url value='/student/profile'/>" class="sidebar-link ${requestScope['javax.servlet.forward.servlet_path'] == '/student/profile' ? 'active' : ''}">
            <i class="fas fa-user-circle"></i> Thông tin cá nhân
        </a>
    </div>
</div> 