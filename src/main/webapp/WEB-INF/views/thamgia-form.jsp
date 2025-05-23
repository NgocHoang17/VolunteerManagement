<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thêm Tham Gia</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="/static/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Thêm Tham Gia</h1>
    </div>

    <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="100">
        <div class="card-body">
            <form:form action="/thamgia/save" modelAttribute="thamGia" method="post" id="thamGiaForm">
                <div class="mb-3">
                    <label class="form-label">Sinh Viên</label>
                    <form:select path="mssv" class="form-select" required="true">
                        <c:forEach var="sv" items="${sinhViens}">
                            <form:option value="${sv.mssv}">${sv.hoTen} (${sv.mssv})</form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Hoạt Động</label>
                    <form:select path="maHD" class="form-select" required="true">
                        <c:forEach var="hd" items="${hoatDongs}">
                            <form:option value="${hd.maHD}">${hd.tenHD} (${hd.maHD})</form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Số Giờ Tham Gia</label>
                    <form:input path="soGioThamGia" type="number" class="form-control" required="true" min="1"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Xếp Loại</label>
                    <form:input path="xepLoai" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Chứng Nhận</label>
                    <form:input path="chungNhan" class="form-control"/>
                </div>
                <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu</button>
                <a href="/thamgia" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
            </form:form>
        </div>
    </div>
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