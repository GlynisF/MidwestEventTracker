$(document).ready(function () {
    // Handle autocomplete for the 'name' field
    $('#name').autocomplete({
        source: function (request, response) {
            $.ajax({
                url: 'http://localhost:8080/MidwestEventTracker_war/services/autocomplete',
                dataType: 'json',
                data: { userInput: request.term },
                success: function (data) {
                    response($.map(data, function (item) {
                        return { label: item.description, value: item.placeId };
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

    // Function to fetch place details
    function fetchPlaceDetails(placeId) {
        fetch(`http://localhost:8080/MidwestEventTracker_war/services/autocomplete/${placeId}`, {
            method: 'GET',
            headers: {
                'Accept': 'application/json; charset=UTF-8',
                'Content-Type': 'application/json; charset=UTF-8'
            },
        })
            .then(response => response.json())
            .then(placeDetails => updatePlaceDetails(placeDetails))
            .catch(error => console.error('Error:', error));
    }

    // Function to update place details in the UI
    function updatePlaceDetails(placeDetails) {
        console.log('Details for selected place:', placeDetails);

        $('#name').val(placeDetails.name);
        $('#address').val(placeDetails.address);
        $('#apartment').val('').focus();
        $('#city').val(placeDetails.city);
        $('#state').val(placeDetails.state);
        $('#zip').val(placeDetails.zip);
        $('#website').val(placeDetails.website);
        $('#phone_number').val(placeDetails.phone_number);

        const wheelchairAccessible = placeDetails.wheelchair_accessible_entrance;
        $('#true').prop('checked', wheelchairAccessible);
        $('#false').prop('checked', !wheelchairAccessible);
    }

    // Event handlers for the form
    $('#autocompleteForm').submit(function (event) {
        event.preventDefault();
        // Handle form submission logic if needed
    });

    $('#name').on('input', handleUserInput);

    // Additional functions or event handlers...
});

// Function to handle user input
function handleUserInput() {
    const userInput = $('#name').val();

    if (userInput.trim() === '') {
        clearFields();
    } else {
        fetchAutocompleteData(userInput);
    }
}

// Function to fetch autocomplete data
function fetchAutocompleteData(userInput) {
    $.ajax({
        url: 'http://localhost:8080/MidwestEventTracker_war/services/autocomplete',
        dataType: 'json',
        data: { userInput: userInput },
        success: function (data) {
            // Handle the autocomplete data as needed
        }
    });
}

// Function to clear form fields
function clearFields() {
    $('#name, #address, #apartment, #city, #state, #zip, #website, #phone_number').val('');
    $('input[name="wheelchair_access"]').prop('checked', false);
}