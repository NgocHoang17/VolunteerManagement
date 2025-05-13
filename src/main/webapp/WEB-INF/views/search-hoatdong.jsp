<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tìm Kiếm Hoạt Động</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Tìm Kiếm Hoạt Động</h1>
    <form action="/search/hoatdong" method="get">
        <div class="mb-3">
            <label>Mã HD</label>
            <input type="text" name="maHD" class="form-control" required>
        </div>
        <button type="submit" class="btn btn-primary">Tìm</button>
    </form>

    <c:if test="${hoatDong != null}">
        <h2>Thông tin Hoạt Động</h2>
        <p>Mã HD: ${hoatDong.maHD}</p>
        <p>Tên HD: ${hoatDong.tenHD}</p>
        <p>Địa Điểm: ${hoatDong.diaDiem}</p>

        <h2>Danh sách Sinh Viên Tham Gia</h2>
        <table class="table">
            <thead>
            <tr>
                <th>MSSV</th>
                <th>Họ Tên</th>
                <th>Số Giờ</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="tg" items="${thamGias}">
                <tr>
                    <td>${tg.sinhVien.mssv}</td>
                    <td>${tg.sinhVien.hoTen}</td>
                    <td>${tg.soGioThamGia}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
</body>
</html>