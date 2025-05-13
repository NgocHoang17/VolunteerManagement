<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thêm Tham Gia</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Thêm Tham Gia</h1>
    <form:form action="/thamgia/save" modelAttribute="thamGia" method="post">
        <div class="mb-3">
            <label>Sinh Viên</label>
            <form:select path="mssv" class="form-control" required="true">
                <c:forEach var="sv" items="${sinhViens}">
                    <form:option value="${sv.mssv}">${sv.hoTen} (${sv.mssv})</form:option>
                </c:forEach>
            </form:select>
        </div>
        <div class="mb-3">
            <label>Hoạt Động</label>
            <form:select path="maHD" class="form-control" required="true">
                <c:forEach var="hd" items="${hoatDongs}">
                    <form:option value="${hd.maHD}">${hd.tenHD} (${hd.maHD})</form:option>
                </c:forEach>
            </form:select>
        </div>
        <div class="mb-3">
            <label>Số Giờ Tham Gia</label>
            <form:input path="soGioThamGia" type="number" class="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label>Xếp Loại</label>
            <form:input path="xepLoai" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Chứng Nhận</label>
            <form:input path="chungNhan" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-primary">Lưu</button>
    </form:form>
</div>
</body>
</html>