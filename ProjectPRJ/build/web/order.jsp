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
        <title>Order</title>
        <title>PHPJabbers.com | Free Car Rental Website Template</title>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
		<link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="assets/css/main.css" />
		<noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
                <style>
                     input[type="date"] {
            width: 70%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 5px;
            margin-bottom: 15px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }

        input[type="date"]:focus {
            border-color: #007bff;
            outline: none;
        }
                    
                    .form-date {
                        display: flex;
                    }
                    
                    .date-in {
                        width: 50%;
                    }

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
                    
                    #result {
                    border: none; 
                    background-color: transparent; 
                    font-size: 24px; 
                    font-weight: bold;
                    color: inherit; 
                    pointer-events: none;
                    height: 2em;
                    margin-bottom: 1em;
                    }
                  </style>
                   <script>
                            function validateDates(vehiclePricePerDay) {
                                    console.log(vehiclePricePerDay); 

                        const pickupDateInput = document.getElementById("pickup_date");
                        const returnDateInput = document.getElementById("return_date");
                        const submitButton = document.getElementById("submit_btn");

                        const pickupDate = new Date(pickupDateInput.value);
                        const returnDate = new Date(returnDateInput.value);
                        const currentDate = new Date();

                        document.getElementById("error_pickup").innerHTML = "";
                        document.getElementById("error_return").innerHTML = "";
                        document.getElementById("result").value = "0$";

                        let isValid = true;
                        currentDate.setHours(0, 0, 0, 0);
                        pickupDate.setHours(1,0,0,0);
                        returnDate.setHours(2,0,0,0);
                        if (pickupDate <= currentDate) {
                            document.getElementById("error_pickup").innerHTML = "Pickup date must be greater than today.";
                            isValid = false;
                        }

                        if (returnDate-1 <= pickupDate) {
                            document.getElementById("error_return").innerHTML = "Return date must be greater than pickup date.";
                            isValid = false;
                        }

                        if (isValid) {
                            const timeDifference = returnDate.setHours(0,0,0,0) - pickupDate.setHours(0,0,0,0);
                            const dayDifference = timeDifference / (1000 * 60 * 60 * 24) + 1; 
                            const total = dayDifference * vehiclePricePerDay; 
                          if(total>0)  document.getElementById("result").value = total.toFixed(2)+' $'; // Hiển thị tổng giá trị với 2 chữ số thập phân
                        }

                        submitButton.disabled = !isValid;
                    }

    </script>
    </head>
    <body>
          <script>
const vehiclePricePerDay = ${vehicle.getPricePerDay()};
    </script>
        <!-- Header -->
                        	        <jsp:include page="includes/header.jsp" />

				<!-- Menu -->
                        		<jsp:include page="includes/menu.jsp" />
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
                                                              <form action="OrderComplete" method="POST">
                                                                  <input type="hidden" name="vehicleID" value="${vehicle.getVehicleId()}" readonly="readonly" />
                                                                  <div class="form-date">
                                                                     <div class="date-in">
                                                                      <label for="pickup_date">Pickup Date:</label>
                                                                        <input type="date" id="pickup_date" name="pickup_date" oninput="validateDates(vehiclePricePerDay)"  required>
                                                                        <br>
                                                                        <span id="error_pickup" style="color:red"></span>
                                                                     </div>
                                                                      <div class="date-in">
                                                                        <label for="return_date">Return Date:</label>
                                                                        <input type="date" id="return_date" name="return_date" oninput="validateDates(vehiclePricePerDay)"  required>
                                                                        <br>
                                                                        <span id="error_return" style="color:red"></span>
                                                                     </div>
                                                                  </div>
                                                                  <label for="total-amount" style="font-size:24px; margin: 0 0 0.2em 0">Total Amount:</label> 
                                                                  <input id="result" type="text" name="total_amount" value="0$" readonly="readonly" />
                                                                        <button type="submit" id="submit_btn" disabled>Submit</button>
                                                              </form>
                                                            </div>

                                                          </div>
                                                             <div class="vehicle-info Description">
                                                              <label>Description: </label> <span id="description">${vehicle.getDescription()}</span>
                                                            </div>
		                        		<jsp:include page="includes/footer.jsp" />
					
            <!-- Scripts -->
            <script src="assets/js/jquery.min.js"></script>
            <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="assets/js/jquery.scrolly.min.js"></script>
            <script src="assets/js/jquery.scrollex.min.js"></script>
            <script src="assets/js/main.js"></script>

    </body>
</html>
