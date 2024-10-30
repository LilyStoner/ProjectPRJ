<%@ page contentType="text/html;charset=UTF-8" language="java" %> 
<html>
    <head>
        <title>Thêm Bảo Trì</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f4f4f4;
                margin: 0;
                padding: 20px;
            }

            h2 {
                color: #333;
            }

            form {
                background-color: #fff;
                padding: 20px;
                border-radius: 5px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                max-width: 400px;
                margin: auto;
            }

            label {
                display: block;
                margin-bottom: 8px;
                font-weight: bold;
            }

            input[type="text"],
            input[type="date"],
            input[type="number"],
            textarea {
                width: 100%;
                padding: 10px;
                margin-bottom: 15px;
                border: 1px solid #ccc;
                border-radius: 4px;
                font-size: 14px;
            }

            input[type="submit"],
            .back-link {
                background-color: #28a745;
                color: white;
                padding: 10px 15px;
                border: none;
                border-radius: 4px;
                cursor: pointer;
                font-size: 16px;
                text-align: center;
                text-decoration: none;
                display: inline-block; /* Tạo ra dạng nút */
                transition: background-color 0.3s;
                margin-top: 10px; /* Thêm khoảng cách giữa nút và form */
            }

            input[type="submit"]:hover,
            .back-link:hover {
                background-color: #218838;
            }

            a {
                color: white; /* Đảm bảo màu chữ trắng */
            }
        </style>
    </head>
    <body>
        <h2>Thêm Bảo Trì cho Xe</h2>
        <form action="MaintenanceHistoryInsert" method="post">
            <label for="vehicle_id">ID Xe:</label>
            <input type="text" id="vehicle_id" name="vehicle_id" value="${vehicle_id}" readonly><br> <!-- Không cho sửa -->

            <label for="maintenance_date">Ngày Bảo Trì:</label>
            <input type="date" id="maintenance_date" name="maintenance_date" required><br>

            <label for="description">Mô Tả:</label>
            <textarea id="description" name="description" required></textarea><br>

            <label for="cost">Chi Phí:</label>
            <input type="number" id="cost" name="cost" required step="0.01"><br>

            <input type="submit" value="Thêm">
        </form>

        <a class="back-link" href="MaintenanceHistoryController?vehicle_id=${param.vehicle_id}">Quay lại</a>
    </body>
</html>
