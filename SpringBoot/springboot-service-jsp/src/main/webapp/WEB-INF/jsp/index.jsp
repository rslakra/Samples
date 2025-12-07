<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Spring Boot JSP - Home</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/navbar-styles.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="navbar.jsp"/>
    <div class="container">
        <h1>Welcome to Spring Boot JSP Library</h1>
        <p class="subtitle">Book Management System</p>
        <p class="description">
            Manage your library collection with ease. Add new books, view your collection, 
            and keep track of all your favorite reads.
        </p>
        
        <div class="nav-buttons">
            <a href="<c:url value="/book/addBook"/>" class="nav-button secondary">Add New Book</a>
            <a href="<c:url value="/book/viewBooks"/>" class="nav-button">View All Books</a>
        </div>
        
        <div class="features">
            <h2>Features</h2>
            <ul>
                <li>Add books with ISBN, title, and author information</li>
                <li>View your complete book collection</li>
                <li>H2 database integration for data persistence</li>
                <li>Built with Spring Boot 3.5.7 and JSP</li>
            </ul>
        </div>
    </div>
</body>
</html>
