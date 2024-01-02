<%--
  Created by IntelliJ IDEA.
  User: GCADAGFISHER
  Date: 10/9/2023
  Time: 4:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <%@include file="includes/head.jsp" %>
    <title>Form</title>
</head>

<div class="container">
    <%@include file="includes/nav.jsp" %>
<body>

<div class="container-fluid mt-5">
    <div class="card">
        <div class="card-header">
            <ul class="nav nav-tabs card-header-tabs" id="myTab">
                <li class="nav-item" role="presentation">
                    <a class="nav-link active" id="notebook" data-bs-toggle="tab" data-bs-target="#notebook-tab" type="button" role="tab" aria-controls="notebook-tab-pane" aria-selected="true" href="#">Notebook</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="venue" data-bs-toggle="tab" data-bs-target="#venue-tab" type="button" role="tab" aria-controls="venue-tab-pane" aria-selected="false" href="#">Venue</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="artist" data-bs-toggle="tab" data-bs-target="#artist-tab" type="button" role="tab" aria-controls="artist-tab-pane" aria-selected="false" href="#">Artist</a>
                </li>
            </ul>
        </div>

        <div class="tab-content" id="tab-tabContent">
            <div class="tab-pane fade show active" id="notebook-tab" role="tabpanel" aria-labelledby="notebook-tab" tabindex="0">
                <div class="card-body" id="notebookCard">
                    <h5 class="card-title">Special title treatment</h5>
                    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>


            <div class="tab-pane fade" id="venue-tab" role="tabpanel" aria-labelledby="venue-tab" tabindex="0">
                <div class="card-body" id="venueCard">
                    <h5 class="card-title text-center">Venue Details</h5>
                    <div class="card-text" id="card-form"></div>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>

            <div class="tab-pane fade" id="artist-tab" role="tabpanel" aria-labelledby="artist-tab" tabindex="0">
                <div class="card-body" id="artistCard">
                    <h5 class="card-title">Special title treatment</h5>
                    <p class="card-text">artist card.</p>
                    <a href="#" class="btn btn-primary">Go somewhere</a>
                </div>
            </div>
        </div>
    </div>

<%@include file="includes/footer.jsp"%>

    <script>

        $(document).ready(function () {
            $('#card-form').load("forms.jsp");
        });
        </script>

</body>
</div>
</html>