<%-- 
    Document   : contract_complete
    Created on : Oct 20, 2024, 9:46:24 PM
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.io.IOException, java.util.ArrayList, java.util.List,model.Customer"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile</title>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
        <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css" />
        <link rel="stylesheet" href="assets/css/main.css" />
        <noscript><link rel="stylesheet" href="assets/css/noscript.css" /></noscript>

    </head>
    <body>
        <jsp:include page="includes/header.jsp" />
        <jsp:include page="includes/menu.jsp" />
        <%
Customer customer = (Customer) session.getAttribute("customer");
        %>
        <h1>Thông tin người dùng</h1>
        <form action="updateCustomer" method="post"> 
            <table>
                <tr>
                    <td style="width: 10em">ID:</td>
                    <td>${customer.customerId}</td>
                </tr>
                <tr>
                    <td>Họ và tên:</td>
                    <td>${customer.fullName}</td>
                </tr>
                <tr>
                    <td>Ngày sinh:</td>
                    <td>                    
                        <input type="date" name="birth" value="${customer.dateOfBirth != null ? customer.dateOfBirth.toString() : "N/A"}" required />
                        </td>
                </tr>
                <tr>
                    <td>Số điện thoại:</td>
                    <td>
                        <input type="text" name="phoneNumber" value="${customer.phoneNumber}" required />
                    </td>
                </tr>
                <tr>
                    <td>Địa chỉ:</td>
                    <td>
                        <input type="text" name="address" value="${customer.address}" required />
                    </td>
                </tr>
                <tr>
                    <td>Số giấy phép lái xe:</td>
                    <td>
                        <% if(customer.getDrivingLicenseNumber()==null||customer.getDrivingLicenseNumber().isEmpty()) {%>
                        <input type="text" name="drivingLicenseNumber" value=""/>
                        <%} else {%>
                        <input type="text" name="drivingLicenseNumber" value="${customer.drivingLicenseNumber}" readonly="readonly"/>
                        <%
                            }
                        %>
                    </td>
                </tr>
            </table>
            <input type="submit" value="Cập nhật" />
        </form>

        <script src="assets/js/jquery.min.js"></script>
        <script src="assets/bootstrap/js/bootstrap.bundle.min.js"></script>
        <script src="assets/js/jquery.scrolly.min.js"></script>
        <script src="assets/js/jquery.scrollex.min.js"></script>
        <script src="assets/js/main.js"></script>
    </body>
</html>
