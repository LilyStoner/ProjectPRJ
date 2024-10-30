/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dal.MaintenanceHistoryDao;
import model.MaintenanceHistoryI;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author admin
 */
@WebServlet(name = "MaintenanceHistoryInsert", urlPatterns = {"/MaintenanceHistoryInsert"})

public class MaintenanceHistoryInsert extends HttpServlet {

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
        try {
            response.setContentType("text/html;charset=UTF-8");
            String vehicle_id = request.getParameter("vehicle_id");
            String maintenance_date = request.getParameter("maintenance_date");
            String description = request.getParameter("description");
            String cost = request.getParameter("cost");

            // Chuyển các giá trị này đến DAO để insert vào database
            MaintenanceHistoryI maintenanceHistory = new MaintenanceHistoryI(
                    Integer.parseInt(vehicle_id),
                    maintenance_date,
                    description,
                    Double.parseDouble(cost)
            );

            MaintenanceHistoryDao dao = new MaintenanceHistoryDao();
            dao.insertMaintenanceHistory(maintenanceHistory);

            // Đưa các tham số trở lại request để hiển thị trên JSP
            request.setAttribute("vehicle_id", vehicle_id);
            request.setAttribute("maintenance_date", maintenance_date);
            request.setAttribute("description", description);
            request.setAttribute("cost", cost);

            // Chuyển hướng đến trang MaintenanceInsert.jsp mà không làm mất các tham số
            request.getRequestDispatcher("MaintenanceInsert.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Nếu có lỗi khi chuyển đổi, bạn có thể xử lý lỗi ở đây
            request.setAttribute("errorMessage", "Vui lòng nhập giá trị hợp lệ cho năm sản xuất và giá mỗi ngày.");
            request.getRequestDispatcher("MaintenanceInsert.jsp").forward(request, response);
        }
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
