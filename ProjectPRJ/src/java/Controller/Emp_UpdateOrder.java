/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import model.Customer;
import model.OrderVehicle;
import model.RentalOrder;
import model.Vehicle;

/**
 *
 * @author Asus
 */
@WebServlet(name = "Emp_UpdateOrder", urlPatterns = {"/Emp_UpdateOrder"})
public class Emp_UpdateOrder extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            DAO dao = new DAO();
            Map<Integer, RentalOrder> listOrders = dao.Emp_getListOrders();

            String update = request.getParameter("up");
            HttpSession session = request.getSession();
            Boolean ok = (Boolean) request.getAttribute("ok");
            if (ok == null) {
                ok = false;
            }

            try {
                int id = (Integer) session.getAttribute("id");
                List<Vehicle> listVehicles = dao.Emp_getVehicleInOrder(id);
                if (update.equalsIgnoreCase("cancle")) {
                    if (listOrders.get(id).getStatus().equalsIgnoreCase("confirmed")) {
                        for (Vehicle v : listVehicles) {
                            dao.Emp_updateVehicleStatus("Available", v.getVehicleId());
                        }
                    }
                    dao.Emp_updateOrderStatus("cancelled", id);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                }
                else if (update.equalsIgnoreCase("confirm")) {
                    if (ok) {
                        dao.Emp_updateOrderStatus("confirmed", id);
                        for (Vehicle v : listVehicles) {
                            dao.Emp_updateVehicleStatus("rented", v.getVehicleId());
                        }
                        response.sendRedirect("Emp_OrderDetail?id=" + id);
                    } else {
                        Boolean reload = true;
                        request.setAttribute("reload", reload);
                        request.getRequestDispatcher("Emp_OrderDetail?id=" + id).forward(request, response);
                    }
                }
                else if (update.equalsIgnoreCase("on going")) {
                    for (Vehicle v : listVehicles) {
                            dao.Emp_updateVehicleStatus("rented", v.getVehicleId());
                    }
                    
                    Date now = new Date();
                    dao.Emp_updateStartDate(id);
                    dao.Emp_updateOrderStatus("on+going", id);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                }
                else if (update.equalsIgnoreCase("completed")) {
                    for (Vehicle v : listVehicles) {
                            dao.Emp_updateVehicleStatus("Available", v.getVehicleId());
                    }
                    dao.Emp_updateEndDate(id);
                    dao.Emp_updateOrderStatus("completed", id);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                    
                }
                else{
                response.sendRedirect("Emp_OrderDetail?id=" + id);
                }
            } catch (Exception e) {
                //response.sendRedirect("Emp_ListOrder");
            }
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
