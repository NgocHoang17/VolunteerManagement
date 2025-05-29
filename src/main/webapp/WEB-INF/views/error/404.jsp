<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<div class="container">
    <div class="row justify-content-center mt-5">
        <div class="col-md-6 text-center">
            <div class="error-page">
                <h1 class="display-1 text-primary mb-4">404</h1>
                <h2 class="mb-4">Không tìm thấy trang</h2>
                <p class="lead mb-4">
                    Trang bạn đang tìm kiếm không tồn tại hoặc đã bị di chuyển.
                </p>
                <div class="mb-4">
                    <a href="<c:url value='/home'/>" class="btn btn-primary">
                        <i class="fas fa-home"></i> Về trang chủ
                    </a>
                </div>
                <p class="text-muted">
                    Nếu bạn tin rằng đây là lỗi, vui lòng liên hệ quản trị viên để được hỗ trợ.
                </p>
            </div>
        </div>
    </div>
</div>

<style>
.error-page {
    padding: 40px;
    background: #fff;
    border-radius: 10px;
    box-shadow: 0 0 20px rgba(0,0,0,0.1);
}

.error-page h1 {
    font-size: 120px;
    font-weight: bold;
    text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
}

.error-page h2 {
    color: #333;
    font-weight: 500;
}

.error-page .lead {
    color: #666;
}

.error-page .btn {
    padding: 10px 30px;
    font-weight: 500;
}

.error-page .btn i {
    margin-right: 8px;
}
</style> 