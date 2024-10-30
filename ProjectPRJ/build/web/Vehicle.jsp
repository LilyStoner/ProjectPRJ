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
            background-color: #e9ecef; /* Màu nền nhạt hơn */
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            min-height: 100vh; /* Đảm bảo footer ở dưới cùng */
        }
        h1 {
            text-align: center;
            color: #343a40; /* Màu chữ đậm hơn */
        }
        .search-container {
            margin-bottom: 20px; /* Khoảng cách giữa thanh tìm kiếm và bảng */
            display: flex;
            justify-content: flex-start; /* Căn trái cho thanh tìm kiếm */
        }
        .search-container input[type="text"] {
            padding: 8px;
            border: 1px solid #ced4da; /* Viền nhẹ cho input */
            border-radius: 4px; /* Bo góc */
            width: 300px; /* Độ rộng của trường tìm kiếm */
        }
        .search-container button {
            padding: 8px 12px;
            margin-left: 5px; /* Khoảng cách giữa input và nút */
            background-color: #007BFF; /* Màu xanh cho nút tìm kiếm */
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-container button:hover {
            background-color: #0056b3; /* Màu đậm hơn khi hover */
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            background-color: #ffffff; /* Màu nền trắng cho bảng */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #dee2e6; /* Màu viền nhẹ hơn */
        }
        th {
            background-color: #007BFF; /* Màu nền xanh cho tiêu đề */
            color: white;
        }
        tr:hover {
            background-color: #f8f9fa; /* Màu nền khi hover nhẹ nhàng */
        }
        /* Đảm bảo rằng tất cả các ô cũng thay đổi màu */
        tr:hover td {
            background-color: #f8f9fa;
        }
        a {
            text-decoration: none;
            color: #007BFF; /* Màu liên kết xanh */
        }
        a:hover {
            text-decoration: underline;
            color: #0056b3; /* Màu liên kết đậm hơn khi hover */
        }
        .action-links {
            display: flex;
            gap: 10px; /* khoảng cách giữa các liên kết */
        }
        footer {
            margin-top: auto; /* Đẩy footer xuống dưới cùng */
            text-align: center;
            padding: 10px;
            background-color: #343a40; /* Màu nền tối cho footer */
            color: white;
        }
        .btn-insert {
            display: block;
            margin: 20px auto; /* Giữa màn hình */
            padding: 10px 20px;
            background-color: #28a745; /* Màu xanh lá cho nút thêm */
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            text-align: center;
            text-decoration: none; /* Bỏ gạch chân */
        }
        .btn-insert:hover {
            background-color: #218838; /* Màu đậm hơn khi hover */
        }
        .btn-action {
            display: inline-block;
            padding: 5px 10px; /* Kích thước nhỏ hơn cho các nút hành động */
            background-color: #007BFF; /* Màu xanh cho nút hành động */
            color: white;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }
        .btn-action:hover {
            background-color: #0056b3; /* Màu đậm hơn khi hover */
        }
    </style>
</head>
<body>
    <h1>Danh Sách Xe</h1>

    <!-- Thanh tìm kiếm -->
    <div class="search-container">
        <form action="VehicleSearch" method="post">
            <input type="text" name="search" value="${param.search}" placeholder="Tìm kiếm xe...">
            <input type="submit" value="Tìm kiếm">
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
            <th>Thao Tác</th> <!-- Cột thao tác -->
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
                        <a href="VehicleUpdateGetId?vehicle_id=${vehicle.vehicle_id}" class="btn-action">Cập Nhật</a>
                        <a href="VehiclesDelete?vehicle_id=${vehicle.vehicle_id}" class="btn-action" onclick="return confirm('Bạn có chắc chắn muốn xóa phương tiện này không?');">Xóa</a>
                        <a href="MaintenanceHistoryController?vehicle_id=${vehicle.vehicle_id}" class="btn-action">Bảo Trì</a>
                    </td>
                </tr>
            </c:forEach>
        </c:if>

        <!-- Hiển thị thông báo nếu không có xe nào -->
        <c:if test="${empty listP}">
            <tr>
                <td colspan="10">Không tìm thấy phương tiện nào.</td> <!-- Cập nhật colspan thành 10 -->
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
