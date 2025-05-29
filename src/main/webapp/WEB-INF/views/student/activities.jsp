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

<!-- Search and Filter Section -->
<div class="card mb-4">
    <div class="card-body">
        <form action="<c:url value='/student/activities'/>" method="GET" class="row g-3">
            <div class="col-md-4">
                <input type="text" class="form-control" name="keyword" value="${param.keyword}" placeholder="Tìm kiếm hoạt động...">
            </div>
            <div class="col-md-3">
                <select class="form-select" name="category">
                    <option value="">Tất cả lĩnh vực</option>
                    <c:forEach items="${categories}" var="category">
                        <option value="${category.id}" ${param.category == category.id ? 'selected' : ''}>
                            ${category.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-3">
                <select class="form-select" name="status">
                    <option value="">Tất cả trạng thái</option>
                    <option value="UPCOMING" ${param.status == 'UPCOMING' ? 'selected' : ''}>Sắp diễn ra</option>
                    <option value="ONGOING" ${param.status == 'ONGOING' ? 'selected' : ''}>Đang diễn ra</option>
                    <option value="COMPLETED" ${param.status == 'COMPLETED' ? 'selected' : ''}>Đã kết thúc</option>
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

<!-- Activities List -->
<div class="row">
    <c:forEach items="${activities}" var="activity">
        <div class="col-md-6 col-lg-4 mb-4">
            <div class="card h-100">
                <img src="${activity.hinhAnhUrl}" class="card-img-top" alt="${activity.tenHoatDong}">
                <div class="card-body">
                    <h5 class="card-title">${activity.tenHoatDong}</h5>
                    <p class="card-text">${activity.moTa}</p>
                    
                    <div class="mb-3">
                        <small class="text-muted d-block">
                            <i class="fas fa-calendar"></i> 
                            <fmt:formatDate value="${activity.ngayBatDau}" pattern="dd/MM/yyyy"/> - 
                            <fmt:formatDate value="${activity.ngayKetThuc}" pattern="dd/MM/yyyy"/>
                        </small>
                        <small class="text-muted d-block">
                            <i class="fas fa-map-marker-alt"></i> ${activity.diaDiem}
                        </small>
                        <small class="text-muted d-block">
                            <i class="fas fa-users"></i> ${activity.soLuongDaDangKy}/${activity.soLuongTinhNguyenVien} tình nguyện viên
                        </small>
                        <small class="text-muted d-block">
                            <i class="fas fa-building"></i> ${activity.toChuc.tenToChuc}
                        </small>
                    </div>

                    <div class="progress mb-3">
                        <div class="progress-bar" role="progressbar" 
                             style="width: ${activity.soLuongDaDangKy * 100 / activity.soLuongTinhNguyenVien}%">
                            ${activity.soLuongDaDangKy}/${activity.soLuongTinhNguyenVien}
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${activity.daDangKy}">
                            <button class="btn btn-success w-100" disabled>
                                <i class="fas fa-check"></i> Đã Đăng Ký
                            </button>
                        </c:when>
                        <c:when test="${activity.soLuongDaDangKy >= activity.soLuongTinhNguyenVien}">
                            <button class="btn btn-secondary w-100" disabled>
                                <i class="fas fa-ban"></i> Đã Đủ Số Lượng
                            </button>
                        </c:when>
                        <c:otherwise>
                            <a href="<c:url value='/student/activities/${activity.maHoatDong}/register'/>" 
                               class="btn btn-primary w-100">
                                <i class="fas fa-user-plus"></i> Đăng Ký Tham Gia
                            </a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </c:forEach>
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

<!-- No Results -->
<c:if test="${empty activities}">
    <div class="text-center py-5">
        <i class="fas fa-search fa-3x text-muted mb-3"></i>
        <h4>Không tìm thấy hoạt động nào</h4>
        <p class="text-muted">Vui lòng thử lại với các tiêu chí tìm kiếm khác</p>
    </div>
</c:if> 