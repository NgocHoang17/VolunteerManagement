<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập - Hệ thống quản lý hoạt động tình nguyện</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            background: #f8f9fa;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        .login-header {
            text-align: center;
            margin-bottom: 30px;
        }
        .login-header i {
            font-size: 48px;
            color: #28a745;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <i class="fas fa-hands-helping mb-4"></i>
            <h2>Đăng nhập</h2>
            <p class="text-muted">Hệ thống quản lý hoạt động tình nguyện</p>
        </div>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger" role="alert">
                Tên đăng nhập hoặc mật khẩu không đúng!
            </div>
        </c:if>
        
        <c:if test="${param.logout != null}">
            <div class="alert alert-success" role="alert">
                Bạn đã đăng xuất thành công!
            </div>
        </c:if>

        <c:if test="${param.expired != null}">
            <div class="alert alert-warning" role="alert">
                Phiên làm việc đã hết hạn. Vui lòng đăng nhập lại!
            </div>
        </c:if>

        <c:if test="${registrationSuccess}">
            <div class="alert alert-success" role="alert">
                Đăng ký thành công! Vui lòng đăng nhập.
            </div>
        </c:if>

        <form action="<c:url value='/login'/>" method="post">
            <div class="mb-3">
                <label for="username" class="form-label">Tên đăng nhập</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">Mật khẩu</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="remember-me" name="remember-me">
                <label class="form-check-label" for="remember-me">Ghi nhớ đăng nhập</label>
            </div>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-primary w-100 mb-3">Đăng nhập</button>
            <div class="text-center">
                <p class="mb-0">Chưa có tài khoản? <a href="<c:url value='/register'/>">Đăng ký ngay</a></p>
            </div>
        </form>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 