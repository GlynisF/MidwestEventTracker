<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <title>Autocomplete Example</title>
</head>

<body class="container">
<div class="container">
    <form class="row g-3 mt-3 justify-content-between" action="http://localhost:8080/MidwestEventTracker_war/services/location" method="post" id="autocompleteForm" autocomplete="off">
        <div class="col-md-6">
            <label for="name" class="form-label fw-semibold">Venue Name</label>
            <input type="text" class="form-control" id="name" placeholder="Name" name="name" autocomplete="off" required>
        </div>
        <div class="col-md-6">
            <label for="phone_number" class="form-label fw-semibold">Phone Number</label>
            <input type="tel" class="form-control" id="phone_number" name="phone_number" placeholder="Phone #">
        </div>
        <div class="col-12">
            <label for="address" class="form-label fw-semibold">Address</label>
            <input type="text" class="form-control" id="address" name="address" placeholder="Address" required>
        </div>
        <div class="col-12">
            <label for="apartment" class="form-label fw-semibold">Address 2</label>
            <input type="text" class="form-control" id="apartment" placeholder="Apartment, studio, or floor">
        </div>
        <div class="col-md-6">
            <label for="city" class="form-label fw-semibold">City</label>
            <input type="text" class="form-control" id="city" name="city" placeholder="City" required>
        </div>
        <div class="col-md-4">
            <label for="state" class="form-label fw-semibold">State</label>
            <input type="text" class="form-control" id="state" name="state" maxlength="2" placeholder="State shorthand" required>
        </div>
        <div class="col-md-2">
            <label for="zip" class="form-label fw-semibold">Zip</label>
            <input type="text" class="form-control" id="zip" maxlength="5" name="zip" placeholder="Postal code" required>
        </div>
        <div class="col-md-6">
            <label for="website" class="form-label fw-semibold">Website</label>
            <input type="text" class="form-control fw-semibold" id="website" name="website" placeholder="Website">
        </div>
        <div class="col-12">
            <fieldset class="row mb-3">
                <legend class="col-form-label col-sm-2 pt-2">Wheelchair Accessible</legend>
                <div class="sm-col-10">
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="wheelchair_accessible_entrance" id="true" value="true" checked>
                        <label class="form-check-label" for="true">
                            Yes
                        </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" type="radio" name="wheelchair_accessible_entrance" id="false" value="false">
                        <label class="form-check-label" for="false">
                            No
                        </label>
                    </div>
                </div>
            </fieldset>
        </div>
        <input type="hidden" id="place_id" name="place_id" value="">

        <div class="col-6">
            <button type="submit" id="submit" name="submit" class="btn btn-primary">Submit</button>
        </div>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.6.4.min.js" integrity="sha384-oKJZR6QQ0lN92NV6XaXjpucqF3gZcOOf18Pz4M/R5tr5BvO+2SRTtHTzKsC9zQ6z" crossorigin="anonymous"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js" integrity="sha384-a9A5ZN4q33dYBjOiR9MK6bWPB9cNwZO6EFwM+BRAXd16STbFOiOjx2BZ0cD+VXpP" crossorigin="anonymous"></script>

<script>
    let placeId;
    $(document).ready(function () {
        $('#name').autocomplete({
            source: function (request, response) {
                $.ajax({
                    url: 'http://localhost:8080/MidwestEventTracker_war/services/autocomplete',
                    dataType: 'json',
                    data: {
                        userInput: request.term
                    },
                    success: function (data) {
                        response($.map(data, function (item) {
                            return {
                                label: item.description,
                                value: item.placeId
                            };
                        }));
                    }
                });
            },
            minLength: 2,
            select: function (event, ui) {
                event.preventDefault();
                $('#name').val(ui.item.label);
                fetchPlaceDetails(ui.item.value);
            }
        });

        function fetchPlaceDetails(selectedPlaceId) {
            fetch('http://localhost:8080/MidwestEventTracker_war/services/autocomplete/' + selectedPlaceId, {
                method: 'GET',
                headers: {
                    'Accept': 'application/json; charset=UTF-8',
                    'Content-Type': 'application/json; charset=UTF-8'
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }

                    return response.json();
                })
                .then(placeDetails => {
                    console.log('Details for selected place:', placeDetails);
                    // Populate form fields using placeDetails
                    $('#name').val(placeDetails.name);
                    $('#phone_number').val(placeDetails.phone_number);
                    $('#address').val(placeDetails.address);
                    $('#apartment').val(placeDetails.apartment);
                    $('#city').val(placeDetails.city);
                    $('#state').val(placeDetails.state);
                    $('#zip').val(placeDetails.zip);
                    $('#website').val(placeDetails.website);
                    $('#place_id').val(selectedPlaceId);

                    const wheelchairAccessible = placeDetails.wheelchair_accessible_entrance;
                    if (wheelchairAccessible !== 'true') {
                        $('#false').prop('checked', true);
                    } else {
                        $('#true').prop('checked', true);
                    }
                    placeId = selectedPlaceId;
                })
                .catch(error => {
                    console.error('Error:', error);
                });
        }

        function handleUserInput() {
            const userInput = $('#name').val();
            if (userInput.trim() === '') {
                // Clear all fields
                $('#name').val('');
                $('#phone_number').val('');
                $('#address').val('');
                $('#apartment').val('');
                $('#city').val('');
                $('#state').val('');
                $('#zip').val('');
                $('#website').val('');
                $('input[name="wheelchair_accessible_entrance"]').prop('checked', false);
                $('#place_id').val('');
            }
        }

        $('#name').on('input', handleUserInput);

        $('#submit').on('click', function() {
            // You can perform additional actions here if needed
            console.log('Form Data:', {
                name: $('#name').val(),
                phoneNumber: $('#phone_number').val(),
                address: $('#address').val(),
                apartment: $('#apartment').val(),
                city: $('#city').val(),
                state: $('#state').val(),
                zip: $('#zip').val(),
                website: $('#website').val(),
                wheelchairAccessibleEntrance: $('input[name="wheelchair_accessible_entrance"]:checked').val(),
                placeId: $('#place_id').val(),
            });
        });
    });
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>