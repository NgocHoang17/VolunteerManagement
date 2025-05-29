<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Statistics Cards -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card border-primary">
            <div class="card-body text-center">
                <h3 class="card-title text-primary">${totalActivities}</h3>
                <p class="card-text">Hoạt Động Đã Tham Gia</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-success">
            <div class="card-body text-center">
                <h3 class="card-title text-success">${totalHours}</h3>
                <p class="card-text">Tổng Số Giờ</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-info">
            <div class="card-body text-center">
                <h3 class="card-title text-info">${totalPoints}</h3>
                <p class="card-text">Điểm Tích Lũy</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-warning">
            <div class="card-body text-center">
                <h3 class="card-title text-warning">${totalCertificates}</h3>
                <p class="card-text">Chứng Nhận</p>
            </div>
        </div>
    </div>
</div>

<!-- Activity History -->
<div class="card">
    <div class="card-header">
        <h5 class="card-title mb-0">Lịch Sử Hoạt Động</h5>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Hoạt Động</th>
                        <th>Thời Gian</th>
                        <th>Vai Trò</th>
                        <th>Số Giờ</th>
                        <th>Điểm</th>
                        <th>Đánh Giá</th>
                        <th>Trạng Thái</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${participations}" var="participation">
                        <tr>
                            <td>
                                <div class="d-flex align-items-center">
                                    <img src="${participation.hoatDong.hinhAnhUrl}" 
                                         class="rounded me-2" style="width: 40px; height: 40px; object-fit: cover;">
                                    <div>
                                        <div class="fw-bold">${participation.hoatDong.tenHoatDong}</div>
                                        <small class="text-muted">
                                            <i class="fas fa-building"></i> ${participation.hoatDong.toChuc.tenToChuc}
                                        </small>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div><fmt:formatDate value="${participation.hoatDong.ngayBatDau}" pattern="dd/MM/yyyy"/></div>
                                <small class="text-muted">
                                    <fmt:formatDate value="${participation.hoatDong.ngayBatDau}" pattern="HH:mm"/> - 
                                    <fmt:formatDate value="${participation.hoatDong.ngayKetThuc}" pattern="HH:mm"/>
                                </small>
                            </td>
                            <td>${participation.vaiTro}</td>
                            <td>${participation.soGio}</td>
                            <td>${participation.diem}</td>
                            <td>
                                <div class="text-warning">
                                    <c:forEach begin="1" end="5" var="i">
                                        <i class="fas fa-star${i <= participation.danhGia ? '' : '-o'}"></i>
                                    </c:forEach>
                                </div>
                            </td>
                            <td>
                                <span class="badge bg-${participation.trangThai == 'COMPLETED' ? 'success' : 
                                    participation.trangThai == 'ONGOING' ? 'primary' : 'secondary'}">
                                    ${participation.trangThai}
                                </span>
                            </td>
                            <td>
                                <div class="dropdown">
                                    <button class="btn btn-light btn-sm" type="button" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/hoatdong/${participation.hoatDong.maHoatDong}'/>">
                                                <i class="fas fa-info-circle"></i> Chi Tiết
                                            </a>
                                        </li>
                                        <c:if test="${participation.trangThai == 'COMPLETED' && empty participation.danhGia}">
                                            <li>
                                                <a class="dropdown-item" href="<c:url value='/student/activities/${participation.hoatDong.maHoatDong}/review'/>">
                                                    <i class="fas fa-star"></i> Đánh Giá
                                                </a>
                                            </li>
                                        </c:if>
                                        <c:if test="${participation.trangThai == 'COMPLETED' && not empty participation.chungNhan}">
                                            <li>
                                                <a class="dropdown-item" href="<c:url value='/student/certificates/${participation.chungNhan.maChungNhan}'/>">
                                                    <i class="fas fa-certificate"></i> Xem Chứng Nhận
                                                </a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Pagination -->
<c:if test="${totalPages > 1}">
    <nav aria-label="Page navigation" class="mt-4">
        <ul class="pagination justify-content-center">
            <li class="page-item ${currentPage == 1 ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage - 1}">
                    <i class="fas fa-chevron-left"></i>
                </a>
            </li>
            
            <c:forEach begin="1" end="${totalPages}" var="page">
                <li class="page-item ${currentPage == page ? 'active' : ''}">
                    <a class="page-link" href="?page=${page}">${page}</a>
                </li>
            </c:forEach>
            
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage + 1}">
                    <i class="fas fa-chevron-right"></i>
                </a>
            </li>
        </ul>
    </nav>
</c:if>

<!-- No History -->
<c:if test="${empty participations}">
    <div class="text-center py-5">
        <i class="fas fa-history fa-3x text-muted mb-3"></i>
        <h4>Chưa có hoạt động nào</h4>
        <p class="text-muted">Hãy tham gia các hoạt động để tích lũy kinh nghiệm</p>
        <a href="<c:url value='/student/activities'/>" class="btn btn-primary">
            <i class="fas fa-search"></i> Tìm Hoạt Động
        </a>
    </div>
</c:if> 