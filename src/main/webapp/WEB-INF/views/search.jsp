<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tìm kiếm - Quản lý tình nguyện viên</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .search-container {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            margin-bottom: 20px;
        }
        .result-container {
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        .nav-tabs .nav-link.active {
            background-color: #28a745;
            color: white;
            border: none;
        }
        .nav-tabs .nav-link {
            color: #28a745;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container my-5">
        <h2 class="text-center mb-4">Tìm kiếm nâng cao</h2>
        
        <!-- Tabs for different search types -->
        <ul class="nav nav-tabs mb-4" id="searchTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" data-bs-toggle="tab" data-bs-target="#sinhvien">
                    <i class="fas fa-user-graduate"></i> Sinh viên
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" data-bs-toggle="tab" data-bs-target="#hoatdong">
                    <i class="fas fa-calendar-alt"></i> Hoạt động
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" data-bs-toggle="tab" data-bs-target="#thamgia">
                    <i class="fas fa-hands-helping"></i> Tham gia
                </button>
            </li>
        </ul>

        <!-- Tab content -->
        <div class="tab-content">
            <!-- Sinh viên search -->
            <div class="tab-pane fade show active" id="sinhvien">
                <div class="search-container">
                    <form action="<c:url value='/search/sinhvien'/>" method="get" class="row g-3">
                        <div class="col-md-4">
                            <label class="form-label">MSSV</label>
                            <input type="text" name="mssv" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Họ tên</label>
                            <input type="text" name="hoTen" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Trường</label>
                            <select name="maTruong" class="form-select">
                                <option value="">Tất cả</option>
                                <c:forEach items="${truongs}" var="truong">
                                    <option value="${truong.maTruong}">${truong.tenTruong}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-search"></i> Tìm kiếm
                            </button>
                        </div>
                    </form>
                </div>
                
                <c:if test="${not empty sinhViens}">
                    <div class="result-container">
                        <h4 class="mb-3">Kết quả tìm kiếm</h4>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>MSSV</th>
                                        <th>Họ tên</th>
                                        <th>Email</th>
                                        <th>Trường</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${sinhViens}" var="sv">
                                        <tr>
                                            <td>${sv.mssv}</td>
                                            <td>${sv.hoTen}</td>
                                            <td>${sv.email}</td>
                                            <td>${sv.truong.tenTruong}</td>
                                            <td>
                                                <a href="<c:url value='/sinhvien/view/${sv.mssv}'/>" class="btn btn-sm btn-info">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>

            <!-- Hoạt động search -->
            <div class="tab-pane fade" id="hoatdong">
                <div class="search-container">
                    <form action="<c:url value='/search/hoatdong'/>" method="get" class="row g-3">
                        <div class="col-md-4">
                            <label class="form-label">Mã hoạt động</label>
                            <input type="text" name="maHD" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Tên hoạt động</label>
                            <input type="text" name="tenHD" class="form-control">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Tổ chức</label>
                            <select name="maToChuc" class="form-select">
                                <option value="">Tất cả</option>
                                <c:forEach items="${toChucs}" var="tc">
                                    <option value="${tc.maToChuc}">${tc.tenToChuc}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-search"></i> Tìm kiếm
                            </button>
                        </div>
                    </form>
                </div>
                
                <c:if test="${not empty hoatDongs}">
                    <div class="result-container">
                        <h4 class="mb-3">Kết quả tìm kiếm</h4>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Mã HD</th>
                                        <th>Tên hoạt động</th>
                                        <th>Địa điểm</th>
                                        <th>Thời gian</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${hoatDongs}" var="hd">
                                        <tr>
                                            <td>${hd.maHD}</td>
                                            <td>${hd.tenHD}</td>
                                            <td>${hd.diaDiem}</td>
                                            <td>
                                                <fmt:formatDate value="${hd.thoiGianBatDau}" pattern="dd/MM/yyyy HH:mm"/> - 
                                                <fmt:formatDate value="${hd.thoiGianKetThuc}" pattern="dd/MM/yyyy HH:mm"/>
                                            </td>
                                            <td>
                                                <a href="<c:url value='/hoatdong/view/${hd.maHD}'/>" class="btn btn-sm btn-info">
                                                    <i class="fas fa-eye"></i> Chi tiết
                                                </a>
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
                                            <a class="page-link" href="?page=${page-1}&maHD=${param.maHD}&tenHD=${param.tenHD}&maToChuc=${param.maToChuc}">
                                                <i class="fas fa-chevron-left"></i>
                                            </a>
                                        </li>
                                    </c:if>
                                    <li class="page-item active">
                                        <span class="page-link">${page + 1}</span>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link" href="?page=${page+1}&maHD=${param.maHD}&tenHD=${param.tenHD}&maToChuc=${param.maToChuc}">
                                            <i class="fas fa-chevron-right"></i>
                                        </a>
                                    </li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                </c:if>
            </div>

            <!-- Tham gia search -->
            <div class="tab-pane fade" id="thamgia">
                <div class="search-container">
                    <form action="<c:url value='/search/thamgia'/>" method="get" class="row g-3">
                        <div class="col-md-3">
                            <label class="form-label">MSSV</label>
                            <input type="text" name="mssv" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Mã hoạt động</label>
                            <input type="text" name="maHD" class="form-control">
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Trạng thái</label>
                            <select name="trangThai" class="form-select">
                                <option value="">Tất cả</option>
                                <option value="PENDING">Chờ duyệt</option>
                                <option value="APPROVED">Đã duyệt</option>
                                <option value="REJECTED">Từ chối</option>
                            </select>
                        </div>
                        <div class="col-md-3">
                            <label class="form-label">Xếp loại</label>
                            <select name="xepLoai" class="form-select">
                                <option value="">Tất cả</option>
                                <option value="Xuất sắc">Xuất sắc</option>
                                <option value="Tốt">Tốt</option>
                                <option value="Khá">Khá</option>
                                <option value="Trung bình">Trung bình</option>
                            </select>
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary">
                                <i class="fas fa-search"></i> Tìm kiếm
                            </button>
                        </div>
                    </form>
                </div>
                
                <c:if test="${not empty thamGias}">
                    <div class="result-container">
                        <h4 class="mb-3">Kết quả tìm kiếm</h4>
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>MSSV</th>
                                        <th>Họ tên</th>
                                        <th>Hoạt động</th>
                                        <th>Trạng thái</th>
                                        <th>Xếp loại</th>
                                        <th>Thao tác</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${thamGias}" var="tg">
                                        <tr>
                                            <td>${tg.sinhVien.mssv}</td>
                                            <td>${tg.sinhVien.hoTen}</td>
                                            <td>${tg.hoatDong.tenHD}</td>
                                            <td>
                                                <span class="badge bg-${tg.trangThai == 'APPROVED' ? 'success' : tg.trangThai == 'PENDING' ? 'warning' : 'danger'}">
                                                    ${tg.trangThai}
                                                </span>
                                            </td>
                                            <td>${tg.xepLoai}</td>
                                            <td>
                                                <a href="<c:url value='/thamgia/view/${tg.mssv}/${tg.maHD}'/>" class="btn btn-sm btn-info">
                                                    <i class="fas fa-eye"></i>
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <jsp:include page="footer.jsp"/>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
    <script>
        AOS.init({duration: 1000});
    </script>
</body>
</html> 