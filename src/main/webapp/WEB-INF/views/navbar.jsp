<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar navbar-expand-lg navbar-dark sticky-top" style="background: linear-gradient(to right, #28a745, #20c997);">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/'/>">
            <i class="fas fa-hands-helping me-2"></i>
            Quản Lý Tình Nguyện
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/'/>">
                        <i class="fas fa-home me-1"></i>
                        Trang chủ
                    </a>
                </li>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/manage/sinhvien'/>">
                            <i class="fas fa-user-graduate me-1"></i>
                            Sinh Viên
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/manage/hoatdong'/>">
                            <i class="fas fa-calendar-alt me-1"></i>
                            Hoạt Động
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/manage/thamgia/new'/>">
                            <i class="fas fa-plus-circle me-1"></i>
                            Thêm Tham Gia
                        </a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <form action="<c:url value='/logout'/>" method="post" style="display: inline;">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn nav-link" style="background: none; border: none;">
                                <i class="fas fa-sign-out-alt me-1"></i>
                                Đăng Xuất
                            </button>
                        </form>
                    </li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/login'/>">
                            <i class="fas fa-sign-in-alt me-1"></i>
                            Đăng Nhập
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/register'/>">
                            <i class="fas fa-user-plus me-1"></i>
                            Đăng Ký
                        </a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>