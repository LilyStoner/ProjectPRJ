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
        </script>

    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
        <%  
            
            RentalOrder rentalOrder = (RentalOrder)request.getAttribute("ro");
            Customer customer = (Customer)request.getAttribute("c");
            List<Vehicle> vehicles = (List<Vehicle>)request.getAttribute("lv");
            Map<Integer,OrderVehicle> orderVehicles = (Map<Integer,OrderVehicle>)request.getAttribute("ov");
            List<String> err = (List<String>)request.getAttribute("err");
            
            
            if(rentalOrder==null){
                response.sendRedirect("Emp_ListOrder");
            }
            else{
                String status = rentalOrder.getStatus();
            
            
            
                
        %>
        <a href="Emp_ListOrder" style="font-weight: bold;">Back to order list</a>

        <div style="display: flex;justify-content: space-around">
            <h1>Order ID: <%=rentalOrder.getOrderId()%></h1>
            Create date: <%=rentalOrder.getCreatedAt()%></br>
            Start date: <%=rentalOrder.getStartDate()%></br>
            End date: <%=rentalOrder.getEndDate()%>
        </div>

        <p style="font-weight: bold">Customer</p>
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
        <p style="font-weight: bold">Vehicles</p>
        <table border="1">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Type</th>
                    <th>Model</th>
                    <th>Registration number</th>
                    <th>price per day</th>
                    <%if(rentalOrder.getStatus().equalsIgnoreCase("pending")){out.println("<th>status</th>");}%>

                </tr>
            </thead>
            <tbody>
                <%
                for(Vehicle v:vehicles){
                
                %>

                <tr <%if(!v.getStatus().equalsIgnoreCase("Available")&&rentalOrder.getStatus().equalsIgnoreCase("pending")){out.println("style=\"color:red\"");}%>>
                    <td><%=v.getVehicleId()%></td>
                    <td><%=v.getVehicleType()%></td>
                    <td><%=v.getBrand()%> <%=v.getModel()%></td>
                    <td><%=v.getRegistrationNumber()%></td>
                    <td><%=v.getPricePerDay()%></td>
                    <%if(rentalOrder.getStatus().equalsIgnoreCase("pending")){%><td><%=v.getStatus()%></td><%}%>
                </tr>

                <%
                    }
                %>
            </tbody>

        </table>
        <p style="font-weight: bold;text-align: left">Total: <%=rentalOrder.getTotalAmount()%></p>
        <h2 <%if(status.equalsIgnoreCase("cancelled")){out.println("style=\"color: red\"");}else{out.println("style=\"color: #1c7430\"");}%>>Status: <%=status%></h2>
        <p style="font-weight: bold">Update</p>
        <%
            for(String s:err){
        %>
        <h2 style="color: red"><%=s%></h2>
        <%    
            }
        %>

        <form action="Emp_UpdateOrder">
            <div style="display: flex;justify-content: space-around">    
                <input style="width: 15vw;height: 50px;background-color: #ff9999;color:  white;text-align: center" type="submit" value="CANCLE" name="up" <%if(!status.equalsIgnoreCase("pending")&&!status.equalsIgnoreCase("confirmed")){out.println("disabled");}%>/>
                <input style="width: 15vw;height: 50px;background-color: #ccffcc;color:  white;text-align: center" type="submit" value="CONFIRM" name="up" <%if(err.size()>0||!status.equalsIgnoreCase("pending")){out.println("disabled");}%>/>
                <input style="width: 15vw;height: 50px;background-color: #ccffcc;color:  white;text-align: center" type="submit" value="ON GOING" name="up" <%if(!status.equalsIgnoreCase("confirmed")){out.println("disabled");}%>/>
                <input style="width: 15vw;height: 50px;background-color: #ccffcc;color:  white;text-align: center" type="submit" value="COMPLETED" name="up" <%if(!status.equalsIgnoreCase("on going")){out.println("disabled");}%>/>

            </div>
        </form>
        <%
            }
        %>

    </body>
</html>
