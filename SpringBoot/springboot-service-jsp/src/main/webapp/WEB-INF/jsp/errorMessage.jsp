<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/navbar-styles.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    <div class="page-content">
        <div class="form-container">
            <h1>Error</h1>
            <div class="error-message">
                <c:if test="${not empty errorTitle}">
                    <h2>${errorTitle}</h2>
                </c:if>
                <c:if test="${empty errorTitle}">
                    <h2>An Error Occurred</h2>
                </c:if>
                
                <c:if test="${not empty message}">
                    <p><strong>Message:</strong> ${message}</p>
                </c:if>
                
                <c:if test="${not empty ref}">
                    <p><strong>Reference:</strong> ${ref}</p>
                </c:if>
                
                <c:if test="${not empty errorCode}">
                    <p><strong>Error Code:</strong> ${errorCode}</p>
                </c:if>
                
                <c:if test="${not empty timestamp}">
                    <p><strong>Timestamp:</strong> ${timestamp}</p>
                </c:if>
                
                <c:if test="${not empty object}">
                    <p><strong>Details:</strong></p>
                    <ul>
                        <c:choose>
                            <c:when test="${not empty object.isbn}">
                                <li>ISBN: ${object.isbn}</li>
                                <c:if test="${not empty object.name}">
                                    <li>Name: ${object.name}</li>
                                </c:if>
                                <c:if test="${not empty object.author}">
                                    <li>Author: ${object.author}</li>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                <li>${object}</li>
                            </c:otherwise>
                        </c:choose>
                    </ul>
                </c:if>
                
                <c:if test="${not empty stackTrace}">
                    <details style="margin-top: 15px;">
                        <summary style="cursor: pointer; color: #666;">Technical Details</summary>
                        <pre style="background-color: #f8f9fa; padding: 10px; border-radius: 4px; overflow-x: auto; margin-top: 10px; font-size: 12px;">${stackTrace}</pre>
                    </details>
                </c:if>
            </div>
            <div style="margin-top: 20px;">
                <a href="<c:url value="/"/>" class="form-submit" style="text-decoration: none; display: inline-block;">Go to Home</a>
                <c:if test="${not empty backUrl}">
                    <a href="<c:url value="${backUrl}"/>" class="btn-edit" style="margin-left: 10px; text-decoration: none; display: inline-block;">Go Back</a>
                </c:if>
            </div>
        </div>
    </div>
</body>
</html>
