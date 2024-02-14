<form id="eventForm" class="editForm" onsubmit="submitEventForm()">
    <div class="form-group">
        <label for="eventName">Event Name</label>
        <input type="text" class="form-control" name="eventName" id="eventName"/>
    </div>
    <input type="hidden" id="eventId" name="eventId"/>
    <button type="button" class="editButton visually-hidden">submit</button>
</form>
<form id="detailsForm" class="editForm" onsubmit="submitDetailsForm()">
    <div class="form-group">
        <label for="eventDate">Event Date</label>
        <input type="date" class="form-control" name="eventDate" id="eventDate"/>
    </div>
    <div class="form-group">
        <label for="startTime">Start Time</label>
        <input type="time" class="form-control" name="startTime" id="startTime"/>
    </div>
    <div class="form-group">
        <label for="endTime">End Time</label>
        <input type="time" class="form-control" name="endTime" id="endTime"/>
    </div>
    <input type="hidden" id="detailsId" name="detailsId"/>
</form>

<script>
    const eventData = JSON.parse(localStorage.getItem('eventData')) || { details: [{}], events: [{}], artists: [{}], notebooks: [{}], locations: [{}] };

    // Function to update form inputs with details
    function updateFormInputs() {
        const eventDetails = eventData.details[0];

        // Update form inputs with details from eventDetails
        $('#eventDate').val(eventDetails.eventDate);
        $('#startTime').val(eventDetails.startTime);
        $('#endTime').val(eventDetails.endTime);
        $('#detailsId').val(eventDetails.id);
        console.log('DETAILS ID:', $('#detailsId').val());

        const event = eventData.events[0];
        $('#eventName').val(event.eventName);
        $('#eventId').val(event.id);
    }

    updateFormInputs();

    function submitForm(url, formData, timeout) {
        return new Promise((resolve, reject) => {
            const timeoutId = setTimeout(() => {
                clearTimeout(timeoutId);
                reject('Request timed out');
            }, timeout);

            fetch(url, {
                method: 'POST',
                body: formData,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                },
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Form submitted successfully:', data);
                    clearTimeout(timeoutId);
                    resolve(data);
                })
                .catch(error => {
                    console.error('Form submission error:', error);
                    clearTimeout(timeoutId);
                    reject(error);
                });
        });
    }


    function submitEventForm() {
        const formData = new URLSearchParams($('#eventForm').serialize());
        const eventId = formData.get('eventId');
        const url = `http://localhost:8080/MidwestEventTracker_war/services/events/update/` + encodeURIComponent(eventId);

        submitForm(url, formData, 5000) // 5000 milliseconds timeout
            .then(response => {
                // Handle the response if needed
                console.log('Response:', response);
            })
            .catch(error => {
                // Handle the error if needed
                console.error('Error:', error);
            });
    }

    function submitDetailsForm() {
        const detailsData = $('#detailsForm').serialize();
        const detailsId = $('#detailsId').val();

        const url = `http://localhost:8080/MidwestEventTracker_war/services/events/details/` + encodeURIComponent(detailsId);

        submitForm(url, detailsData, 5000) // 5000 milliseconds timeout
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