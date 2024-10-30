/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author admin
 */
public class MaintenanceHistoryI {

    private int vehicle_id;
    private String maintenance_date;
    private String description;
    private double cost;

    public MaintenanceHistoryI() {
    }

    public MaintenanceHistoryI(int vehicle_id, String maintenance_date, String description, double cost) {
        this.vehicle_id = vehicle_id;
        this.maintenance_date = maintenance_date;
        this.description = description;
        this.cost = cost;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getMaintenance_date() {
        return maintenance_date;
    }

    public void setMaintenance_date(String maintenance_date) {
        this.maintenance_date = maintenance_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "MaintenanceHistoryI{" + "vehicle_id=" + vehicle_id + ", maintenance_date=" + maintenance_date + ", description=" + description + ", cost=" + cost + '}';
    }

}
