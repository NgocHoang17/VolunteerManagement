<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><tiles:getAsString name="title"/></title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet">
    <style>
        .sidebar {
            background-color: #f8f9fa;
            min-height: calc(100vh - 56px);
            padding: 1rem;
            border-right: 1px solid #dee2e6;
        }
        .sidebar-content {
            display: flex;
            flex-direction: column;
            gap: 0.5rem;
        }
        .sidebar-link {
            color: #495057;
            text-decoration: none;
            padding: 0.5rem 1rem;
            border-radius: 0.25rem;
            display: flex;
            align-items: center;
            gap: 0.5rem;
        }
        .sidebar-link:hover {
            background-color: #e9ecef;
            color: #212529;
        }
        .sidebar-link.active {
            background-color: #0d6efd;
            color: white;
        }
        .sidebar-link i {
            width: 1.25rem;
        }
        .main-content {
            padding: 1rem;
        }
    </style>
    
    <!-- Additional head content -->
    <tiles:insertAttribute name="head" ignore="true" />
</head>
<body>
    <!-- Navbar -->
    <tiles:insertAttribute name="navbar" />
    
    <!-- Main Content -->
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <c:set var="hasSidebar">
                <tiles:insertAttribute name="sidebar" ignore="true"/>
            </c:set>
            <c:if test="${not empty hasSidebar}">
                <div class="col-md-3 col-lg-2">
                    <tiles:insertAttribute name="sidebar" />
                </div>
            </c:if>
            
            <!-- Content -->
            <div class="${not empty hasSidebar ? 'col-md-9 col-lg-10' : 'col-12'} main-content">
                <tiles:insertAttribute name="content" />
            </div>
        </div>
    </div>
    
    <!-- Footer -->
    <tiles:insertAttribute name="footer" />
    
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- jQuery -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <!-- Custom JavaScript -->
    <script src="<c:url value='/resources/js/script.js'/>"></script>
</body>
</html> 