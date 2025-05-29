<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="container-fluid px-4">
    <h1 class="mt-4">Dashboard</h1>
    <ol class="breadcrumb mb-4">
        <li class="breadcrumb-item active">Quản lý hoạt động tình nguyện</li>
    </ol>

    <!-- Thống kê tổng quan -->
    <div class="row">
        <div class="col-xl-3 col-md-6">
            <div class="card bg-primary text-white mb-4">
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
                    <a class="small text-white stretched-link" href="<c:url value='/organization/activities'/>">Chi tiết</a>
                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6">
            <div class="card bg-success text-white mb-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="small">Tổng số tình nguyện viên</div>
                            <div class="h3 mb-0">${totalVolunteers}</div>
                        </div>
                        <div>
                            <i class="fas fa-users fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                    <a class="small text-white stretched-link" href="<c:url value='/organization/volunteers'/>">Chi tiết</a>
                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6">
            <div class="card bg-warning text-white mb-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="small">Hoạt động đang diễn ra</div>
                            <div class="h3 mb-0">${activeActivities}</div>
                        </div>
                        <div>
                            <i class="fas fa-clock fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                    <a class="small text-white stretched-link" href="<c:url value='/organization/activities?status=active'/>">Chi tiết</a>
                    <div class="small text-white"><i class="fas fa-angle-right"></i></div>
                </div>
            </div>
        </div>
        <div class="col-xl-3 col-md-6">
            <div class="card bg-info text-white mb-4">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <div class="small">Chứng nhận đã cấp</div>
                            <div class="h3 mb-0">${totalCertificates}</div>
                        </div>
                        <div>
                            <i class="fas fa-certificate fa-2x"></i>
                        </div>
                    </div>
                </div>
                <div class="card-footer d-flex align-items-center justify-content-between">
                    <a class="small text-white stretched-link" href="<c:url value='/organization/certificates'/>">Chi tiết</a>
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
                <table class="table table-bordered table-hover" id="recentActivitiesTable">
                    <thead>
                        <tr>
                            <th>Tên hoạt động</th>
                            <th>Thời gian bắt đầu</th>
                            <th>Địa điểm</th>
                            <th>Số TNV đăng ký</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${recentActivities}" var="activity">
                            <tr>
                                <td>${activity.tenHoatDong}</td>
                                <td><fmt:formatDate value="${activity.thoiGianBatDau}" pattern="dd/MM/yyyy HH:mm"/></td>
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
                                    <a href="<c:url value='/organization/activities/${activity.maHoatDong}'/>" 
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

<script>
$(document).ready(function() {
    $('#recentActivitiesTable').DataTable({
        language: {
            url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/vi.json'
        }
    });
});
</script> 