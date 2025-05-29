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
    
    <!-- jQuery first -->
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" rel="stylesheet">
    <!-- DataTables CSS -->
    <link href="https://cdn.datatables.net/1.11.5/css/dataTables.bootstrap5.min.css" rel="stylesheet">
    
    <style>
        body {
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }
        .main-wrapper {
            flex: 1;
            display: flex;
            min-height: 0;
        }
        .sidebar {
            background-color: #f8f9fa;
            border-right: 1px solid #dee2e6;
            width: 250px;
            padding: 1rem;
            flex-shrink: 0;
        }
        .main-content {
            flex: 1;
            padding: 1rem;
            overflow-y: auto;
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
        @media (max-width: 768px) {
            .sidebar {
                width: 100%;
                border-right: none;
                border-bottom: 1px solid #dee2e6;
            }
            .main-wrapper {
                flex-direction: column;
            }
        }
    </style>
    
    <!-- Additional head content -->
    <tiles:insertAttribute name="head" ignore="true" />
</head>
<body>
    <!-- Navbar -->
    <tiles:insertAttribute name="navbar" />
    
    <!-- Main Wrapper -->
    <div class="main-wrapper">
        <!-- Sidebar -->
        <c:set var="hasSidebar">
            <tiles:insertAttribute name="sidebar" ignore="true"/>
        </c:set>
        <c:if test="${not empty hasSidebar}">
            <div class="sidebar">
                <tiles:insertAttribute name="sidebar" />
            </div>
        </c:if>
        
        <!-- Content -->
        <main class="main-content">
            <tiles:insertAttribute name="content" />
        </main>
    </div>
    
    <!-- Footer -->
    <tiles:insertAttribute name="footer" />
    
    <!-- Bootstrap Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <!-- DataTables JS -->
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/dataTables.bootstrap5.min.js"></script>
    
    <script>
        $(document).ready(function() {
            try {
                // Initialize DataTables if tables exist
                if ($('#activitiesTable').length) {
                    $('#activitiesTable').DataTable({
                        language: {
                            url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/vi.json'
                        }
                    });
                }
                if ($('#usersTable').length) {
                    $('#usersTable').DataTable({
                        language: {
                            url: 'https://cdn.datatables.net/plug-ins/1.11.5/i18n/vi.json'
                        }
                    });
                }
            } catch (e) {
                console.error('Error initializing DataTables:', e);
            }
        });
    </script>
</body>
</html> 