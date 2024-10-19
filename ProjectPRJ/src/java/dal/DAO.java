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
    
    public List<Vehicle> getAllVehiclesByStatus(String status){
     List<Vehicle> list = new ArrayList<>();
        try {
            String sql ="select* from Vehicle where status='"+status+"'";
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
    
    public List<RentalOrder> getAllContractOfCustomerByStatus(int customerID, String status) {
        String sql = "select * from RentalOrder join Customer on RentalOrder.customer_id=Customer.customer_id where status='"+status+"' and user_id="+customerID;
        List<RentalOrder> list = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
            RentalOrder ro = new RentalOrder(
                    rs.getInt("order_id"),
                    rs.getInt("customer_id"),
                    rs.getDate("start_date") != null ? rs.getDate("start_date").toLocalDate() : null,
                    rs.getDate("end_date") != null ? rs.getDate("end_date").toLocalDate() : null,
                    rs.getDouble("total_amount"),
                    rs.getString("status"),
                    rs.getInt("deposit_paid"),
                    rs.getDate("created_at") != null ? rs.getDate("created_at").toString() : null,
                    rs.getString("name")
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
    
    public RentalOrder getLastOrderOfCustomerID(int id) {
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
                    rs.getDate("created_at") != null ? rs.getDate("created_at").toString() : null,
                    rs.getString("name")
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
     * @param name
     * @param createdAt
     * @return 
     */
    
  public void addRentalOrder(Integer customerId, LocalDate startDate, LocalDate endDate, String totalAmount, String status, Boolean depositPaid, String name) throws SQLException {
    String sql = """
                 INSERT INTO [dbo].[RentalOrder] (customer_id, start_date, end_date, total_amount, status, deposit_paid, name, created_at )
                 VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE());
                 """;
   String sql1="delete from RentalOrder\n" +"where status='Waiting' and name is null";
      try(PreparedStatement pstm1 = connection.prepareStatement(sql1)) {
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
     public void deleteRentalOrder(int customerId, int orderId) {
        String sql = "DELETE FROM RentalOrder WHERE order_id = ? AND customer_id = ?";
    
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            statement.setInt(2, customerId);
            
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Rental order deleted successfully.");
            } else {
                System.out.println("No rental order found with the specified order ID and customer ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  
 
    
    public static void main(String[] args) {
       DAO dao = new DAO();
    }
}
