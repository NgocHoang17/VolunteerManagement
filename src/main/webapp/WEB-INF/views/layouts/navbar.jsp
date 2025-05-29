<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="<c:url value='/home'/>">
            <i class="fas fa-hands-helping"></i> Volunteer Management
        </a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" 
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/home'/>">
                            <i class="fas fa-home"></i> Trang chủ
                        </a>
                    </li>
                </sec:authorize>
            </ul>

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
                    <li class="nav-item dropdown">
                        <a class="nav-link" href="#" id="userDropdown" role="button" 
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
                            <li><hr class="dropdown-divider"></li>
                            <li>
                                <form action="<c:url value='/auth/logout'/>" method="post">
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