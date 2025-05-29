<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid px-4">
    <h1 class="mt-4">Quản lý sinh viên</h1>
    <ol class="breadcrumb mb-4">
        <li class="breadcrumb-item"><a href="<c:url value='/admin/dashboard'/>">Dashboard</a></li>
        <li class="breadcrumb-item active">Quản lý sinh viên</li>
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
            <form method="get" action="<c:url value='/admin/students'/>" class="row g-3">
                <div class="col-md-3">
                    <label class="form-label">Mã sinh viên</label>
                    <input type="text" class="form-control" name="maSinhVien" value="${param.maSinhVien}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Họ tên</label>
                    <input type="text" class="form-control" name="hoTen" value="${param.hoTen}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Lớp</label>
                    <input type="text" class="form-control" name="lop" value="${param.lop}">
                </div>
                <div class="col-md-3">
                    <label class="form-label">Trạng thái</label>
                    <select class="form-select" name="trangThai">
                        <option value="">Tất cả</option>
                        <option value="ACTIVE" ${param.trangThai == 'ACTIVE' ? 'selected' : ''}>Hoạt động</option>
                        <option value="INACTIVE" ${param.trangThai == 'INACTIVE' ? 'selected' : ''}>Không hoạt động</option>
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

    <!-- Danh sách sinh viên -->
    <div class="card mb-4">
        <div class="card-header">
            <i class="fas fa-table me-1"></i>
            Danh sách sinh viên
            <div class="float-end">
                <a href="<c:url value='/admin/students/export'/>" class="btn btn-success btn-sm">
                    <i class="fas fa-file-excel"></i> Xuất Excel
                </a>
            </div>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover" id="studentsTable">
                    <thead>
                        <tr>
                            <th>Mã SV</th>
                            <th>Họ tên</th>
                            <th>Email</th>
                            <th>Lớp</th>
                            <th>Số điện thoại</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${students.content}" var="student">
                            <tr>
                                <td>${student.maSinhVien}</td>
                                <td>${student.hoTen}</td>
                                <td>${student.email}</td>
                                <td>${student.lop}</td>
                                <td>${student.soDienThoai}</td>
                                <td>
                                    <span class="badge bg-${student.trangThai == 'ACTIVE' ? 'success' : 'danger'}">
                                        ${student.trangThai == 'ACTIVE' ? 'Hoạt động' : 'Không hoạt động'}
                                    </span>
                                </td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a href="<c:url value='/admin/students/${student.maSinhVien}'/>" 
                                           class="btn btn-primary btn-sm" title="Xem chi tiết">
                                            <i class="fas fa-eye"></i>
                                        </a>
                                        <a href="<c:url value='/admin/students/${student.maSinhVien}/edit'/>" 
                                           class="btn btn-warning btn-sm" title="Chỉnh sửa">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <button type="button" class="btn btn-danger btn-sm" title="Xóa"
                                                onclick="confirmDelete('${student.maSinhVien}')">
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
            <c:if test="${students.totalPages > 1}">
                <nav aria-label="Page navigation" class="mt-4">
                    <ul class="pagination justify-content-center">
                        <li class="page-item ${students.first ? 'disabled' : ''}">
                            <a class="page-link" href="?page=0">Đầu</a>
                        </li>
                        <li class="page-item ${students.first ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${students.number - 1}">Trước</a>
                        </li>
                        <c:forEach begin="${Math.max(0, students.number - 2)}" 
                                 end="${Math.min(students.totalPages - 1, students.number + 2)}" var="i">
                            <li class="page-item ${i == students.number ? 'active' : ''}">
                                <a class="page-link" href="?page=${i}">${i + 1}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${students.last ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${students.number + 1}">Sau</a>
                        </li>
                        <li class="page-item ${students.last ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${students.totalPages - 1}">Cuối</a>
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
                Bạn có chắc chắn muốn xóa sinh viên này không?
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
    $('#studentsTable').DataTable({
        language: {
            url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/vi.json'
        },
        order: [[1, 'asc']], // Sắp xếp theo họ tên
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
function confirmDelete(maSinhVien) {
    $('#deleteForm').attr('action', '<c:url value="/admin/students/"/>' + maSinhVien + '/delete');
    $('#deleteModal').modal('show');
}
</script> 