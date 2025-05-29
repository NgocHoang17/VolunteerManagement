<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!-- DataTables CSS -->
<link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">

<!-- Debug Information -->
<sec:authorize access="isAuthenticated()">
    <div class="alert alert-info alert-dismissible fade show mb-4" role="alert">
        <h5 class="alert-heading">Debug Information</h5>
        <p class="mb-0">
            <strong>Username:</strong> <sec:authentication property="principal.username" /><br/>
            <strong>Roles:</strong> <sec:authentication property="principal.authorities" /><br/>
            <strong>Session ID:</strong> <%= session.getId() %><br/>
            <strong>Request URI:</strong> ${pageContext.request.requestURI}<br/>
            <strong>Context Path:</strong> ${pageContext.request.contextPath}<br/>
            <strong>Servlet Path:</strong> ${requestScope['javax.servlet.forward.servlet_path']}
        </p>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</sec:authorize>

<!-- Error Messages -->
<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>Lỗi!</strong> ${errorMessage}
        <c:if test="${not empty errorDetails}">
            <hr>
            <p class="mb-0">Chi tiết lỗi: ${errorDetails}</p>
        </c:if>
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<div class="container-fluid px-4">
    <h1 class="mt-4">Dashboard</h1>
    <ol class="breadcrumb mb-4">
        <li class="breadcrumb-item active">Quản trị hệ thống</li>
    </ol>

    <!-- Thống kê tổng quan -->
    <div class="row">
        <div class="col-xl-3 col-md-6">
            <div class="card bg-primary text-white mb-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="small">Tổng số sinh viên</div>
                            <div class="h3 mb-0">${totalStudents}</div>
                        </div>
                        <div>
                            <i class="fas fa-users fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                    <a class="small text-white stretched-link" href="<c:url value='/admin/students'/>">Chi tiết</a>
                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6">
            <div class="card bg-success text-white mb-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="small">Tổng số tổ chức</div>
                            <div class="h3 mb-0">${totalOrganizations}</div>
                        </div>
                        <div>
                            <i class="fas fa-building fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                    <a class="small text-white stretched-link" href="<c:url value='/admin/organizations'/>">Chi tiết</a>
                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6">
            <div class="card bg-warning text-white mb-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="small">Tổng số hoạt động</div>
                            <div class="h3 mb-0">${totalActivities}</div>
                        </div>
                        <div>
                            <i class="fas fa-calendar-alt fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                    <a class="small text-white stretched-link" href="<c:url value='/admin/activities'/>">Chi tiết</a>
                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6">
            <div class="card bg-info text-white mb-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="small">Tổng số chứng nhận</div>
                            <div class="h3 mb-0">${totalCertificates}</div>
                        </div>
                        <div>
                            <i class="fas fa-certificate fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                    <a class="small text-white stretched-link" href="<c:url value='/admin/certificates'/>">Chi tiết</a>
                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                </div>
            </div>
        </div>
    </div>

    <!-- Hoạt động gần đây -->
    <div class="card mb-4">
        <div class="card-header">
            <i class="fas fa-table me-1"></i>
            Hoạt động gần đây
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover" id="activitiesTable">
                    <thead>
                        <tr>
                            <th>Mã hoạt động</th>
                            <th>Tên hoạt động</th>
                            <th>Tổ chức</th>
                            <th>Thời gian bắt đầu</th>
                            <th>Số lượng TNV</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${recentActivities}" var="activity">
                            <tr>
                                <td>${activity.maHoatDong}</td>
                                <td>${activity.tenHoatDong}</td>
                                <td>${activity.toChuc.tenToChuc}</td>
                                <td><fmt:formatDate value="${activity.thoiGianBatDau}" pattern="dd/MM/yyyy HH:mm"/></td>
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
                                    <a href="<c:url value='/admin/activities/${activity.maHoatDong}'/>" 
                                       class="btn btn-primary btn-sm">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Người dùng mới -->
    <div class="card mb-4">
        <div class="card-header">
            <i class="fas fa-users me-1"></i>
            Người dùng mới đăng ký
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-bordered table-hover" id="usersTable">
                    <thead>
                        <tr>
                            <th>Username</th>
                            <th>Email</th>
                            <th>Vai trò</th>
                            <th>Ngày đăng ký</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${recentUsers}" var="user">
                            <tr>
                                <td>${user.username}</td>
                                <td>${user.email}</td>
                                <td>
                                    <c:forEach items="${user.authorities}" var="authority">
                                        <span class="badge bg-info">${authority.authority}</span>
                                    </c:forEach>
                                </td>
                                <td><fmt:formatDate value="${user.createdAt}" pattern="dd/MM/yyyy HH:mm"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${user.enabled}">
                                            <span class="badge bg-success">Hoạt động</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge bg-danger">Bị khóa</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="<c:url value='/admin/users/${user.username}'/>" 
                                       class="btn btn-primary btn-sm">
                                        <i class="fas fa-eye"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- DataTables JavaScript -->
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>

<script>
$(document).ready(function() {
    // Tự động đóng alert sau 5 giây
    setTimeout(function() {
        $('.alert').alert('close');
    }, 5000);

    // Function to safely initialize a DataTable
    function initializeDataTable(tableId, config) {
        try {
            // Destroy existing instance if it exists
            if ($.fn.DataTable.isDataTable('#' + tableId)) {
                $('#' + tableId).DataTable().clear().destroy();
                $('#' + tableId).empty(); // Clear the table html
            }

            // Initialize new instance with error handling
            return $('#' + tableId).DataTable({
                language: {
                    url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/vi.json'
                },
                pageLength: 5,
                responsive: true,
                destroy: true,
                ...config
            });
        } catch (error) {
            console.error(`Error initializing DataTable ${tableId}:`, error);
        }
    }

    // Initialize Activities Table
    initializeDataTable('activitiesTable', {
        order: [[3, 'desc']], // Sắp xếp theo thời gian bắt đầu
        columnDefs: [{
            targets: -1, // Last column
            orderable: false // Disable sorting for action column
        }]
    });

    // Initialize Users Table
    initializeDataTable('usersTable', {
        order: [[3, 'desc']], // Sắp xếp theo ngày đăng ký
        columnDefs: [{
            targets: -1, // Last column
            orderable: false // Disable sorting for action column
        }]
    });
});
</script> 