<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Danh Sách Sinh Viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="<c:url value='/static/style.css'/>" rel="stylesheet">
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Danh Sách Sinh Viên</h1>
    </div>

    <!-- Tìm kiếm -->
    <form action="<c:url value='/manage/sinhvien'/>" method="get" class="mb-4" data-aos="fade-up" data-aos-delay="100">
        <div class="input-group">
            <input type="text" name="keyword" class="form-control" placeholder="Tìm theo tên hoặc email..." value="${keyword}">
            <select name="maTruong" class="form-select">
                <option value="">Chọn trường</option>
                <c:forEach var="truong" items="${truongs}">
                    <option value="${truong.maTruong}" ${maTruong == truong.maTruong ? 'selected' : ''}>${truong.tenTruong}</option>
                </c:forEach>
            </select>
            <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i> Tìm</button>
        </div>
    </form>

    <!-- Danh sách -->
    <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="200">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <h2 class="h4 mb-0">Danh Sách Sinh Viên</h2>
            <a href="<c:url value='/manage/sinhvien/new'/>" class="btn btn-light">
                <i class="fas fa-plus"></i> Thêm Sinh Viên
            </a>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>MSSV</th>
                        <th>Họ Tên</th>
                        <th>Email</th>
                        <th>Trường</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="sv" items="${sinhViens}">
                        <tr>
                            <td>${sv.mssv}</td>
                            <td>${sv.hoTen}</td>
                            <td>${sv.email}</td>
                            <td>${sv.truong.tenTruong}</td>
                            <td>
                                <a href="<c:url value='/manage/sinhvien/${sv.mssv}/edit'/>" class="btn btn-sm btn-warning">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="<c:url value='/manage/sinhvien/${sv.mssv}/delete'/>" 
                                       class="btn btn-sm btn-danger" 
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên này?')">
                                        <i class="fas fa-trash"></i>
                                    </a>
                                </sec:authorize>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script src="<c:url value='/static/js/scripts.js'/>"></script>
<script>
    AOS.init({duration: 1000});
</script>
</body>
</html> 