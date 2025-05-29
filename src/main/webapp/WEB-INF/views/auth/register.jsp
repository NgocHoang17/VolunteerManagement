<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng ký - Hệ thống quản lý hoạt động tình nguyện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: linear-gradient(135deg, #0143a3, #0273d4);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px 0;
        }
        .register-container {
            max-width: 600px;
            margin: 2rem auto;
            padding: 2rem;
            background: #fff;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }
        .register-header {
            text-align: center;
            margin-bottom: 2rem;
        }
        .register-header i {
            font-size: 3rem;
            color: #0143a3;
            margin-bottom: 1rem;
        }
        .register-header h2 {
            color: #333;
            margin-bottom: 0.5rem;
        }
        .form-label {
            font-weight: 500;
            color: #495057;
        }
        .form-control {
            padding: 0.75rem;
            border-radius: 8px;
            border: 1px solid #ced4da;
            transition: all 0.3s;
        }
        .form-control:focus {
            border-color: #0143a3;
            box-shadow: 0 0 0 0.2rem rgba(1, 67, 163, 0.25);
        }
        .btn-primary {
            background: #0143a3;
            border: none;
            padding: 12px;
            font-weight: 500;
            letter-spacing: 0.5px;
            transition: all 0.3s;
        }
        .btn-primary:hover {
            background: #0273d4;
            transform: translateY(-2px);
        }
        .form-error {
            color: #dc3545;
            font-size: 0.875rem;
            margin-top: 0.25rem;
        }
        .alert {
            border-radius: 8px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <div class="register-header">
            <i class="fas fa-user-plus"></i>
            <h2>Đăng ký tài khoản</h2>
            <p class="text-muted">Hệ thống quản lý hoạt động tình nguyện</p>
        </div>

        <c:if test="${not empty error}">
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
        </c:if>

        <c:if test="${not empty successMessage}">
            <div class="alert alert-success" role="alert">
                ${successMessage}
            </div>
        </c:if>

        <form:form action="${pageContext.request.contextPath}/auth/register" method="post" modelAttribute="user" onsubmit="return validateForm()">
            <div class="mb-3">
                <label for="username" class="form-label">Tên đăng nhập <span class="text-danger">*</span></label>
                <form:input path="username" class="form-control" required="required" placeholder="Nhập tên đăng nhập"/>
                <form:errors path="username" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu <span class="text-danger">*</span></label>
                <form:password path="password" class="form-control" required="required" placeholder="Nhập mật khẩu"/>
                <form:errors path="password" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Xác nhận mật khẩu <span class="text-danger">*</span></label>
                <input type="password" class="form-control" id="confirmPassword" required placeholder="Nhập lại mật khẩu">
                <div id="passwordError" class="form-error" style="display: none;">
                    Mật khẩu xác nhận không khớp
                </div>
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email <span class="text-danger">*</span></label>
                <form:input path="email" type="email" class="form-control" required="required" placeholder="Nhập địa chỉ email"/>
                <form:errors path="email" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="fullName" class="form-label">Họ và tên <span class="text-danger">*</span></label>
                <form:input path="fullName" class="form-control" required="required" placeholder="Nhập họ và tên"/>
                <form:errors path="fullName" cssClass="form-error" />
            </div>

            <div class="mb-3">
                <label for="phone" class="form-label">Số điện thoại</label>
                <form:input path="phone" class="form-control" placeholder="Nhập số điện thoại"/>
                <form:errors path="phone" cssClass="form-error" />
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-user-plus"></i> Đăng ký
                </button>
            </div>

            <div class="text-center mt-3">
                <p>Đã có tài khoản? <a href="<c:url value='/auth/login'/>">Đăng nhập</a></p>
            </div>
        </form:form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
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
</body>
</html> 