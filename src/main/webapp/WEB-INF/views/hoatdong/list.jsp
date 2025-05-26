<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Danh Sách Hoạt Động</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="<c:url value='/static/style.css'/>" rel="stylesheet">
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Danh Sách Hoạt Động</h1>
    </div>

    <!-- Tìm kiếm -->
    <form action="<c:url value='/manage/hoatdong'/>" method="get" class="mb-4" data-aos="fade-up" data-aos-delay="100">
        <div class="input-group">
            <input type="text" name="keyword" class="form-control" placeholder="Tìm theo tên hoặc địa điểm..." value="${keyword}">
            <select name="maToChuc" class="form-select">
                <option value="">Chọn tổ chức</option>
                <c:forEach var="toChuc" items="${toChucs}">
                    <option value="${toChuc.maToChuc}" ${maToChuc == toChuc.maToChuc ? 'selected' : ''}>${toChuc.tenToChuc}</option>
                </c:forEach>
            </select>
            <button class="btn btn-primary" type="submit"><i class="fas fa-search"></i> Tìm</button>
        </div>
    </form>

    <!-- Danh sách -->
    <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="200">
        <div class="card-header bg-primary text-white d-flex justify-content-between align-items-center">
            <h2 class="h4 mb-0">Danh Sách Hoạt Động</h2>
            <a href="<c:url value='/manage/hoatdong/new'/>" class="btn btn-light">
                <i class="fas fa-plus"></i> Thêm Hoạt Động
            </a>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>Mã HD</th>
                        <th>Tên HD</th>
                        <th>Địa Điểm</th>
                        <th>Tổ Chức</th>
                        <th>Thời Gian</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="hd" items="${hoatDongs}">
                        <tr>
                            <td>${hd.maHD}</td>
                            <td>${hd.tenHD}</td>
                            <td>${hd.diaDiem}</td>
                            <td>${hd.toChuc.tenToChuc}</td>
                            <td>
                                <fmt:formatDate value="${hd.thoiGianBatDau}" pattern="dd/MM/yyyy HH:mm"/> - 
                                <fmt:formatDate value="${hd.thoiGianKetThuc}" pattern="dd/MM/yyyy HH:mm"/>
                            </td>
                            <td>
                                <a href="<c:url value='/manage/hoatdong/${hd.maHD}/edit'/>" class="btn btn-sm btn-warning">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="<c:url value='/manage/hoatdong/${hd.maHD}/delete'/>" 
                                       class="btn btn-sm btn-danger" 
                                       onclick="return confirm('Bạn có chắc chắn muốn xóa hoạt động này?')">
                                        <i class="fas fa-trash"></i>
                                    </a>
                                </sec:authorize>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- Phân trang -->
            <div class="d-flex justify-content-center mt-4">
                <nav aria-label="Page navigation">
                    <ul class="pagination">
                        <c:if test="${page > 0}">
                            <li class="page-item">
                                <a class="page-link" href="?page=${page-1}&keyword=${keyword}&maToChuc=${maToChuc}">
                                    <i class="fas fa-chevron-left"></i>
                                </a>
                            </li>
                        </c:if>
                        <li class="page-item active">
                            <span class="page-link">${page + 1}</span>
                        </li>
                        <li class="page-item">
                            <a class="page-link" href="?page=${page+1}&keyword=${keyword}&maToChuc=${maToChuc}">
                                <i class="fas fa-chevron-right"></i>
                            </a>
                        </li>
                    </ul>
                </nav>
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