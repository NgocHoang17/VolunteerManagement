<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<nav class="navbar navbar-expand-lg navbar-dark bg-gradient sticky-top">
    <div class="container">
        <a class="navbar-brand" href="/">Quản Lý Tình Nguyện</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Dashboard</a>
                </li>
                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
                    <li class="nav-item">
                        <a class="nav-link" href="/sinhvien">Sinh Viên</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/hoatdong">Hoạt Động</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/thamgia/new">Thêm Tham Gia</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/logout">Đăng Xuất</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="!isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="/login">Đăng Nhập</a>
                    </li>
                </sec:authorize>
            </ul>
        </div>
    </div>
</nav>