<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Filter Section -->
<div class="card mb-4">
    <div class="card-body">
        <form action="<c:url value='/organization/reports'/>" method="GET" class="row g-3">
            <div class="col-md-4">
                <label class="form-label">Thời Gian</label>
                <select class="form-select" name="period" onchange="this.form.submit()">
                    <option value="week" ${param.period == 'week' ? 'selected' : ''}>Tuần này</option>
                    <option value="month" ${param.period == 'month' ? 'selected' : ''}>Tháng này</option>
                    <option value="quarter" ${param.period == 'quarter' ? 'selected' : ''}>Quý này</option>
                    <option value="year" ${param.period == 'year' ? 'selected' : ''}>Năm nay</option>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label">Hoạt Động</label>
                <select class="form-select" name="activity" onchange="this.form.submit()">
                    <option value="">Tất cả hoạt động</option>
                    <c:forEach items="${activities}" var="activity">
                        <option value="${activity.maHoatDong}" ${param.activity == activity.maHoatDong ? 'selected' : ''}>
                            ${activity.tenHoatDong}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4">
                <label class="form-label">Xuất Báo Cáo</label>
                <div class="d-grid">
                    <button type="button" class="btn btn-outline-primary" onclick="exportReport()">
                        <i class="fas fa-download"></i> Tải Xuống PDF
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>

<!-- Statistics Cards -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card border-primary">
            <div class="card-body text-center">
                <h3 class="card-title text-primary">${totalActivities}</h3>
                <p class="card-text">Hoạt Động</p>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="card border-success">
            <div class="card-body text-center">
                <h3 class="card-title text-success">${totalVolunteers}</h3>
                <p class="card-text">Tình Nguyện Viên</p>
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

<!-- Charts Row -->
<div class="row mb-4">
    <!-- Activities Chart -->
    <div class="col-md-6">
        <div class="card h-100">
            <div class="card-header">
                <h5 class="card-title mb-0">Thống Kê Hoạt Động</h5>
            </div>
            <div class="card-body">
                <canvas id="activitiesChart"></canvas>
            </div>
        </div>
    </div>
    
    <!-- Volunteers Chart -->
    <div class="col-md-6">
        <div class="card h-100">
            <div class="card-header">
                <h5 class="card-title mb-0">Thống Kê Tình Nguyện Viên</h5>
            </div>
            <div class="card-body">
                <canvas id="volunteersChart"></canvas>
            </div>
        </div>
    </div>
</div>

<!-- Top Volunteers Table -->
<div class="card mb-4">
    <div class="card-header">
        <h5 class="card-title mb-0">Top Tình Nguyện Viên</h5>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Tình Nguyện Viên</th>
                        <th>Số Hoạt Động</th>
                        <th>Số Giờ</th>
                        <th>Đánh Giá</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${topVolunteers}" var="volunteer" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>
                                <div class="d-flex align-items-center">
                                    <img src="${volunteer.avatar}" 
                                         class="rounded-circle me-2" style="width: 32px; height: 32px; object-fit: cover;">
                                    <div>
                                        <div class="fw-bold">${volunteer.hoTen}</div>
                                        <small class="text-muted">${volunteer.email}</small>
                                    </div>
                                </div>
                            </td>
                            <td>${volunteer.soHoatDong}</td>
                            <td>${volunteer.tongSoGio}</td>
                            <td>
                                <div class="text-warning">
                                    <c:forEach begin="1" end="5" var="i">
                                        <i class="fas fa-star${i <= volunteer.danhGiaTrungBinh ? '' : '-o'}"></i>
                                    </c:forEach>
                                    <small class="text-muted ms-1">(${volunteer.danhGiaTrungBinh})</small>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Recent Activities Table -->
<div class="card">
    <div class="card-header">
        <h5 class="card-title mb-0">Hoạt Động Gần Đây</h5>
    </div>
    <div class="card-body">
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Hoạt Động</th>
                        <th>Thời Gian</th>
                        <th>Địa Điểm</th>
                        <th>TNV</th>
                        <th>Trạng Thái</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${recentActivities}" var="activity">
                        <tr>
                            <td>
                                <div class="d-flex align-items-center">
                                    <img src="${activity.hinhAnhUrl}" 
                                         class="rounded me-2" style="width: 32px; height: 32px; object-fit: cover;">
                                    <div class="fw-bold">${activity.tenHoatDong}</div>
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
                            <td>${activity.soLuongDaDangKy}/${activity.soLuongTinhNguyenVien}</td>
                            <td>
                                <span class="badge bg-${activity.trangThai == 'COMPLETED' ? 'success' : 
                                    activity.trangThai == 'ONGOING' ? 'primary' : 'secondary'}">
                                    ${activity.trangThai}
                                </span>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- JavaScript for Charts -->
<script>
// Activities Chart
const activitiesCtx = document.getElementById('activitiesChart').getContext('2d');
new Chart(activitiesCtx, {
    type: 'line',
    data: {
        labels: JSON.parse('${activitiesChartLabels}'),
        datasets: [{
            label: 'Số Hoạt Động',
            data: JSON.parse('${activitiesChartData}'),
            borderColor: 'rgb(75, 192, 192)',
            tension: 0.1
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false
    }
});

// Volunteers Chart
const volunteersCtx = document.getElementById('volunteersChart').getContext('2d');
new Chart(volunteersCtx, {
    type: 'bar',
    data: {
        labels: JSON.parse('${volunteersChartLabels}'),
        datasets: [{
            label: 'Số TNV',
            data: JSON.parse('${volunteersChartData}'),
            backgroundColor: 'rgba(54, 162, 235, 0.2)',
            borderColor: 'rgb(54, 162, 235)',
            borderWidth: 1
        }]
    },
    options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

function exportReport() {
    const period = document.querySelector('select[name="period"]').value;
    const activity = document.querySelector('select[name="activity"]').value;
    window.location.href = `/organization/reports/export?period=${period}&activity=${activity}`;
}
</script> 