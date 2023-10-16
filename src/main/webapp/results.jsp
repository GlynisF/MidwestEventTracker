<%--
  Created by IntelliJ IDEA.
  User: GCADAGFISHER
  Date: 9/28/2023
  Time: 12:10 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<div class="container">
    <%@include file="includes/head.jsp" %>
    <%@include file="includes/nav.jsp" %>
    <body>
    <c:choose>
        <c:when test="${empty users}">
            <p>Sorry, there was a problem signing you up. Please try again.</p>
        </c:when>
        <c:otherwise>
            <table class="table table-striped row">
                <thead class="row">
                <tr>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>User Name</th>
                    <th>Email</th>
                    <th>Notebook Title</th>
                </tr>
                </thead>
                <c:forEach var="user" items="${users}">
                    <td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.userName}</td>
                        <td>${user.email}</td>
                        <td>
                        <c:forEach var="notebook" items="${user.notebooks}">
                        ${notebook.id} ${notebook}
                        </c:forEach>
                    </td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
    <%@include file="includes/footer.jsp" %>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
            crossorigin="anonymous"></script>
    </body>
</html>