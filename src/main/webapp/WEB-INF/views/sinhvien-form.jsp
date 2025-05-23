<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thêm/Chỉnh Sửa Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="/static/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">${sinhVien.mssv == null ? 'Thêm Sinh Viên' : 'Chỉnh Sửa Sinh Viên'}</h1>
    </div>

    <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="100">
        <div class="card-body">
            <form:form action="/sinhvien/save" modelAttribute="sinhVien" method="post" id="sinhVienForm">
                <form:hidden path="mssv"/>
                <div class="mb-3">
                    <label class="form-label">MSSV</label>
                    <form:input path="mssv" class="form-control" required="true" disabled="${sinhVien.mssv != null}"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Họ Tên</label>
                    <form:input path="hoTen" class="form-control" required="true"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <form:input path="email" class="form-control" type="email" required="true"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Số ĐT</label>
                    <form:input path="soDT" class="form-control" pattern="[0-9]{10}"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Lớp</label>
                    <form:input path="lop" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Khoa</label>
                    <form:input path="khoa" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Trường</label>
                    <form:select path="truong.maTruong" class="form-select" required="true">
                        <c:forEach var="truong" items="${truongs}">
                            <form:option value="${truong.maTruong}">${truong.tenTruong}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu</button>
                <a href="/sinhvien" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
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