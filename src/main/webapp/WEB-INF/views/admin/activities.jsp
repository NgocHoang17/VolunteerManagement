<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid px-4">
    <h1 class="mt-4">Quản lý hoạt động</h1>
    <ol class="breadcrumb mb-4">
        <li class="breadcrumb-item"><a href="<c:url value='/admin/dashboard'/>">Dashboard</a></li>
        <li class="breadcrumb-item active">Quản lý hoạt động</li>
    </ol>

    <!-- Thông báo -->
    <c:if test="${not empty message}">
        <div class="alert alert-${messageType} alert-dismissible fade show" role="alert">
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>

    <!-- Bộ lọc -->
    <div class="card mb-4">
        <div class="card-body">
            <form method="get" action="<c:url value='/admin/activities'/>" class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Mã hoạt động</label>
                    <input type="text" class="form-control" name="maHoatDong" value="${param.maHoatDong}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Tên hoạt động</label>
                    <input type="text" class="form-control" name="tenHoatDong" value="${param.tenHoatDong}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Tổ chức</label>
                    <select class="form-select" name="maToChuc">
                        <option value="">Tất cả</option>
                        <c:forEach items="${organizations}" var="org">
                            <option value="${org.maToChuc}" ${param.maToChuc == org.maToChuc ? 'selected' : ''}>
                                ${org.tenToChuc}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col-md-3">
                    <label class="form-label">Trạng thái</label>
                    <select class="form-select" name="trangThai">
                        <option value="">Tất cả</option>
                        <option value="CHUA_DIEN_RA" ${param.trangThai == 'CHUA_DIEN_RA' ? 'selected' : ''}>
                            Chưa diễn ra
                        </option>
                        <option value="DANG_DIEN_RA" ${param.trangThai == 'DANG_DIEN_RA' ? 'selected' : ''}>
                            Đang diễn ra
                        </option>
                        <option value="DA_HOAN_THANH" ${param.trangThai == 'DA_HOAN_THANH' ? 'selected' : ''}>
                            Đã hoàn thành
                        </option>
                        <option value="DA_HUY" ${param.trangThai == 'DA_HUY' ? 'selected' : ''}>
                            Đã hủy
                        </option>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-search"></i> Tìm kiếm
                    </button>
                    <button type="reset" class="btn btn-secondary">
                        <i class="fas fa-redo"></i> Đặt lại
                    </button>
                </div>
            </form>
        </div>
    </div>

    <!-- Danh sách hoạt động -->
    <div class="card mb-4">
        <div class="card-header">
            <i class="fas fa-table me-1"></i>
            Danh sách hoạt động
            <div class="float-end">
                <a href="<c:url value='/admin/activities/export'/>" class="btn btn-success btn-sm">
                    <i class="fas fa-file-excel"></i> Xuất Excel
                </a>
            </div>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover" id="activitiesTable">
                    <thead>
                        <tr>
                            <th>Mã HĐ</th>
                            <th>Tên hoạt động</th>
                            <th>Tổ chức</th>
                            <th>Thời gian</th>
                            <th>Địa điểm</th>
                            <th>Số TNV</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${activities.content}" var="activity">
                            <tr>
                                <td>${activity.maHoatDong}</td>
                                <td>${activity.tenHoatDong}</td>
                                <td>${activity.toChuc.tenToChuc}</td>
                                <td>
                                    <fmt:formatDate value="${activity.thoiGianBatDau}" pattern="dd/MM/yyyy HH:mm"/>
                                    -
                                    <fmt:formatDate value="${activity.thoiGianKetThuc}" pattern="dd/MM/yyyy HH:mm"/>
                                </td>
                                <td>${activity.diaDiem}</td>
                                <td>${activity.soLuongDaDangKy}/${activity.soLuongTinhNguyenVien}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${activity.trangThai == 'CHUA_DIEN_RA'}">
                                            <span class="badge bg-warning">Chưa diễn ra</span>
                                        </c:when>
                                        <c:when test="${activity.trangThai == 'DANG_DIEN_RA'}">
                                            <span class="badge bg-primary">Đang diễn ra</span>
                                        </c:when>
                                        <c:when test="${activity.trangThai == 'DA_HOAN_THANH'}">
                                            <span class="badge bg-success">Đã hoàn thành</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-secondary">Đã hủy</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="<c:url value='/admin/activities/${activity.maHoatDong}'/>" 
                                           class="btn btn-primary btn-sm" title="Xem chi tiết">
                                            <i class="fas fa-eye"></i>
                                        </a>
                                        <a href="<c:url value='/admin/activities/${activity.maHoatDong}/edit'/>" 
                                           class="btn btn-warning btn-sm" title="Chỉnh sửa">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <button type="button" class="btn btn-danger btn-sm" title="Xóa"
                                                onclick="confirmDelete('${activity.maHoatDong}')">
                                            <i class="fas fa-trash"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Phân trang -->
            <c:if test="${activities.totalPages > 1}">
                <nav aria-label="Page navigation" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${activities.first ? 'disabled' : ''}">
                            <a class="page-link" href="?page=0">Đầu</a>
                        </li>
                        <li class="page-item ${activities.first ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${activities.number - 1}">Trước</a>
                        </li>
                        <c:forEach begin="${Math.max(0, activities.number - 2)}" 
                                 end="${Math.min(activities.totalPages - 1, activities.number + 2)}" var="i">
                            <li class="page-item ${i == activities.number ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}">${i + 1}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${activities.last ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${activities.number + 1}">Sau</a>
                        </li>
                        <li class="page-item ${activities.last ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${activities.totalPages - 1}">Cuối</a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>

<!-- Modal xác nhận xóa -->
<div class="modal fade" id="deleteModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận xóa</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn xóa hoạt động này không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <form id="deleteForm" method="post" style="display: inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="submit" class="btn btn-danger">Xóa</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- DataTables -->
<link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

<script>
$(document).ready(function() {
    // Khởi tạo DataTable
    $('#activitiesTable').DataTable({
        language: {
            url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/vi.json'
        },
        order: [[3, 'desc']], // Sắp xếp theo thời gian
        pageLength: 10,
        dom: 'Bfrtip',
        buttons: [
            'copy', 'csv', 'excel', 'pdf', 'print'
        ]
    });

    // Tự động đóng alert sau 5 giây
    setTimeout(function() {
        $('.alert').alert('close');
    }, 5000);
});

// Xác nhận xóa
function confirmDelete(maHoatDong) {
    $('#deleteForm').attr('action', '<c:url value="/admin/activities/"/>' + maHoatDong + '/delete');
    $('#deleteModal').modal('show');
}
</script> 