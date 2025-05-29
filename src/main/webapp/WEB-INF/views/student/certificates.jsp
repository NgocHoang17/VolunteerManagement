<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Certificates Grid -->
<div class="row">
    <c:forEach items="${certificates}" var="certificate">
        <div class="col-md-6 col-lg-4 mb-4">
            <div class="card h-100">
                <div class="card-body">
                    <div class="text-center mb-3">
                        <i class="fas fa-certificate fa-3x text-warning"></i>
                    </div>
                    <h5 class="card-title text-center">${certificate.tenChungNhan}</h5>
                    <div class="text-center text-muted mb-3">
                        <fmt:formatDate value="${certificate.ngayCap}" pattern="dd/MM/yyyy"/>
                    </div>
                    
                    <div class="mb-3">
                        <small class="text-muted d-block">
                            <i class="fas fa-calendar-alt"></i> Hoạt Động:
                            <span class="fw-bold">${certificate.hoatDong.tenHoatDong}</span>
                        </small>
                        <small class="text-muted d-block">
                            <i class="fas fa-building"></i> Tổ Chức:
                            <span class="fw-bold">${certificate.hoatDong.toChuc.tenToChuc}</span>
                        </small>
                        <small class="text-muted d-block">
                            <i class="fas fa-clock"></i> Số Giờ:
                            <span class="fw-bold">${certificate.soGio}</span>
                        </small>
                        <small class="text-muted d-block">
                            <i class="fas fa-star"></i> Điểm:
                            <span class="fw-bold">${certificate.diem}</span>
                        </small>
                    </div>

                    <div class="d-grid gap-2">
                        <button class="btn btn-primary" onclick="viewCertificate('${certificate.maChungNhan}')">
                            <i class="fas fa-eye"></i> Xem Chứng Nhận
                        </button>
                        <button class="btn btn-outline-primary" onclick="downloadCertificate('${certificate.maChungNhan}')">
                            <i class="fas fa-download"></i> Tải Xuống
                        </button>
                    </div>
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
                <a class="page-link" href="?page=${currentPage - 1}">
                    <i class="fas fa-chevron-left"></i>
                </a>
            </li>
            
            <c:forEach begin="1" end="${totalPages}" var="page">
                <li class="page-item ${currentPage == page ? 'active' : ''}">
                    <a class="page-link" href="?page=${page}">${page}</a>
                </li>
            </c:forEach>
            
            <li class="page-item ${currentPage == totalPages ? 'disabled' : ''}">
                <a class="page-link" href="?page=${currentPage + 1}">
                    <i class="fas fa-chevron-right"></i>
                </a>
            </li>
        </ul>
    </nav>
</c:if>

<!-- No Certificates -->
<c:if test="${empty certificates}">
    <div class="text-center py-5">
        <i class="fas fa-certificate fa-3x text-muted mb-3"></i>
        <h4>Chưa có chứng nhận nào</h4>
        <p class="text-muted">Hãy tham gia và hoàn thành các hoạt động để nhận chứng nhận</p>
        <a href="<c:url value='/student/activities'/>" class="btn btn-primary">
            <i class="fas fa-search"></i> Tìm Hoạt Động
        </a>
    </div>
</c:if>

<!-- Certificate View Modal -->
<div class="modal fade" id="certificateModal" tabindex="-1">
    <div class="modal-dialog modal-lg modal-dialog-centered">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Chứng Nhận Hoạt Động Tình Nguyện</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
            </div>
            <div class="modal-body">
                <div id="certificateContent" class="text-center">
                    <!-- Certificate content will be loaded here -->
                </div>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript for Certificate Actions -->
<script>
function viewCertificate(certificateId) {
    fetch(`/api/certificates/${certificateId}`)
        .then(response => response.text())
        .then(html => {
            document.getElementById('certificateContent').innerHTML = html;
            new bootstrap.Modal(document.getElementById('certificateModal')).show();
        })
        .catch(error => console.error('Error:', error));
}

function downloadCertificate(certificateId) {
    window.location.href = `/api/certificates/${certificateId}/download`;
}
</script> 