<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html>
<%@include file="includes/head.jsp" %>

<div class="container">
    <%@include file="includes/nav.jsp" %>

    <body class="container-fluid mx-0">
    <div class="row mt-5">
        <!-- Vertical Navigation Bar -->
        <div class="col-md-2" id="sidebar">
            <div class="card">
                <div class="card-body">
                    <div class="d-grid gap-3 fw-bolder mx-auto dropright">
                        <ul class="nav flex-column nav-tabs">
                            <c:forEach var="notebook" items="${sessionScope.user.notebooks}">
                                <c:set var="notebook" value="${notebook}" scope="session"/>
                                <li class="nav-item dropright">
                                    <button class="btn p-3 dropdown-toggle" id="menu1" type="button"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                        <c:out value="${notebook.title}"/>
                                    </button>
                                    <ul class="dropdown-menu col-12 mx-auto" role="menu" aria-labelledby="menu1">
                                        <c:forEach var="event" items="${notebook.events}">
                                            <c:set var="events" value="${event}" scope="session"/>
                                            <li>
                                                <a class="dropdown-item details-button" href="#"
                                                   data-notebook-id="${notebook.id}">${event.eventName}</a>
                                                <input type="hidden" class="event-id" id="eventId" value="${event.id}"/>
                                            </li>
                                        </c:forEach>
                                        <!-- Dropdown Divider with Link -->
                                        <li class="dropdown-divider"></li>
                                        <li>
                                            <a class="dropdown-item" href="#">Additional Link</a>
                                        </li>
                                    </ul>
                                </li>
                            </c:forEach>
                            <button type="button" id="addButton" class="btn btn-secondary">Add Notebook</button>
                            <a href="edit?eventId=" id="jq">jquery</a>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- Event Details Card -->
        <div class="col-md-10">
            <div class="card" id="eventCard"> <!-- Added id to the event card -->
                <div class="card-body">
                    <h5 class="card-title text-center">Event Details</h5>
                    <div class="card-text" id="event-results">
                        <ul id="event-details-list"></ul>
                    </div>
                    <a  id="editButton" class="btn btn-secondary">Edit Notebook</a>
                </div>
            </div>
            <div class="card" id="notebookCard">
                <div class="card-body" id="notebookBody">
                    <h5 class="card-title text-center add-notebook-div">Event Details</h5>
                    <div class="card-text add-notebook-div" id="add-notebook-card">
                        <form id="addNotebookForm" action="addNotebook" method="post">
                            <input class="form-control form-control-lg" type="text" name="title"
                                   placeholder="Notebook title"/>
                            <input type="hidden" name="userId" value="${sessionScope.userId}">
                            <button type="submit" class="btn btn-secondary" id="add-notebook-button">Add Notebook
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="includes/footer.jsp" %>

        <script>
            $(document).ready(function () {
                $('#notebookCard').hide();

                $('.dropdown-item').on('click', function (e) {
                    e.preventDefault();
                    // Clear existing event details when a new notebook is clicked
                    $('#event-details-list').empty();
                });

                $('.details-button').on('click', function (e) {
                    e.preventDefault();
                    getEvents();
                });

                $('#editButton').on('click', function (e) {
                    e.preventDefault();
                    const eventId = $('#eventId').val();
                    const editUrl = 'edit?eventId=' + eventId;
                    $('#editButton').attr('href', editUrl);
                });


                $('#addButton').on('click', function (e) {
                    e.preventDefault();
                    $('#eventCard').hide();
                    $('#editButton').hide();
                    $('#notebookCard').show();
                })

            });

            function getEvents() {
                const notebookId = event.currentTarget.getAttribute('data-notebook-id');

                fetch('http://localhost:8080/MidwestEventTracker_war/services/notebooks/' + encodeURIComponent(notebookId), {
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json; charset=UTF-8',
                        'Content-Type': 'application/json'
                    },
                })
                    .then(response => {
                        console.log(response);
                        return response.json();
                    })
                    .then(data => {
                        console.log(data);
                        let eventData = data;
                        localStorage.setItem('eventData', JSON.stringify(eventData));
                        displayEventDetails(data);
                        return data;
                    })
                    .catch(error => {
                        console.error('Error:', error);
                    });
            }

            function displayEventDetails(eventDetailsList) {
                $('#event-details-list').empty();

                if (eventDetailsList.hasOwnProperty('details')) {
                    const details = eventDetailsList.details;
                    if (details.length > 0) {
                        details.forEach(eventDetails => {
                            const html = `<li id="class">Date of Event: ` + eventDetails.eventDate + `</li>`;
                            $('#event-details-list').append(html).append(`<li> Start Time: ` + eventDetails.startTime + `</li>`).append(`<li> End Time: ` + eventDetails.endTime + `</li>`);

                            if (eventDetailsList.hasOwnProperty('locations')) {
                                const locations = eventDetailsList.locations;
                                if (locations.length > 0) {
                                    locations.forEach(location => {
                                        $('#event-details-list').append(`<li>Venue: ` + location.name + `</li>`).append(`<li>Phone Number: ` + location.phoneNumber + `</li>`).append(`<li>Website: ` + location.website + `</li>`);
                                    });
                                } else {
                                    $('#event-details-list').append('<li>No event details available.</li>');
                                }
                            }
                        });
                    } else {
                        $('#event-details-list').append('<li>No events available.</li>');
                    }
                }
            }
        </script>
    </div>
    </body>
</div>
</html>