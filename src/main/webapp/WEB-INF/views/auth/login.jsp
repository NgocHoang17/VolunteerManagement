<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <div class="card">
                <div class="card-header">
                    <h3 class="text-center">Đăng nhập</h3>
                </div>
                <div class="card-body">
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            Tên đăng nhập hoặc mật khẩu không đúng.
                        </div>
                    </c:if>
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success">
                            Bạn đã đăng xuất thành công.
                        </div>
                    </c:if>
                    <c:if test="${registrationSuccess != null}">
                        <div class="alert alert-success">
                            Đăng ký thành công! Vui lòng đăng nhập.
                        </div>
                    </c:if>
                    
                    <form action="<c:url value='/auth/login'/>" method="post">
                        <div class="form-group">
                            <label for="username">Tên đăng nhập</label>
                            <input type="text" class="form-control" id="username" name="username" required>
                        </div>
                        <div class="form-group">
                            <label for="password">Mật khẩu</label>
                            <input type="password" class="form-control" id="password" name="password" required>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block">Đăng nhập</button>
                        </div>
                    </form>
                    <div class="text-center">
                        <p>Chưa có tài khoản? <a href="<c:url value='/auth/register'/>">Đăng ký</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div> 