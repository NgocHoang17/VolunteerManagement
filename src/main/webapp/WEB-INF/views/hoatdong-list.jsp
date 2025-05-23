<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Danh Sách Hoạt Động</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="/static/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">Danh Sách Hoạt Động</h1>
    </div>

    <!-- Tìm kiếm -->
    <form class="mb-4" data-aos="fade-up" data-aos-delay="100">
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
        <div class="card-header bg-primary text-white">
            <h2 class="h4 mb-0">Danh Sách Hoạt Động</h2>
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
                    <c:forEach var="hd" items="${hoatDongs.content}">
                        <tr>
                            <td>${hd.maHD}</td>
                            <td>${hd.tenHD}</td>
                            <td>${hd.diaDiem}</td>
                            <td>${hd.toChuc.tenToChuc}</td>
                            <td>${hd.thoiGianBatDau} - ${hd.thoiGianKetThuc}</td>
                            <td>
                                <a href="/hoatdong/edit/${hd.maHD}" class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></a>
                                <sec:authorize access="hasRole('ROLE_ADMIN')">
                                    <a href="/hoatdong/delete/${hd.maHD}" class="btn btn-sm btn-danger" onclick="return confirm('Xóa hoạt động này?')"><i class="fas fa-trash"></i></a>
                                </sec:authorize>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- Phân trang -->
            <nav>
                <ul class="pagination justify-content-center">
                    <c:if test="${hoatDongs.hasPrevious()}">
                        <li class="page-item"><a class="page-link" href="?page=${hoatDongs.number - 1}&keyword=${keyword}&maToChuc=${maToChuc}">Trước</a></li>
                    </c:if>
                    <c:forEach begin="0" end="${hoatDongs.totalPages - 1}" var="i">
                        <li class="page-item ${hoatDongs.number == i ? 'active' : ''}">
                            <a class="page-link" href="?page=${i}&keyword=${keyword}&maToChuc=${maToChuc}">${i + 1}</a>
                        </li>
                    </c:forEach>
                    <c:if test="${hoatDongs.hasNext()}">
                        <li class="page-item"><a class="page-link" href="?page=${hoatDongs.number + 1}&keyword=${keyword}&maToChuc=${maToChuc}">Sau</a></li>
                    </c:if>
                </ul>
            </nav>
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