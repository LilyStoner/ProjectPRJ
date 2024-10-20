/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class RentalOrder {

    private int orderId;
    private int customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalAmount;
    private String status;
    private int depositPaid;
    private String createdAt;
    private String name;

    // Constructor
    public RentalOrder() {
    }

    public RentalOrder(int orderId, int customerId, LocalDate startDate, LocalDate endDate, Double totalAmount, String status, int depositPaid, String createdAt, String name) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.depositPaid = depositPaid;
        this.createdAt = createdAt;
        this.name=name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public String getTotalAmount() {
        return String.format("%.2f", totalAmount);
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(int depositPaid) {
        this.depositPaid = depositPaid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "RentalOrder{" + "orderId=" + orderId + ", customerId=" + customerId + ", startDate=" + startDate + ", endDate=" + endDate + ", totalAmount=" + totalAmount + ", status=" + status + ", depositPaid=" + depositPaid + ", createdAt=" + createdAt + ", name=" + name + '}';
    }

            

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }
}