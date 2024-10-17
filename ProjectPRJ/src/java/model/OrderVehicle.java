/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.time.LocalDate;

/**
 *
 * @author ADMIN
 */
public class OrderVehicle {

    private int orderVehicleId;
    private Integer orderId;
    private Integer vehicleId;
    private LocalDate pickupDate;
    private LocalDate returnDate;

    // Constructor
    public OrderVehicle() {
    }

    public OrderVehicle(int orderVehicleId, Integer orderId, Integer vehicleId, LocalDate pickupDate, LocalDate returnDate) {
        this.orderVehicleId = orderVehicleId;
        this.orderId = orderId;
        this.vehicleId = vehicleId;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public int getOrderVehicleId() {
        return orderVehicleId;
    }

    public void setOrderVehicleId(int orderVehicleId) {
        this.orderVehicleId = orderVehicleId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Integer vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    // Override toString() method for debugging
    @Override
    public String toString() {
        return "OrderVehicle{" +
                "orderVehicleId=" + orderVehicleId +
                ", orderId=" + orderId +
                ", vehicleId=" + vehicleId +
                ", pickupDate=" + pickupDate +
                ", returnDate=" + returnDate +
                '}';
    }
}