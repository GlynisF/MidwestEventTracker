<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="includes/head.jsp"%>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.7/css/jquery.dataTables.css" />
    <link rel="stylesheet" type="text/css" href="css/editor.dataTables.min.css"/>
    <title>Title</title>
</head>
<body class="container">
<%@include file="includes/nav.jsp"%>
<div class="container">
    <div class="card">
        <div class="card-body">
            <div class="card-header">
                <h5>Artist Info</h5>
            </div>
            <table id="artistTable" class="display">
                <thead>
                <tr>
                    <th>Moniker</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Booking Fee</th>
                </tr>
                </thead>
                <tbody id="artistBody">
                <!-- DataTable body will be dynamically populated here -->
                </tbody>
            </table>
            <button type="button" id="artistButton">Update Artists</button>
        </div>
    </div>
    <%@include file="includes/footer.jsp"%>
</div>

<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.js"></script>
<script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.js"></script>
<script>

    $(document).ready(function() {
        const editArtistDetails = JSON.parse(localStorage.getItem('eventData')) || {artists: []};
        const table = $('#artistTable').DataTable({
            data: editArtistDetails.artists,
            columns: [
                { data: 'id' },
                { data: 'moniker'},
                { data: 'artist_first_name'},
                { data: 'artist_last_name'},
                { data: 'artist_email'},
                { data: 'booking_fee'}
            ],
            select: true,
            lengthChange: false,
            buttons: [
                { extend: 'create', editor: editor },
                { extend: 'edit',   editor: editor },
                { extend: 'remove', editor: editor }
            ]
        });

        const editor = new $.fn.dataTable.Editor({
            ajax: {
                create: {
                    type: 'POST',
                    url: function (data) {
                        return 'http://localhost:8080/MidwestEventTracker_war/services/artists/update/' + data.id; // Your endpoint URL for updating
                    }
                },
                edit: {
                    type: 'PUT',
                    url: function (data) {
                        return 'http://localhost:8080/MidwestEventTracker_war/services/artists/update/' + data.id; // Your endpoint URL for updating
                    }
                },
                remove: {
                    type: 'DELETE',
                    url: function (data) {
                        return 'http://localhost:8080/MidwestEventTracker_war/j.jsp/' + data.id; // Your endpoint URL for deleting
                    }
                }
            },
            table: '#artistTable',
            fields: [
                {
                    label: "ID:",
                    name: "id",
                    type: "hidden"
                },
                {
                    label: "Moniker:",
                    name: "moniker"
                },
                {
                    label: "First Name:",
                    name: "artist_first_name"
                },
                {
                    label: "Last Name:",
                    name: "artist_last_name"
                },
                {
                    label: "Email:",
                    name: "artist_email"
                },
                {
                    label: "Booking Fee:",
                    name: "booking_fee"
                }
            ]
        });

        // Attach the submitArtistForm function to the button click event
        $('#artistButton').on('click', function(e) {
            e.preventDefault();
            submitArtistForm();
        });

        function submitArtistForm() {
            const selectedRows = table.rows({selected: true}).data();

            if (selectedRows.length > 0) {
                // Assuming the data structure has an 'id' property
                const artistId = selectedRows[0].id;

                editor.field('id').val(artistId);
                editor.submit();
            } else {
                console.log('No rows selected for editing.');
            }
        }
    });
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="en">
        <head>
        <%@include file="includes/head.jsp" %>
        <link rel="stylesheet"
    href="https://cdn.datatables.net/v/dt/jqc-1.12.4/dt-1.13.6/b-2.4.2/sl-1.7.0/datatables.min.css"/>
        <link rel="stylesheet" href="Editor-2.2.2/css/editor.dataTables.css">

        <script src="https://cdn.datatables.net/v/dt/jqc-1.12.4/dt-1.13.6/b-2.4.2/sl-1.7.0/datatables.min.js"></script>
<script src="Editor-2.2.2/js/dataTables.editor.js"></script>
<title>Title</title>
<c:set var="artistsList" value="${sessionScope.artists}" scope="session"/>
</head>
<body class="container">
<%@include file="includes/nav.jsp" %>
<div class="container">
    <div class="card">
        <div class="card-body">
            <div class="card-header">
                <h5>Artist Info</h5>
            </div>
            <table id="artistTable" class="display">
                <thead>
                <tr>
                    <th>id</th>
                    <th>Moniker</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Email</th>
                    <th>Booking Fee</th>
                </tr>
                </thead>
            </table>
            <button type="button" id="artistButton">Update Artists</button>
        </div>
    </div>
    <%@include file="includes/footer.jsp" %>
</div>

<script>
    $(document).ready(function() {
        const searchParam = new URLSearchParams(window.location.search);
        const eventId = searchParam.get('eventId');

        const editor = new $.fn.dataTable.Editor({
            fields: [
                {label: 'Moniker', name: 'moniker'},
                {label: 'First Name', name: 'artist_first_name'},
                {label: 'Last Name', name: 'artist_last_name'},
                {label: 'Email', name: 'artist_email'},
                {label: 'Booking Fee', name: 'bookingFeeFormatted'}
            ],
            idSrc: 'id',
            table: '#artistTable'
        });

        // Fetch data from the server using AJAX
        $.ajax({
            url: 'http://localhost:8080/MidwestEventTracker_war/services/artists/' + eventId,
            method: 'GET',
            contentType: 'application/json',
            success: function (data) {
                // Initialize DataTable with fetched data
                const table = $('#artistTable').DataTable({
                    data: data,
                    dom: 'Bfrtip',
                    columns: [
                        {data: 'id', visible: false},
                        {data: 'moniker'},
                        {data: 'artist_first_name'},
                        {data: 'artist_last_name'},
                        {data: 'artist_email'},
                        {data: 'bookingFeeFormatted'}
                    ],
                    select: true,
                    lengthChange: false,
                    rowId: 'id',
                    buttons: [
                        {extend: 'edit', editor: editor},
                        {extend: 'remove', editor: editor}
                    ]
                });
            },
            error: function (error) {
                console.error('Error fetching data:', error);
            }
        });


    });


</script>

</body>
</html>
</script>

</body>
</html>