<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ - Quản lý tình nguyện viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .navbar {
            background-color: #28a745;
        }
        .navbar-brand, .nav-link {
            color: white !important;
        }
        .nav-link:hover {
            color: #f8f9fa !important;
        }
        .activity-card {
            transition: transform 0.3s;
            margin-bottom: 20px;
        }
        .activity-card:hover {
            transform: translateY(-5px);
        }
        .activity-image {
            height: 200px;
            object-fit: cover;
        }
    </style>
</head>
<body>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container">
            <a class="navbar-brand" href="<c:url value='/'/>">
                <i class="fas fa-hands-helping"></i> Quản lý tình nguyện
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/'/>">
                            <i class="fas fa-home"></i> Trang chủ
                        </a>
                    </li>
                    <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_STUDENT')">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown">
                                <i class="fas fa-tasks"></i> Quản lý
                            </a>
                            <ul class="dropdown-menu">
                                <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')">
                                    <li><a class="dropdown-item" href="<c:url value='/sinhvien'/>">Quản lý sinh viên</a></li>
                                    <li><a class="dropdown-item" href="<c:url value='/hoatdong'/>">Quản lý hoạt động</a></li>
                                </sec:authorize>
                                <li><a class="dropdown-item" href="<c:url value='/thamgia'/>">Đăng ký tham gia</a></li>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <li><hr class="dropdown-divider"></li>
                                    <li><a class="dropdown-item" href="<c:url value='/report'/>">Báo cáo thống kê</a></li>
                                </sec:authorize>
                            </ul>
                        </li>
                    </sec:authorize>
                </ul>
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='/logout'/>">
                            <i class="fas fa-sign-out-alt"></i> Đăng xuất
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Main Content -->
    <div class="container my-5">
        <h1 class="text-center mb-5">Hoạt động tình nguyện tiêu biểu</h1>
        
        <div class="row">
            <!-- Hoạt động 1 -->
            <div class="col-md-4">
                <div class="card activity-card">
                    <img src="<c:url value='/resources/images/activity1.jpg'/>" class="card-img-top activity-image" alt="Hiến máu nhân đạo">
                    <div class="card-body">
                        <h5 class="card-title">Hiến máu nhân đạo</h5>
                        <p class="card-text">Chương trình hiến máu tình nguyện, góp phần cứu giúp những người bệnh cần máu điều trị.</p>
                        <a href="#" class="btn btn-primary">Xem chi tiết</a>
                    </div>
                </div>
            </div>
            
            <!-- Hoạt động 2 -->
            <div class="col-md-4">
                <div class="card activity-card">
                    <img src="<c:url value='/resources/images/activity2.jpg'/>" class="card-img-top activity-image" alt="Dạy học tình nguyện">
                    <div class="card-body">
                        <h5 class="card-title">Dạy học tình nguyện</h5>
                        <p class="card-text">Chương trình dạy học miễn phí cho trẻ em có hoàn cảnh khó khăn.</p>
                        <a href="#" class="btn btn-primary">Xem chi tiết</a>
                    </div>
                </div>
            </div>
            
            <!-- Hoạt động 3 -->
            <div class="col-md-4">
                <div class="card activity-card">
                    <img src="<c:url value='/resources/images/activity3.jpg'/>" class="card-img-top activity-image" alt="Bảo vệ môi trường">
                    <div class="card-body">
                        <h5 class="card-title">Bảo vệ môi trường</h5>
                        <p class="card-text">Hoạt động dọn dẹp, làm sạch môi trường và trồng cây xanh.</p>
                        <a href="#" class="btn btn-primary">Xem chi tiết</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>