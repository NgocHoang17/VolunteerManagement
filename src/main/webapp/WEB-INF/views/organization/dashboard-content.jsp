<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="container-fluid">
    <!-- Page Title -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Trang tổ chức - ${toChuc.tenToChuc}</h2>
        <div>
            <a href="<c:url value='/organization/activities/new'/>" class="btn btn-primary">
                <i class="fas fa-plus"></i> Tạo hoạt động mới
            </a>
        </div>
    </div>

    <!-- Statistics Cards -->
    <div class="row">
        <div class="col-md-4">
            <div class="card bg-primary text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="card-title mb-0">Tổng số hoạt động</h6>
                            <h2 class="mt-2 mb-0">${totalActivities}</h2>
                        </div>
                        <i class="fas fa-calendar-alt fa-2x"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card bg-success text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="card-title mb-0">Tổng số tình nguyện viên</h6>
                            <h2 class="mt-2 mb-0">${totalVolunteers}</h2>
                        </div>
                        <i class="fas fa-users fa-2x"></i>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card bg-info text-white">
                <div class="card-body">
                    <div class="d-flex justify-content-between align-items-center">
                        <div>
                            <h6 class="card-title mb-0">Hoạt động đang diễn ra</h6>
                            <h2 class="mt-2 mb-0">${activeActivities}</h2>
                        </div>
                        <i class="fas fa-clock fa-2x"></i>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Recent Activities -->
    <div class="row mt-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Hoạt động gần đây</h5>
                    <a href="<c:url value='/organization/activities'/>" class="btn btn-sm btn-primary">
                        Xem tất cả
                    </a>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Tên hoạt động</th>
                                    <th>Thời gian</th>
                                    <th>Địa điểm</th>
                                    <th>Số lượng đăng ký</th>
                                    <th>Trạng thái</th>
                                    <th>Thao tác</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${recentActivities}" var="activity">
                                    <tr>
                                        <td>${activity.tenHoatDong}</td>
                                        <td>
                                            <fmt:formatDate value="${activity.thoiGianBatDau}" pattern="dd/MM/yyyy"/>
                                        </td>
                                        <td>${activity.diaDiem}</td>
                                        <td>${activity.soLuongDangKy}/${activity.soLuongToiDa}</td>
                                        <td>
                                            <span class="badge bg-${activity.trangThai == 'COMPLETED' ? 'success' : 
                                                activity.trangThai == 'IN_PROGRESS' ? 'warning' : 'secondary'}">
                                                ${activity.trangThai}
                                            </span>
                                        </td>
                                        <td>
                                            <a href="<c:url value='/organization/activities/${activity.maHoatDong}'/>" 
                                               class="btn btn-sm btn-info">
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
    </div>

    <!-- Recent Volunteers -->
    <div class="row mt-4">
        <div class="col-12">
            <div class="card">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Tình nguyện viên gần đây</h5>
                    <a href="<c:url value='/organization/volunteers'/>" class="btn btn-sm btn-primary">
                        Xem tất cả
                    </a>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th>Họ tên</th>
                                    <th>Mã sinh viên</th>
                                    <th>Email</th>
                                    <th>Số điện thoại</th>
                                    <th>Hoạt động</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${recentVolunteers}" var="volunteer">
                                    <tr>
                                        <td>${volunteer.sinhVien.hoTen}</td>
                                        <td>${volunteer.sinhVien.maSinhVien}</td>
                                        <td>${volunteer.sinhVien.email}</td>
                                        <td>${volunteer.sinhVien.soDienThoai}</td>
                                        <td>${volunteer.hoatDong.tenHoatDong}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 