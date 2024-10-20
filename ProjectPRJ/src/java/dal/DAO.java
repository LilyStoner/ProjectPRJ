/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Customer;
import model.OrderVehicle;
import model.RentalOrder;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import model.Vehicle;

/**
 *
 * @author ADMIN
 */
public class DAO extends DBContext {

    public List<Vehicle> getAllVehicles() {
        List<Vehicle> list = new ArrayList<>();
        try {
            String sql = "select* from Vehicle";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vehicle v = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
                list.add(v);
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Vehicle> getAllVehiclesByStatus(String status) {
        List<Vehicle> list = new ArrayList<>();
        try {
            String sql = "select* from Vehicle where status='" + status + "'";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vehicle v = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
                list.add(v);
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Vehicle getVehicleById(int id) {
        try {
            String sql = "select * from Vehicle where vehicle_id=" + id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Vehicle v = new Vehicle();
            while (rs.next()) {
                v = new Vehicle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
                return v;
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<RentalOrder> getAllContractOfUserByStatus(int usedID, String status) {
        String sql = "select * from RentalOrder join Customer on RentalOrder.customer_id=Customer.customer_id where status='" + status + "' and user_id=" + usedID;
        List<RentalOrder> list = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                RentalOrder ro = new RentalOrder(
                        rs.getInt("order_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null,
                        rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                        rs.getDouble("total_amount"),
                        rs.getString("status"),
                        rs.getInt("deposit_paid"),
                        rs.getDate("created_at") != null ? rs.getDate("created_at").toString() : null
                );
                list.add(ro);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public RentalOrder getLastOrderOfUserID(int id) {
        String sql = """
                 SELECT TOP 1 * 
                 FROM [dbo].[RentalOrder] 
                 JOIN Customer ON RentalOrder.customer_id = Customer.customer_id 
                 WHERE user_id = ? 
                 ORDER BY order_id DESC;
                 """;
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    RentalOrder ro = new RentalOrder(
                            rs.getInt("order_id"),
                            rs.getInt("customer_id"),
                            rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null,
                            rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                            rs.getDouble("total_amount"),
                            rs.getString("status"),
                            rs.getInt("deposit_paid"),
                            rs.getDate("created_at") != null ? rs.getDate("created_at").toString() : null
                    );
                    return ro;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
        return null;
    }

    /**
     *
     * @param customerId
     * @param startDate
     * @param endDate
     * @param totalAmount
     * @param status
     * @param depositPaid
     * @param createdAt
     * @return
     */
    public void addRentalOrder(Integer customerId, LocalDate startDate, LocalDate endDate, String totalAmount, String status, Boolean depositPaid, String name) throws SQLException {
        String sql = """
                 INSERT INTO [dbo].[RentalOrder] (customer_id, start_date, end_date, total_amount, status, deposit_paid, name, created_at )
                 VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE());
                 """;
        String sql1 = "delete from RentalOrder\n" + "where status='Waiting' and name is null";
        try (PreparedStatement pstm1 = connection.prepareStatement(sql1)) {
            int rowsDelete = pstm1.executeUpdate();
            if (rowsDelete >= 0) {
                System.out.println("Dữ liệu đã được xóa thành công.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa dữ liệu: " + e.getMessage());
        }
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);

            if (startDate == null || startDate.equals(LocalDate.MAX)) {
                pstmt.setNull(2, java.sql.Types.DATE);
            } else {
                pstmt.setDate(2, java.sql.Date.valueOf(startDate));
            }

            if (endDate == null || endDate.equals(LocalDate.MAX)) {
                pstmt.setNull(3, java.sql.Types.DATE);
            } else {
                pstmt.setDate(3, java.sql.Date.valueOf(endDate));
            }

            pstmt.setBigDecimal(4, new java.math.BigDecimal(totalAmount));
            pstmt.setString(5, status);
            pstmt.setBoolean(6, depositPaid != null && depositPaid);
            pstmt.setString(7, name);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Dữ liệu đã được thêm thành công.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm dữ liệu: " + e.getMessage());
        }
    }

    public void addOrderVehicle(Integer orderId, Integer vehicleId, LocalDate pickupDate, LocalDate returnDate) {
        String sql = """
                 INSERT INTO [dbo].[OrderVehicle] (order_id, vehicle_id, pickup_date, return_date)
                 VALUES (?, ?, ?, ?);
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, orderId, java.sql.Types.INTEGER);
            pstmt.setObject(2, vehicleId, java.sql.Types.INTEGER);

            if (pickupDate != null) {
                pstmt.setDate(3, java.sql.Date.valueOf(pickupDate));
            } else {
                pstmt.setNull(3, java.sql.Types.DATE);
            }

            if (returnDate != null) {
                pstmt.setDate(4, java.sql.Date.valueOf(returnDate));
            } else {
                pstmt.setNull(4, java.sql.Types.DATE);
            }

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Đơn thuê xe đã được thêm thành công.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm đơn thuê xe: " + e.getMessage());
        }
    }

    public void updateRentalOrder(Integer orderId, LocalDate startDate, LocalDate endDate, String totalAmount, String status, Boolean depositPaid, Integer vehicleID) {
        String sql = """
                 UPDATE [dbo].[RentalOrder]
                 SET start_date = ?, end_date = ?, total_amount = ?, status = ?, deposit_paid = ?, created_at = GETDATE()
                 WHERE order_id = ?;
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            if (startDate == null || startDate.equals(LocalDate.MAX)) {
                pstmt.setNull(1, java.sql.Types.DATE);
            } else {
                pstmt.setDate(1, java.sql.Date.valueOf(startDate));
            }

            if (endDate == null || endDate.equals(LocalDate.MAX)) {
                pstmt.setNull(2, java.sql.Types.DATE);
            } else {
                pstmt.setDate(2, java.sql.Date.valueOf(endDate));
            }

            pstmt.setBigDecimal(3, new java.math.BigDecimal(totalAmount));
            pstmt.setString(4, status);
            pstmt.setBoolean(5, depositPaid != null && depositPaid);

            pstmt.setInt(6, orderId);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Dữ liệu đã được cập nhật thành công.");
            } else {
                System.out.println("Không tìm thấy bản ghi để cập nhật.");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật dữ liệu: " + e.getMessage());
        }
        addOrderVehicle(orderId, vehicleID, startDate, endDate);
    }

    public Map<Integer, RentalOrder> Emp_getListOrders() {
        Map<Integer, RentalOrder> list = new HashMap<>();
        try {
            String sql = "select* from RentalOrder \n"
                    + "where status != 'waiting'\n"
                    + "order by created_at desc";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                RentalOrder ro = new RentalOrder();
                ro.setCreatedAt(rs.getString("created_at"));
                ro.setCustomerId(rs.getInt("customer_id"));
                ro.setDepositPaid(rs.getInt("deposit_paid"));
                ro.setEndDate(LocalDate.parse(rs.getString("end_date")));
                ro.setOrderId(rs.getInt("order_id"));
                ro.setStartDate(LocalDate.parse(rs.getString("start_date")));
                ro.setStatus(rs.getString("status"));
                ro.setTotalAmount(rs.getDouble("total_amount"));
                list.put(ro.getOrderId(), ro);
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Map<Integer, Customer> Emp_getListCustomers() {
        Map<Integer, Customer> list = new HashMap<>();
        try {
            String sql = "select* from Customer";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Customer c = new Customer();
                c.setAddress(rs.getString("address"));
                c.setCustomerId(rs.getInt("customer_id"));
                c.setDateOfBirth(rs.getDate("date_of_birth"));
                c.setDrivingLicenseNumber(rs.getString("driving_license_number"));
                c.setFullName(rs.getString("full_name"));
                c.setPhoneNumber(rs.getString("phone_number"));
                c.setUserId(rs.getInt("user_id"));
                list.put(c.getCustomerId(), c);
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public List<Vehicle> Emp_getVehicleInOrder(int order_id) {
        List<Vehicle> list = new ArrayList<Vehicle>();
        try {
            String sql = "Select* from Vehicle v\n"
                    + "where v.vehicle_id in (select ov.vehicle_id from OrderVehicle ov\n"
                    + "where ov.order_id = " + order_id + ")";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Vehicle v = new Vehicle();
                v.setVehicleId(rs.getInt("vehicle_id"));
                v.setVehicleType(rs.getString("vehicle_type"));
                v.setModel(rs.getString("model"));
                v.setBrand(rs.getString("brand"));
                v.setRegistrationNumber(rs.getString("registration_number"));
                v.setManufactureYear(rs.getInt("manufacture_year"));
                v.setPricePerDay(rs.getDouble("price_per_day"));
                v.setStatus(rs.getString("status"));
                v.setDescription(rs.getString("description"));
                v.setImage(rs.getString("image"));
                list.add(v);
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public Map<Integer, OrderVehicle> Emp_getOrderVehicles(int order_id) {
        Map<Integer, OrderVehicle> list = new HashMap<>();
        try {
            String sql = "select* from OrderVehicle where order_id = " + order_id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                OrderVehicle ov = new OrderVehicle();
                ov.setOrderId(order_id);
                ov.setOrderVehicleId(rs.getInt("order_vehicle_id"));
                ov.setPickupDate(LocalDate.parse(rs.getString("pickup_date")));
                ov.setReturnDate(LocalDate.parse(rs.getString("return_date")));
                ov.setVehicleId(rs.getInt("vehicle_id"));
                list.put(ov.getVehicleId(), ov);
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }

    public void Emp_updateOrderStatus(String status, int order_id) {
        String sql = """
                 Update RentalOrder
                 set status = ?
                 where order_id = ?
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, order_id);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public void Emp_updateVehicleStatus(String status, int id) {
        String sql = """
                 Update Vehicle
                 set status = ?
                 where vehicle_id = ?
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public void Emp_updateStartDate(int order_id) {
        String sql = """
                 Update RentalOrder
                 set start_date = GETDATE(),deposit_paid=1
                 where order_id = ?
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, order_id);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public void Emp_updateEndDate(int order_id) {
        String sql = """
                 Update RentalOrder
                                  set end_date = GETDATE()
                                  where order_id = ?
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, order_id);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        System.out.println(dao.getAllContractOfUserByStatus(1, "Pending"));
    }
}
