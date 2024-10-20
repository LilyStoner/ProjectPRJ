<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.IOException, java.util.ArrayList, java.util.List,model.Vehicle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Contracts</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/css/main.css" />
        <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
        <script>
            function filterOrders(status) {
                const url = `Contract?status=` + status;
                window.location.href = url; // Tải lại trang với tham số trạng thái
            }
            function openForm() {
                document.getElementById('popupForm').style.display = 'block';
                document.getElementById('popupOverlay').style.display = 'block';
            }

            // Đóng form pop-up
            function closeForm() {
                document.getElementById('popupForm').style.display = 'none';
                document.getElementById('popupOverlay').style.display = 'none';
            }
        </script>

        <style>
            /* Định dạng cho form pop-up */
           
            .popup-form {
                display: none;
                position: fixed;
                left: 50%;
                top: 50%;
                transform: translate(-50%, -50%);
                width: 300px;
                padding: 20px;
                background-color: white;
                border: 2px solid #ccc;
                z-index: 1000;
                box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            }
            /* Nền mờ khi pop-up hiển thị */
            .popup-overlay {
                display: none;
                position: fixed;
                top: 0;
                left: 0;
                width: 100%;
                height: 100%;
                background-color: rgba(0, 0, 0, 0.5);
                z-index: 999;
            }
        </style>
    </head>
    <body class="is-preload">
        <!-- Wrapper -->
        <div id="wrapper">
            <jsp:include page="includes/header.jsp" />
            <jsp:include page="includes/menu.jsp" />
            <%
                   boolean add =true;
                   Integer vehicleID =null;   
                   try {
                       vehicleID = Integer.parseInt(request.getParameter("vehicleID"));
                } catch (NumberFormatException e) {
                    add =false;
                     }
            %>
            <!-- Main -->
            <%
                String status = (String)request.getAttribute("status");
            %>
            <div class="container">
                <h1 class="text-center"><%=status%> Contracts</h1>

                <!-- Buttons to filter rental orders by status -->
                <div class="text-center" style="margin-bottom:2em">
                    <% if(add){} else {%>
                    <button  onclick="filterOrders('Waiting')">Waiting</button>
                    <button  onclick="filterOrders('Pending')">Pending</button>
                    <button  onclick="filterOrders('Confirmed')">Confirmed</button>
                    <button  onclick="filterOrders('Cancelled')">Cancelled</button>
                    <button  onclick="filterOrders('Completed')">Completed</button>

                    <% } %>
                </div>

                <%
                    if(status!=null&&status.equalsIgnoreCase("waiting")) {
                %>
                <div style="display: flex; justify-content: center; margin-bottom: 1em">
                    <button style="background-color: lightgreen" onclick="openForm()">Create Contract</button>
                </div>

                <div class="popup-overlay" id="popupOverlay"></div>


                <div class="popup-form" id="popupForm">
                    <h3>Create Rental Order</h3>
                    <%if (add) { vehicleID = Integer.parseInt(request.getParameter("vehicleID"));%>  
                    <form id="createOrderForm" action="Contract?vehicleID=<%=vehicleID%>" method="POST">
                        <% } else {%>
                        <form id="createOrderForm" action="Contract" method="POST">
                            <%}%>

                            <label for="name">Rental Order Name:</label>
                            <input style="margin-bottom:1em" type="text" id="contractName" name="contractName" required>
                            <button type="submit">Submit</button>
                            <button onclick="closeForm()">Close</button>
                        </form>
                </div>

                <%}%>

                <div class="table-container">
                    <table class="table table-striped table-bordered">
                        <thead>
                            <tr>
                                <%
                                          if(status!=null&&status.equalsIgnoreCase("waiting")) 
                                %>
                                <th>Name</th>
                                <th>Pickup Date</th>
                                <th>Return Date</th>
                                <th>Total Amount</th>
                                <th>Status</th>
                                <th>Deposit Paid</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="order" items="${list}">
                                <tr>
                                    <%
                                                      if(status!=null&&status.equalsIgnoreCase("waiting")) 
                                    %>
                                    <td>${order.getName()}</td>
                                    <td>${order.getStartDate()}</td>
                                    <td>${order.getEndDate()}</td>
                                    <td>${order.getTotalAmount()}</td>
                                    <td>${order.getStatus()}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${order.depositPaid > 0}">
                                                Yes
                                            </c:when>
                                            <c:otherwise>
                                                No
                                            </c:otherwise>
                                        </c:choose>
                                    </td>     
                                    <td> <%if (add) { vehicleID = Integer.parseInt(request.getParameter("vehicleID"));%>  
                                        <form action="Contract?vehicleID=<%=vehicleID%>" method="POST" style="display: flex; justify-content: space-evenly">

                                            <% } else {%>
                                            <form action="Contract" method="POST" style="display: flex; justify-content: space-evenly">
                                                <%}%>
                                                <input type="hidden" name="orderID" value="${order.getOrderId()}" readonly="readonly" />
                                                <button type="submit" value="View" name="action" >View</button>
                                                <button type="submit" value="Delete" name="action" >Del</button>
                                                <%if (add) %> 
                                                <button type="submit" value="Add" name="action" >Add</button>

                                            </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>


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