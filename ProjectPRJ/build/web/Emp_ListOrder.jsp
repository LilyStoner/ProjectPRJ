<%-- 
    Document   : order.jsp
    Created on : Oct 17, 2024, 9:34:55 PM
    Author     : ADMIN
--%>

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
            function redirectToPage() {
                var selectedValue = document.getElementById("status").value;
                window.location.href = "Emp_ListOrder?status=" + selectedValue;
            }
        </script>

    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
        <%
            Map<Integer, RentalOrder> listOrders = (Map<Integer, RentalOrder>) request.getAttribute("lo");
            Map<Integer, Customer> listCustomers = (Map<Integer, Customer>) request.getAttribute("lc");

            String status = request.getParameter("status");
            if (status == null) {
                status = "all";
            }
            if (!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("confirmed") && !status.equalsIgnoreCase("cancelled") && !status.equalsIgnoreCase("on going") && !status.equalsIgnoreCase("completed")) {
                status = "all";
            }

            if (listOrders == null) {
                response.sendRedirect("Emp_ListOrder");
            } else {

        %>
        <select id = "status" name="status"  onchange="redirectToPage()">
            <option value="all" <%if(status.equalsIgnoreCase("all")){out.println("selected");}%>>All</option>
            <option value="pending" <%if(status.equalsIgnoreCase("pending")){out.println("selected");}%>>Pending</option>
            <option value="confirmed" <%if(status.equalsIgnoreCase("confirmed")){out.println("selected");}%>>Confirmed</option>
            <option value="cancelled" <%if(status.equalsIgnoreCase("cancled")){out.println("selected");}%>>Cancled</option>
            <option value="on going" <%if(status.equalsIgnoreCase("on going")){out.println("selected");}%>>On Going</option>
            <option value="completed" <%if(status.equalsIgnoreCase("completed")){out.println("selected");}%>>Completed</option>
        </select>
        <table border="1">
            <thead>
                <tr>
                    <th>Order</th>
                    <th>Customer</th>
                    <th>Vehicles</th>
                    <th>Status</th>
                    <th>Update</th>
                </tr>
            </thead>
            <tbody>


                <%                    int count = 0;
                    for (int oid : listOrders.keySet()) {
                        RentalOrder ro = listOrders.get(oid);

                        if (status.equalsIgnoreCase("all") || listOrders.get(oid).getStatus().equalsIgnoreCase(status)) {
                %>
                <tr>
                    <td>
                        <div>
                            ID: <%=oid%></br>
                            Create date: <%=listOrders.get(oid).getCreatedAt()%>
                        </div>
                    </td>
                    <td>
                        <div>
                            Customer's name: <%=listCustomers.get(ro.getCustomerId()).getFullName()%>
                            </br> 
                            Phone number: <%=listCustomers.get(ro.getCustomerId()).getPhoneNumber()%>
                        </div>
                    </td>
                    <td>
                        <%
                            DAO dao = new DAO();
                            List<Vehicle> list = dao.Emp_getVehicleInOrder(oid);
                            for (Vehicle v : list) {
                                out.println(v.getModel() + "</br>");
                            }
                        %>
                    </td>
                    <td>
                        <%=ro.getStatus()%>
                    </td>
                    <td>
                        <a href="Emp_OrderDetail?id=<%=oid%>" >Show detail</a>
                    </td>
                </tr>
                <%
                            count++;
                        }
                    }

                %>
            </tbody>
        </table>
            
            
        <%  
            if(count==0){
            %><h2 style="color: red">Rental order <%=status%> list is empty!</h2><%
            }
            
            }
        %>




</body>
</html>
