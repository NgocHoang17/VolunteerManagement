<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Thêm Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h1>Thêm Sinh Viên</h1>
    <form:form action="/sinhvien/save" modelAttribute="sinhVien" method="post">
        <div class="mb-3">
            <label>MSSV</label>
            <form:input path="mssv" class="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label>Họ Tên</label>
            <form:input path="hoTen" class="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label>Email</label>
            <form:input path="email" class="form-control" required="true"/>
        </div>
        <div class="mb-3">
            <label>Số ĐT</label>
            <form:input path="soDT" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Lớp</label>
            <form:input path="lop" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Khoa</label>
            <form:input path="khoa" class="form-control"/>
        </div>
        <div class="mb-3">
            <label>Trường</label>
            <form:input path="truong" class="form-control"/>
        </div>
        <button type="submit" class="btn btn-primary">Lưu</button>
    </form:form>
</div>
</body>
</html>