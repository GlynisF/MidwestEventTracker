<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Autocomplete Example</title>
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
</head>
<body>

<label for="autocomplete">Autocomplete: </label>
<input type="text" id="autocomplete">
<p id="demo">Fetch a file to change this text.</p>

<script>
    getText("fetch_info.txt");

    async function getText(file) {
        let myObject = await fetch(file);
        let myText = await myObject.text();
        document.getElementById("demo").innerHTML = myText;
    }
</script>

</body>
</html>