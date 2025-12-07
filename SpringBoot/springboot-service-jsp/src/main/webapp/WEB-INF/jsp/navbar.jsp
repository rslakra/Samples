<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar">
    <div class="navbar-container">
        <a href="<c:url value="/"/>" class="navbar-brand">Spring Boot JSP Library</a>
        <div class="navbar-menu-wrapper">
            <ul class="navbar-menu navbar-menu-left">
                <li class="navbar-item">
                    <a href="<c:url value="/"/>" class="navbar-link">Home</a>
                </li>
                <li class="navbar-item dropdown">
                    <a href="#" class="navbar-link dropdown-toggle">Book</a>
                    <ul class="dropdown-menu">
                        <li><a href="<c:url value="/book/viewBooks"/>">List Books</a></li>
                        <li><a href="<c:url value="/book/addBook"/>">Add Book</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="navbar-menu navbar-menu-right">
                <li class="navbar-item dropdown">
                    <a href="#" class="navbar-link dropdown-toggle">Profile</a>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="<c:url value="/profile/settings"/>">Settings</a></li>
                        <li><a href="<c:url value="/profile/logout"/>">Logout</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

