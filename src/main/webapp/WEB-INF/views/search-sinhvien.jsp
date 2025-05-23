<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tìm Kiếm Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="/static/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Tìm Kiếm Sinh Viên</h1>
    </div>

    <form action="/search/sinhvien" method="get" class="mb-4" data-aos="fade-up" data-aos-delay="100">
        <div class="input-group">
            <input type="text" name="mssv" class="form-control" placeholder="Nhập MSSV" required>
            <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i> Tìm</button>
        </div>
    </form>

    <c:if test="${sinhVien != null}">
        <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="200">
            <div class="card-header bg-primary text-white">
                <h2 class="h4 mb-0">Thông tin Sinh Viên</h2>
            </div>
            <div class="card-body">
                <p><strong>MSSV:</strong> ${sinhVien.mssv}</p>
                <p><strong>Họ Tên:</strong> ${sinhVien.hoTen}</p>
                <p><strong>Email:</strong> ${sinhVien.email}</p>
                <p><strong>Tổng Giờ Tình Nguyện:</strong> ${totalHours}</p>

                <h3 class="mt-4">Các Hoạt Động Tham Gia</h3>
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th>Mã HD</th>
                            <th>Tên HD</th>
                            <th>Số Giờ</th>
                            <th>Xếp Loại</th>
                            <th>Trạng Thái</th>
                            <th>Hành động</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="tg" items="${thamGias}">
                            <tr>
                                <td>${tg.hoatDong.maHD}</td>
                                <td>${tg.hoatDong.tenHD}</td>
                                <td>${tg.soGioThamGia}</td>
                                <td>${tg.xepLoai}</td>
                                <td>${tg.trangThai}</td>
                                <td>
                                    <c:if test="${tg.trangThai == 'APPROVED'}">
                                        <a href="/thamgia/certificate?mssv=${tg.mssv}&maHD=${tg.maHD}" class="btn btn-sm btn-success"><i class="fas fa-file-pdf"></i> Xuất PDF</a>
                                    </c:if>
                                    <a href="/danhgia/new?mssv=${tg.mssv}&maHD=${tg.maHD}" class="btn btn-sm btn-info"><i class="fas fa-star"></i> Đánh Giá</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script src="/static/js/scripts.js"></script>
<script>
    AOS.init({duration: 1000});
</script>
</body>
</html>