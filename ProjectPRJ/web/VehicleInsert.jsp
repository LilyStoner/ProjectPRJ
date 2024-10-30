<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Thêm Xe Mới</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            h1 {
                text-align: center;
                color: #333;
            }

            .header {
                display: flex;
                justify-content: flex-end; /* Căn chỉnh nút ở bên phải */
                margin-bottom: 20px; /* Khoảng cách dưới header */
            }

            form {
                max-width: 600px;
                margin: 0 auto;
                background: white;
                padding: 20px;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            }

            label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
                color: #555;
            }

            input[type="text"],
            input[type="number"],
            textarea {
                width: calc(100% - 22px);
                padding: 10px;
                margin-bottom: 20px;
                border: 1px solid #ccc;
                border-radius: 4px;
                box-sizing: border-box;
            }

            input[type="submit"],
            input[type="button"] {
                width: 48%;
                padding: 10px;
                border: none;
                border-radius: 4px;
                color: white;
                font-size: 16px;
                cursor: pointer;
                transition: background-color 0.3s;
            }

            input[type="submit"] {
                background-color: #28a745; /* Màu xanh cho nút Thêm Xe */
            }

            input[type="button"] {
                background-color: #dc3545; /* Màu đỏ cho nút Hủy */
            }

            input[type="button"].home {
                background-color: #007bff; /* Màu xanh dương cho nút Home */
                font-size: 14px; /* Giảm kích thước chữ */
                padding: 8px; /* Giảm padding */
                width: auto; /* Đặt width là tự động để nút nhỏ lại */
                margin-left: 10px; /* Khoảng cách bên trái */
            }

            input[type="submit"]:hover {
                background-color: #218838; /* Màu xanh đậm khi hover */
            }

            input[type="button"]:hover,
            input[type="button"].home:hover {
                background-color: #c82333; /* Màu đỏ đậm khi hover */
            }

            textarea {
                height: 100px;
            }
        </style>
    </head>
    <body>
        <div class="header">
            <input type="button" class="home" value="Trang Chủ" onclick="window.location.href = 'VehicleController';">
        </div>
        <h1>Thêm Xe Mới</h1>
        <form action="VehicleInsert" method="post">
            <label for="vehicle_type">Loại xe (sedan, SUV, hatchback, ...):</label>
            <input type="text" id="vehicle_type" name="vehicle_type" required>

            <label for="model">Mẫu xe (Lambo, Toyota,....):</label>
            <input type="text" id="model" name="model" required>

            <label for="brand">Thương hiệu (Toyota, Ford, Honda, BMW, Mercedes-Benz,....):</label>
            <input type="text" id="brand" name="brand" required>

            <label for="registration_number">Số đăng ký:</label>
            <input type="text" id="registration_number" name="registration_number" required>

            <label for="manufacture_year">Năm sản xuất:</label>
            <input type="number" id="manufacture_year" name="manufacture_year" required>

            <label for="price_per_day">Giá mỗi ngày ($):</label>
            <input type="number" step="0.01" id="price_per_day" name="price_per_day" required>

            <label for="status">Trạng thái:</label>
            <select name="status" required>
                <option value="Available" ${vehicle.status == 'available' ? 'selected' : ''}>Available</option>
                <option value="Maintenance" ${vehicle.status == 'maintenance' ? 'selected' : ''}>Maintenance</option>
                <option value="Rented" ${vehicle.status == 'rented' ? 'selected' : ''}>Rented</option>
            </select><br><br>

            <label for="description">Mô tả:</label>
            <textarea id="description" name="description" required></textarea>

            <label for="img">Link ảnh:</label>
            <input type="text" id="img" name="img" required>


            <input type="button" value="Hủy" onclick="window.location.href = 'VehicleController';">
            <input type="submit" value="Thêm Xe">
        </form>
    </body>
</html>
