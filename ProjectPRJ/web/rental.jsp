<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.IOException, java.util.ArrayList, java.util.List,model.Vehicle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <title>PHPJabbers.com | Free Car Rental Website Template</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/css/main.css" />
        <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
        <style>

            .vehicle-container {
                width: 100%;
                padding: 20px;
                background-color: #ffffff;
                border-radius: 8px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                display: flex;
                justify-content: space-around;
            }

            .vehicle-container h2 {
                margin-top: 0;
                color: #333;
            }

            .vehicle-info {
                margin-bottom: 10px;
            }

            .vehicle-info label {
                font-weight: bold;
                color: #555;
                margin: 0 0 0.5rem 0;
            }

            .vehicle-info span {
                margin-left: 5px;
                color: #777;
            }

            .vehicle-image img {
                max-width: 100%;
                border-radius: 4px;
            }

            .Description {
                margin-left: 4rem;
                font-size: 2rem;
            }
            .Description span {
                margin-left: 4rem;
                font-size: 1.2rem;
            }
        </style>
    </head>
    <body class="is-preload">
        <!-- Wrapper -->
        <div id="wrapper">

            <!-- Header -->
            <jsp:include page="includes/header.jsp" />

            <!-- Menu -->
            <jsp:include page="includes/menu.jsp" />

            <%
                                           String returnDate = request.getParameter("return_date");
                                           String pickupDate = request.getParameter("pickup_date");
            %>
            <!-- Main -->
            <div id="main">
                <div class="inner">
                    <h1>Offers</h1>
                    <div class="vehicle-container">
                        <div>
                            <h2>Product details:</h2>

                            <div class="vehicle-info">
                                <label>Type: </label> <span id="vehicle_type">${vehicle.getVehicleType()}</span>
                            </div>
                            <div class="vehicle-info">
                                <label>Model: </label> <span id="model">${vehicle.getModel()}</span>
                            </div>

                            <div class="vehicle-info">
                                <label>Brand: </label> <span id="brand">${vehicle.getBrand()}</span>
                            </div>

                            <div class="vehicle-info">
                                <label>Registration Number: </label> <span id="registration_number">${vehicle.getRegistrationNumber()}</span>
                            </div>

                            <div class="vehicle-info">
                                <label>Manufacture Year: </label> <span id="manufacture_year">${vehicle.getManufactureYear()}</span>
                            </div>

                            <div class="vehicle-info">
                                <label>Price Per Day: </label> <span id="price_per_day">${vehicle.getPricePerDay()}$</span>
                            </div>

                            <div class="vehicle-info">
                                <label>Status: </label> <span id="status">${vehicle.getStatus()}</span>
                            </div>

                        </div>

                        <div class="vehicle-image">
                            <img src="${vehicle.getImage()}" alt="Hình ảnh xe">
                            <br>

                            <c:if test="${vehicle.status ne 'maintenance' && vehicle.status ne 'Maintenance' }">
                                <input type="button" value="Add To Contract" name="Contract" onclick="window.location.href = 'Contract?vehicleID=${vehicle.getVehicleId()}'"/>
                                <%if (returnDate!= null && pickupDate!=null){%> 
                                <input type="button" value="Rent Now" name="Rent" onclick="window.location.href = 'order?vehicleID=${vehicle.getVehicleId()}&return_date=<%=returnDate%>&pickup_date=<%=pickupDate%>'" />

                                <%} else {%>
                                <input type="button" value="Rent Now" name="Rent" onclick="window.location.href = 'order?vehicleID=${vehicle.getVehicleId()}'" />

                                <%}%>
                            </c:if>
                            <c:if test="${vehicle.status eq 'maintenance' || vehicle.status eq 'Maintenance'}">
                                <h1>This car not available now!</h1>
                            </c:if>

                        </div>

                    </div>
                    <div class="vehicle-info Description">
                        <label>Description: </label> <span id="description">${vehicle.getDescription()}</span>
                    </div>

                </div>
            </div>

            <!-- Footer -->
            <jsp:include page="includes/footer.jsp" />


        </div>

        <!-- Scripts -->
        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/jquery.scrolly.min.js"></script>
        <script src="assets/js/jquery.scrollex.min.js"></script>
        <script src="assets/js/main.js"></script>

    </body>
</html>