<%@page contentType="text/html; charset=UTF-8"%>
<%@page import="model.Vehicle"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cập Nhật Thông Tin Xe</title>
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
            form {
                background-color: #ffffff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                max-width: 800px; /* Đã thay đổi từ 600px thành 800px */
                margin: 20px auto;
                width: 100%; /* Đảm bảo form luôn chiếm toàn bộ chiều rộng */
            }
            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
                color: #495057;
            }
            input[type="text"], select {
                width: 100%;
                padding: 8px;
                border: 1px solid #ced4da;
                border-radius: 4px;
                margin-bottom: 15px;
                box-sizing: border-box;
            }
            input[type="submit"] {
                background-color: #28a745;
                color: white;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                padding: 10px 15px;
                font-size: 16px;
                display: block;
                width: 100%;
            }
            input[type="submit"]:hover {
                background-color: #218838;
            }
            img {
                margin: 10px 0;
            }
            .back-link {
                display: block;
                margin: 20px auto;
                text-align: center;
                text-decoration: none;
                color: #007BFF;
            }
            .back-link:hover {
                text-decoration: underline;
            }
        </style>
    </head>
    <body>
        <h1>Cập Nhật Thông Tin Xe</h1>

        <form action="VehicleUpdate" method="POST">
            <label>ID:</label>
            <input type="text" name="vehicle_id" value="${vehicle.vehicle_id}" readonly/><br>
            <label>Loại Phương Tiện:</label>
            <input type="text" name="vehicle_type" value="${vehicle.vehicle_type}"/><br/>
            <label>Mô Hình:</label>
            <input type="text" name="model" value="${vehicle.model}"/><br/>
            <label>Thương Hiệu:</label>
            <input type="text" name="brand" value="${vehicle.brand}"/><br/>
            <label>Số Đăng Ký:</label>
            <input type="text" name="registration_number" value="${vehicle.registration_number}"/><br/>
            <label>Năm Sản Xuất:</label>
            <input type="text" name="manufacture_year" value="${vehicle.manufacture_year}"/><br/>
            <label>Giá/Ngày:</label>
            <input type="text" name="price_per_day" value="${vehicle.price_per_day}"/><br/>
            
            <label>Trạng Thái:</label>
            <select name="status">
                <option value="Available" <c:if test="${vehicle.status == 'Available'}">selected</c:if>>Available</option>
                <option value="Rented" <c:if test="${vehicle.status == 'Rented'}">selected</c:if>>Rented</option>
                <option value="Unavailable" <c:if test="${vehicle.status == 'Unavailable'}">selected</c:if>>Unavailable</option>
            </select><br/>

            <!-- Hiển thị hình ảnh hiện tại -->
            <label>Hình Ảnh Hiện Tại:</label><br/>
            <img src="${vehicle.img}" alt="Hình Ảnh Xe" style="width: 200px; height: auto;"/><br/>

            <!-- Trường nhập liệu cho đường dẫn hình ảnh mới -->
            <label>Link Ảnh Mới:</label>
            <input type="text" name="img" value="${vehicle.img}"/><br/>

            <input type="submit" value="Cập Nhật"/>
        </form>

        <a href="VehicleController" class="back-link">Quay Lại</a>
    </body>
</html>
