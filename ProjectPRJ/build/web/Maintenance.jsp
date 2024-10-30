<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.List" %>
<%@ page import="model.MaintenanceHistory" %>
<html>
    <head>
        <title>Lịch sử bảo trì</title>
        <style>
            /* Định dạng cho body */
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                color: #333;
                margin: 20px;
            }

            /* Định dạng cho tiêu đề */
            h2 {
                text-align: center;
                color: #4CAF50;
            }

            /* Định dạng cho bảng */
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
            }

            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #ddd;
            }

            /* Định dạng cho các tiêu đề bảng */
            th {
                background-color: #4CAF50;
                color: white;
            }

            /* Định dạng cho hàng khi di chuột */
            tr:hover {
                background-color: #f1f1f1;
            }

            /* Định dạng cho thông báo không có dữ liệu */
            td[colspan="6"] {
                text-align: center;
                color: #888;
            }

            /* Định dạng cho nút */
            .button {
                display: inline-block;
                padding: 10px 15px;
                margin: 5px 0;
                font-size: 16px;
                color: white;
                background-color: #007BFF;
                text-align: center;
                text-decoration: none;
                border-radius: 4px;
                transition: background-color 0.3s ease;
            }

            .button:hover {
                background-color: #0056b3;
            }

            /* Định dạng cho nút xóa */
            .delete-button {
                background-color: #dc3545; /* Màu đỏ cho nút xóa */
            }

            .delete-button:hover {
                background-color: #c82333; /* Màu đỏ đậm cho nút xóa khi hover */
            }
        </style>
    </head>
    <body>

        <h2>Lịch sử bảo trì</h2>

        <!-- Bảng hiển thị lịch sử bảo trì -->
        <table border="1">
            <tr>
                <th>ID Xe</th>
                <th>ID bảo trì</th>
                <th>Ngày Bảo Trì</th>
                <th>Mô Tả</th>
                <th>Chi Phí</th>
                <th>Thay Đổi</th>
            </tr>
            <c:if test="${not empty maintenanceHistory}">
                <c:forEach var="history" items="${maintenanceHistory}">
                    <tr>
                        <td>${history.vehicle_id}</td>
                        <td>${history.maintenance_id}</td>
                        <td>${history.maintenance_date}</td>
                        <td>${history.description}</td>
                        <td>${history.cost}</td>
                        <td>
                            <a href="MaintenanceHistoryDelete?maintenance_id=${history.maintenance_id}&vehicle_id=${history.vehicle_id}" 
                               class="button delete-button" 
                               onclick="return confirm('Bạn có chắc chắn muốn xóa phương tiện này không?');">Xóa</a>
                        </td>
                    </tr> 
                </c:forEach>
                <tr>
                    <td colspan="6" style="text-align: center;">
                        <a href="MaintenanceHistoryGetIdInsert?vehicle_id=${maintenanceHistory[0].vehicle_id}" class="button">Thêm bảo trì</a>
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty maintenanceHistory}">
                <tr>
                    <td colspan="6">Không có dữ liệu lịch sử bảo trì.</td>
                </tr>
                <tr>
                    <td colspan="6" style="text-align: center;">
                        <a href="MaintenanceHistoryGetIdInsert?vehicle_id=${param.vehicle_id}" class="button">Thêm bảo trì</a>
                    </td>
                </tr>
            </c:if>
        </table>

        <div style="text-align: center;">
            <a href="VehicleController" class="button">Quay Lại</a> 
        </div>

    </body>
</html>
