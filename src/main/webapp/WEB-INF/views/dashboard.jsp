<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="/static/style.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Dashboard Quản Lý Tình Nguyện</h1>
        <p class="lead text-muted">Hoạt động phổ biến nhất: <span class="text-primary">${mostPopularActivity}</span></p>
    </div>

    <div class="row">
        <div class="col-md-6 mb-4" data-aos="fade-up" data-aos-delay="100">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h2 class="h4 mb-0">Thống Kê Sinh Viên</h2>
                </div>
                <div class="card-body">
                    <canvas id="sinhVienChart"></canvas>
                </div>
            </div>
        </div>
        <div class="col-md-6 mb-4" data-aos="fade-up" data-aos-delay="200">
            <div class="card shadow-sm">
                <div class="card-header bg-primary text-white">
                    <h2 class="h4 mb-0">Thống Kê Hoạt Động</h2>
                </div>
                <div class="card-body">
                    <canvas id="hoatDongChart"></canvas>
                </div>
            </div>
        </div>
    </div>

    <sec:authorize access="hasRole('ROLE_ADMIN')">
        <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="300">
            <div class="card-header bg-primary text-white">
                <h2 class="h4 mb-0">Báo Cáo</h2>
            </div>
            <div class="card-body">
                <a href="/report/export" class="btn btn-primary"><i class="fas fa-download"></i> Xuất Báo Cáo Excel</a>
            </div>
        </div>
    </sec:authorize>
</div>

<jsp:include page="footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script src="/static/js/scripts.js"></script>
<script>
    AOS.init({duration: 1000});

    // Sinh viên chart
    new Chart(document.getElementById('sinhVienChart'), {
        type: 'bar',
        data: {
            labels: ['Miền Bắc', 'Miền Trung', 'Miền Nam'],
            datasets: [{
                label: 'Số Sinh Viên',
                data: [120, 80, 200], // Thay bằng dữ liệu thực tế từ server
                backgroundColor: ['#007bff', '#28a745', '#dc3545']
            }]
        },
        options: {
            scales: {
                y: { beginAtZero: true }
            }
        }
    });

    // Hoạt động chart
    new Chart(document.getElementById('hoatDongChart'), {
        type: 'pie',
        data: {
            labels: ['Tình Nguyện Mùa Hè', 'Ngày Xanh', 'Khác'],
            datasets: [{
                label: 'Số Hoạt Động',
                data: [50, 30, 20], // Thay bằng dữ liệu thực tế từ server
                backgroundColor: ['#007bff', '#28a745', '#dc3545']
            }]
        }
    });
</script>
</body>
</html>