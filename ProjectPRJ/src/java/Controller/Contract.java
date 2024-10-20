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
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.RentalOrder;
import model.Vehicle;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Contract", urlPatterns = {"/Contract"})
public class Contract extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        DAO dao = new DAO();
        HttpSession session = request.getSession();
        int customerID = 1;
        String action = request.getParameter("action");
      
        String contractName = request.getParameter("contractName");
        if (contractName != null) {
            dao.addRentalOrder(customerID, LocalDate.MAX, LocalDate.MAX, "0.00", "Waiting", Boolean.FALSE, contractName);
        }

        String status = request.getParameter("status");

        if (status == null || status.isEmpty()) {
            status = "Waiting";
        }
        request.setAttribute("status", status);
        List<RentalOrder> list = dao.getAllContractOfCustomerByStatus(1, status);
        request.setAttribute("list", list);

        try {
            Vehicle v = dao.getVehicleById(Integer.parseInt(request.getParameter("vehicleID")));
            request.setAttribute("vehicle", v);
              int orderID = Integer.parseInt(request.getParameter("orderID"));
            if (action.equalsIgnoreCase("Delete")) {
                dao.deleteRentalOrder(customerID, orderID);
            }
            if (action.equalsIgnoreCase("Add")) {
                RentalOrder ro = dao.getRentalOrderById(orderID);
                dao.addOrderVehicle(orderID, v.getVehicleId(), ro.getStartDate() , ro.getEndDate());
                request.setAttribute("ro", ro);
                request.getRequestDispatcher("viewContract").forward(request, response);
            }
        } catch (NumberFormatException e) {
        }
        
        
        request.getRequestDispatcher("contracts.jsp").forward(request, response);
    }

    public static void main(String[] args) throws SQLException {
        DAO dao = new DAO();
        System.out.println(dao.getRentalOrderById(91));
        

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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Contract.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Contract.class.getName()).log(Level.SEVERE, null, ex);
        }
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
