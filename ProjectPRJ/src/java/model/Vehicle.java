/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author ADMIN
 */
public class Vehicle {
    private int vehicleId;
    private String vehicleType;
    private String model;
    private String brand;
    private String registrationNumber;
    private int manufactureYear;
    private double pricePerDay;
    private String status;
    private String description;
    private String image;

    // Constructor
    public Vehicle() {
    }

    public Vehicle(int vehicleId, String vehicleType, String model, String brand,
                   String registrationNumber, int manufactureYear, double pricePerDay,
                   String status, String description, String image) {
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.model = model;
        this.brand = brand;
        this.registrationNumber = registrationNumber;
        this.manufactureYear = manufactureYear;
        this.pricePerDay = pricePerDay;
        this.status = status;
        this.description = description;
        this.image = image;
    }

    // Getters and Setters
    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public void setManufactureYear(int manufactureYear) {
        this.manufactureYear = manufactureYear;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getImg() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
      public int getVehicle_id() {
        return vehicleId;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicleId = vehicle_id;
    }

    public String getVehicle_type() {
        return vehicleType;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicleType = vehicle_type;
    }
    
    public String getRegistration_number() {
        return registrationNumber;
    }
    
    public int getManufacture_year() {
        return manufactureYear;
    }
     public double getPrice_per_day() {
        return pricePerDay;
    }

    // Optional: Override toString() for easy printing
    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId=" + vehicleId +
                ", vehicleType='" + vehicleType + '\'' +
                ", model='" + model + '\'' +
                ", brand='" + brand + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", manufactureYear=" + manufactureYear +
                ", pricePerDay=" + pricePerDay +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
