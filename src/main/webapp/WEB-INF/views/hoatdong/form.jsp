<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thêm/Chỉnh Sửa Hoạt Động</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="https://unpkg.com/aos@2.3.1/dist/aos.css">
    <link href="<c:url value='/static/style.css'/>" rel="stylesheet">
</head>
<body>
<jsp:include page="../navbar.jsp"/>

<div class="container my-5">
    <div class="text-center mb-4" data-aos="fade-up">
        <h1 class="display-4 fw-bold">${hoatDong.maHD == null ? 'Thêm Hoạt Động' : 'Chỉnh Sửa Hoạt Động'}</h1>
    </div>

    <div class="card shadow-sm" data-aos="fade-up" data-aos-delay="100">
        <div class="card-body">
            <form:form action="${pageContext.request.contextPath}/manage/hoatdong/save" modelAttribute="hoatDong" method="post" id="hoatDongForm">
                <form:hidden path="maHD"/>
                <div class="mb-3">
                    <label class="form-label">Mã HD</label>
                    <form:input path="maHD" class="form-control" required="true" disabled="${hoatDong.maHD != null}"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Tên HD</label>
                    <form:input path="tenHD" class="form-control" required="true"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Địa Điểm</label>
                    <form:input path="diaDiem" class="form-control"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Tổ Chức</label>
                    <form:select path="toChuc.maToChuc" class="form-select" required="true">
                        <c:forEach var="toChuc" items="${toChucs}">
                            <form:option value="${toChuc.maToChuc}">${toChuc.tenToChuc}</form:option>
                        </c:forEach>
                    </form:select>
                </div>
                <div class="mb-3">
                    <label class="form-label">Thời Gian Bắt Đầu</label>
                    <form:input path="thoiGianBatDau" type="datetime-local" class="form-control" required="true"/>
                </div>
                <div class="mb-3">
                    <label class="form-label">Thời Gian Kết Thúc</label>
                    <form:input path="thoiGianKetThuc" type="datetime-local" class="form-control" required="true"/>
                </div>
                <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Lưu</button>
                <a href="<c:url value='/manage/hoatdong'/>" class="btn btn-secondary"><i class="fas fa-arrow-left"></i> Quay lại</a>
            </form:form>
        </div>
    </div>
</div>

<jsp:include page="../footer.jsp"/>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://unpkg.com/aos@2.3.1/dist/aos.js"></script>
<script src="<c:url value='/static/js/scripts.js'/>"></script>
<script>
    AOS.init({duration: 1000});
</script>
</body>
</html> 