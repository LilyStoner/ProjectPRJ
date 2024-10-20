<%-- 
    Document   : order.jsp
    Created on : Oct 17, 2024, 9:34:55 PM
    Author     : ADMIN
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.OrderVehicle"%>
<%@page import="model.Vehicle"%>
<%@page import="java.util.List"%>
<%@page import="dal.DAO"%>
<%@page import="model.Customer"%>
<%@page import="java.util.Map"%>
<%@page import="model.RentalOrder"%>
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
        <script>
                            function validateDates(vehiclePricePerDay) {
                                    console.log(vehiclePricePerDay); 

                        const pickupDateInput = document.getElementById("pickup_date");
                        const returnDateInput = document.getElementById("return_date");
                        const submitButton = document.getElementById("submit_btn");
                        const saveButton = document.getElementById("save");
                        
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
                        let total=0;
                        if (isValid) {
                            const timeDifference = returnDate.setHours(0,0,0,0) - pickupDate.setHours(0,0,0,0);
                            const dayDifference = timeDifference / (1000 * 60 * 60 * 24) + 1; 
                             total = dayDifference * vehiclePricePerDay; 
                          if(total>0)  document.getElementById("result").value = total.toFixed(2)+' $'; // Hiển thị tổng giá trị với 2 chữ số thập phân
                        }
                        
                        if (total<=0) {
                            isValid=false;
                        }

                        submitButton.disabled = !isValid;
                        saveButton.disabled = !isValid;
                    }

    </script>

    </head>
    <body>
        <jsp:include page="includes/header.jsp" />  
        <jsp:include page="includes/menu.jsp" />
        <div class="table-container">
            <table class="table table-striped table-bordered">
                <thead>
                <th>Type</th>
                <th>Model</th>
                <th>Brand</th>
                <th>Registration Number</th>
                <th>Price Per Day</th>
                </tr>
                </thead>
                <% 
                    String status = (String) request.getAttribute("status");
                    double vehiclePricePerDay = 0;
                    List<Vehicle> vlist = (List<Vehicle>) request.getAttribute("vList");
                    for(Vehicle v : vlist) {
                    vehiclePricePerDay+=v.getPricePerDay();
                    }
                %>
                <c:forEach var="v" items="${vList}">
                    <tr>
                        <td>${v.getVehicleType()}</td>
                        <td>${v.getModel()}</td>
                        <td>${v.getBrand()}</td>
                        <td>${v.getRegistrationNumber()}</td>
                        <td>${v.getPricePerDay()}</td>
                    </tr>
                </c:forEach>
            </table>
                <%
                 if(status.equalsIgnoreCase("waiting")) {
                %>
            <form action="ContractComplete" method="POST">
                <div class="form-date">
                    <div class="date-in">
                        <label for="pickup_date">Pickup Date:</label>
                        <input type="date" id="pickup_date" name="pickup_date" oninput="validateDates(<%=vehiclePricePerDay%>)"  required>
                        <br>
                        <span id="error_pickup" style="color:red"></span>
                    </div>
                    <div class="date-in">
                        <label for="return_date">Return Date:</label>
                        <input type="date" id="return_date" name="return_date" oninput="validateDates(<%=vehiclePricePerDay%>)"  required>
                        <br>
                        <span id="error_return" style="color:red"></span>
                    </div>
                </div>
                <label for="total-amount" style="font-size:24px; margin: 0 0 0.2em 0">Total Amount:</label> 
                <input id="result" type="text" name="total_amount" value="0$" readonly="readonly" />
                <button type="submit" id="submit_btn" disabled>Submit</button>
                <button type="submit" id="save" disabled>Save</button>
            </form>
                        <%} else {%>
                         <label for="pickup_date">Pickup Date:</label>
                         <input type="text" name="pickup" value="${ro.getStartDate()}" readonly="readonly" />
                         <label for="return_date">Return Date:</label>
                        <input type="text" name="pickup" value="${ro.getEndDate()}" readonly="readonly" />
                        <label for="Total-amount">Total Amount:</label>
                        <input type="text" name="pickup" value="${ro.getTotalAmount()}" readonly="readonly" />
            <%}%>
        </div>
                                    <!-- Scripts -->
            <script src="assets/js/jquery.min.js"></script>
            <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="assets/js/jquery.scrolly.min.js"></script>
            <script src="assets/js/jquery.scrollex.min.js"></script>
            <script src="assets/js/main.js"></script>


    </body>
</html>
