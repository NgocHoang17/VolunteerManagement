<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Hệ Thống Quản Lý Tình Nguyện</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome cho icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <!-- AOS Animation -->
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <!-- Custom CSS -->
    <link href="/static/style.css" rel="stylesheet">
</head>
<body>
<!-- Thanh điều hướng -->
<nav class="navbar navbar-expand-lg navbar-dark bg-gradient sticky-top">
    <div class="container">
        <a class="navbar-brand" href="/">Quản Lý Tình Nguyện</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/sinhvien/new">Thêm Sinh Viên</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/hoatdong/new">Thêm Hoạt Động</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/thamgia/new">Thêm Tham Gia</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Nội dung chính -->
<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Hệ Thống Quản Lý Hoạt Động Tình Nguyện</h1>
        <p class="lead text-muted">Hoạt động phổ biến nhất: <span class="text-primary">${mostPopularActivity}</span></p>
    </div>

    <!-- Tìm kiếm -->
    <div class="mb-4" data-aos="fade-up" data-aos-delay="100">
        <div class="input-group">
            <input type="text" id="searchInput" class="form-control" placeholder="Tìm kiếm sinh viên hoặc hoạt động...">
            <button class="btn btn-primary" type="button"><i class="fas fa-search"></i> Tìm</button>
        </div>
    </div>

    <!-- Danh sách Sinh Viên -->
    <div class="card shadow-sm mb-5" data-aos="fade-up" data-aos-delay="200">
        <div class="card-header bg-primary text-white">
            <h2 class="h4 mb-0">Danh Sách Sinh Viên</h2>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>MSSV</th>
                        <th>Họ Tên</th>
                        <th>Email</th>
                        <th>Hoạt động tình nguyện</th>
                    </tr>
                    </thead>
                    <tbody id="sinhVienTable">
                    <c:forEach var="sv" items="${sinhViens}">
                        <tr>
                            <td>${sv.mssv}</td>
                            <td>${sv.hoTen}</td>
                            <td>${sv.email}</td>
                            <td>
                                <a href="/sinhvien/edit/${sv.mssv}" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                                <a href="/sinhvien/delete/${sv.mssv}" class="btn btn-sm btn-danger" onclick="return confirm('Xóa sinh viên này?')"><i class="fas fa-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Danh sách Hoạt Động -->
    <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="300">
        <div class="card-header bg-primary text-white">
            <h2 class="h4 mb-0">Danh Sách Hoạt Động</h2>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Mã HD</th>
                        <th>Tên HD</th>
                        <th>Địa Điểm</th>
                        <th>Hoạt động tình nguyện</th>
                    </tr>
                    </thead>
                    <tbody id="hoatDongTable">
                    <c:forEach var="hd" items="${hoatDongs}">
                        <tr>
                            <td>${hd.maHD}</td>
                            <td>${hd.tenHD}</td>
                            <td>${hd.diaDiem}</td>
                            <td>
                                <a href="/hoatdong/edit/${hd.maHD}" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                                <a href="/hoatdong/delete/${hd.maHD}" class="btn btn-sm btn-danger" onclick="return confirm('Xóa hoạt động này?')"><i class="fas fa-trash"></i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Toast thông báo -->
<div class="toast-container position-fixed bottom-0 end-0 p-3">
    <div id="successToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
        <div class="toast-header bg-success text-white">
            <strong class="me-auto">Thành công</strong>
            <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
        </div>
        <div class="toast-body">
            Thao tác thành công!
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- AOS Animation -->
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script>
    AOS.init({ duration: 1000 });

    // Tìm kiếm client-side
    document.getElementById('searchInput').addEventListener('input', function() {
        const searchValue = this.value.toLowerCase();
        const sinhVienRows = document.querySelectorAll('#sinhVienTable tr');
        const hoatDongRows = document.querySelectorAll('#hoatDongTable tr');

        sinhVienRows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(searchValue) ? '' : 'none';
        });

        hoatDongRows.forEach(row => {
            const text = row.textContent.toLowerCase();
            row.style.display = text.includes(searchValue) ? '' : 'none';
        });
    });

    // Hiển thị toast nếu có thông báo thành công
    <c:if test="${not empty successMessage}">
    const toast = new bootstrap.Toast(document.getElementById('successToast'));
    toast.show();
    </c:if>
</script>
</body>
</html>