<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<%@include file="includes/head.jsp" %>
<div class="container mt-5">
    <body class="row mx-auto mt-5">
    <header class="main_header">

        <!-- This div is  intentionally blank. It creates the transparent black overlay over the video which you can modify in the CSS -->
        <div class="overlay"></div>
        <!-- The HTML5 video element that will create the background video on the header -->
        <video playsinline="playsinline" autoplay="autoplay" muted="muted" loop="loop">
            <source src="images/tv_home.mp4" type="video/mp4">
        </video>

        <!-- The header content -->
        <div class="container h-100">
            <div class="d-flex h-100 text-center align-items-center">
                <div class="w-100 text-white">
                    <h1 class="display-3">Midwest Event Tracker</h1>
                    <h2><a class="lead mb-0" href="logIn">Login / Signup </a></h2>
                    <h2><a class="lead mb-0" href="search.jsp">Search Users</a></h2>
                </div>
            </div>
        </div>

    </header>
    <html>
    <body>
    <!-- Other content -->
    </body>
    </html>


    <%@include file="includes/script.js" %>

    </body>

</div>
</html>