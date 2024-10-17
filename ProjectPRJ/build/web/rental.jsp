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
	</head>
	<body class="is-preload">
		<!-- Wrapper -->
			<div id="wrapper">

				<!-- Header -->
                        	        <jsp:include page="includes/header.jsp" />

				<!-- Menu -->
                        		<jsp:include page="includes/menu.jsp" />


				<!-- Main -->
					<div id="main">
						<div class="inner">
							<h1>Offers</h1>

							<div class="image main">
								<img src="images/banner-image-6-1920x500.jpg" class="img-fluid" alt="" />
							</div>

							<!-- Offers -->
                                                        <%
                                                        List<Vehicle> listVehicle = (List<Vehicle>) request.getAttribute("listVehicle");
                                                        %>
							<section class="tiles">
                                                                <%  
                                                                for (int i = 0; i < listVehicle.size(); i++) { 
                                                                %>
                                                                    <article class="style<%= (i % 3) + 1 %>">
                                                                        <span class="image">
                                                                            <img src="<%=listVehicle.get(i).getImage()%>" alt="" />
                                                                        </span>
                                                                        <a href="Rental">
                                                                            <h2><%=listVehicle.get(i).getBrand()%>-<%=listVehicle.get(i).getModel()%></h2>
                                                                            <h2><%=listVehicle.get(i).getVehicleType()%></h2>
                                                                            <p>price from: <strong> $<%=listVehicle.get(i).getPricePerDay()%> </strong> per weekend</p>
                                                                            <div class="content">
                                                                                <p><%=listVehicle.get(i).getDescription()%></p>
                                                                            </div>
                                                                        </a>
                                                                    </article>
                                                                <% 
                                                                } 
                                                                %>
<!--                                                            <button class="prev" onclick="moveSlides(-1)">&#10094;</button>
                                                            <button class="next" onclick="moveSlides(1)">&#10095;</button>-->
                                                        </section>
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