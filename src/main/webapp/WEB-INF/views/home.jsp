<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Trang chủ - Hệ thống quản lý hoạt động tình nguyện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .hero-section {
            background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)), url('https://images.unsplash.com/photo-1593113598332-cd288d649433?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1470&q=80');
            background-size: cover;
            background-position: center;
            height: calc(100vh - 56px); /* Trừ đi chiều cao của navbar */
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            color: white;
            margin-top: 56px; /* Thêm margin-top bằng chiều cao của navbar */
        }
        .hero-content {
            max-width: 800px;
            padding: 20px;
        }
        .hero-title {
            font-size: 3.5rem;
            font-weight: bold;
            margin-bottom: 1.5rem;
        }
        .hero-subtitle {
            font-size: 1.5rem;
            margin-bottom: 2rem;
        }
        .btn-hero {
            padding: 12px 30px;
            font-size: 1.2rem;
            margin: 0 10px;
        }
        .features-section {
            padding: 80px 0;
            background: white;
        }
        .feature-card {
            text-align: center;
            padding: 30px;
            margin-bottom: 30px;
            transition: transform 0.3s ease;
        }
        .feature-card:hover {
            transform: translateY(-10px);
        }
        .feature-icon {
            font-size: 48px;
            color: #28a745;
            margin-bottom: 20px;
        }
        @media (max-width: 768px) {
            .hero-title {
                font-size: 2.5rem;
            }
            .hero-subtitle {
                font-size: 1.2rem;
            }
            .btn-hero {
                padding: 10px 20px;
                font-size: 1rem;
                margin: 5px;
            }
        }
    </style>
</head>
<body>
    <!-- Include Navbar -->
    <jsp:include page="navbar.jsp" />

    <!-- Hero Section -->
    <section class="hero-section">
        <div class="hero-content">
            <h1 class="hero-title">Hệ Thống Quản Lý Hoạt Động Tình Nguyện</h1>
            <p class="hero-subtitle">Tham gia các hoạt động tình nguyện, lan tỏa yêu thương và xây dựng cộng đồng tốt đẹp hơn</p>
            <div>
                <a href="<c:url value='/login'/>" class="btn btn-primary btn-hero">
                    <i class="fas fa-sign-in-alt me-2"></i>Đăng nhập
                </a>
                <a href="<c:url value='/register'/>" class="btn btn-success btn-hero">
                    <i class="fas fa-user-plus me-2"></i>Đăng ký
                </a>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features-section">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <div class="feature-card">
                        <i class="fas fa-hands-helping feature-icon"></i>
                        <h3>Tình Nguyện Viên</h3>
                        <p>Tham gia các hoạt động tình nguyện ý nghĩa, phát triển kỹ năng và mở rộng mạng lưới.</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <i class="fas fa-calendar-check feature-icon"></i>
                        <h3>Quản Lý Hoạt Động</h3>
                        <p>Theo dõi và quản lý các hoạt động tình nguyện một cách hiệu quả và chuyên nghiệp.</p>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="feature-card">
                        <i class="fas fa-chart-line feature-icon"></i>
                        <h3>Báo Cáo & Thống Kê</h3>
                        <p>Xem báo cáo chi tiết và thống kê về các hoạt động tình nguyện.</p>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 