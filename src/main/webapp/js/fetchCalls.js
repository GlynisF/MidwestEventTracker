$(document).ready(function () {
    // Check if the sidebar content exceeds the height and show the arrow
    checkOverflow();

    // Handle click on the arrow to scroll the sidebar
    $('.arrow-down').on('click', function () {
        $('#sidebar').animate({scrollTop: $('#sidebar')[0].scrollHeight}, 'slow');
    });

    $('.dropdown-item').on('click', function (e) {
        e.preventDefault();
        // Clear existing event details when a new notebook is clicked
        $('#event-details-list').empty();
    });

    $('.details-button').on('click', function (e) {
        e.preventDefault();
        getEvents();

    });
});

function getEvents() {
    let notebookId = event.currentTarget.getAttribute('data-notebook-id');

    fetch('http://localhost:8080/MidwestEventTracker_war/services/notebooks/' + encodeURIComponent(notebookId), {
        method: 'GET',
        headers: {
            'Accept': 'application/json; charset=UTF-8',
            'Content-Type': 'application/json'
        },
    })
        .then(response => {
            console.log(response); // Log the entire response
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
            debugger;
            console.error('Error:', error);
        });
}


function displayEventDetails(eventDetailsList) {
    // Clear existing content
    $('#event-details-list').empty();

    if (eventDetailsList.hasOwnProperty('details')) {
        const details = eventDetailsList.details;
        if (details.length > 0) {
            details.forEach(eventDetails => {
                // Generate HTML for event details and append to the list
                const html = `<li id="class">Date of Event: ` + eventDetails.date_of_event + `</li>`;
                $('#event-details-list').append(html)
                    .append(`<li> Start Time: ` + eventDetails.start_time + `</li>`)
                    .append(`<li> End Time: ` + eventDetails.end_time + `</li>`);
                // Add other properties as needed
                if (eventDetailsList.hasOwnProperty('locations')) {
                    const locations = eventDetailsList.locations;
                    if (locations.length > 0) {
                        locations.forEach(location => {
                            // Generate HTML for event details and append to the list
                            $('#event-details-list').append(`<li>Venue: ` + location.name + `</li>`)
                                .append(`<li>Phone Number: ` + location.phoneNumber + `</li>`)
                                .append(`<li>Website: ` + location.website + `</li>`);
                            // Add other properties as needed
                        });
                    } else {
                        // No event details available
                        $('#event-details-list').append('<li>No event details available.</li>');
                    }
                }
            });
        } else {
            // No events available
            $('#event-details-list').append('<li>No events available.</li>');
        }
    }
}

function checkOverflow() {
    let sidebar = document.getElementById('sidebar');
    let arrowDown = document.querySelector('.arrow-down');

    if (sidebar.scrollHeight > sidebar.clientHeight) {
        arrowDown.style.display = 'block';
    } else {
        arrowDown.style.display = 'none';
    }
}



function menu3() {
    // Hide the Notebook tab
    $('#notebook-tab').removeClass('show active');

    // Show the Event tab
    $('#event-tab').addClass('show active');

    // Trigger Bootstrap's tab show method to update the active tab
    $('a[href="#event-tab"]').tab('show');
}