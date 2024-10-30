/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class VehicleI {

    private String vehicle_type;
    private String model;
    private String brand;
    private String registration_number;
    private int manufacture_year;
    private double price_per_day;
    private String status;
    private String description;
    private String img;

    public VehicleI() {
    }

    public VehicleI(String vehicle_type, String model, String brand, String registration_number, int manufacture_year, double price_per_day, String status, String description, String img) {
        this.vehicle_type = vehicle_type;
        this.model = model;
        this.brand = brand;
        this.registration_number = registration_number;
        this.manufacture_year = manufacture_year;
        this.price_per_day = price_per_day;
        this.status = status;
        this.description = description;
        this.img = img;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
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

    public String getRegistration_number() {
        return registration_number;
    }

    public void setRegistration_number(String registration_number) {
        this.registration_number = registration_number;
    }

    public int getManufacture_year() {
        return manufacture_year;
    }

    public void setManufacture_year(int manufacture_year) {
        this.manufacture_year = manufacture_year;
    }

    public double getPrice_per_day() {
        return price_per_day;
    }

    public void setPrice_per_day(double price_per_day) {
        this.price_per_day = price_per_day;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "VehicleI{" + "vehicle_type=" + vehicle_type + ", model=" + model + ", brand=" + brand + ", registration_number=" + registration_number + ", manufacture_year=" + manufacture_year + ", price_per_day=" + price_per_day + ", status=" + status + ", description=" + description + ", img=" + img + '}';
    }

}
