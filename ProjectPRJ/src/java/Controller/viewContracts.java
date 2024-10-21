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
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import model.OrderVehicle;
import model.RentalOrder;
import model.Vehicle;

/**
 *
 * @author ADMIN
 */
@WebServlet(name="viewContracts", urlPatterns={"/viewContract"})
public class viewContracts extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        DAO dao = new DAO();
        String status = request.getParameter("status");
        request.setAttribute("status", status);
        RentalOrder ro = (RentalOrder) request.getAttribute("ro");
        if(ro==null ) ro = dao.getRentalOrderById(Integer.parseInt(request.getParameter("orderremove")));
        String action = request.getParameter("action");
        if(action.equalsIgnoreCase("remove")) {
                            int vehicleremomve = Integer.parseInt(request.getParameter("vehicleremove"));
                            Vehicle v = dao.getVehicleById(vehicleremomve);
            if (isVehicleInRentalOrder(ro, vehicleremomve, dao)) {
                         boolean check = true;
                    if (ro.getDepositPaid() == 0) {
                        check = false;
                    }
                         if (ro.getStartDate() != null && ro.getEndDate() != null) {
                   
                        Double total = Double.parseDouble(ro.getTotalAmount()) - (-ChronoUnit.DAYS.between(ro.getEndDate(), ro.getStartDate()) + 1) * v.getPricePerDay();
                        dao.updateRentalOrder(ro.getOrderId(), ro.getStartDate(), ro.getEndDate(), String.format("%.2f", total), ro.getStatus(), check, null);
                    }
                         dao.deleteOrderVehicle(vehicleremomve, ro.getOrderId());
                }
        }
        HttpSession session = request.getSession();
        List<OrderVehicle> ov = dao.getAllOrderVehiclesByOrderId(ro.getOrderId());
        List<Vehicle> listVehicle = new ArrayList<>();
        for (OrderVehicle orderVehicle : ov) {
            listVehicle.add(dao.getVehicleById(orderVehicle.getVehicleId()));
        }
        request.setAttribute("vList", listVehicle);
        session.setAttribute("ro", ro);
        request.setAttribute("ro", ro);
        request.getRequestDispatcher("viewContract.jsp").forward(request, response);
    } 
    public static void main(String[] args) {
        
    }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
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
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

      private boolean isVehicleInRentalOrder(RentalOrder ro, int id, DAO dao) {
        for (OrderVehicle ov : dao.getAllOrderVehiclesByOrderId(ro.getOrderId())) {
            if (ov.getVehicleId() == id) {
                return true;
            }
        }
        return false;
    }

}
