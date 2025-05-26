<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký - Hệ thống quản lý hoạt động tình nguyện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: #f8f9fa;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px 0;
        }
        .register-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 500px;
        }
        .register-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .register-header i {
            font-size: 48px;
            color: #28a745;
        }
        .form-error {
            color: #dc3545;
            font-size: 0.875rem;
            margin-top: 0.25rem;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="register-header">
            <i class="fas fa-user-plus mb-4"></i>
            <h2>Đăng ký tài khoản</h2>
            <p class="text-muted">Hệ thống quản lý hoạt động tình nguyện</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/register" method="post" modelAttribute="user" onsubmit="return validateForm()">
            <div class="mb-3">
                <label for="username" class="form-label">Tên đăng nhập <span class="text-danger">*</span></label>
                <form:input path="username" class="form-control" required="required" />
                <form:errors path="username" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                <form:password path="password" class="form-control" required="required" />
                <form:errors path="password" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Xác nhận mật khẩu <span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="confirmPassword" required>
                <div id="passwordError" class="form-error" style="display: none;">
                    Mật khẩu xác nhận không khớp
                </div>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                <form:input path="email" type="email" class="form-control" required="required" />
                <form:errors path="email" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="fullName" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                <form:input path="fullName" class="form-control" required="required" />
                <form:errors path="fullName" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Số điện thoại</label>
                <form:input path="phone" class="form-control" pattern="[0-9]{10}" title="Vui lòng nhập số điện thoại 10 chữ số" />
                <form:errors path="phone" cssClass="form-error" />
            </div>

            <button type="submit" class="btn btn-primary w-100 mb-3">Đăng ký</button>
            <div class="text-center">
                <p class="mb-0">Đã có tài khoản? <a href="<c:url value='/login'/>">Đăng nhập</a></p>
            </div>
        </form:form>
    </div>

    <script>
        function validateForm() {
            var password = document.getElementById('password').value;
            var confirmPassword = document.getElementById('confirmPassword').value;
            var passwordError = document.getElementById('passwordError');
            
            if (password !== confirmPassword) {
                passwordError.style.display = 'block';
                return false;
            }
            passwordError.style.display = 'none';
            return true;
        }
    </script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 