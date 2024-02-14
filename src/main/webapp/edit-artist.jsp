<div id="artistForm" class="editForm editArtistFormContainer">
<input type="text" name="artist_first_name[]"/>
</div>


 <script>
     $(document).ready(function() {
     function populateRows() {
         const artistBody = $('#artistBody');
         editArtistDetails.artists.forEach(artist => {
             artistBody.append('<tr>');
             artistBody.append(addRow(artist.moniker));
             artistBody.append(addRow(artist.artist_first_name));
             artistBody.append(addRow(artist.artist_last_name));
             artistBody.append(addRow(artist.artist_email));
             artistBody.append(addRow(artist.bookingFeeFormatted));
             artistBody.append('</tr>');
             artistBody.append('<tr><td><input type="hidden" id="' + artist.id + '"/></tr><td>');

         })
     }

     function addRow(artistItem) {
         return `<td><input type="text" class="editArtist" value="` + artistItem + `"/></td>`;
     }


    function updateArtistInputs() {
        let artistDetails = eventData.artists;

        // Update form inputs with details from eventDetails
        $('#moniker').val(artistDetails.moniker);
        $('#artist_first_name').val(artistDetails.artist_first_name);
        $('#artist_last_name').val(artistDetails.artist_last_name);
        $('#booking_fee').val(artistDetails.booking_fee);
        $('#artist_email').val(artistDetails.artist_email);
        $('#artistId').val(artistDetails.id);
        console.log('ARTIST ID:', $('#artistId').val());
        console.log('ARTIST ARRAY: ', artistDetails);

    }

    updateArtistInputs();

    function submitArtistForm() {
        const artistData = new URLSearchParams($('#artistForm').serialize());
        const artistId = $('#artistId').val();
        console.log(artistData);
        console.log(artistId);
        const url = `http://localhost:8080/MidwestEventTracker_war/services/artists/update/` + encodeURIComponent(artistId);

        fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json; charset=utf-8;',
            },
            body: artistData,
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                // Handle the response if needed
                console.log('Response:', data);
            })
            .catch(error => {
                // Handle the error if needed
                console.error('Error:', error);
            });
    }
     });
</script>