<%-- 
    Document   : order.jsp
    Created on : Oct 17, 2024, 9:34:55 PM
    Author     : ADMIN
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Vehicle"%>
<%@page import="java.util.List"%>
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
                var selectedStatus = document.getElementById("status").value;
                var selectedDep = document.getElementById("depo").value;
                var PN = document.getElementById("search-byPN").value;
                var VRN = document.getElementById("search-byVRN").value;
                var OrderID = document.getElementById("search-OrderID").value;
                window.location.href = "Emp_ListOrder?status=" + selectedStatus
                        + "&&depo=" + selectedDep
                        + "&&PN=" + PN
                        + "&&VRN=" + VRN
                        +"&&OrderID=" + OrderID
            }
        </script>

    </head>
    <body>
        <jsp:include page="Emp_header.jsp" />
        <%
            Map<Integer, RentalOrder> listOrders = (Map<Integer, RentalOrder>) request.getAttribute("lo");
            Map<Integer, Customer> listCustomers = (Map<Integer, Customer>) request.getAttribute("lc");
            List<Integer> orderBy = (List<Integer>) request.getAttribute("orderBy");
            Map<Integer,List<Vehicle>> mapVehicles= (Map<Integer,List<Vehicle>>)request.getAttribute("mapVehicles");
            
            String status = request.getParameter("status");
            if (status == null) {
                status = "all";
            }
            if (!status.equalsIgnoreCase("pending") && !status.equalsIgnoreCase("confirmed") && !status.equalsIgnoreCase("cancelled") && !status.equalsIgnoreCase("on going") && !status.equalsIgnoreCase("completed")) {
                status = "all";
            }
            String depo = request.getParameter("depo");
            int OrderID = -1;
            try {
                    OrderID = Integer.parseInt(request.getParameter("OrderID"));
                } catch (Exception e) {
                }
            int checkDepo = 2;
            if (depo == null) {
                depo = "all";
                checkDepo = 2;
            }
            if (!depo.equalsIgnoreCase("deposited") && !depo.equalsIgnoreCase("undeposited")) {
                depo = "all";
                checkDepo = 2;
            } else {
                if (depo.equalsIgnoreCase("deposited")) {
                    checkDepo = 1;
                } else {
                    checkDepo = 0;
                }
            }
            String PN = request.getParameter("PN");
            Boolean checkPN = false;

            if (PN == null || PN.length() == 0) {
                checkPN = true;
            }

            String VRN = request.getParameter("VRN");
            Boolean checkVRN = false;
            List<Integer> listOrderByVRN = new ArrayList<Integer>();
            if (VRN == null || VRN.length() == 0) {
                checkVRN = true;
            }else{
                for (int checkVRNid : listOrders.keySet()) {
                    List<Vehicle> listV = mapVehicles.get(checkVRNid);
                    for (Vehicle v : listV) {
                        if (v.getRegistrationNumber().equalsIgnoreCase(VRN)) {
                            listOrderByVRN.add(checkVRNid);
                        }
                    }
                }
            }

            if (listOrders == null) {
                response.sendRedirect("Emp_ListOrder");
            } else {

        %><table border="1">
            <tbody>
                <tr>
                    <td>


                        <select style="font-weight: bold" id = "status" name="status"  onchange="redirectToPage()">
                            <option value="all" <%if (status.equalsIgnoreCase("all")) {
                                    out.println("selected");
                                }%>>All</option>
                            <option value="pending" <%if (status.equalsIgnoreCase("pending")) {
                                    out.println("selected");
                                }%>>Pending</option>
                            <option value="confirmed" <%if (status.equalsIgnoreCase("confirmed")) {
                                    out.println("selected");
                                }%>>Confirmed</option>
                            <option value="cancelled" <%if (status.equalsIgnoreCase("cancelled")) {
                                    out.println("selected");
                                }%>>cancelled</option>
                            <option value="on going" <%if (status.equalsIgnoreCase("on going")) {
                                    out.println("selected");
                                }%>>On Going</option>
                            <option value="completed" <%if (status.equalsIgnoreCase("completed")) {
                                    out.println("selected");
                                }%>>Completed</option>
                        </select>

                    </td>
                    <td>
                        <select style="font-weight: bold" id = "depo" name="status"  onchange="redirectToPage()">
                            <option value="all" <%if (depo.equalsIgnoreCase("all")) {
                                    out.println("selected");
                                }%>>All</option>
                            <option value="deposited" <%if (depo.equalsIgnoreCase("deposited")) {
                                    out.println("selected");
                                }%>>Deposited</option>
                            <option value="undeposited" <%if (depo.equalsIgnoreCase("undeposited")) {
                                    out.println("selected");
                                }%>>Undeposited</option>
                        </select>
                    </td>
            <div>
                <td>
                    <input type="text" placeholder="Customer phone number" id="search-byPN" value="<%if(!checkPN){out.println(PN);}%>">

                </td>
                <td>
                    <input type="text" placeholder="Vehicle registration number" id="search-byVRN" value="<%if(!checkVRN){out.println(VRN);}%>">
                </td>
                <td>
                    <input type="text" placeholder="Order ID" id="search-OrderID" value="<%if(OrderID>0){out.println(OrderID);}%>">
                </td>
                <td>
                    <button onclick="redirectToPage()" style="width: 100%">Filter</button>
                </td>
            </div>
        </tr>
    </tbody>
</table>

<table border="1">
    <thead>
        <tr>
            <th>Order</th>
            <th>Customer</th>
            <th>Vehicles</th>
            <th>Start date</th>
            <th>End date</th>
            <th>Total</th>
            <th>Status</th>
            <th>Update</th>
        </tr>
    </thead>
    <tbody>


        <%                    int count = 0;
            for (int oid : orderBy) {
                RentalOrder ro = listOrders.get(oid);
                if ((status.equalsIgnoreCase("all") || listOrders.get(oid).getStatus().equalsIgnoreCase(status))
                        && (checkDepo == 2 || ro.getDepositPaid() == checkDepo)
                        && (checkPN||listCustomers.get(ro.getCustomerId()).getPhoneNumber().equalsIgnoreCase(PN))
                        && (checkVRN||listOrderByVRN.contains(ro.getOrderId()))
                        && (OrderID<0)||oid==OrderID) {
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
                    List<Vehicle> list = mapVehicles.get(oid);
                    for (Vehicle v : list) {
                        out.println(v.getModel() + "(" + v.getRegistrationNumber() + ")" + "</br>");
                    }
                %>
            </td>

            <td>
                <%=ro.getStartDate()%>
            </td>

            <td>
                <%=ro.getEndDate()%>
            </td>

            <td>
                <%=ro.getTotalAmount()%>
            </td>



            <td>
                <%=ro.getStatus()%>
            </td>
            <td>
                <a href="Emp_OrderDetail?id=<%=oid%>" style="text-decoration: none"><button>Show detail</button></a>
            </td>
        </tr>
        <%
                    count++;
                }
            }

        %>
    </tbody>
</table>


<%            if (count == 0) {
%><h2 style="color: red">Rental order <%=status%> list is empty!</h2><%
                }

            }
%>




</body>
</html>
