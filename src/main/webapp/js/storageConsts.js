function fetchDetailsData() {

    const notebookId = $('#notebookId').val();

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
          //  displayEventDetails(data);
            return data;
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function eventDataArray() {
    const eventData = JSON.parse(localStorage.getItem('eventData')) || { details: [{}], events: [{}], artists: [{}], artists: [{}], locations: [{}] };
    return eventData;
}

function populateDetailsCard() {
    let data = eventDataArray();
    console.log('data array: ', data);
    const details = data.details[0];
    const event = data.events[0];
    const artist = data.artists;
    const location = data.locations[0];
    console.log(artist);
    const artistContainer = $('#details-header');
    const artistDetails = $('#details-text');
    // notebook name for selected event

    artistDetails.append(`<h3 class="text-center card-header pt-3">` + data.notebookTitle + `</h3>`);
    artistDetails.append(`<h4 class="text-center pt-3">` + event.eventName + `</h4>`);


        artistDetails.append(`<h5 class="pt-3">Event Details</h5>`);
        artistDetails.append(`<p>Date of Event: ` + details.eventDate +`</p>`);
        artistDetails.append(`<p>Start Time: ` + details.startTime + `</p>`);
        artistDetails.append(`<p>End Time: ` + details.endTime + `</p><hr class="border-dashed border-2"/>`);
        artistDetails.append(`<h5 class="pt-3">Artist Info</h5>`);
        // artist details
        if (artist.length > 0) {
            artist.forEach(artists => {
                artistDetails.append(`<h6 class="pt-2">` + artists.moniker + ` (Moniker)</h6>`);
                artistDetails.append(`<p>Artist Name: ` + artists.artist_first_name + ` ` + artists.artist_last_name + `</p>`);
                artistDetails.append(`<p>Artist Email Address: ` + artists.artist_email + `</p>`);
                artistDetails.append(`<p>Booking Fee: $` + artists.bookingFeeFormatted + `</p>`);
            })
        }
        artistDetails.append(`<hr class="border-dashed border-2"/>`);

        artistDetails.append(`<h5 class="pt-3">Venue Info</h5>`);
        artistDetails.append(`<p>Venue Name: ` + location.name + `</p>`);
        artistDetails.append(`<p>Address: ` + location.address + ` - ` + location.apartment + ` ` + location.city + `, ` + location.state + `</p>`);
        artistDetails.append(`<p>Phone #: ` + location.phoneNumber + `</p>`);
        artistDetails.append(`<p>Wheelchair Access: ` + location.wheelchair_accessible_entrance + `</p>`);
        artistDetails.append(`<p>Webiste: ` + location.website + `</p>`);
}