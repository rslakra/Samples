<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Settings</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/navbar-styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/modal-styles.css"/>" rel="stylesheet" type="text/css">
    <style>
        .settings-section {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #ddd;
        }
        .settings-section:last-child {
            border-bottom: none;
        }
        .settings-section h2 {
            color: #333;
            margin-bottom: 10px;
            font-size: 20px;
        }
        .settings-section p {
            color: #666;
            margin-bottom: 20px;
        }
        .form-group label {
            display: flex;
            align-items: center;
            gap: 8px;
        }
        .form-group input[type="checkbox"] {
            width: auto;
            margin: 0;
        }
    </style>
</head>
<body>
    <jsp:include page="../navbar.jsp"/>
    <div class="page-content">
        <div class="form-container">
            <h1>Settings</h1>
            
            <div class="settings-section">
                <h2>Application Settings</h2>
                <p>Configure your application preferences here.</p>
                
                <div class="form-group">
                    <label>Application Name</label>
                    <input type="text" value="Spring Boot JSP Library" readonly class="form-control" style="background-color: #f8f9fa;">
                </div>
                
                <div class="form-group">
                    <label>Version</label>
                    <input type="text" value="1.0.0" readonly class="form-control" style="background-color: #f8f9fa;">
                </div>
                
                <div class="form-group">
                    <label>Database</label>
                    <input type="text" value="H2 Database" readonly class="form-control" style="background-color: #f8f9fa;">
                </div>
            </div>

            <div class="settings-section" style="margin-top: 30px;">
                <h2>User Preferences</h2>
                <p>Customize your user experience.</p>
                
                <div class="form-group">
                    <label>
                        <input type="checkbox" checked> Enable notifications
                    </label>
                </div>
                
                <div class="form-group">
                    <label>
                        <input type="checkbox"> Show book count on home page
                    </label>
                </div>
            </div>

            <div style="margin-top: 30px;">
                <button type="button" class="form-submit" onclick="showSuccessModal()">Save Settings</button>
            </div>
        </div>
    </div>

    <!-- Success Modal -->
    <div id="successModal" class="modal-overlay">
        <div class="modal-dialog">
            <div class="modal-header">
                <h3 class="modal-title">Success</h3>
                <button type="button" class="modal-close" onclick="closeSuccessModal()">&times;</button>
            </div>
            <div class="modal-body">
                <p style="color: #28a745; font-weight: bold;">Settings saved successfully!</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="modal-btn modal-btn-success" onclick="closeSuccessModal()">OK</button>
            </div>
        </div>
    </div>

    <script>
        function showSuccessModal() {
            document.getElementById('successModal').classList.add('show');
        }

        function closeSuccessModal() {
            document.getElementById('successModal').classList.remove('show');
        }

        // Close modal when clicking outside
        document.getElementById('successModal').addEventListener('click', function(e) {
            if (e.target === this) {
                closeSuccessModal();
            }
        });
    </script>
</body>
</html>
