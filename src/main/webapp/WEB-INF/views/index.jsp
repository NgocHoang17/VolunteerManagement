<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Hệ Thống Quản Lý Tình Nguyện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="/static/css/style.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Hệ Thống Quản Lý Hoạt Động Tình Nguyện</h1>
    <p>Hoạt Động Phổ Biến Nhất: ${mostPopularActivity}</p>
    <a href="/sinhvien/new" class="btn btn-primary">Thêm Sinh Viên</a>
    <a href="/hoatdong/new" class="btn btn-primary">Thêm Hoạt Động</a>
    <a href="/thamgia/new" class="btn btn-primary">Thêm Tham Gia</a>

    <h2>Danh sách Sinh Viên</h2>
    <table class="table">
        <thead>
        <tr>
            <th>MSSV</th>
            <th>Họ Tên</th>
            <th>Email</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="sv" items="${sinhViens}">
            <tr>
                <td>${sv.mssv}</td>
                <td>${sv.hoTen}</td>
                <td>${sv.email}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <h2>Danh sách Hoạt Động</h2>
    <table class="table">
        <thead>
        <tr>
            <th>Mã HD</th>
            <th>Tên HD</th>
            <th>Địa Điểm</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="hd" items="${hoatDongs}">
            <tr>
                <td>${hd.maHD}</td>
                <td>${hd.tenHD}</td>
                <td>${hd.diaDiem}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>