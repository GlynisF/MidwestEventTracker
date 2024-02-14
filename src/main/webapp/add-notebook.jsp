<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="includes/head.jsp"%>
    <title>Title</title>
</head>

<body>
<!-- start main container -->
<div class="container">
    <%@include file="includes/nav.jsp"%>

    <div class="container-fluid mx-auto p-1">
        <div class="row">
            <div class="nav d-flex align-items-start justify-content-xl-between flex-column col-3">
                <div class="nav-item dropend notebook-container">
                    <c:forEach var="notebook" items="${sessionScope.user.notebooks}">
                        <button class="nav-link btn btn-secondary dropdown-toggle active" type="button" data-bs-toggle="dropdown" aria-expanded="false">${notebook.title}</button>
                        <input type="hidden" id="notebookId" value="${notebook.id}"/>
                        <ul class="nav-item dropdown-menu mx-auto" id="notebook-dropdown">
                            <c:choose>
                            <c:when test = "${empty notebook.events}">
                                <li class="dropdown-item">No events</li>
                                <li><hr class="dropdown-divider"></li>
                                <li><button class="dropdown-item" type="button">Create Event</button></li>
                            </c:when>
                            <c:otherwise>
                            <c:forEach var="event" items="${notebook.events}">
                            <li><button class="nav-link dropdown-item" type="button" data-event-id="${event.id}">${event.eventName}</button></li>
                            </c:forEach>
                            <li><hr class="dropdown-divider"></li>
                            <li><button class="dropdown-item" href="#">Create Event</button></li>
                            </c:otherwise>
                            </c:choose>
                        </ul>
                    </c:forEach>
                </div>
            </div>
            <div class="col-9">
                <div class="card">
                        <div class="card-body justify-content-evenly">
                            <div class="card-header" id="details-header">
                                <div class="card-text" id="details-text">

                                </div>
                        </div>
                    </div>
            </div>
            </div>
            <a id="editButton" class="btn btn-secondary">Edit Notebook</a>
        </div>
    </div>

    <%@include file="includes/footer.jsp"%>
</div>
<!-- end main container / div -->

</body>
<script src="js/storageConsts.js"></script>
<script>
    $(document).ready(function() {
        $('.dropdown-item').on('click', function(e) {
            e.preventDefault();
            $('#card-text').empty();
            fetchDetailsData();
            populateDetailsCard(); // Pass eventData to the function
            const eventId = $(this).data('event-id');

            // Update the href attribute of the "Edit Notebook" link
            const editUrl = 'edit?eventId=' + eventId;
            $('#editButton').attr('href', editUrl);
        });


    });
</script>


</html>