
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
    
      public List<OrderVehicle> getAllOrderVehiclesByOrderId(int orderId) {
        List<OrderVehicle> orderVehicles = new ArrayList<>();
        String query = "SELECT * FROM OrderVehicle WHERE order_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                OrderVehicle orderVehicle = new OrderVehicle(
                resultSet.getInt("order_vehicle_id"),
                resultSet.getInt("order_id"),
                resultSet.getInt("vehicle_id"),
                resultSet.getDate("pickup_date") != null ? resultSet.getDate("pickup_date").toLocalDate() : null,
                resultSet.getDate("return_date") != null ? resultSet.getDate("return_date").toLocalDate() : null   
                );
                orderVehicles.add(orderVehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Xử lý ngoại lệ tùy thuộc vào nhu cầu
        }

        return orderVehicles;
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
     * mot hai ba bon
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
      public RentalOrder getRentalOrderById(int orderId) {
        RentalOrder rentalOrder = null;
        String sql = "SELECT * FROM RentalOrder WHERE order_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int paid=0;
                if (resultSet.getBoolean("deposit_paid")) {
                    paid=1;
                }
                rentalOrder = new RentalOrder(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                       resultSet.getDate("start_date") != null ? resultSet.getDate("start_date").toLocalDate() : null,
                    resultSet.getDate("end_date") != null ? resultSet.getDate("end_date").toLocalDate() : null,
                     resultSet.getDouble("total_amount"),
                        resultSet.getString("status"),
                        paid,
                    resultSet.getDate("created_at") != null ? resultSet.getDate("created_at").toString() : null,
                                                resultSet.getString("name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rentalOrder;
    }

      public void deleteOrderVehicle(int vehicleId, int orderId) {
          String SQL = "DELETE FROM OrderVehicle WHERE vehicle_id = ? AND order_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
             
            statement.setInt(1, vehicleId);
            statement.setInt(2, orderId);
            
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted>0) {
                System.out.println("Good");
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
    if(vehicleID!=null)  addOrderVehicle(orderId, vehicleID, null, null);
}
     public void deleteRentalOrder(int customerId, int orderId) {
        String sql = "delete from OrderVehicle where order_id = ? DELETE FROM RentalOrder WHERE order_id = ? AND customer_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, orderId);
            statement.setInt(2, orderId);
            statement.setInt(3, customerId);
            
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
        public List<RentalOrder> getAllRentalOrdersOfCustomer(int CustomerID){
        String sql = "SELECT * FROM RentalOrder WHERE customer_id = ?";
        List<RentalOrder> ro = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, CustomerID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int paid=0;
                if (resultSet.getBoolean("deposit_paid")) {
                    paid=1;
                }
                ro.add(new RentalOrder(
                        resultSet.getInt("order_id"),
                        resultSet.getInt("customer_id"),
                       resultSet.getDate("start_date") != null ? resultSet.getDate("start_date").toLocalDate() : null,
                    resultSet.getDate("end_date") != null ? resultSet.getDate("end_date").toLocalDate() : null,
                     resultSet.getDouble("total_amount"),
                        resultSet.getString("status"),
                        paid,
                    resultSet.getDate("created_at") != null ? resultSet.getDate("created_at").toString() : null,
                                                resultSet.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ro;
    }
    
    //check cac thu cac thu
     public boolean isARentalOrderOfCustomer(int CustomerID, int orderID){
         for (RentalOrder ro : getAllRentalOrdersOfCustomer(CustomerID)) {
             if (ro.getOrderId()==orderID) {
                 return true;
             }
         }
         return false;   
    }
     public List<Vehicle> listCarCanRentInRange(LocalDate pickDate, LocalDate returDate) {
         List<Vehicle> list = new ArrayList<>();
         try {
            String sql = "DECLARE @pick DATE, @return DATE\n" +
"SET @pick = '"+pickDate+"'                \n" +
"SET @return = '"+returDate+"'\n" +
"\n" +
"select * from Vehicle\n" +
"where vehicle_id not in (\n" +
"SELECT Distinct v.vehicle_id\n" +
"FROM Vehicle v \n" +
"JOIN OrderVehicle ov ON v.vehicle_id = ov.vehicle_id\n" +
"JOIN RentalOrder r ON r.order_id = ov.order_id\n" +
"where (r.status = N'on going' or r.status = N'confirmed') \n" +
"	and ((@return between r.[start_date] and r.end_date) \n" +
"	or (@pick between r.[start_date] and r.end_date)\n" +
"		or (r.[start_date] between @pick and @return) \n" +
"		or (r.end_date between @pick and @return)))";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
               int r = rs.getInt(1);
               list.add(getVehicleById(r));
            }
            st.close();
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return list;
    }
    //check cac thu cac thu

     

    public Map<Integer, RentalOrder> Emp_getListOrders() {
        Map<Integer, RentalOrder> list = new HashMap<>();
        try {
            String sql = "select* from RentalOrder \n"
                    + "where [status] != 'waiting'\n"
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
                try {
                    ov.setPickupDate(rs.getDate("pickup_date").toLocalDate());
                } catch (Exception e) {
                    ov.setPickupDate(null);
                }
                try {
                    ov.setReturnDate(rs.getDate("return_date").toLocalDate());
                } catch (Exception e) {
                    ov.setReturnDate(null);
                }
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

    public void Emp_updateOrderTotal(double total, int order_id) {
        String sql = """
                 update RentalOrder
                 set total_amount = ?
                 where order_id = ?
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, total);
            pstmt.setInt(2, order_id);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public void Emp_updateOrderStatus(String status, int order_id) {
        String sql = """
                 Update RentalOrder
                 set [status] = ?
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

    public void Emp_updateDeposit(int order_id, int x) {
        String sql = """
                 Update RentalOrder
                 set deposit_paid=?
                 where order_id = ?
                 """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, x);
            pstmt.setInt(2, order_id);
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

    public void Emp_updatePickupDate(int vehicle_id, int order_id) {
        String sql = """
                 update OrderVehicle
                 set pickup_date = GETDATE()
                 where vehicle_id = ?
                     and order_id =?""";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vehicle_id);
            pstmt.setInt(2, order_id);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public void Emp_updateReturnDate(int vehicle_id, int order_id) {
        String sql = """
                 update OrderVehicle
                 set return_date = GETDATE()
                 where vehicle_id = ?
                     and order_id =?""";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, vehicle_id);
            pstmt.setInt(2, order_id);
            int rowsUpdated = pstmt.executeUpdate();
        } catch (SQLException e) {
        }

    }

    public int Emp_checkConfirm(int order_id, int vehicle_id) {
        int r = -1;
        try {
            String sql = "DECLARE @pick Date, @return Date, @v_id int, @o_id int\n"
                    + "set @v_id =                      \n"+vehicle_id
                    + "set @o_id = \n"+order_id
                    + "\n"
                    + "select @pick = [start_date],@return = [end_date] from RentalOrder\n"
                    + "where order_id = @o_id\n"
                    + "select r.order_id from OrderVehicle o join RentalOrder r\n"
                    + "on o.order_id = r.order_id\n"
                    + "where (r.status = N'on going' or r.status = N'confirmed') \n"
                    + "	and ((@return between r.[start_date] and r.end_date) \n"
                    + "	or (@pick between r.[start_date] and r.end_date)\n"
                    + "		or (r.[start_date] between @pick and @return) \n"
                    + "		or (r.end_date between @pick and @return))\n"
                    + "	and o.vehicle_id = @v_id\n"
                    + "	and r.order_id != @o_id";
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                r = rs.getInt(1);
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }



 
    
    public static void main(String[] args) {
       DAO dao = new DAO();
        System.out.println(dao.getAllRentalOrdersOfCustomer(1));
    }
}
