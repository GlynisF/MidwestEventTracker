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
            <a class="btn btn-secondary" id="artistButton">Update Artists</a>
        </div>
    </div>
    <%@include file="includes/footer.jsp" %>
</div>
<script>
    $(document).ready(function () {
        const searchParam = new URLSearchParams(window.location.search);
        const eventId = searchParam.get('eventId');

        const editor = new $.fn.dataTable.Editor({
            fields: [
                { label: 'Moniker', name: 'moniker' },
                { label: 'First Name', name: 'artist_first_name' },
                { label: 'Last Name', name: 'artist_last_name' },
                { label: 'Email', name: 'artist_email' },
                { label: 'Booking Fee', name: 'booking_fee' }
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
                { data: 'artist_email' },
                {
                    data: 'booking_fee',
                    render: function (data, type, row) {
                        const roundedData = Number(data).toFixed(2);
                        return '$' + roundedData;
                    }
                }
            ],
            order: [1, 'asc'],
            select: {
                type: 'os'
            },
            lengthChange: false,
            rowId: 'id',
            buttons: [
                { extend: 'edit', editor: editor },
                { extend: 'remove', editor: editor },
                { extend: 'create', editor: editor }
            ],
        });

        // Populate artist table
        <c:forEach var="artist" items="${sessionScope.artists}">
        table.row.add({
            'id': '${artist.id}',
            'moniker': '${artist.moniker}',
            'artist_first_name': '${artist.firstName}',
            'artist_last_name': '${artist.lastName}',
            'artist_email': '${artist.email}',
            'booking_fee': '${artist.bookingFee}'
        }).draw();
        </c:forEach>;

        // Edit cell event
        $('#artistTable').on('click', 'tbody tr td', function () {
            editor.inline($(this), {
                submit: 'allIfChanged'
            });
        });

        // Update Artists button click event
        $('#artistButton').on('click', async function (e) {
            e.preventDefault();

            // Iterate through each row with changes
            table.rows({ search: 'applied' }).every(async function (rowIdx, tableLoop, rowLoop) {
                const data = this.data();
                const artistId = data.id;

                // Check if the row is modified
                if (this.any()) {
                    // Prepare data for submission
                    const rowData = {
                        id: artistId,
                        moniker: data.moniker || '',
                        artist_first_name: data.artist_first_name || '',
                        artist_last_name: data.artist_last_name || '',
                        artist_email: data.artist_email || '',
                        booking_fee: data.booking_fee || ''
                    };

                    try {
                        // Submit data to REST endpoint using Fetch API with await
                        const response = await fetch('http://localhost:8080/MidwestEventTracker_war/services/artists/' + artistId, {
                            method: 'PUT',
                            headers: {
                                'Content-Type': 'application/json',
                                'Accept': 'application/json'
                            },
                            body: JSON.stringify(rowData)
                        });

                        if (!response.ok) {
                            throw new Error('Network response was not ok: ' + response.statusText);
                        }

                        const responseData = await response.json();
                        console.log('Data submitted successfully:', responseData);
                    } catch (error) {
                        console.error('Error submitting data:', error.message);
                    }
                }
            });
        });
<!-- closing brackets for docu ready -->
    });
</script>

</body>
</html>