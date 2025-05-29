<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Statistics Cards -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card border-primary">
            <div class="card-body text-center">
                <h3 class="card-title text-primary">${totalVolunteers}</h3>
                <p class="card-text">Tổng Số TNV</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-success">
            <div class="card-body text-center">
                <h3 class="card-title text-success">${activeVolunteers}</h3>
                <p class="card-text">TNV Đang Hoạt Động</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-info">
            <div class="card-body text-center">
                <h3 class="card-title text-info">${totalHours}</h3>
                <p class="card-text">Tổng Số Giờ</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-warning">
            <div class="card-body text-center">
                <h3 class="card-title text-warning">${averageRating}</h3>
                <p class="card-text">Đánh Giá Trung Bình</p>
            </div>
        </div>
    </div>
</div>

<!-- Search and Filter -->
<div class="card mb-4">
    <div class="card-body">
        <form action="<c:url value='/organization/volunteers'/>" method="GET" class="row g-3">
            <div class="col-md-4">
                <input type="text" class="form-control" name="keyword" value="${param.keyword}" 
                       placeholder="Tìm kiếm theo tên, email, trường...">
            </div>
            <div class="col-md-3">
                <select class="form-select" name="activity">
                    <option value="">Tất cả hoạt động</option>
                    <c:forEach items="${activities}" var="activity">
                        <option value="${activity.maHoatDong}" ${param.activity == activity.maHoatDong ? 'selected' : ''}>
                            ${activity.tenHoatDong}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                <select class="form-select" name="sortBy">
                    <option value="name" ${param.sortBy == 'name' ? 'selected' : ''}>Tên</option>
                    <option value="hours" ${param.sortBy == 'hours' ? 'selected' : ''}>Số giờ</option>
                    <option value="rating" ${param.sortBy == 'rating' ? 'selected' : ''}>Đánh giá</option>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-primary w-100">
                    <i class="fas fa-search"></i> Tìm Kiếm
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Volunteers Table -->
<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Tình Nguyện Viên</th>
                        <th>Trường</th>
                        <th>Hoạt Động</th>
                        <th>Số Giờ</th>
                        <th>Đánh Giá</th>
                        <th>Trạng Thái</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${volunteers}" var="volunteer">
                        <tr>
                            <td>
                                <div class="d-flex align-items-center">
                                    <img src="${volunteer.avatar}" 
                                         class="rounded-circle me-2" style="width: 40px; height: 40px; object-fit: cover;">
                                    <div>
                                        <div class="fw-bold">${volunteer.hoTen}</div>
                                        <small class="text-muted">${volunteer.email}</small>
                                    </div>
                                </div>
                            </td>
                            <td>${volunteer.tenTruong}</td>
                            <td>${volunteer.soHoatDong} hoạt động</td>
                            <td>${volunteer.tongSoGio} giờ</td>
                            <td>
                                <div class="text-warning">
                                    <c:forEach begin="1" end="5" var="i">
                                        <i class="fas fa-star${i <= volunteer.danhGiaTrungBinh ? '' : '-o'}"></i>
                                    </c:forEach>
                                    <small class="text-muted ms-1">(${volunteer.danhGiaTrungBinh})</small>
                                </div>
                            </td>
                            <td>
                                <span class="badge bg-${volunteer.trangThai == 'ACTIVE' ? 'success' : 'secondary'}">
                                    ${volunteer.trangThai}
                                </span>
                            </td>
                            <td>
                                <div class="dropdown">
                                    <button class="btn btn-light btn-sm" type="button" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/organization/volunteers/${volunteer.maSinhVien}'/>">
                                                <i class="fas fa-info-circle"></i> Chi Tiết
                                            </a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/organization/volunteers/${volunteer.maSinhVien}/history'/>">
                                                <i class="fas fa-history"></i> Lịch Sử
                                            </a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/organization/volunteers/${volunteer.maSinhVien}/review'/>">
                                                <i class="fas fa-star"></i> Đánh Giá
                                            </a>
                                        </li>
                                        <c:if test="${volunteer.trangThai == 'ACTIVE'}">
                                            <li><hr class="dropdown-divider"></li>
                                            <li>
                                                <a class="dropdown-item text-danger" href="#" 
                                                   onclick="confirmBlock('${volunteer.maSinhVien}', '${volunteer.hoTen}')">
                                                    <i class="fas fa-ban"></i> Chặn TNV
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
                <a class="page-link" href="?page=${currentPage - 1}${searchParams}">
                    <i class="fas fa-chevron-left"></i>
                </a>
            </li>
            
            <c:forEach begin="1" end="${totalPages}" var="page">
                <li class="page-item ${currentPage == page ? 'active' : ''}">
                    <a class="page-link" href="?page=${page}${searchParams}">${page}</a>
                </li>
            </c:forEach>
            
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage + 1}${searchParams}">
                    <i class="fas fa-chevron-right"></i>
                </a>
            </li>
        </ul>
    </nav>
</c:if>

<!-- No Volunteers -->
<c:if test="${empty volunteers}">
    <div class="text-center py-5">
        <i class="fas fa-users fa-3x text-muted mb-3"></i>
        <h4>Không tìm thấy tình nguyện viên nào</h4>
        <p class="text-muted">Vui lòng thử lại với các tiêu chí tìm kiếm khác</p>
    </div>
</c:if>

<!-- Block Confirmation Modal -->
<div class="modal fade" id="blockModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác Nhận Chặn Tình Nguyện Viên</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Bạn có chắc chắn muốn chặn tình nguyện viên "<span id="volunteerName"></span>"?</p>
                <p class="text-danger">Lưu ý: TNV sẽ không thể đăng ký tham gia các hoạt động của tổ chức!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <form id="blockForm" method="POST" style="display: inline;">
                    <button type="submit" class="btn btn-danger">
                        <i class="fas fa-ban"></i> Xác Nhận Chặn
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript -->
<script>
function confirmBlock(volunteerId, volunteerName) {
    document.getElementById('volunteerName').textContent = volunteerName;
    document.getElementById('blockForm').action = `/organization/volunteers/${volunteerId}/block`;
    new bootstrap.Modal(document.getElementById('blockModal')).show();
}
</script> 