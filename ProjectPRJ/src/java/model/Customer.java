/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ADMIN
 */
public class Customer {

     int customerId;
     Integer userId;
     String fullName;
     String phoneNumber;
     String address;
     String drivingLicenseNumber;
     Date dateOfBirth;

    // Constructor
    public Customer() {
    }

    public Customer(int customerId, Integer userId, String fullName, String phoneNumber, String address, String drivingLicenseNumber, Date dateOfBirth) {
        this.customerId = customerId;
        this.userId = userId;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.dateOfBirth = dateOfBirth;
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    // Override toString() method for debugging
    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", userId=" + userId +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}