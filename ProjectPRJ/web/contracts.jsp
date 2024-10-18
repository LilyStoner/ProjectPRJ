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
            const url = `Contract?status=`+status;
            window.location.href = url; // Tải lại trang với tham số trạng thái
        }
    </script>
	</head>
	<body class="is-preload">
		<!-- Wrapper -->
			<div id="wrapper">
                                <jsp:include page="includes/header.jsp" />
				<jsp:include page="includes/menu.jsp" />
                                <%
                                       boolean add =true;
                                           try {
                                          int vehicleID = Integer.parseInt(request.getParameter("vehicleID"));
                                    } catch (NumberFormatException e) {
                                        add =false;
                                         }
                                %>
				<!-- Main -->
                                <div class="container">
                                <h1 class="text-center">Contracts</h1>

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
                                 <div class="table-container">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
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
                <td style="display: flex; justify-content: space-evenly" >
                    <button >View</button>
                   <%if (add) %> <button >Add</button>
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