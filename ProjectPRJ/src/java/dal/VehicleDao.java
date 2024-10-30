/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.Vehicle;

import model.VehicleI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao extends DBContext{

    Connection conn = null; // Kết nối với SQL Server
    PreparedStatement ps = null; // Ném câu lệnh query sang SQL Server
    ResultSet rs = null; // Nhận kết quả trả về

    public List<Vehicle> getAllVehicles() {
        String query = "SELECT * FROM Vehicle;";
        List<Vehicle> list = new ArrayList<>();
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    public List<Vehicle> getAllVehiclesByBrand(String brand) {
        List<Vehicle> list = new ArrayList<>();
        String query = "SELECT * FROM Vehicle \n"
                + "WHERE brand LIKE ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + brand + "%"); // Tìm kiếm theo thương hiệu
            rs = ps.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return list;
    }

    // CREATE
    public boolean addVehicle(VehicleI vehicle) {//package VehicleDao
        String query = "INSERT INTO [dbo].[Vehicle]\n"
                + "			   ([vehicle_type]\n"
                + "			   ,[model]\n"
                + "			   ,[brand]\n"
                + "			   ,[registration_number]\n"
                + "			   ,[manufacture_year]\n"
                + "			   ,[price_per_day]\n"
                + "			   ,[status]\n"
                + "			   ,[description]\n"
                + "			   ,[image])\n"
                + "		 VALUES\n"
                + "			   (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            ps.setString(1, vehicle.getVehicle_type());
            ps.setString(2, vehicle.getModel());
            ps.setString(3, vehicle.getBrand());
            ps.setString(4, vehicle.getRegistration_number());
            ps.setInt(5, vehicle.getManufacture_year());
            ps.setDouble(6, vehicle.getPrice_per_day());
            ps.setString(7, vehicle.getStatus());
            ps.setString(8, vehicle.getDescription());
            ps.setString(9, vehicle.getImg());

            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

    // READ
    public Vehicle getVehicleById(int vehicle_id) {
        String query = "SELECT * FROM Vehicle WHERE vehicle_id = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, vehicle_id);
            rs = ps.executeQuery();
            if (rs.next()) {
                return new Vehicle(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getDouble(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return null;
    }

    // UPDATE in VehicleDao
    public Vehicle getIdToUpdate(int vehicleId) {
        String query = "SELECT * FROM Vehicle WHERE vehicle_id = ?";
        Vehicle vehicle = null;

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, vehicleId);
            rs = ps.executeQuery();

            if (rs.next()) {
                vehicle = new Vehicle(
                        rs.getInt("vehicle_id"),
                        rs.getString("vehicle_type"),
                        rs.getString("model"),
                        rs.getString("brand"),
                        rs.getString("registration_number"),
                        rs.getInt("manufacture_year"),
                        rs.getDouble("price_per_day"),
                        rs.getString("status"),
                        rs.getString("description"),
                        rs.getString("img"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicle;
    }

    public boolean updateVehicle(Vehicle vehicle) {
        String query = "UPDATE Vehicle SET vehicle_type = ?, model = ?, brand = ?, registration_number = ?, "
                + "manufacture_year = ?, price_per_day = ?, status = ?, description = ?, image = ? "
                + "WHERE vehicle_id = ?;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);

            // Thiết lập các tham số cho câu lệnh SQL
            ps.setString(1, vehicle.getVehicle_type());
            ps.setString(2, vehicle.getModel());
            ps.setString(3, vehicle.getBrand());
            ps.setString(4, vehicle.getRegistration_number());
            ps.setInt(5, vehicle.getManufacture_year());
            ps.setDouble(6, vehicle.getPrice_per_day());
            ps.setString(7, vehicle.getStatus());
            ps.setString(8, vehicle.getDescription());
            ps.setString(9, vehicle.getImg());
            ps.setInt(10, vehicle.getVehicle_id());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteVehicle(int vehicleId) {

        try {
            conn = new DBContext().getConnection();
            String query = "DELETE FROM vehicle WHERE vehicle_id = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, vehicleId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lấy tất cả các xe
//ket thuc ket noi
    private void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
