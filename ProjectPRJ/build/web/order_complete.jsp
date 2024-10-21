<%-- 
    Document   : order.jsp
    Created on : Oct 17, 2024, 9:34:55 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>OrderComplete</title>
        <title>PHPJabbers.com | Free Car Rental Website Template</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="assets/css/main.css" />
		<noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
            
    </head>
    <body>

        <!-- Header -->
                        	        <jsp:include page="includes/header.jsp" />

				<!-- Menu -->
                        		<jsp:include page="includes/menu.jsp" />
     <h1>Car Renting Successful!</h1>
    <p>Thank you for your order. Here is your order information:</p>

    <div class="order-details">
        <h3>Order information:</h3>
        <p><strong>Type:</strong> ${vehicle.getVehicleType()}</p>
        <p><strong>Model:</strong> ${vehicle.getModel()}</p>
        <p><strong>Registration Number:</strong> ${vehicle.getRegistrationNumber()}</p>
        <p><strong>Pickup Date:</strong> ${pickup_date}</p>
        <p><strong>Return Date:</strong> ${return_date}</p>
        <p><strong>Total:</strong> ${total_amount} $</p>
    </div>

         <button  onclick="window.location.href='home'">Back To Home Page</button>
         <br>
         <br>                                        
                                        <jsp:include page="includes/footer.jsp" />
            <!-- Scripts -->
            <script src="assets/js/jquery.min.js"></script>
            <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="assets/js/jquery.scrolly.min.js"></script>
            <script src="assets/js/jquery.scrollex.min.js"></script>
            <script src="assets/js/main.js"></script>


    </body>
</html>
