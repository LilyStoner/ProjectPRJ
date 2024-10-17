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
								<p>Etiam quis viverra lorem, in semper lorem. Sed nisl arcu euismod sit amet nisi euismod sed cursus arcu elementum ipsum arcu vivamus quis venenatis orci lorem ipsum et magna feugiat veroeros aliquam. Lorem ipsum dolor sit amet nullam dolore.</p>
							</header>

							<br>

							<h2 class="h2">Offers</h2>

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
                                                                        <a href="offers.html">
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
							<p class="text-center"><a href="offers.html">View Offers &nbsp;<i class="fa fa-long-arrow-right"></i></a></p>

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