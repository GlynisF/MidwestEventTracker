<%--
  Created by IntelliJ IDEA.
  User: GCADAGFISHER
  Date: 9/27/2023
  Time: 7:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<%@include file="includes/head.jsp" %>

<body class="container">

<%@include file="includes/nav.jsp" %>

<h1 class="row">Sign up form</h1>

<div class="row">

    <form action="searchUser" method="GET" class="col-lg-9 gap-3 mx-auto justify-content-center">
        <fieldset>
            <legend>User Details</legend>
            <div class="form-group">
                <label for="user_name" class="form-label mt-4">User Name</label>
                <input type="text" class="form-control" id="user_name" name="user_name" placeholder="Pick a username">
            </div>
            <div class="form-group">
                <label for="first_name" class="form-label mt-4">First Name</label>
                <input type="text" class="form-control" id="first_name" name="first_name"
                       placeholder="Enter a first name">
            </div>
            <div class="form-group">
                <label for="last_name" class="form-label mt-4">Last Name</label>
                <input type="text" class="form-control" id="last_name" name="last_name" placeholder="Enter a last name">
            </div>
            <div class="form-group">
                <label for="date_of_birth" class="form-label mt-4">Birthdate</label>
                <input type="date" class="form-control" id="date_of_birth" name="date_of_birth"
                       placeholder="Enter a  (yyyy/mm/dd)">
            </div>
            <div class="form-group">
                <label for="gender" class="form-label mt-4">Example select</label>
                <select class="form-select" id="gender" name="gender">
                    <option value="M">Male</option>
                    <option value="F">Female</option>
                    <option value="NB">Non-Binary</option>
                    <option value="NS">Prefer not to say</option>
                </select>
            </div>
            <div class="form-group">
                <label for="email_address" class="form-label mt-4">Email address</label>
                <input type="email" class="form-control" id="email_address" name="email_address"
                       placeholder="Enter an email address">
            </div>
            <div class="form-group">
                <label for="password" class="form-label mt-4">Password</label>
                <input type="password" class="form-control" id="password" name="password"
                       placeholder="Enter a password">
            </div>
        </fieldset>
        <div class="form-group d-grid gap-2 mt-5">
            <button class="btn btn-lg btn-primary" name="submit" type="submit">Submit</button>
            <button class="btn btn-lg btn-secondary border-black border-2" name="reset" type="reset">Clear form</button>
        </div>
    </form>
</div>
<%@include file="includes/footer.jsp" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
        crossorigin="anonymous"></script>
</body>
</html>