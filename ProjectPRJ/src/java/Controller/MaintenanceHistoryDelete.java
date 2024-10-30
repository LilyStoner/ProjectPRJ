package Controller;

import dal.MaintenanceHistoryDao;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "MaintenanceHistoryDelete", urlPatterns = {"/MaintenanceHistoryDelete"})

public class MaintenanceHistoryDelete extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String maintenanceIdStr = request.getParameter("maintenance_id");
        MaintenanceHistoryDao DAO = new MaintenanceHistoryDao();

        // Chuyển đổi và xóa
        int maintenanceId = Integer.parseInt(maintenanceIdStr);
        boolean isDeleted = DAO.deleteMaintenance(maintenanceId);

        // Thiết lập thông báo
        String message = isDeleted ? "Xóa lịch sử bảo trì thành công." : "Xóa lịch sử bảo trì thất bại.";
        request.setAttribute("message", message);

        // Chuyển hướng về trang MaintenanceHistoryController
        response.sendRedirect("MaintenanceHistoryController?vehicle_id=" + request.getParameter("vehicle_id"));

    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
// String vehicle_id = request.getParameter("vehicle_id");
//        String maintenance_date = request.getParameter("maintenance_date");
//        String description = request.getParameter("description");
//        String cost = request.getParameter("cost");
