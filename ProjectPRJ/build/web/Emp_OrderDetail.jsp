<%-- 
    Document   : order.jsp
    Created on : Oct 17, 2024, 9:34:55 PM
    Author     : ADMIN
--%>

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
            function confirmChoice(value, id) {
                if (confirm("Are you sure to update order with ID=" + id + " to " + value + "?")) {
                    window.location = ("Emp_OrderDetail?id=" + id + "&&up=" + value);
                }
            }
        </script>

    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
                <jsp:include page="includes/menu.jsp" />

        <%

            RentalOrder rentalOrder = (RentalOrder) request.getAttribute("ro");
            Customer customer = (Customer) request.getAttribute("c");
            List<Vehicle> vehicles = (List<Vehicle>) request.getAttribute("lv");
            Map<Integer, OrderVehicle> orderVehicles = (Map<Integer, OrderVehicle>) request.getAttribute("ov");
            List<String> err = (List<String>) request.getAttribute("err");
            List<Integer> err_id = (List<Integer>) request.getAttribute("err_id");

            if (rentalOrder == null) {
                response.sendRedirect("Emp_ListOrder");
            } else {
                String status = rentalOrder.getStatus();


        %>
        <a  href="Emp_ListOrder" style="font-weight: bold;text-decoration: none"><button>Back to order list</button></a>
        <div style="margin-top: 50px">
            <div style="display: flex;justify-content: space-around">
                <h1>Order ID: <%=rentalOrder.getOrderId()%></h1>
                Create date: <%=rentalOrder.getCreatedAt()%></br>
                Start date: <%=rentalOrder.getStartDate()%></br>
                End date: <%=rentalOrder.getEndDate()%>
            </div>
            </br>
            <h2 <%if (status.equalsIgnoreCase("cancelled")) {
                    out.println("style=\"color: red\"");
                } else {
                    out.println("style=\"color: #1c7430\"");
                }%>>Status: <%=status%></h2>

            <h2>CUSTOMER</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>Customer ID</th>
                        <th>Full name</th>
                        <th>Phone number</th>
                        <th>Address</th>
                        <th>Driving license</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><%=customer.getCustomerId()%></td>
                        <td><%=customer.getFullName()%></td>
                        <td><%=customer.getPhoneNumber()%></td>
                        <td><%=customer.getAddress()%></td>
                        <td><%=customer.getDrivingLicenseNumber()%></td>
                    </tr>
                </tbody>
            </table>
            <h2>VEHICLES</h2>
            <table border="1">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Type</th>
                        <th>Model</th>
                        <th>Registration number</th>
                        <th>Pickup date</th>
                        <th>Return date</th>
                        <th>price per day</th>
                            <%if (rentalOrder.getStatus().equalsIgnoreCase("pending")) {
                                    out.println("<th>status</th>");
                                }%>
                        <th>Status</th>
                        <th>image</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        for (Vehicle v : vehicles) {

                    %>

                    <tr <%if (err_id.contains(v.getVehicleId())) {
                            out.println("style=\"color:red\"");
                        }%>>
                        <td><%=v.getVehicleId()%></td>
                        <td><%=v.getVehicleType()%></td>
                        <td><%=v.getBrand()%> <%=v.getModel()%></td>
                        <td><%=v.getRegistrationNumber()%></td>
                        <td><%=orderVehicles.get(v.getVehicleId()).getPickupDate()%></td>
                        <td><%=orderVehicles.get(v.getVehicleId()).getReturnDate()%></td>
                        <td><%=v.getPricePerDay()%></td>
                        <%if (rentalOrder.getStatus().equalsIgnoreCase("pending")) {%><td><%=v.getStatus()%></td><%}%>
                        <td>
                            <%
                                if (rentalOrder.getStatus().equalsIgnoreCase("on going")) {
                                    if (v.getStatus().equalsIgnoreCase("rented")) {
                            %>
                            <button onclick="confirmChoice('return vehicle <%=v.getVehicleId()%>',<%=rentalOrder.getOrderId()%>)" style="background-color: #66ccff;color:  white;text-align: center" type="submit" name="up" >Return vehicle</button>
                            <%      } else {
                            %>
                            <button style="background-color: #66ccff;color:  white;text-align: center" type="submit" name="up" >Returned</button>
                            <%
                                }
                            } else if (rentalOrder.getStatus().equalsIgnoreCase("completed")) {
                            %>
                            <button style="background-color: #33cc00;color:  white;text-align: center" type="submit" name="up" >Returned</button>
                            <%
                            } else if (rentalOrder.getStatus().equalsIgnoreCase("cancelled")) {
                            %>
                            <button style="background-color: #66ccff;text-align: center" type="submit" name="up" >Order cancelled</button>
                            <%
                            } else {
                            %>
                            <%if (err_id.contains(v.getVehicleId())){
                            %>
                            <button style="background-color: #ff9999;color:  white;text-align: center" type="submit" name="up" >Unavailable</button>
                            <%
    }else{
%>
                            <button style="background-color: #33cc00;color:  white;text-align: center" type="submit" name="up" >Available</button>
                            <%
    }%>
                            <%
                                }
                            %>


                        </td>
                        <td><img src="v.getImage()" alt="<%=v.getBrand()%> <%=v.getModel()%> image"></td>
                    </tr>

                    <%
                        }
                    %>
                </tbody>
            </table>
            <p style="font-weight: bold;text-align: left">Total: <%=rentalOrder.getTotalAmount()%></p>

            <h2>DEPOSIT</h2>
            <%
                if (rentalOrder.getDepositPaid() == 0) {
            %>
            <button onclick="confirmChoice('deposited',<%=rentalOrder.getOrderId()%>)" style="height: 50px;background-color: #ff9999;color:  white;text-align: center" type="submit" name="up" <%if(!err_id.isEmpty()||rentalOrder.getStatus().equalsIgnoreCase("completed")||rentalOrder.getStatus().equalsIgnoreCase("cancelled")){out.println("disabled");}%>>UPDATE DEPOSIT NOW</button>
            <%
            } else {
            %>
            <button <%if(status.equalsIgnoreCase("confirmed")){%>onclick="confirmChoice('undeposited',<%=rentalOrder.getOrderId()%>)"<%}%> style="width: 15vw;height: 50px;background-color: #33cc00;color:  white;text-align: center" type="submit" name="up" >DEPOSITED</button>
            <%
                }

            %>  






            <%                if (!err.isEmpty()) {
            %><h2 style="color: red">ERROR:</h2><%
                }
                for (String s : err) {
            %>
            <p style="color: red;font-weight: bold"><%=s%></p>
            <%
                }
            %>
            <h2>UPDATE</h2>
            <div style="display: flex;justify-content: space-around">    
                <button onclick="confirmChoice('cancel',<%=rentalOrder.getOrderId()%>)" style="width: 15vw;height: 50px;background-color: #ff9999;color:  white;text-align: center" type="submit" value="CANCLE" name="up" <%if ((!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("confirmed"))|| rentalOrder.getDepositPaid()==1) {
                        out.println("disabled");
                    }%>>CANCEL</button>
                <button onclick="confirmChoice('confirm',<%=rentalOrder.getOrderId()%>)" style="width: 15vw;height: 50px;background-color: #ccffcc;color:  white;text-align: center" type="submit" value="CONFIRM" name="up" <%if (err_id.size() > 0 || !status.equalsIgnoreCase("pending")) {
                        out.println("disabled");
                    }%>>CONFIRM</button>
                <button onclick="confirmChoice('on going',<%=rentalOrder.getOrderId()%>)" style="width: 15vw;height: 50px;background-color: #ccffcc;color:  white;text-align: center" type="submit" value="ON GOING" name="up" <%if (err.size() > 0 ||!status.equalsIgnoreCase("confirmed")) {
                        out.println("disabled");
                    }%>>ON GOING</button>
                <button onclick="confirmChoice('completed',<%=rentalOrder.getOrderId()%>)" style="width: 15vw;height: 50px;background-color: #ccffcc;color:  white;text-align: center" type="submit" value="COMPLETED" name="up" <%if (!status.equalsIgnoreCase("on going")||err_id.size() > 0) {
                        out.println("disabled");
                    }%>>COMPLETE</button>
            </div>

            </br>
            </br>

            <%
                }
            %>
        </div>
        
            <!-- Scripts -->
            <script src="assets/js/jquery.min.js"></script>
            <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
            <script src="assets/js/jquery.scrolly.min.js"></script>
            <script src="assets/js/jquery.scrollex.min.js"></script>
            <script src="assets/js/main.js"></script>

    </body>
</html>
