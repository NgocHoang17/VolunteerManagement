<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container-fluid">
    <!-- Page Title -->
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Tổng quan quản lý</h2>
        <div>
            <button class="btn btn-primary">
                <i class="fas fa-plus"></i> Tạo hoạt động mới
            </button>
        </div>
    </div>

    <!-- Statistics Cards -->
    <div class="row">
        <div class="col-md-3">
            <div class="dashboard-stats">
                <div class="stats-icon">
                    <i class="fas fa-calendar-check"></i>
                </div>
                <div class="stats-number">8</div>
                <div class="stats-label">Hoạt động đang diễn ra</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="dashboard-stats">
                <div class="stats-icon">
                    <i class="fas fa-users"></i>
                </div>
                <div class="stats-number">120</div>
                <div class="stats-label">Tình nguyện viên đang tham gia</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="dashboard-stats">
                <div class="stats-icon">
                    <i class="fas fa-clock"></i>
                </div>
                <div class="stats-number">5</div>
                <div class="stats-label">Hoạt động chờ duyệt</div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="dashboard-stats">
                <div class="stats-icon">
                    <i class="fas fa-star"></i>
                </div>
                <div class="stats-number">4.5</div>
                <div class="stats-label">Đánh giá trung bình</div>
            </div>
        </div>
    </div>

    <!-- Activities Table -->
    <div class="card mt-4">
        <div class="card-header">
            <h5 class="mb-0">Hoạt động gần đây</h5>
        </div>
        <div class="card-body">
            <div class="table-responsive">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Tên hoạt động</th>
                            <th>Ngày bắt đầu</th>
                            <th>Số lượng TNV</th>
                            <th>Trạng thái</th>
                            <th>Thao tác</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>Hiến máu nhân đạo</td>
                            <td>15/03/2024</td>
                            <td>50/100</td>
                            <td><span class="badge badge-success">Đang diễn ra</span></td>
                            <td>
                                <button class="btn btn-sm btn-info"><i class="fas fa-edit"></i></button>
                                <button class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                        <tr>
                            <td>Dọn dẹp bãi biển</td>
                            <td>20/03/2024</td>
                            <td>30/80</td>
                            <td><span class="badge badge-warning">Sắp diễn ra</span></td>
                            <td>
                                <button class="btn btn-sm btn-info"><i class="fas fa-edit"></i></button>
                                <button class="btn btn-sm btn-danger"><i class="fas fa-trash"></i></button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- Charts -->
    <div class="row mt-4">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Thống kê tham gia</h5>
                </div>
                <div class="card-body">
                    <canvas id="participationChart"></canvas>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">Đánh giá hoạt động</h5>
                </div>
                <div class="card-body">
                    <canvas id="ratingChart"></canvas>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>
// Participation Chart
const participationCtx = document.getElementById('participationChart').getContext('2d');
new Chart(participationCtx, {
    type: 'line',
    data: {
        labels: ['T1', 'T2', 'T3', 'T4', 'T5', 'T6'],
        datasets: [{
            label: 'Số lượng tham gia',
            data: [65, 80, 45, 90, 75, 120],
            borderColor: '#1976d2',
            tension: 0.1
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});

// Rating Chart
const ratingCtx = document.getElementById('ratingChart').getContext('2d');
new Chart(ratingCtx, {
    type: 'bar',
    data: {
        labels: ['1 sao', '2 sao', '3 sao', '4 sao', '5 sao'],
        datasets: [{
            label: 'Số lượng đánh giá',
            data: [2, 5, 15, 40, 38],
            backgroundColor: '#4caf50'
        }]
    },
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});
</script> 