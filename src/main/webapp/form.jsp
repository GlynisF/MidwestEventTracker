<form class="row g-3" id="autocompleteForm" autocomplete="off">
    <div class="col-md-6">
        <label for="name" class="form-label">Venue Name</label>
        <input type="text" class="form-control" id="name" placeholder="Name" autocomplete="off" required>
    </div>
    <div class="col-md-6">
        <label for="phone_number" class="form-label">Phone Number</label>
        <input type="tel" class="form-control" id="phone_number" placeholder="Phone #">
    </div>
    <div class="col-12">
        <label for="address" class="form-label">Address</label>
        <input type="text" class="form-control" id="address" placeholder="Address" required>
    </div>
    <div class="col-12">
        <label for="apartment" class="form-label">Address 2</label>
        <input type="text" class="form-control" id="apartment" placeholder="Apartment, studio, or floor">
    </div>
    <div class="col-md-6">
        <label for="city" class="form-label">City</label>
        <input type="text" class="form-control" id="city" placeholder="City" required>
    </div>
    <div class="col-md-4">
        <label for="state" class="form-label">State</label>
        <input type="text" class="form-control" id="state" maxlength="2" placeholder="State shorthand" required>
    </div>
    <div class="col-md-2">
        <label for="zip" class="form-label">Zip</label>
        <input type="text" class="form-control" id="zip" maxlength="5" placeholder="Postal code" required>
    </div>
    <fieldset class="row mb-3">
        <legend class="col-form-label col-sm-2 pt-0">Wheelchair Accessible</legend>
        <div class="row-col-sm-10">
            <div class="form-check">
                <input class="form-check-input" type="radio" name="wheelchair_access" id="true" value="Yes" checked>
                <label class="form-check-label" for="true">
                    Yes
                </label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="wheelchair_access" id="false" value="No">
                <label class="form-check-label" for="false">
                    No
                </label>
            </div>
        </div>
    </fieldset>
    <div class="col-12">
        <label for="website" class="form-label">Website</label>
        <input type="text" class="form-control" id="website" placeholder="Website">
    </div>
    <!-- Add more form fields as needed -->
    <button type="submit" class="btn btn-primary">Submit</button>
</form>