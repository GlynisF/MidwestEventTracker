<%--
  Created by IntelliJ IDEA.
  User: GCADAGFISHER
  Date: 1/22/2024
  Time: 1:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="container">
    <form class="row g-3 mt-3 justify-content-between" action="form" method="post" id="autocompleteForm" autocomplete="off">
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
            <input type="text" class="form-control" id="apartment" name="apartment" placeholder="Apartment, studio, or floor">
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

</body>
</html>