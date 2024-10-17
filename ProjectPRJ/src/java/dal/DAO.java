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
import model.Vehicle;


/**
 *
 * @author ADMIN
 */
public class DAO extends DBContext{
    
    public List<Vehicle> getAllVehicles(){
     List<Vehicle> list = new ArrayList<>();
        try {
            String sql ="select* from Vehicle";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
                Vehicle v = new Vehicle(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
                list.add(v);
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
     return list;
    }
    
    public Vehicle getVehicleById(int id){
        try {
            String sql ="select * from Vehicle where vehicle_id="+id;
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            Vehicle v = new Vehicle();
            while(rs.next()){
                v = new Vehicle(rs.getInt(1),rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8), rs.getString(9), rs.getString(10));
                 return v;
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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
    
  public void addRentalOrder(Integer customerId, LocalDate startDate, LocalDate endDate, String totalAmount, String status, Boolean depositPaid) {
    String sql = """
                 INSERT INTO [dbo].[RentalOrder] (customer_id, start_date, end_date, total_amount, status, deposit_paid, created_at)
                 VALUES (?, ?, ?, ?, ?, ?, GETDATE());
                 """;

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

        int rowsInserted = pstmt.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("Dữ liệu đã được thêm thành công.");
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi thêm dữ liệu: " + e.getMessage());
    }
}
     public void addOrderVehicle( Integer orderId, Integer vehicleId, LocalDate pickupDate, LocalDate returnDate) {
        try {
            String sql = """
                         INSERT INTO [dbo].[OrderVehicle] (order_id, vehicle_id, pickup_date, return_date)
                         VALUES ("""+orderId+", "+vehicleId+", '"+pickupDate+"', '"+returnDate+"');";
            connection.createStatement().execute(sql);
        } catch (Exception e) {
        }
    }

    
    public static void main(String[] args) {
       
    }
}
