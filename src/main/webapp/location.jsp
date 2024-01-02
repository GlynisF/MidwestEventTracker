<%--
  Created by IntelliJ IDEA.
  User: GCADAGFISHER
  Date: 12/25/2023
  Time: 12:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js" integrity="sha256-xLD7nhI62fcsEZK2/v8LsBcb4lG7dgULkuXoXB/j91c=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.8/css/dataTables.bootstrap5.min.css">

    <script src="https://cdn.datatables.net/1.13.8/js/jquery.dataTables.min.js"></script>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <title>Autocomplete Form</title>
</head>
<body>
<div class="container">
    <form class="row g-3" id="autocompleteForm" autocomplete="off">
        <div class="col-md-6">
            <label for="name" class="form-label">Location Name</label>
            <input type="text" class="form-control" id="name" placeholder="Enter location name">
        </div>
        <div class="col-md-6">
            <label for="phone_number" class="form-label">Phone Number</label>
            <input type="text" class="form-control" id="phone_number" placeholder="Phone Number">
        </div>
        <div class="col-12">
            <label for="address" class="form-label">Address</label>
            <input type="text" class="form-control" id="address" placeholder="1234 Main St">
        </div>
        <div class="col-12">
            <label for="apartment" class="form-label">Address 2</label>
            <input type="text" class="form-control" id="apartment" placeholder="Apartment, studio, or floor">
        </div>
        <div class="col-md-6">
            <label for="city" class="form-label">City</label>
            <input type="text" class="form-control" id="city">
        </div>
        <div class="col-md-4">
            <label for="state" class="form-label">State (2 characters)</label>
            <input type="text" class="form-control" id="state" maxlength="2">
        </div>
        <div class="col-md-2">
            <label for="zip" class="form-label">Zip</label>
            <input type="text" class="form-control" id="zip">
        </div>
        <div class="col-12">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" id="wheelchair_accessible">
                <label class="form-check-label" for="wheelchair_accessible">
                    Wheelchair Accessible
                </label>
            </div>
        </div>
        <div class="col-12">
            <label for="website" class="form-label">Website</label>
            <input type="text" class="form-control" id="website" placeholder="Website">
        </div>
        <!-- Add more form fields as needed -->
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
</div>

</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>
</html>