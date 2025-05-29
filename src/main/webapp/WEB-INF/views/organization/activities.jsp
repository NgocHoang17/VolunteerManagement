<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<style>
.progress {
    height: 20px;
}
.progress-bar {
    min-width: 2em;
}
</style>

<!-- Action Buttons -->
<div class="mb-4">
    <a href="<c:url value='/organization/activities/create'/>" class="btn btn-primary">
        <i class="fas fa-plus"></i> Tạo Hoạt Động Mới
    </a>
</div>

<!-- Statistics Cards -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card border-primary">
            <div class="card-body text-center">
                <h3 class="card-title text-primary">${totalActivities}</h3>
                <p class="card-text">Tổng Số Hoạt Động</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-success">
            <div class="card-body text-center">
                <h3 class="card-title text-success">${activeActivities}</h3>
                <p class="card-text">Hoạt Động Đang Diễn Ra</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-info">
            <div class="card-body text-center">
                <h3 class="card-title text-info">${totalVolunteers}</h3>
                <p class="card-text">Tổng Số TNV</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-warning">
            <div class="card-body text-center">
                <h3 class="card-title text-warning">${totalHours}</h3>
                <p class="card-text">Tổng Số Giờ</p>
            </div>
        </div>
    </div>
</div>

<!-- Search and Filter -->
<div class="card mb-4">
    <div class="card-body">
        <form action="<c:url value='/organization/activities'/>" method="GET" class="row g-3">
            <div class="col-md-4">
                <input type="text" class="form-control" name="keyword" value="${param.keyword}" 
                       placeholder="Tìm kiếm theo tên hoạt động...">
            </div>
            <div class="col-md-3">
                <select class="form-select" name="status">
                    <option value="">Tất cả trạng thái</option>
                    <option value="DRAFT" ${param.status == 'DRAFT' ? 'selected' : ''}>Nháp</option>
                    <option value="PENDING" ${param.status == 'PENDING' ? 'selected' : ''}>Chờ duyệt</option>
                    <option value="APPROVED" ${param.status == 'APPROVED' ? 'selected' : ''}>Đã duyệt</option>
                    <option value="ONGOING" ${param.status == 'ONGOING' ? 'selected' : ''}>Đang diễn ra</option>
                    <option value="COMPLETED" ${param.status == 'COMPLETED' ? 'selected' : ''}>Đã kết thúc</option>
                    <option value="CANCELLED" ${param.status == 'CANCELLED' ? 'selected' : ''}>Đã hủy</option>
                </select>
            </div>
            <div class="col-md-3">
                <select class="form-select" name="sortBy">
                    <option value="createdDate" ${param.sortBy == 'createdDate' ? 'selected' : ''}>Ngày tạo</option>
                    <option value="startDate" ${param.sortBy == 'startDate' ? 'selected' : ''}>Ngày bắt đầu</option>
                    <option value="volunteers" ${param.sortBy == 'volunteers' ? 'selected' : ''}>Số lượng TNV</option>
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

<!-- Activities Table -->
<div class="card">
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Hoạt Động</th>
                        <th>Thời Gian</th>
                        <th>Địa Điểm</th>
                        <th>Tình Nguyện Viên</th>
                        <th>Trạng Thái</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${activities}" var="activity">
                        <tr>
                            <td>
                                <div class="d-flex align-items-center">
                                    <img src="${activity.hinhAnhUrl}" 
                                         class="rounded me-2" style="width: 40px; height: 40px; object-fit: cover;">
                                    <div>
                                        <div class="fw-bold">${activity.tenHoatDong}</div>
                                        <small class="text-muted">${activity.moTaNgan}</small>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div><fmt:formatDate value="${activity.ngayBatDau}" pattern="dd/MM/yyyy"/></div>
                                <small class="text-muted">
                                    <fmt:formatDate value="${activity.ngayBatDau}" pattern="HH:mm"/> - 
                                    <fmt:formatDate value="${activity.ngayKetThuc}" pattern="HH:mm"/>
                                </small>
                            </td>
                            <td>${activity.diaDiem}</td>
                            <td>
                                <div class="progress">
                                    <div class="progress-bar" role="progressbar" style="width: ${activity.soLuongDaDangKy * 100 / activity.soLuongTinhNguyenVien}%; min-width: 2em;">
                                        ${activity.soLuongDaDangKy}/${activity.soLuongTinhNguyenVien}
                                    </div>
                                </div>
                            </td>
                            <td>
                                <span class="badge bg-${activity.trangThai == 'DRAFT' ? 'secondary' : 
                                    activity.trangThai == 'PENDING' ? 'warning' :
                                    activity.trangThai == 'APPROVED' ? 'info' :
                                    activity.trangThai == 'ONGOING' ? 'primary' :
                                    activity.trangThai == 'COMPLETED' ? 'success' : 'danger'}">
                                    ${activity.trangThai}
                                </span>
                            </td>
                            <td>
                                <div class="dropdown">
                                    <button class="btn btn-light btn-sm" type="button" data-bs-toggle="dropdown">
                                        <i class="fas fa-ellipsis-v"></i>
                                    </button>
                                    <ul class="dropdown-menu dropdown-menu-end">
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/organization/activities/${activity.maHoatDong}'/>">
                                                <i class="fas fa-info-circle"></i> Chi Tiết
                                            </a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/organization/activities/${activity.maHoatDong}/edit'/>">
                                                <i class="fas fa-edit"></i> Chỉnh Sửa
                                            </a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="<c:url value='/organization/activities/${activity.maHoatDong}/volunteers'/>">
                                                <i class="fas fa-users"></i> Quản Lý TNV
                                            </a>
                                        </li>
                                        <c:if test="${activity.trangThai == 'DRAFT'}">
                                            <li>
                                                <a class="dropdown-item text-primary" href="<c:url value='/organization/activities/${activity.maHoatDong}/submit'/>">
                                                    <i class="fas fa-paper-plane"></i> Gửi Phê Duyệt
                                                </a>
                                            </li>
                                        </c:if>
                                        <c:if test="${activity.trangThai != 'COMPLETED' && activity.trangThai != 'CANCELLED'}">
                                            <li><hr class="dropdown-divider"></li>
                                            <li>
                                                <a class="dropdown-item text-danger" href="#" 
                                                   onclick="confirmCancel('${activity.maHoatDong}', '${activity.tenHoatDong}')">
                                                    <i class="fas fa-times"></i> Hủy Hoạt Động
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

<!-- No Activities -->
<c:if test="${empty activities}">
    <div class="text-center py-5">
        <i class="fas fa-calendar-times fa-3x text-muted mb-3"></i>
        <h4>Chưa có hoạt động nào</h4>
        <p class="text-muted">Hãy tạo hoạt động mới để bắt đầu</p>
        <a href="<c:url value='/organization/activities/create'/>" class="btn btn-primary">
            <i class="fas fa-plus"></i> Tạo Hoạt Động
        </a>
    </div>
</c:if>

<!-- Cancel Confirmation Modal -->
<div class="modal fade" id="cancelModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác Nhận Hủy Hoạt Động</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <p>Bạn có chắc chắn muốn hủy hoạt động "<span id="activityName"></span>"?</p>
                <p class="text-danger">Lưu ý: Hành động này không thể hoàn tác!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                <form id="cancelForm" method="POST" style="display: inline;">
                    <button type="submit" class="btn btn-danger">
                        <i class="fas fa-times"></i> Xác Nhận Hủy
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript -->
<script>
function confirmCancel(activityId, activityName) {
    document.getElementById('activityName').textContent = activityName;
    document.getElementById('cancelForm').action = `/organization/activities/${activityId}/cancel`;
    new bootstrap.Modal(document.getElementById('cancelModal')).show();
}
</script> 