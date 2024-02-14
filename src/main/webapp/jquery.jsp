<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="includes/head.jsp" %>
    <link rel="stylesheet" href="https://cdn.datatables.net/v/dt/jqc-1.12.4/dt-1.13.6/b-2.4.2/sl-1.7.0/datatables.min.css"/>
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
                <h5 class="text-center display-4">Artist Info</h5>
            </div>
            <table id="artistTable" class="display">
                <thead>
                <tr>
                    <th></th>
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
    <%@include file="includes/footer.jsp" %>
</div>

<script>
    $(document).ready(function() {
        const searchParam = new URLSearchParams(window.location.search);
        const eventId = searchParam.get('eventId');


        const editor = new $.fn.dataTable.Editor({
            fields: [
                { label: 'Moniker', name: 'moniker' },
                { label: 'First Name', name: 'artist_first_name' },
                { label: 'Last Name', name: 'artist_last_name' },
                { label: 'Email', name: 'artist_email' },
                { label: 'Booking Fee', name: 'bookingFeeFormatted' }
            ],
            idSrc: 'id',
            table: '#artistTable',
        });

        const table = $('#artistTable').DataTable({
            dom: 'Bfrtip',
            columns: [
                { data: 'id', visible: false },
                { data: 'moniker' },
                { data: 'artist_first_name' },
                { data: 'artist_last_name' },
                { data: 'artist_email'},
                { data: 'bookingFeeFormatted' }
            ],
            order: [1, 'asc'],
            select: {
                style: 'os',
                selector: 'td:first-child'
            },
            lengthChange: false,
            rowId: 'id',
            buttons: [
                { extend: 'edit', editor: editor },
                { extend: 'remove', editor: editor },
                { extend: 'create', editor: editor }
            ],
        });

        // Populate DataTable using JSTL
        <c:forEach var="artist" items="${sessionScope.artists}">
        table.row.add({
            'id': '${artist.id}',
            'moniker': '${artist.moniker}',
            'artist_first_name': '${artist.firstName}',
            'artist_last_name': '${artist.lastName}',
            'artist_email': '${artist.email}',
            'bookingFeeFormatted': '${artist.bookingFeeFormatted}'
        }).draw();
        </c:forEach>;

        // Inline editing on cell click
        $('#artistTable').on('click', 'tbody td:not(:first-child)', function() {
                editor.inline(this, {
                    onBackground: 'blur',
                    onBlur: submitArtistForm()
                });
            });

        function submitArtistForm() {
            const selectedRows = table.rows({selected: true}).data();

            editor.on('blur', function (e, json, data, action) {

                // Capture the modified data
                const modifiedData = json.data[0];
                console.log(modifiedData);
                const selectedRowId = table.rows({selected: true}).data()[0].id;
                console.log(selectedRowId);
                // Send the modified data to the server using your own Ajax call
                $.ajax({
                    url: 'http://localhost:8080/MidwestEventTracker_war/services/artists/' + selectedRowId,
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(modifiedData),
                    success: function (response) {
                        console.log(data);
                        console.log('Data saved successfully');
                    },
                    error: function (error) {
                        console.error('Error saving data:', error);
                    }
                });

            });
        }
    });



</script>
</body>
</html>