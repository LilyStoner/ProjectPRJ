package model;

/**
 *
 * @author admin
 */
public class MaintenanceHistory {

    private int vehicle_id;
    private int maintenance_id; 
    private String maintenance_date;
    private String description;
    private double cost;

    // Constructor với tham số
    public MaintenanceHistory(int vehicle_id, int maintenance_id, String maintenance_date, String description, double cost) {
        this.vehicle_id = vehicle_id;
        this.maintenance_id = maintenance_id;
        this.maintenance_date = maintenance_date;
        this.description = description;
        this.cost = cost;
    }

    public MaintenanceHistory() {
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getMaintenance_id() {
        return maintenance_id;
    }

    public void setMaintenance_id(int maintenance_id) {
        this.maintenance_id = maintenance_id;
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

//    @Override
//    public String toString() {
//        return "MaintenanceHistory{" +
//                "vehicle_id=" + vehicle_id +
//                ", maintenance_id=" + maintenance_id +
//                ", maintenance_date='" + maintenance_date + '\'' +
//                ", description='" + description + '\'' +
//                ", cost=" + cost +
//                '}';
//    }
    @Override
    public String toString() {
        return "MaintenanceHistory{" + "vehicle_id=" + vehicle_id + ", maintenance_id=" + maintenance_id + ", maintenance_date=" + maintenance_date + ", description=" + description + ", cost=" + cost + '}';
    }
}
