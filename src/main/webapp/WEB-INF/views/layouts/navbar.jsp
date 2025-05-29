<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
    <div class="container-fluid">
        <!-- Toggle Button -->
        <button id="sidebarToggle" class="btn btn-link d-md-none rounded-circle me-3">
            <i class="fas fa-bars"></i>
        </button>

        <!-- Brand -->
        <a class="navbar-brand" href="<c:url value='/home'/>">
            <i class="fas fa-hands-helping"></i> Volunteer Management
        </a>

        <!-- Navbar Toggler -->
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" 
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <!-- Navbar Content -->
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <!-- Left Side -->
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/home'/>">
                            <i class="fas fa-home"></i> Trang chủ
                        </a>
                    </li>
                </sec:authorize>
            </ul>

            <!-- Right Side -->
            <ul class="navbar-nav ms-auto">
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/auth/login'/>">
                            <i class="fas fa-sign-in-alt"></i> Đăng nhập
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/auth/register'/>">
                            <i class="fas fa-user-plus"></i> Đăng ký
                        </a>
                    </li>
                </sec:authorize>

                <sec:authorize access="isAuthenticated()">
                    <!-- Notifications -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="notificationsDropdown" role="button" 
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fas fa-bell"></i>
                            <span class="badge bg-danger">3</span>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="notificationsDropdown">
                            <li><h6 class="dropdown-header">Thông báo</h6></li>
                            <li><hr class="dropdown-divider"></li>
                            <li><a class="dropdown-item" href="#">Thông báo 1</a></li>
                            <li><a class="dropdown-item" href="#">Thông báo 2</a></li>
                            <li><a class="dropdown-item" href="#">Thông báo 3</a></li>
                        </ul>
                    </li>

                    <!-- User Menu -->
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" 
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="fas fa-user-circle"></i>
                            <sec:authentication property="principal.username" />
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userDropdown">
                            <sec:authorize access="hasRole('ROLE_ADMIN')">
                                <li>
                                    <a class="dropdown-item" href="<c:url value='/admin/dashboard'/>">
                                        <i class="fas fa-tachometer-alt"></i> Quản trị
                                    </a>
                                </li>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_STUDENT')">
                                <li>
                                    <a class="dropdown-item" href="<c:url value='/student/dashboard'/>">
                                        <i class="fas fa-tachometer-alt"></i> Dashboard
                                    </a>
                                </li>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_ORGANIZATION')">
                                <li>
                                    <a class="dropdown-item" href="<c:url value='/organization/dashboard'/>">
                                        <i class="fas fa-tachometer-alt"></i> Dashboard
                                    </a>
                                </li>
                            </sec:authorize>
                            <li>
                                <a class="dropdown-item" href="<c:url value='/profile'/>">
                                    <i class="fas fa-user"></i> Hồ sơ
                                </a>
                            </li>
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form action="<c:url value='/auth/logout'/>" method="post" id="logoutForm">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                    <button type="submit" class="dropdown-item">
                                        <i class="fas fa-sign-out-alt"></i> Đăng xuất
                                    </button>
                                </form>
                            </li>
                        </ul>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>

<style>
.navbar {
    box-shadow: 0 2px 4px rgba(0,0,0,.1);
}
.navbar-brand {
    font-weight: 600;
}
.nav-link {
    font-weight: 500;
    padding: 0.5rem 1rem;
}
.dropdown-item {
    padding: 0.5rem 1rem;
}
.dropdown-menu {
    border: none;
    border-radius: 0.5rem;
}
</style> 