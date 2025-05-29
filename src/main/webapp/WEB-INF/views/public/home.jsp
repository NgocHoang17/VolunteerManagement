<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
.hero-section {
    background: linear-gradient(135deg, #0143a3, #0273d4);
    color: white;
    padding: 100px 0;
    margin-top: -24px;
}

.feature-card {
    border: none;
    transition: transform 0.3s;
    margin-bottom: 30px;
}

.feature-card:hover {
    transform: translateY(-10px);
}

.feature-icon {
    font-size: 2.5rem;
    margin-bottom: 20px;
    color: #0143a3;
}

.btn-primary {
    background-color: #0143a3;
    border-color: #0143a3;
    padding: 12px 30px;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 1px;
}

.btn-success {
    padding: 12px 30px;
    font-weight: 600;
    text-transform: uppercase;
    letter-spacing: 1px;
}

.lead {
    font-size: 1.4rem;
    font-weight: 300;
}
</style>

<!-- Hero Section -->
<section class="hero-section">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-10 text-center">
                <h1 class="display-3 mb-4">Hệ thống quản lý tình nguyện viên</h1>
                <p class="lead mb-5">Nơi kết nối những trái tim nhiệt huyết với các hoạt động tình nguyện ý nghĩa</p>
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
            </div>
        </div>
    </div>
</section>

<!-- Features Section -->
<section class="py-5">
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card feature-card text-center p-4">
                    <div class="card-body">
                        <i class="fas fa-hands-helping feature-icon"></i>
                        <h3 class="card-title">Tham gia hoạt động</h3>
                        <p class="card-text">Dễ dàng tìm kiếm và đăng ký tham gia các hoạt động tình nguyện phù hợp.</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card feature-card text-center p-4">
                    <div class="card-body">
                        <i class="fas fa-certificate feature-icon"></i>
                        <h3 class="card-title">Chứng nhận điểm</h3>
                        <p class="card-text">Tự động cập nhật và quản lý điểm tình nguyện cho mỗi hoạt động tham gia.</p>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card feature-card text-center p-4">
                    <div class="card-body">
                        <i class="fas fa-users feature-icon"></i>
                        <h3 class="card-title">Kết nối cộng đồng</h3>
                        <p class="card-text">Xây dựng mạng lưới tình nguyện viên và tổ chức rộng khắp.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-8 text-center">
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