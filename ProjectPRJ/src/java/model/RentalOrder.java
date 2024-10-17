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
    private Integer customerId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal totalAmount;
    private String status;
    private Boolean depositPaid;
    private LocalDateTimeOv createdAt;

    // Constructor
    public RentalOrder() {
    }

    public RentalOrder(int orderId, Integer customerId, LocalDate startDate, LocalDate endDate, BigDecimal totalAmount, String status, Boolean depositPaid, LocalDateTimeOv createdAt) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalAmount = totalAmount;
        this.status = status;
        this.depositPaid = depositPaid;
        this.createdAt = createdAt;
    }

    // Getters and setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }


    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDepositPaid() {
        return depositPaid;
    }

    public void setDepositPaid(Boolean depositPaid) {
        this.depositPaid = depositPaid;
    }

    public LocalDateTimeOv getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTimeOv createdAt) {
        this.createdAt = createdAt;
    }

    // Override toString() method for debugging
    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", totalAmount=" + totalAmount +
                ", status='" + status + '\'' +
                ", depositPaid=" + depositPaid +
                ", createdAt=" + createdAt +
                '}';
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
}