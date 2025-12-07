<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>View Books</title>
    <link href="<c:url value="/css/styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/navbar-styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/table-styles.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/css/modal-styles.css"/>" rel="stylesheet" type="text/css">
</head>
<body>
    <jsp:include page="../navbar.jsp"/>
    <div class="page-content">
        <div class="page-header">
            <h1>Book Collection</h1>
            <a href="<c:url value="/book/addBook"/>" class="btn-add">+ Add Book</a>
        </div>

        <!-- Toast Notification -->
        <c:if test="${addBookSuccess}">
            <div id="toast" class="toast show">
                Successfully added Book with ISBN: ${savedBook.isbn}
            </div>
        </c:if>
        <c:if test="${updateBookSuccess}">
            <div id="toast" class="toast show">
                Successfully updated Book with ISBN: ${updatedBook.isbn}
            </div>
        </c:if>
        <c:if test="${deleteBookSuccess}">
            <div id="toast" class="toast show">
                Successfully deleted Book with ISBN: ${deletedIsbn}
            </div>
        </c:if>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>ISBN</th>
                        <th>Name</th>
                        <th>Author</th>
                        <th class="action-column">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty books}">
                            <tr>
                                <td colspan="4" style="text-align: center; padding: 20px;">
                                    No books found. <a href="<c:url value="/book/addBook"/>">Add your first book</a>
                                </td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${books}" var="book">
                                <tr>
                                    <td>${book.isbn}</td>
                                    <td>${book.name}</td>
                                    <td>${book.author}</td>
                                    <td class="action-column">
                                        <a href="<c:url value="/book/editBook?isbn=${book.isbn}"/>" class="btn-action-edit" title="Edit Book">✏️</a>
                                        <button type="button" class="btn-action-delete" onclick="showDeleteModal('${book.isbn}', '${book.name}')" title="Delete Book">🗑️</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Delete Confirmation Modal -->
    <div id="deleteModal" class="modal-overlay">
        <div class="modal-dialog">
            <div class="modal-header">
                <h3 class="modal-title">Confirm Delete</h3>
                <button type="button" class="modal-close" onclick="closeDeleteModal()">&times;</button>
            </div>
            <div class="modal-body">
                <p>Are you sure you want to delete the book "<span id="deleteBookName"></span>"?</p>
                <p style="color: #dc3545; font-weight: bold;">This action cannot be undone.</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="modal-btn modal-btn-secondary" onclick="closeDeleteModal()">Cancel</button>
                <form id="deleteForm" action="<c:url value="/book/deleteBook"/>" method="post" style="display: inline;">
                    <input type="hidden" name="isbn" id="deleteIsbn"/>
                    <button type="submit" class="modal-btn modal-btn-danger">Delete</button>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Auto-hide toast after 3 seconds
        const toast = document.getElementById('toast');
        if (toast) {
            setTimeout(function() {
                toast.classList.remove('show');
                setTimeout(function() {
                    toast.remove();
                }, 300);
            }, 3000);
        }

        // Delete Modal Functions
        function showDeleteModal(isbn, bookName) {
            document.getElementById('deleteIsbn').value = isbn;
            document.getElementById('deleteBookName').textContent = bookName;
            document.getElementById('deleteModal').classList.add('show');
        }

        function closeDeleteModal() {
            document.getElementById('deleteModal').classList.remove('show');
        }

        // Close modal when clicking outside
        document.getElementById('deleteModal').addEventListener('click', function(e) {
            if (e.target === this) {
                closeDeleteModal();
            }
        });
    </script>
</body>
</html>
