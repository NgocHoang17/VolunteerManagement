<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Thêm/Chỉnh Sửa Đánh Giá</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="/static/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Thêm/Chỉnh Sửa Đánh Giá</h1>
    </div>

    <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="100">
        <div class="card-body">
            <form:form action="/danhgia/save" modelAttribute="danhGia" method="post" id="danhGiaForm">
                <form:hidden path="mssv"/>
                <form:hidden path="maHD"/>
                <div class="mb-3">
                    <label class="form-label">Điểm (1-10)</label>
                    <form:input path="diem" type="number" class="form-control" required="true" min="1" max="10"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Nhận Xét</label>
                    <form:textarea path="nhanXet" class="form-control" rows="5"/>
                </div>
                <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu</button>
                <a href="/search/sinhvien?mssv=${danhGia.mssv}" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
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