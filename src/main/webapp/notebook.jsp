<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="includes/head.jsp" %>
    <%@include file="includes/nav.jsp" %>
    <meta charset="UTF-8" />
    <title>Title</title>
</head>
<form>
    <div class="form-group">
        <label for="title">Notebook Title</label>
        <input type="text" class="form-control" id="title" placeholder="Enter an title"/>
    </div>

</form>
<body>
<%@include file="includes/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>
</html>