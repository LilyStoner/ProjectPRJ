INSERT INTO [dbo].[User] (username, email, password_hash, registration_date, last_login)
VALUES 
('john_doe', 'john.doe@example.com', 'hashed_password_123', '2024-10-20', '2024-10-20'),
('jane_smith', 'jane.smith@example.com', 'hashed_password_456', '2024-10-19', '2024-10-20');


INSERT INTO [dbo].[Customer] (user_id, full_name, phone_number, address, driving_license_number, date_of_birth)
VALUES 
(1, 'Tran Thi B', '0909123457', '456 Đường XYZ, Quận 2, TP.HCM', 'DL87654321', '1985-05-12'),
(2, 'Le Hoang C', '0909123458', '789 Đường LMN, Quận 3, TP.HCM', 'DL11223344', '1992-09-20');

INSERT INTO [dbo].[Vehicle] (vehicle_type, model, brand, registration_number, manufacture_year, price_per_day, status, description, image)
VALUES
('4-seater', 'Model A', 'Brand A', 'ABC1234', 2022, 50.00, 'available', 'Compact car with 4 seats', 'images\\product-4-720x480.jpg'),
('4-seater', 'Model B', 'Brand B', 'DEF5678', 2021, 55.00, 'available', 'Economical sedan', 'images\\product-4-720x480.jpg'),
('4-seater', 'Model C', 'Brand C', 'GHI9012', 2020, 60.00, 'rented', 'Comfortable 4-seater', 'images\\product-4-720x480.jpg'),
('5-seater', 'Model D', 'Brand D', 'JKL3456', 2019, 70.00, 'available', 'SUV with 5 seats', 'images\\product-4-720x480.jpg'),
('5-seater', 'Model E', 'Brand E', 'MNO7890', 2021, 75.00, 'maintenance', 'Luxury 5-seater', 'images\\product-4-720x480.jpg'),
('5-seater', 'Model F', 'Brand F', 'PQR1234', 2022, 80.00, 'available', 'Spacious 5-seater', 'images\\product-4-720x480.jpg'),
('6-seater', 'Model G', 'Brand G', 'STU5678', 2021, 85.00, 'available', '6-seater family car', 'images\\product-4-720x480.jpg'),
('6-seater', 'Model H', 'Brand H', 'VWX9012', 2020, 90.00, 'rented', 'Large 6-seater', 'images\\product-4-720x480.jpg'),
('6-seater', 'Model I', 'Brand I', 'YZA3456', 2019, 95.00, 'available', 'Comfortable 6-seater', 'images\\product-4-720x480.jpg'),
('7-seater', 'Model J', 'Brand J', 'BCD7890', 2021, 100.00, 'maintenance', '7-seater SUV', 'images\\product-4-720x480.jpg'),
('7-seater', 'Model K', 'Brand K', 'EFG1234', 2022, 105.00, 'available', 'Spacious 7-seater', 'images\\product-4-720x480.jpg'),
('7-seater', 'Model L', 'Brand L', 'HIJ5678', 2020, 110.00, 'rented', 'Luxury 7-seater', 'images\\product-4-720x480.jpg'),
('8-seater', 'Model M', 'Brand M', 'KLM9012', 2021, 115.00, 'available', 'Large 8-seater', 'images\\product-4-720x480.jpg'),
('8-seater', 'Model N', 'Brand N', 'NOP3456', 2022, 120.00, 'available', 'Comfortable 8-seater', 'images\\product-4-720x480.jpg'),
('8-seater', 'Model O', 'Brand O', 'QRS7890', 2020, 125.00, 'maintenance', 'Family 8-seater', 'images\\product-4-720x480.jpg'),
('9-seater', 'Model P', 'Brand P', 'TUV1234', 2019, 130.00, 'available', '9-seater van', 'images\\product-4-720x480.jpg'),
('9-seater', 'Model Q', 'Brand Q', 'WXY5678', 2021, 135.00, 'rented', 'Large 9-seater', 'images\\product-4-720x480.jpg'),
('9-seater', 'Model R', 'Brand R', 'ZAB9012', 2022, 140.00, 'available', 'Spacious 9-seater', 'images\\product-4-720x480.jpg'),
('10-seater', 'Model S', 'Brand S', 'CDE3456', 2020, 145.00, 'available', '10-seater minibus', 'images\\product-4-720x480.jpg'),
('10-seater', 'Model T', 'Brand T', 'FGH7890', 2019, 150.00, 'maintenance', 'Luxury 10-seater', 'images\\product-4-720x480.jpg'),
('10-seater', 'Model U', 'Brand U', 'IJK1234', 2021, 155.00, 'available', 'Spacious 10-seater', 'images\\product-4-720x480.jpg'),
('11-seater', 'Model V', 'Brand V', 'LMN5678', 2022, 160.00, 'rented', 'Large 11-seater', 'images\\product-4-720x480.jpg'),
('11-seater', 'Model W', 'Brand W', 'OPQ9012', 2020, 165.00, 'available', 'Comfortable 11-seater', 'images\\product-4-720x480.jpg'),
('11-seater', 'Model X', 'Brand X', 'RST3456', 2021, 170.00, 'available', 'Family 11-seater', 'images\\product-4-720x480.jpg'),
('12-seater', 'Model Y', 'Brand Y', 'UVW7890', 2022, 175.00, 'maintenance', 'Spacious 12-seater', 'images\\product-4-720x480.jpg'),
('12-seater', 'Model Z', 'Brand Z', 'XYZ1234', 2020, 180.00, 'available', 'Luxury 12-seater', 'images\\product-4-720x480.jpg'),
('12-seater', 'Model AA', 'Brand AA', 'BCD5678', 2021, 185.00, 'rented', 'Large 12-seater', 'images\\product-4-720x480.jpg'),
('13-seater', 'Model AB', 'Brand AB', 'EFG9012', 2022, 190.00, 'available', 'Comfortable 13-seater', 'images\\product-4-720x480.jpg'),
('13-seater', 'Model AC', 'Brand AC', 'HIJ3456', 2020, 195.00, 'available', 'Spacious 13-seater', 'images\\product-4-720x480.jpg'),
('13-seater', 'Model AD', 'Brand AD', 'KLM7890', 2021, 200.00, 'maintenance', 'Luxury 13-seater', 'images\\product-4-720x480.jpg');
