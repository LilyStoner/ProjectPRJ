<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.io.IOException, java.util.ArrayList, java.util.List,model.Vehicle"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
    <head>
        <title>Car Rental</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/css/main.css" />
        <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>
        <style>
            .vehicle-prev,
            .vehicle-next {
                width: 5%; /* Điều chỉnh kích thước nếu cần */
                height: 20%;
                top: 50%; /* Đặt mũi tên ở giữa hình ảnh */
                transform: translateY(-62%); /* Căn giữa theo chiều dọc */
                background-color: rgba(182 ,176,176, 0.5); /* Màu nền đỏ, có độ trong suốt */

            }

            .vehicle-prev {
                left: -5rem; /* Đặt khoảng cách bên trái */
            }

            .vehicle-next {
                right: -5rem; /* Đặt khoảng cách bên phải */
            }
            .vehicle-prev:hover,
            .vehicle-next:hover {
                background-color: rgba(182 ,176,176,  0.7); /* Màu nền đỏ đậm khi hover */
            }
        </style>
        
              <script>
                      function setTodayAsPickupDate() {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0'); // Lấy tháng từ 0-11 nên cần +1
        const day = String(today.getDate()).padStart(2, '0');

        // Gán giá trị cho input pickup_date
        const formattedToday = year+'-'+month+'-'+day;
        document.getElementById("pickup_date").value = formattedToday;
    }

    // Gọi hàm khi trang được tải
    window.onload = function() {
        const pickupDateInput = document.getElementById("pickup_date");
        const returnDateInput = document.getElementById("return_date");
        if (!pickupDateInput.value) {
    setTodayAsPickupDate(); 
}       
         if (returnDateInput.value&&pickupDateInput.value) {
    validateDates();
}       
        

    };
                            function validateDates() {

                        const pickupDateInput = document.getElementById("pickup_date");
                        const returnDateInput = document.getElementById("return_date");
                        const submitButton = document.getElementById("submit_btn");

                        const pickupDate = new Date(pickupDateInput.value);
                        const returnDate = new Date(returnDateInput.value);
                        const currentDate = new Date();

                        document.getElementById("error_pickup").innerHTML = "";
                        document.getElementById("error_return").innerHTML = "";

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
                        submitButton.disabled = !isValid;
                    }

    </script>

    </head>
    <body class="is-preload">
        <!-- Wrapper -->
        <div id="wrapper">
            <jsp:include page="includes/header.jsp" />
            <jsp:include page="includes/menu.jsp" />

            <!-- Main -->
            <div id="main">
                <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                        <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                    </ol>
                    <div class="carousel-inner">
                        <div class="carousel-item active">
                            <img class="d-block w-100" src="images/slider-image-1-1920x700.jpg" alt="First slide">
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="images/slider-image-2-1920x700.jpg" alt="Second slide">
                        </div>
                        <div class="carousel-item">
                            <img class="d-block w-100" src="images/slider-image-3-1920x700.jpg" alt="Third slide">
                        </div>
                    </div>
                    <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                        <span class="carousel-control-prev-icon" aria-hidden="true"></span>

                        <span class="sr-only">Previous</span>
                    </a>
                    <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                        <span class="carousel-control-next-icon" aria-hidden="true"></span>
                        <span class="sr-only">Next</span>
                    </a>
                </div>

                <br>
                <br>

                <div class="inner">
                    <!-- About Us -->
                    <header id="inner">
                        <h1>Rent a car at low prices</h1>
                        <form action="home">
                            <%
                                String returnDate = request.getParameter("return_date");
                                String pickupDate = request.getParameter("pickup_date");
                            %>
                            <div class="form-date" style="display: flex">
                                <div class="date-in">
                                    <label for="pickup_date">Pickup Date:</label>
                                    <%
                                    if(pickupDate!=null && !pickupDate.isBlank() && !pickupDate.isEmpty())
                                    {%>
                                    <input type="date" id="pickup_date" value="<%=pickupDate%>" name="pickup_date" oninput="validateDates()" required >
                                   <%}else {%>
                                    <input type="date" id="pickup_date" name="pickup_date" oninput="validateDates()"  >
                                    <%}%>
                                    <br>
                                    <span id="error_pickup" style="color:red"></span>
                                    <label style="margin: 1em 0 0 0; font-size: 1.1em"for="type">Type: </label>
                                         

                                    <select name="Type" oninput="validateDates()">
                                          <% String type = request.getParameter("Type");
                                    if(type!=null && !type.isBlank() && !type.isEmpty()) {                                   
                                          %>  
                                        <option><%=type%></option>
                                        <%}%>
                                        <option>All</option>
                                        <c:forEach items="${listType}" var="t">
                                            <option>${t}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="date-in" style="margin-left: 3em">
                                    <label for="return_date">Return Date:</label>
                                     <%
                                    if(returnDate!=null && !returnDate.isBlank() && !returnDate.isEmpty()) {
                                    %>
                                    <input type="date" id="return_date" value="<%=returnDate%>" name="return_date" oninput="validateDates()" required >
                                    <%} else {%>
                                    <input type="date" id="return_date" name="return_date" oninput="validateDates()" required >
                                    <%}%>
                                    <br>
                                    <span id="error_return" style="color:red"></span>
                                </div>
                            </div>
                            <button style="margin-top: 2em" type="submit" id="submit_btn" disabled>Search</button>
                        </form>
                    </header>

                    <br>

                    <h2 class="h2">Offers</h2>

                    <!-- Offers -->
                    <div id="vehicleCarousel" class="carousel slide" data-ride="carousel">
                        <%
List<Vehicle> listVehicle = (List<Vehicle>) request.getAttribute("listVehicle");
if (listVehicle == null || listVehicle.isEmpty()) {
                        %>
                        <p>No vehicles available at the moment.</p>
                        <%
                            } else {
                                int vehiclesPerPage = 9; 
                                int totalVehicles = listVehicle.size();
                                int totalPages = (int) Math.ceil((double) totalVehicles / vehiclesPerPage);
                        %>
                        <div >
                            <div class="carousel-inner">
                                <% 
                                for (int currentPage = 0; currentPage < totalPages; currentPage++) { 
                                    int start = currentPage * vehiclesPerPage; 
                                    int end = Math.min(start + vehiclesPerPage, totalVehicles); 
                                %>
                                <div class="carousel-item <%= currentPage == 0 ? "active" : "" %>">
                                    <section class="tiles">
                                        <%  
                                        for (int i = start; i < end; i++) { 
                                            int style = 0;
                                            if (listVehicle.get(i).getStatus().equalsIgnoreCase("available")) style = 3;
                                            if (listVehicle.get(i).getStatus().equalsIgnoreCase("maintenance")) style = 1;
                                            if (listVehicle.get(i).getStatus().equalsIgnoreCase("rented")) style = 2;
                                        %>
                                        <article class="style<%= style %>">
                                            <span class="image">
                                                <img src="<%= listVehicle.get(i).getImage() %>" alt="" />
                                            </span>
                                             <%if (returnDate==null||pickupDate==null) {%>    
                                            <a href="Rental?vehicleId=<%= listVehicle.get(i).getVehicleId() %>">
                                          <%}%>     
                                          <%if (returnDate!=null && pickupDate!=null) {%>     <a href="Rental?vehicleId=<%= listVehicle.get(i).getVehicleId() %>&return_date=<%=returnDate%>&pickup_date=<%=pickupDate%>">
                                          <%}%>  
                                                <h2><%= listVehicle.get(i).getBrand() %>-<%= listVehicle.get(i).getModel() %></h2>
                                                <h2><%= listVehicle.get(i).getVehicleType() %></h2>
                                                <p>Price from: <strong> $<%= listVehicle.get(i).getPricePerDay() %> </strong> per weekend</p>
                                                <div class="content">
                                                    <p><%= listVehicle.get(i).getDescription() %></p>
                                                </div>
                                            </a>
                                        </article>
                                        <% 
                                        } 
                                        %>
                                    </section>
                                </div>
                                <% 
                                } 
                                %>
                            </div>
                        </div>
                        <a  class="carousel-control-prev vehicle-prev" href="#vehicleCarousel" role="button" data-slide="prev">
                            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                            <span class="sr-only">Previous</span>
                        </a>
                        <a  class="carousel-control-next vehicle-next" href="#vehicleCarousel" role="button" data-slide="next">
                            <span class="carousel-control-next-icon" aria-hidden="true"></span>
                            <span class="sr-only">Next</span>
                        </a>
                    </div>

                    <p class="text-center"><a href="offers.html">View Offers &nbsp;<i class="fa fa-long-arrow-right"></i></a></p>
                            <%
                                }
                            %>


                    <br>
                </div>

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