<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<footer class="footer mt-auto py-3 bg-light">
    <div class="container-fluid">
        <div class="d-flex align-items-center justify-content-between small">
            <div class="text-muted">
                Copyright &copy; Volunteer Management System 2024
            </div>
            <div>
                <a href="<c:url value='/privacy-policy'/>" class="text-muted text-decoration-none">
                    Chính sách bảo mật
                </a>
                &middot;
                <a href="<c:url value='/terms-of-service'/>" class="text-muted text-decoration-none">
                    Điều khoản sử dụng
                </a>
            </div>
        </div>
    </div>
</footer>

<style>
footer {
    position: relative;
    bottom: 0;
    width: 100%;
    border-top: 1px solid #dee2e6;
}
footer a {
    color: #6c757d;
    text-decoration: none;
}
footer a:hover {
    color: #0d6efd;
    text-decoration: underline;
}
</style> 