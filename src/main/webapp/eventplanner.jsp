<%--
  Created by IntelliJ IDEA.
  User: GCADAGFISHER
  Date: 10/9/2023
  Time: 4:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="includes/head.jsp" %>
    <title>Event Planner</title>
</head>

<body>
<div class="container">
    <%@include file="includes/nav.jsp" %>

    <div class="card">
        <div class="card-header">
            <ul class="nav nav-tabs card-header-tabs" id="myTab">
                <li class="nav-item" role="presentation">
                    <a class="nav-link active" id="notebook-tab" data-bs-toggle="tab" data-bs-target="#notebook-tab-content" type="button" role="tab" aria-controls="notebook-tab-content" aria-selected="true" href="#">Notebook</a>
                </li>
                <li class="nav-item" role="presentation">
                    <a class="nav-link" id="event-tab" data-bs-toggle="tab" data-bs-target="#event-tab-content" type="button" role="tab" aria-controls="event-tab-content" aria-selected="false" href="#">Event</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="venue-tab" data-bs-toggle="tab" data-bs-target="#venue-tab-content" type="button" role="tab" aria-controls="venue-tab-content" aria-selected="false" href="#">Venue</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="artist-tab" data-bs-toggle="tab" data-bs-target="#artist-tab-content" type="button" role="tab" aria-controls="artist-tab-content" aria-selected="false" href="#">Artist</a>
                </li>
            </ul>
        </div>

        <div class="tab-content" id="tab-tabContent">
            <div class="tab-pane fade show active" id="notebook-tab-content" role="tabpanel" aria-labelledby="notebook-tab" tabindex="0">
                <div class="card-body" id="notebookCard">
                    <h5 class="card-title text-center">Notebooks</h5>
                    <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                    <div class="card-text" id="notebook-card">

                    </div>
                    <div class="card-text" id="edit-notebooks"></div>
                    <a href="#add-notebook" id="addNotebookLink" class="btn btn-primary">Create new Notebook</a>
                </div>
            </div>

            <div class="tab-pane fade" id="event-tab-content" role="tabpanel" aria-labelledby="event-tab" tabindex="0">
                <div class="card-body" id="eventCard">
                    <h5 class="card-title text-center">Event Details</h5>
                    <div class="card-text" id="event-form">
                        <jsp:include page="edit-event.jsp"/>
                    </div>
                    <div class="card-text" id="event-results"></div>
                </div>
            </div>

            <div class="tab-pane fade" id="venue-tab-content" role="tabpanel" aria-labelledby="venue-tab" tabindex="0">
                <div class="card-body" id="venueCard">
                    <h5 class="card-title text-center">Venue Details</h5>
                    <div class="card-text" id="card-form">
                        <jsp:include page="edit-location.jsp"/>
                    </div>
                    <div class="card-text" id="form-results"></div>
                    <a href="#location2"></a>
                </div>
            </div>

            <div class="tab-pane fade" id="artist-tab-content" role="tabpanel" aria-labelledby="artist-tab" tabindex="0">
                <div class="card-body" id="artistCard">
                    <h5 class="card-title">Special title treatment</h5>
                    <p class="card-text">artist card.</p>
                    <div class="card-text" id="artist-card">
                        <jsp:include page="edit-artist.jsp"/>
                </div>
            </div>
        </div>

        <button id="updateEvent" type="submit" name="eventForm" class="btn btn-primary">Update Event</button>
    </div>

    <%@include file="includes/footer.jsp"%>

        <script>
            $(document).ready(function () {
                $('#updateEvent').on('click', function (e) {
                    e.preventDefault();
                    console.log('Button clicked!');
                    $('.editForm').submit();// Prevent default form submission;
                });


                $('form input').on('change', function () {
                    $(this).addClass('fw-bolder border-1 border-black');
                    getLocationFormValues();
                });
            });
        </script>

</body>
</html>