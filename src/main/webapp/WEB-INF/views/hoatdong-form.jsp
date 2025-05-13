<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Thêm Hoạt Động</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Thêm Hoạt Động</h1>
    <form:form action="/hoatdong/save" modelAttribute="hoatDong" method="post">
        <div class="mb-3">
            <label>Mã HD</label>
            <form:input path="maHD" class="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label>Tên HD</label>
            <form:input path="tenHD" class="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label>Đơn Vị Tổ Chức</label>
            <form:input path="donViToChuc" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Địa Điểm</label>
            <form:input path="diaDiem" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Thời Gian Bắt Đầu</label>
            <form:input path="thoiGianBatDau" type="datetime-local" class="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label>Thời Gian Kết Thúc</label>
            <form:input path="thoiGianKetThuc" type="datetime-local" class="form-control" required="true"/>
        </div>
        <button type="submit" class="btn btn-primary">Lưu</button>
    </form:form>
</div>
</body>
</html>