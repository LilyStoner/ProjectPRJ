<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="model.Vehicle"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh Sách Xe</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #e9ecef;
                margin: 0;
                padding: 20px;
                display: flex;
                flex-direction: column;
                min-height: 100vh;
            }
            h1 {
                text-align: center;
                color: #343a40;
            }
            .search-container {
                margin-bottom: 20px;
                display: flex;
                justify-content: flex-start;
            }
            .search-container input[type="text"] {
                padding: 8px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                width: 300px;
            }
            .search-container button {
                padding: 8px 12px;
                margin-left: 5px;
                background-color: #007BFF;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
            }
            .search-container button:hover {
                background-color: #0056b3;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                margin: 20px 0;
                background-color: #ffffff;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            }
            th, td {
                padding: 12px;
                text-align: left;
                border-bottom: 1px solid #dee2e6;
            }
            th {
                background-color: #007BFF;
                color: white;
            }
            tr:hover {
                background-color: #f8f9fa;
            }
            tr:hover td {
                background-color: #f8f9fa;
            }
            a {
                text-decoration: none;
                color: #007BFF;
            }
            a:hover {
                text-decoration: underline;
                color: #0056b3;
            }
            .action-links {
                display: flex;
                gap: 10px;
            }
            footer {
                margin-top: auto;
                text-align: center;
                padding: 10px;
                background-color: #343a40;
                color: white;
            }
            .btn-insert {
                display: block;
                margin: 20px auto;
                padding: 10px 20px;
                background-color: #28a745;
                color: white;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                text-align: center;
                text-decoration: none;
            }
            .btn-insert:hover {
                background-color: #218838;
            }
        </style>
    </head>
    <body>
        <h1>Danh Sách Xe</h1>

        <!-- Thanh tìm kiếm -->
        <div class="search-container">
            <form action="VehicleSearch" method="GET">
                <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm xe...">
                <input type="submit" value="Tìm kiếm">
            </form>
        </div>
        <div class="search-container" style="margin-top: 20px;">
            <form action="VehicleController" method="GET">
                <input type="submit" value="Quay lại Danh Sách Xe" style="background-color: #dc3545; color: white; border: none; border-radius: 4px; padding: 8px 12px; cursor: pointer;">
            </form>
        </div>

        <table>
            <tr>
                <th>ID</th>
                <th>Loại Phương Tiện</th>
                <th>Mô Hình</th>
                <th>Thương Hiệu</th>
                <th>Số Đăng Ký</th>
                <th>Năm Sản Xuất</th>
                <th>Giá/Ngày</th>
                <th>Trạng Thái</th>
                <th>Ảnh</th>
                <th>Thao Tác</th>
            </tr>

            <!-- Kiểm tra và hiển thị danh sách xe -->
            <c:if test="${not empty listP}">
                <c:forEach var="vehicle" items="${listP}">
                    <tr>
                        <td>${vehicle.vehicle_id}</td>
                        <td>${vehicle.vehicle_type}</td>
                        <td>${vehicle.model}</td>
                        <td>${vehicle.brand}</td>
                        <td>${vehicle.registration_number}</td>
                        <td>${vehicle.manufacture_year}</td>
                        <td>${vehicle.price_per_day}</td>
                        <td>${vehicle.status}</td>
                        <td><img src="${vehicle.img}" alt="alt" style="width:300px;height:auto;"/></td>
                        <td class="action-links">
                            <a href="VehicleUpdateGetId?vehicle_id=${vehicle.vehicle_id}">Cập Nhật</a>
                            <a href="VehiclesDelete?vehicle_id=${vehicle.vehicle_id}" onclick="return confirm('Bạn có chắc chắn muốn xóa phương tiện này không?');">Xóa</a>
                            <a href="MaintenanceHistoryController?vehicle_id=${vehicle.vehicle_id}">Bảo Trì</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>

            <!-- Hiển thị thông báo nếu không có xe nào -->
            <c:if test="${empty listP}">
                <tr>
                    <td colspan="10">Không tìm thấy phương tiện nào.</td>
                </tr>
            </c:if>
        </table>

        <a href="VehicleInsert" class="btn-insert">Thêm Xe</a> 

        <footer>
            <p>Cửa hàng thuê xe ABC</p>
            <p>Số điện thoại liên hệ: 0123-456-789</p>
        </footer>
    </body>
</html>
