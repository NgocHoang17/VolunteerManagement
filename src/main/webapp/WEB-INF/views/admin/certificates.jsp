<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid px-4">
    <h1 class="mt-4">Quản lý chứng nhận</h1>
    <ol class="breadcrumb mb-4">
        <li class="breadcrumb-item"><a href="<c:url value='/admin/dashboard'/>">Dashboard</a></li>
        <li class="breadcrumb-item active">Quản lý chứng nhận</li>
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
            <form method="get" action="<c:url value='/admin/certificates'/>" class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Mã chứng nhận</label>
                    <input type="text" class="form-control" name="maChungNhan" value="${param.maChungNhan}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Sinh viên</label>
                    <input type="text" class="form-control" name="tenSinhVien" value="${param.tenSinhVien}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Hoạt động</label>
                    <input type="text" class="form-control" name="tenHoatDong" value="${param.tenHoatDong}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Trạng thái</label>
                    <select class="form-select" name="trangThai">
                        <option value="">Tất cả</option>
                        <option value="PENDING" ${param.trangThai == 'PENDING' ? 'selected' : ''}>Chờ duyệt</option>
                        <option value="APPROVED" ${param.trangThai == 'APPROVED' ? 'selected' : ''}>Đã duyệt</option>
                        <option value="REJECTED" ${param.trangThai == 'REJECTED' ? 'selected' : ''}>Từ chối</option>
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

    <!-- Danh sách chứng nhận -->
    <div class="card mb-4">
        <div class="card-header">
            <i class="fas fa-table me-1"></i>
            Danh sách chứng nhận
            <div class="float-end">
                <a href="<c:url value='/admin/certificates/export'/>" class="btn btn-success btn-sm">
                    <i class="fas fa-file-excel"></i> Xuất Excel
                </a>
            </div>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover" id="certificatesTable">
                    <thead>
                        <tr>
                            <th>Mã CN</th>
                            <th>Sinh viên</th>
                            <th>Hoạt động</th>
                            <th>Ngày cấp</th>
                            <th>Người duyệt</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${certificates.content}" var="cert">
                            <tr>
                                <td>${cert.maChungNhan}</td>
                                <td>
                                    ${cert.sinhVien.hoTen}
                                    <br>
                                    <small class="text-muted">${cert.sinhVien.maSinhVien}</small>
                                </td>
                                <td>
                                    ${cert.hoatDong.tenHoatDong}
                                    <br>
                                    <small class="text-muted">${cert.hoatDong.maHoatDong}</small>
                                </td>
                                <td>
                                    <fmt:formatDate value="${cert.ngayCap}" pattern="dd/MM/yyyy"/>
                                </td>
                                <td>${cert.nguoiDuyet.hoTen}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${cert.trangThai == 'PENDING'}">
                                            <span class="badge bg-warning">Chờ duyệt</span>
                                        </c:when>
                                        <c:when test="${cert.trangThai == 'APPROVED'}">
                                            <span class="badge bg-success">Đã duyệt</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Từ chối</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="<c:url value='/admin/certificates/${cert.maChungNhan}'/>" 
                                           class="btn btn-primary btn-sm" title="Xem chi tiết">
                                            <i class="fas fa-eye"></i>
                                        </a>
                                        <c:if test="${cert.trangThai == 'PENDING'}">
                                            <button type="button" class="btn btn-success btn-sm" title="Duyệt"
                                                    onclick="confirmApprove('${cert.maChungNhan}')">
                                                <i class="fas fa-check"></i>
                                            </button>
                                            <button type="button" class="btn btn-danger btn-sm" title="Từ chối"
                                                    onclick="confirmReject('${cert.maChungNhan}')">
                                                <i class="fas fa-times"></i>
                                            </button>
                                        </c:if>
                                        <c:if test="${cert.trangThai == 'APPROVED'}">
                                            <a href="<c:url value='/admin/certificates/${cert.maChungNhan}/download'/>" 
                                               class="btn btn-info btn-sm" title="Tải xuống">
                                                <i class="fas fa-download"></i>
                                            </a>
                                        </c:if>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <!-- Phân trang -->
            <c:if test="${certificates.totalPages > 1}">
                <nav aria-label="Page navigation" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${certificates.first ? 'disabled' : ''}">
                            <a class="page-link" href="?page=0">Đầu</a>
                        </li>
                        <li class="page-item ${certificates.first ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${certificates.number - 1}">Trước</a>
                        </li>
                        <c:forEach begin="${Math.max(0, certificates.number - 2)}" 
                                 end="${Math.min(certificates.totalPages - 1, certificates.number + 2)}" var="i">
                            <li class="page-item ${i == certificates.number ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}">${i + 1}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${certificates.last ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${certificates.number + 1}">Sau</a>
                        </li>
                        <li class="page-item ${certificates.last ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${certificates.totalPages - 1}">Cuối</a>
                        </li>
                    </ul>
                </nav>
            </c:if>
        </div>
    </div>
</div>

<!-- Modal xác nhận duyệt -->
<div class="modal fade" id="approveModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận duyệt</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                Bạn có chắc chắn muốn duyệt chứng nhận này không?
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <form id="approveForm" method="post" style="display: inline;">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <button type="submit" class="btn btn-success">Duyệt</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Modal xác nhận từ chối -->
<div class="modal fade" id="rejectModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Xác nhận từ chối</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="rejectForm" method="post">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                    <div class="mb-3">
                        <label for="lyDo" class="form-label">Lý do từ chối</label>
                        <textarea class="form-control" id="lyDo" name="lyDo" rows="3" required></textarea>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Hủy</button>
                <button type="button" class="btn btn-danger" onclick="submitReject()">Từ chối</button>
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
    $('#certificatesTable').DataTable({
        language: {
            url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/vi.json'
        },
        order: [[3, 'desc']], // Sắp xếp theo ngày cấp
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

// Xác nhận duyệt
function confirmApprove(maChungNhan) {
    $('#approveForm').attr('action', '<c:url value="/admin/certificates/"/>' + maChungNhan + '/approve');
    $('#approveModal').modal('show');
}

// Xác nhận từ chối
function confirmReject(maChungNhan) {
    $('#rejectForm').attr('action', '<c:url value="/admin/certificates/"/>' + maChungNhan + '/reject');
    $('#rejectModal').modal('show');
}

// Submit form từ chối
function submitReject() {
    if ($('#lyDo').val().trim() === '') {
        alert('Vui lòng nhập lý do từ chối');
        return;
    }
    $('#rejectForm').submit();
}
</script> 