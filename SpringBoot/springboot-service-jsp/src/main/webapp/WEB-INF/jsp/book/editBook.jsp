<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><c:choose><c:when test="${isEditMode}">Edit Book</c:when><c:otherwise>Add Book</c:otherwise></c:choose></title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/navbar-styles.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="../navbar.jsp"/>
    <div class="page-content">
        <div class="form-container">
            <h1><c:choose><c:when test="${isEditMode}">Edit Book</c:when><c:otherwise>Add New Book</c:otherwise></c:choose></h1>
            <c:url var="save_book_url" value="/book/saveBook"/>
            <form:form action="${save_book_url}" method="post" modelAttribute="book">
                <div class="form-group">
                    <form:label path="isbn">ISBN:</form:label>
                    <c:choose>
                        <c:when test="${isEditMode}">
                            <form:input type="text" path="isbn" cssClass="form-control" readonly="true" style="background-color: #f8f9fa;"/>
                        </c:when>
                        <c:otherwise>
                            <form:input type="text" path="isbn" cssClass="form-control"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="form-group">
                    <form:label path="name">Book Name:</form:label>
                    <form:input type="text" path="name" cssClass="form-control"/>
                </div>
                <div class="form-group">
                    <form:label path="author">Author Name:</form:label>
                    <form:input type="text" path="author" cssClass="form-control"/>
                </div>
                <div style="display: flex; gap: 10px; align-items: center;">
                    <button type="submit" class="form-submit">
                        <c:choose><c:when test="${isEditMode}">Update Book</c:when><c:otherwise>Add Book</c:otherwise></c:choose>
                    </button>
                    <a href="<c:url value="/book/viewBooks"/>" class="btn-edit">Cancel</a>
                </div>
            </form:form>
        </div>
    </div>
</body>
</html>

