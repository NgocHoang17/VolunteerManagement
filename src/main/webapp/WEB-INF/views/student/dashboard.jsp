<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Dashboard Content -->
<div class="container-fluid">
    <!-- Statistics Cards -->
    <div class="row mb-4">
        <div class="col-md-3">
            <div class="card border-primary">
                <div class="card-body text-center">
                    <h3 class="card-title text-primary">${totalActivities}</h3>
                    <p class="card-text">Hoạt động đã tham gia</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card border-success">
                <div class="card-body text-center">
                    <h3 class="card-title text-success">${totalHours}</h3>
                    <p class="card-text">Tổng số giờ CTXH</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card border-info">
                <div class="card-body text-center">
                    <h3 class="card-title text-info">${totalPoints}</h3>
                    <p class="card-text">Điểm rèn luyện</p>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card border-warning">
                <div class="card-body text-center">
                    <h3 class="card-title text-warning">${totalCertificates}</h3>
                    <p class="card-text">Chứng nhận đạt được</p>
                </div>
            </div>
        </div>
    </div>

    <!-- Recent Activities -->
    <div class="card">
        <div class="card-header">
            <h5 class="card-title mb-0">Hoạt động gần đây</h5>
        </div>
        <div class="card-body">
            <c:if test="${not empty recentActivities}">
                <div class="table-responsive">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>Hoạt động</th>
                                <th>Thời gian</th>
                                <th>Địa điểm</th>
                                <th>Trạng thái</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${recentActivities}" var="activity">
                                <tr>
                                    <td>
                                        <div class="d-flex align-items-center">
                                            <img src="${activity.hinhAnhUrl}" class="rounded me-2" 
                                                 style="width: 40px; height: 40px; object-fit: cover;">
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
                                        <span class="badge bg-${activity.trangThai == 'PENDING' ? 'warning' : 
                                            activity.trangThai == 'APPROVED' ? 'success' : 
                                            activity.trangThai == 'REJECTED' ? 'danger' : 'secondary'}">
                                            ${activity.trangThai}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="<c:url value='/student/activities/${activity.maHoatDong}'/>" 
                                           class="btn btn-sm btn-light">
                                            <i class="fas fa-info-circle"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            
            <c:if test="${empty recentActivities}">
                <div class="text-center py-5">
                    <i class="fas fa-calendar-times fa-3x text-muted mb-3"></i>
                    <h4>Chưa có hoạt động nào</h4>
                    <p class="text-muted">Hãy tham gia các hoạt động để tích lũy giờ CTXH</p>
                    <a href="<c:url value='/student/activities'/>" class="btn btn-primary">
                        <i class="fas fa-search"></i> Tìm hoạt động
                    </a>
                </div>
            </c:if>
        </div>
    </div>
</div>