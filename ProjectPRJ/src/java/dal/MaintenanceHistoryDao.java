package dal;

import model.MaintenanceHistory;
import model.MaintenanceHistoryI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class MaintenanceHistoryDao extends DBContext{

    Connection conn = null; // Kết nối với SQL Server
    PreparedStatement ps = null; // Ném câu lệnh query sang SQL Server
    ResultSet rs = null; // Nhận kết quả trả về

    public List<MaintenanceHistory> getMaintenanceHistoryByVehicleId(String vehicle_id) {
        List<MaintenanceHistory> maintenanceHistoryList = new ArrayList<>();
        String query = "SELECT * FROM MaintenanceHistory WHERE vehicle_id = ?";

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, Integer.parseInt(vehicle_id));
            rs = ps.executeQuery(); // Thực hiện câu lệnh

            while (rs.next()) { // Duyệt qua tất cả các kết quả
                // Tạo đối tượng MaintenanceHistory từ kết quả và thêm vào danh sách
                MaintenanceHistory maintenanceHistory = new MaintenanceHistory(
                        rs.getInt("vehicle_id"),
                        rs.getInt("maintenance_id"),
                        rs.getString("maintenance_date"),
                        rs.getString("description"),
                        rs.getDouble("cost")
                );
                maintenanceHistoryList.add(maintenanceHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In ra thông tin lỗi
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return maintenanceHistoryList; // Trả về danh sách MaintenanceHistory
    }

    public boolean insertMaintenanceHistory(MaintenanceHistoryI maintenanceHistoryI) {
        String query = "INSERT INTO MaintenanceHistory(vehicle_id, maintenance_date, description, cost) VALUES (?, ?, ?, ?)";
        try {
            conn = new DBContext().getConnection(); // Kết nối database
            ps = conn.prepareStatement(query); // Chuẩn bị câu lệnh SQL

            // Thiết lập các tham số cho câu lệnh SQL
            ps.setInt(1, maintenanceHistoryI.getVehicle_id());
            ps.setString(2, maintenanceHistoryI.getMaintenance_date());
            ps.setString(3, maintenanceHistoryI.getDescription());
            ps.setDouble(4, maintenanceHistoryI.getCost());

            int result = ps.executeUpdate(); // Thực hiện câu lệnh
            return result > 0; // Kiểm tra kết quả
        } catch (SQLException e) {
            e.printStackTrace(); // In ra thông tin lỗi
        } finally {
            try {
                if (ps != null) {
                    ps.close(); // Đóng PreparedStatements
                }
                if (conn != null) {
                    conn.close(); // Đóng Connection
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean deleteMaintenance(int maintenanceId) {
        String query = "DELETE FROM MaintenanceHistory WHERE maintenance_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(query);
            ps.setInt(1, maintenanceId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
