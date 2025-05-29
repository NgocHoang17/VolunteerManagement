<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">
            <h1 class="display-4 mb-4">Hệ thống quản lý tình nguyện viên</h1>
            <p class="lead mb-4">Nơi kết nối những trái tim nhiệt huyết với các hoạt động tình nguyện ý nghĩa.</p>
            
            <sec:authorize access="!isAuthenticated()">
                <div class="mt-4">
                    <a href="<c:url value='/auth/login'/>" class="btn btn-primary btn-lg me-3">
                        <i class="fas fa-sign-in-alt"></i> Đăng nhập
                    </a>
                    <a href="<c:url value='/auth/register'/>" class="btn btn-success btn-lg">
                        <i class="fas fa-user-plus"></i> Đăng ký
                    </a>
                </div>
            </sec:authorize>
            
            <sec:authorize access="isAuthenticated()">
                <div class="mt-4">
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <a href="<c:url value='/admin/dashboard'/>" class="btn btn-primary btn-lg me-3">
                            <i class="fas fa-tachometer-alt"></i> Quản trị hệ thống
                        </a>
                    </sec:authorize>
                    
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <a href="<c:url value='/student/dashboard'/>" class="btn btn-success btn-lg me-3">
                            <i class="fas fa-user-graduate"></i> Trang sinh viên
                        </a>
                    </sec:authorize>
                    
                    <sec:authorize access="hasRole('ROLE_ORGANIZATION')">
                        <a href="<c:url value='/organization/dashboard'/>" class="btn btn-info btn-lg me-3">
                            <i class="fas fa-building"></i> Trang tổ chức
                        </a>
                    </sec:authorize>
                </div>
            </sec:authorize>
        </div>
    </div>

    <!-- Featured Activities -->
    <div class="row mt-5">
        <div class="col-12">
            <h2 class="text-center mb-4">Hoạt động nổi bật</h2>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Hiến máu nhân đạo</h5>
                    <p class="card-text">Chương trình hiến máu tình nguyện, góp phần cứu giúp người bệnh.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Dạy học tình nguyện</h5>
                    <p class="card-text">Chương trình dạy học miễn phí cho trẻ em có hoàn cảnh khó khăn.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Bảo vệ môi trường</h5>
                    <p class="card-text">Hoạt động dọn dẹp, làm sạch môi trường và trồng cây xanh.</p>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
.card {
    transition: transform 0.3s;
    margin-bottom: 1rem;
}

.card:hover {
    transform: translateY(-5px);
}

.btn-lg {
    padding: 1rem 2rem;
}
</style> 