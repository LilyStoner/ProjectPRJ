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
     * @throws java.sql.SQLException
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        DAO dao = new DAO();
        HttpSession session = request.getSession();
        int customerID = 1;
        String action = request.getParameter("action");
      
        String contractName = request.getParameter("contractName");
        if (contractName != null && !contractName.isEmpty() ) {
            dao.addRentalOrder(customerID, LocalDate.MAX, LocalDate.MAX, "0.00", "Waiting", Boolean.FALSE, contractName);
        }

        String status = request.getParameter("status");

        if (status == null || status.isEmpty()) {
            status = "Waiting";
        }
        request.setAttribute("status", status);
       
            
            int vid = vehicleValid(request.getParameter("vehicleID"), dao.getAllVehicles());
            Vehicle v = dao.getVehicleById(vid);
            request.setAttribute("vehicle", v);
            String oid = request.getParameter("orderID");
            if(oid!=null){
                int orderID = Integer.parseInt(oid);
                RentalOrder ro = dao.getRentalOrderById(orderID);
                request.setAttribute("ro", ro);
            if (action.equalsIgnoreCase("Delete")) {
                dao.deleteRentalOrder(customerID, orderID);
            }
            if (action.equalsIgnoreCase("View")) {
                request.getRequestDispatcher("viewContract").forward(request, response);
            }
            if (action.equalsIgnoreCase("Add")) {
                dao.addOrderVehicle(orderID, v.getVehicleId(), ro.getStartDate() , ro.getEndDate());
                request.getRequestDispatcher("viewContract").forward(request, response);
            }
        }
         List<RentalOrder> list = dao.getAllContractOfCustomerByStatus(customerID, status);
        request.setAttribute("list", list);
        
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

       private int vehicleValid(String vehicle,  List<Vehicle> listVehicle) {
        Boolean check = true;
        int id = -1;
        if (vehicle == null) {
            check = false;
        } else {
            try {
                id = Integer.parseInt(vehicle);
            } catch (NumberFormatException e) {
                check = false;
            }

            boolean found = false;
            for (Vehicle vehicle1 : listVehicle) {
                if (vehicle1.getVehicleId() == id) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                check = false;
            }
        }
        if (check==false) {
            return -1;
        }
        else return id;
    }
}
