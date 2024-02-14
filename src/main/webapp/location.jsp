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
    <c:set var="location" value="${sessionScope.locations}" scope="session"/>
</head>
<body class="container">
<%@include file="includes/nav.jsp" %>
<div class="container">
    <div class="card">
        <div class="card-body">
            <div class="card-header">
                <h5 class="text-center display-4">Location Info</h5>
            </div>
            <div class="text-nowrap">
                <table id="locationTable" class="display mw-auto justify-content-evenly">
                    <thead>
                    <tr>
                        <th scope="col">Venue</th>
                        <th scope="col">Address</th>
                        <th scope="col">Apartment</th>
                        <th scope="col">City</th>
                        <th scope="col">State</th>
                        <th scope="col">Zip</th>
                        <th scope="col">Website</th>
                        <th scope="col">Phone</th>
                        <th scope="col">Disabled Access</th>
                    </tr>
                    </thead>
                    <tbody id="locationBody">
                    <!-- DataTable body will be dynamically populated here -->
                    </tbody>
                </table>
            </div>
            <a class="btn btn-secondary" id="locationButton">Update Locations</a>
        </div>
    </div>
    <%@include file="includes/footer.jsp" %>
</div>
<script>
    (function($){
    $(document).ready(function () {

        const editor = new $.fn.dataTable.Editor({
            fields: [
                { label: 'Venue', name: 'name' },
                { label: 'Address', name: 'address' },
                { label: 'Apartment', name: 'apartment' },
                { label: 'City', name: 'city' },
                { label: 'State', name: 'state' },
                { label: 'Zip', name: 'zip' },
                { label: 'Website', name: 'website' },
                { label: 'Phone', name: 'phoneNumber' },
                {
                    label: 'Disabled Access',
                    name: 'wheelchairAccessibleEntrance',
                    type: 'select',
                    options: [
                        {label:'true', value:'true',},
                        {label:'false', value:'false'},
                    ]
                }
            ],
            formOptions: {
                inline: {
                    onBlur: 'submit',
                    submit: 'allIfChanged',
                }
            },
            idSrc: "id",
            table: '#locationTable',
        });

        const table = $('#locationTable').DataTable({
            dom: 'Bfrtip',
            columns: [
                { data: 'name'},
                {
                    data: 'address',
                    render: function(data, type, row) {
                        if (type === 'display' && row.apartment.length > 0) {
                            return data + ', ' + row.apartment;
                        }
                        return data; // Otherwise, display only the address
                    }
                },
                { data: 'apartment', visible: false},
                { data: 'city', name: 'city' },
                { data: 'state', name: 'state' },
                { data: 'zip', name: 'zip' },
                { data: 'website', name: 'website' },
                { data: 'phoneNumber', name:'phoneNumber' },
                { data: 'wheelchairAccessibleEntrance'},
            ],
            order: [1, 'asc'],
            select: {
                type: 'os'
            },
            lengthChange: false,
            fixedHeader: true,
            rowId: 'id',
            buttons: [
                { extend: 'edit', editor: editor },
                { extend: 'remove', editor: editor },
                { extend: 'create', editor: editor }
            ],
        });

        // Populate location table
        <c:forEach var="location" items="${sessionScope.locations}">
        table.row.add({
            'name': '${location.name}',
            'address': '${location.address}',
            'apartment': '${location.apartment}',
            'city': '${location.city}',
            'state': '${location.state}',
            'zip': '${location.zip}',
            'website': '${location.website}',
            'phoneNumber': '${location.phoneNumber}',
            'wheelchairAccessibleEntrance': '${location.wheelchairAccessibleEntrance}'
        }).draw();
        </c:forEach>;

        // Edit cell event
        $('#locationTable').on('click', 'tbody tr td', function () {
            editor.inline(this);
            let wca = table.column( 'wheelchairAccessibleEntrance:name' ).data();
            console.log(wca);
        });


        const wcaA = table.column( 'wheelchairAccessibleEntrance:name' ).data();

        editor.on( 'open', function ( e, type ) {
            $('select[name="wheelchairAccessibleEntrance"]').filter('option[value="' + wcaA + '"]').prop('selected', true);
        } );


        // Update Locations button click event
        $('#locationButton').on('click', async function (e) {
            e.preventDefault();

            // Iterate through each row with changes
            table.rows({ search: 'applied' }).every(async function (rowIdx, tableLoop, rowLoop) {
                const data = this.data();
                let locationId = table.rows(this).$('#id').val();
                // Check if the row is modified
                if (this.any()) {
                    // Prepare data for submission
                    const rowData = {
                        name: data.name || '',
                        address: data.address || '',
                        apartment: data.apartment || '',
                        city: data.city || '',
                        state: data.state || '',
                        zip: data.zip || '',
                        website: data.website || '',
                        phoneNumber: data.phoneNumber || '',
                        wheelchairAccessibleEntrance: data.wheelchairAccessibleEntrance
                    };

                    try {
                        // Submit data to REST endpoint using Fetch API with await
                        const response = await fetch('http://localhost:8080/MidwestEventTracker_war/services/locations/' + locationId, {
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
    });
    }(jQuery));
</script>

</body>
</html>