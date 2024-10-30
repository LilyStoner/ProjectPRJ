/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dal.VehicleDao;
import model.Vehicle;
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
@WebServlet(name = "VehicleUpdate", urlPatterns = {"/VehicleUpdate"})

public class VehicleUpdate extends HttpServlet {

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
        String vehicleIdParam = request.getParameter("vehicle_id");
        String vehicleType = request.getParameter("vehicle_type");
        String model = request.getParameter("model");
        String brand = request.getParameter("brand");
        String registrationNumber = request.getParameter("registration_number");
        String manufactureYearParam = request.getParameter("manufacture_year");
        String pricePerDayParam = request.getParameter("price_per_day");
        String status = request.getParameter("status");
        String description = request.getParameter("description"); // Cập nhật thêm thuộc tính mô tả
        String img = request.getParameter("img");

        // Chuyển đổi ID, năm sản xuất và giá tiền
        int vehicleId = Integer.parseInt(vehicleIdParam);
        int manufactureYear = Integer.parseInt(manufactureYearParam);
        double pricePerDay = Double.parseDouble(pricePerDayParam);

        // Tạo đối tượng Vehicle
        Vehicle vehicle = new Vehicle(vehicleId, vehicleType, model, brand, registrationNumber, manufactureYear, pricePerDay, status, description, img);

        // Cập nhật thông tin xe trong cơ sở dữ liệu
        VehicleDao vehicleDao = new VehicleDao();
        boolean isUpdated = vehicleDao.updateVehicle(vehicle);

        if (isUpdated) {
            request.setAttribute("message", "Cập nhật thành công!");
        } else {
            request.setAttribute("message", "Cập nhật không thành công, vui lòng thử lại.");
        }

        // Lưu thông tin xe vào request để hiển thị lại trong form
        request.setAttribute("vehicle", vehicle);

        // Chuyển tiếp đến trang cập nhật với thông báo
        request.getRequestDispatcher("VehicleUpdate.jsp").forward(request, response);

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
