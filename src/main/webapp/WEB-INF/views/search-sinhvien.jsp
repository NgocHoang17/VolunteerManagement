<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tìm Kiếm Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Tìm Kiếm Sinh Viên</h1>
    <form action="/search/sinhvien" method="get">
        <div class="mb-3">
            <label>MSSV</label>
            <input type="text" name="mssv" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Tìm</button>
    </form>

    <c:if test="${sinhVien != null}">
        <h2>Thông tin Sinh Viên</h2>
        <p>MSSV: ${sinhVien.mssv}</p>
        <p>Họ Tên: ${sinhVien.hoTen}</p>
        <p>Email: ${sinhVien.email}</p>
        <p>Tổng Giờ Tình Nguyện: ${totalHours}</p>

        <h2>Các Hoạt Động Tham Gia</h2>
        <table class="table">
            <thead>
            <tr>
                <th>Mã HD</th>
                <th>Tên HD</th>
                <th>Số Giờ</th>
                <th>Xếp Loại</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="tg" items="${thamGias}">
                <tr>
                    <td>${tg.hoatDong.maHD}</td>
                    <td>${tg.hoatDong.tenHD}</td>
                    <td>${tg.soGioThamGia}</td>
                    <td>${tg.xepLoai}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>