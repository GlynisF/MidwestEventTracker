let eventName;
let eventDate;
let startTime;
let endTime;
let eventId;
let detailsId;

const eventData = JSON.parse(localStorage.getItem('eventData')) || {details: [{}], events: [{}], locations: [{}]};

    function init() {
        eventName = document.querySelector('#eventName');
        eventDate = document.querySelector('#eventDate');
        startTime = document.querySelector('#startTime');
        endTime = document.querySelector('#endTime');
        eventId = document.querySelector('#eventId');
        detailsId = document.querySelector('#detailsId');

        updateFormInputs();
        //create function with a listener for input changed & on form submit
        $('form input').on('change', function () {
            getFormValues();
        });
    }



// Function to update form inputs with details
function updateFormInputs() {
    const eventDetails = eventData.details[0];

    eventDate.val(eventDetails.date_of_event);
    startTime.val(eventDetails.start_time);
    endTime.val(eventDetails.end_time);

    const event = eventData.events[0];
    eventName.val(event.eventName);

}


function getFormValues() {
     eventName = $('#eventName').val();
     eventDate = $('#eventDate').val();
     startTime = $('#startTime').val();
     endTime = $('#endTime').val();

    // Log the values (you can modify this part)
    console.log(eventName, eventDate, startTime, endTime);
}