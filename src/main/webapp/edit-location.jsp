<form id="locationForm" class="editForm" onsubmit="submitLocationForm()">
    <div class="row g-3">
    <div class="col-md-6">
        <label for="name" class="form-label">Venue Name</label>
        <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="col-md-6">
        <label for="phone_number" class="form-label">Phone Number</label>
        <input type="tel" class="form-control" name="phoneNumber" id="phone_number">
    </div>
    <div class="col-6">
        <label for="address" class="form-label">Address</label>
        <input type="text" class="form-control" name="address" id="address"  required>
    </div>
    <div class="col-6">
        <label for="apartment" class="form-label">Address 2</label>
        <input type="text" class="form-control" name="apartment" id="apartment">
    </div>
    <div class="col-md-6">
        <label for="city" class="form-label">City</label>
        <input type="text" class="form-control" name="city" id="city" required>
    </div>
    <div class="col-md-4">
        <label for="state" class="form-label">State</label>
        <input type="text" class="form-control" id="state" name="state" maxlength="2"  required>
    </div>
    <div class="col-md-2">
        <label for="zip" class="form-label">Zip</label>
        <input type="text" class="form-control" id="zip" name="zip" maxlength="5"  required>
    </div>

    <fieldset class="row mb-3">
        <legend class="col-form-label col-sm-2 pt-0">Wheelchair Accessible</legend>
        <div class="row-col-sm-4 ">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="wheelchair_accessible_entrance" id="true" value="Yes" checked>
                <label class="form-check-label" for="true">
                    Yes
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="wheelchair_accessible_entrance" id="false" value="No">
                <label class="form-check-label" for="false">
                    No
                </label>
            </div>
        </div>
    </fieldset>
    <div class="col-4">
        <label for="website" name="website" class="form-label">Website</label>
        <input type="text" class="form-control" name="website" id="website">
    </div>
    <input type="hidden" id="locationId" name="locationId"/>
    </div>
</form>

<script>
    function updateLocationInputs() {
        let locations = eventData.locations[0];

        $('#name').val(locations.name);
        $('#phone_number').val(locations.phoneNumber);
        $('#address').val(locations.address);
        $('#apartment').val(locations.apartment);
        $('#city').val(locations.city);
        $('#state').val(locations.state);
        $('#zip').val(locations.zip);
        $('#website').val(locations.website);
        $('#locationId').val(locations.id);

        // Check the value of the wheelchair_accessible_entrance radio buttons
        const wheelchairAccessibleEntrance = locations.wheelchair_accessible_entrance;
        $('input[name="wheelchair_accessible_entrance"]').filter(`[value="${wheelchairAccessibleEntrance}"]`).prop('checked', true);
    }

    updateLocationInputs();

    function getLocationFormValues() {
        const name = $('#name').val();
        const phoneNumber = $('#phone_number').val();
        const address = $('#address').val();
        const apartment = $('#apartment').val();
        const city = $('#city').val();
        const state = $('#state').val();
        const zip = $('#zip').val();
        const website = $('#website').val();
        const locationId = $('#locationId').val();
        // Get the value of the selected radio button for wheelchair accessibility
        const wheelchairAccessibleEntrance = $('input[name="wheelchair_accessible_entrance"]:checked').val();
        // Log all the values
        console.log(name, phoneNumber, address, apartment, city, state, zip, website, wheelchairAccessibleEntrance, locationId);
    }

    async function submitLocationForm() {
        const locationData = $('#locationForm').serialize();
        const locationId = $('#locationId').val();
        console.log(locationId);
        console.log(locationData);
        const url = `http://localhost:8080/MidwestEventTracker_war/services/locations/edit/` + encodeURIComponent(locationId);

        submitForm(url, locationData, 5000)
            .then(response => {
                // Handle the response if needed
                console.log('Response:', response);
            })
            .catch(error => {
                // Handle the error if needed
                console.error('Error:', error);
            });
    }
</script>