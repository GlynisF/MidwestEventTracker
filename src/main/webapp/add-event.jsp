<%--
  Created by IntelliJ IDEA.
  User: GCADAGFISHER
  Date: 1/26/2024
  Time: 3:17 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="includes/head.jsp"%>
    <title>Title</title>
</head>
<!-- start main container -->
<div class="container">
    <%@include file="includes/nav.jsp"%>

<body class="container">

<!-- start card div -->
<div class="card">
    <h5 class="card-header">Add Event</h5>
    <div class="card-body">
        <h5 class="card-title">Event Details</h5>
        <div id="addEventDiv">
            <form id="addEvent" class="addForm" onsubmit="submitAddEventForm()">
                <jsp:include page="formTemplates/eventForm.html"/>
            </form>
            <form id="addDetails" class="addForm">
                <jsp:include page="formTemplates/detailsForm.html"/>
            </form>
            <input type="hidden" id="notebookId"/>
            <button type="button" id="addButton">button</button>
    </div>
</div>
<!-- end card div -->
</div>

<script>

    $('#addButton').on('click', function(e) {
    e.preventDefault();
    $('.addForm').submit();
})
    function submitAddEventForm(){

        const addEventData = {
            eventId: $('#eventId').val(),
            eventName: $('#eventName').val(),
            user: ${sessionScope.user}
        }
        const eventName = $('#eventName').val();
        fetch('http://localhost:8080/MidwestEventTracker_war/servcies/events/' + eventName, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(addEventData),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
                // Handle success response
            })
            .catch(error => {
                console.error('Error:', error);
                // Handle error
            });

    }
</script>
</body>



</html>