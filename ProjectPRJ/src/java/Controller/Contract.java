/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import static com.oracle.wls.shaded.org.apache.xpath.axes.HasPositionalPredChecker.check;
import java.time.temporal.ChronoUnit;
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
import model.Customer;
import model.OrderVehicle;
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
        if(session.getAttribute("username")==null) {
                response.sendRedirect("login");
                return;
            }
        int customerID = ((Customer)session.getAttribute("customer")).getUserId();
 Customer c = dao.getCustomerByID((String)session.getAttribute("username"));
 if(c.getDrivingLicenseNumber()==null||c.getDrivingLicenseNumber().isEmpty()||c.getDrivingLicenseNumber().isBlank()) {
            response.sendRedirect("profile");
            return;
        }
        String action = request.getParameter("action");

        String contractName = request.getParameter("contractName");
        if (contractName != null && !contractName.isEmpty()) {
            dao.addRentalOrder(customerID, LocalDate.MAX, LocalDate.MAX, "0.00", "Waiting", Boolean.FALSE, contractName);
        }

        String status = request.getParameter("status");

        if (status == null || status.isEmpty()) {
            status = "Waiting";
        }
        request.setAttribute("status", status);

        int vid = vehicleValid(request.getParameter("vehicleID"), dao.getAllVehicles());
        if(request.getParameter("vehicleID")==null||vid!=-1) {
            Vehicle v = dao.getVehicleById(vid);
        request.setAttribute("vehicle", v);
        String oid = request.getParameter("orderID");
        if (oid != null) {
            int orderID = -1;
            try {
                 orderID = Integer.parseInt(oid);
                 if(!dao.isARentalOrderOfCustomer(customerID, orderID)){  
                   response.sendRedirect("home");
             return;    
            }
            } catch (NumberFormatException e) {
                  response.sendRedirect("home");
             return;
            }
           
            RentalOrder ro = dao.getRentalOrderById(orderID);
            if(!status.equalsIgnoreCase(ro.getStatus())){
                response.sendRedirect("home");
             return;
            }
            request.setAttribute("ro", ro);
            if (action.equalsIgnoreCase("Delete")) {
                dao.deleteRentalOrder(customerID, orderID);
            }
            if (action.equalsIgnoreCase("View")) {
                request.getRequestDispatcher("viewContract").forward(request, response);
            }
            if (action.equalsIgnoreCase("Add")) {
                    if (!isVehicleInRentalOrder(ro, vid, dao)) {
                         boolean check = true;
                    if (ro.getDepositPaid() == 0) {
                        check = false;
                    }
                         if (ro.getStartDate() != null && ro.getEndDate() != null) {
                   
                        Double total = Double.parseDouble(ro.getTotalAmount()) + (-ChronoUnit.DAYS.between(ro.getEndDate(), ro.getStartDate()) + 1) * v.getPricePerDay();
                        dao.updateRentalOrder(ro.getOrderId(), ro.getStartDate(), ro.getEndDate(), String.format("%.2f", total), ro.getStatus(), check, vid);
                      
                    }
                         else{
                             dao.updateRentalOrder(ro.getOrderId(), ro.getStartDate(), ro.getEndDate(), ro.getTotalAmount(), ro.getStatus(), check, vid);
                         }
                           ro = dao.getRentalOrderById(orderID);
                        request.setAttribute("ro", ro);
                }
                request.getRequestDispatcher("viewContract").forward(request, response);
            }   
        }
        }
        else{
             response.sendRedirect("home");
             return;
        }
        List<RentalOrder> list = dao.getAllContractOfCustomerByStatus(customerID, status);
        request.setAttribute("list", list);
        request.getRequestDispatcher("contracts.jsp").forward(request, response);
        }
    

    public static void main(String[] args) throws SQLException {
        DAO dao = new DAO();
        RentalOrder ro = dao.getRentalOrderById(190);
        Double total = Double.valueOf(ro.getTotalAmount());
        Double total2 = 2000.02 * ((ChronoUnit.DAYS.between(ro.getEndDate(), ro.getStartDate())) + 1);
        System.out.println(String.format("%.2f", total2));
        dao.updateRentalOrder(ro.getOrderId(), ro.getStartDate(), ro.getEndDate(), String.format("%.2f", total), ro.getStatus(), true, null);

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

    private boolean isVehicleInRentalOrder(RentalOrder ro, int id, DAO dao) {
        for (OrderVehicle ov : dao.getAllOrderVehiclesByOrderId(ro.getOrderId())) {
            if (ov.getVehicleId() == id) {
                return true;
            }
        }
        return false;
    }
    
    private int vehicleValid(String vehicle, List<Vehicle> listVehicle) {
    if (vehicle == null) {
        return -1;
    }

    try {
        int id = Integer.parseInt(vehicle);
        for (Vehicle v : listVehicle) {
            if (v.getVehicleId() == id && !v.getStatus().equalsIgnoreCase("maintenance")) {
                return id;
            }
        }
    } catch (NumberFormatException e) {
        return -1;
    }

    return -1;
}
}
