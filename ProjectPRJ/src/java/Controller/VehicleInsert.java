package Controller;

import dal.VehicleDao;
import model.VehicleI;
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
@WebServlet(name = "VehicleInsert", urlPatterns = {"/VehicleInsert"})

public class VehicleInsert extends HttpServlet {

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
        try {
            String vehicleType = request.getParameter("vehicle_type");
            String model = request.getParameter("model");
            String brand = request.getParameter("brand");
            String registrationNumber = request.getParameter("registration_number");

            // Kiểm tra và chuyển đổi năm sản xuất
            String manufactureYearStr = request.getParameter("manufacture_year");
            int manufactureYear = Integer.parseInt(manufactureYearStr); // có thể throw exception nếu null

            // Kiểm tra và chuyển đổi giá mỗi ngày
            String pricePerDayStr = request.getParameter("price_per_day");
            double pricePerDay = Double.parseDouble(pricePerDayStr); // có thể throw exception nếu null

            String status = request.getParameter("status");
            String description = request.getParameter("description");
            String img = request.getParameter("img");

            // Tạo đối tượng Vehicle
            VehicleI vehicle = new VehicleI(vehicleType, model, brand, registrationNumber, manufactureYear, pricePerDay, status, description, img);
            VehicleDao dao = new VehicleDao();
            dao.addVehicle(vehicle);

            response.sendRedirect("VehicleInsert.jsp");
        } catch (NumberFormatException e) {
            // Nếu có lỗi khi chuyển đổi, bạn có thể xử lý lỗi ở đây
            request.setAttribute("errorMessage", "Vui lòng nhập giá trị hợp lệ cho năm sản xuất và giá mỗi ngày.");
            request.getRequestDispatcher("VehicleInsert.jsp").forward(request, response);
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
