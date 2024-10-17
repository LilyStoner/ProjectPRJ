/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

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
import model.LocalDateTimeOv;
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
    
    /**
     *
     * @param customerId
     * @param startDate
     * @param endDate
     * @param totalAmount
     * @param status
     * @param depositPaid
     * @param createdAt
     */
    
    public void addRentalOrder(Integer customerId, LocalDate startDate, LocalDate endDate, BigDecimal totalAmount, String status, Boolean depositPaid, LocalDateTimeOv createdAt) {
        int paid;
        if (depositPaid) {
             paid=1;
        }
        else paid=0;
        try {
            String sql = """
                         INSERT INTO [dbo].[RentalOrder] (customer_id, start_date, end_date, total_amount, status, deposit_paid, created_at)
                         VALUES ("""+customerId+", '"+startDate.toString()+"', '"+endDate+"', "+totalAmount+", '"+status+"', "+paid+", "+createdAt+");";
            connection.createStatement().execute(sql);
        } catch (Exception e) {
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
