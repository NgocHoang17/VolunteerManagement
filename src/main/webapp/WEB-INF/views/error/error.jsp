<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container">
    <div class="row justify-content-center">
        <div class="col-lg-6">
            <div class="text-center mt-4">
                <h1 class="display-1">Lỗi</h1>
                <p class="lead">Đã có lỗi xảy ra</p>
                <c:if test="${not empty errorMessage}">
                    <p class="text-danger">${errorMessage}</p>
                </c:if>
                <c:if test="${not empty errorDetails}">
                    <div class="alert alert-danger">
                        <pre class="mb-0">${errorDetails}</pre>
                    </div>
                </c:if>
                <a href="<c:url value='/'/>" class="btn btn-primary">
                    <i class="fas fa-arrow-left me-1"></i>
                    Trở về trang chủ
                </a>
            </div>
        </div>
    </div>
</div>

<style>
.display-1 {
    font-size: 6rem;
    font-weight: 300;
    line-height: 1.2;
}
.lead {
    font-size: 1.25rem;
    font-weight: 300;
}
pre {
    white-space: pre-wrap;
    word-wrap: break-word;
}
</style> 