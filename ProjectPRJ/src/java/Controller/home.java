/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import dal.DAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import model.Vehicle;

/**
 *
 * @author ADMIN
 */
public class home extends HttpServlet {

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
        DAO dao = new DAO();
        List<Vehicle> listVehicle = dao.getAllVehicles();
        HttpSession session = request.getSession();
       session.setAttribute("customer", dao.getCustomerByID(2));
        
        
        List<String> allType = new ArrayList<>();
        String pick = request.getParameter("pickup_date");
        String re = request.getParameter("return_date");

        Set<String> uniqueTypes = new HashSet<>();
        for (Vehicle vehicle : listVehicle) {
            String type = vehicle.getVehicleType();
            if (uniqueTypes.add(type)) {
                allType.add(type);
            }
        }
        request.setAttribute("listType", allType);

        String type = request.getParameter("Type");
        if (type != null && !type.isEmpty() && allType.contains(type)) {
            Iterator<Vehicle> iterator = listVehicle.iterator();
            try {
                LocalDate pickupDate = LocalDate.parse(pick);
                LocalDate returnDate = LocalDate.parse(re);
                listVehicle = dao.listCarCanRentInRange(pickupDate, returnDate);
                iterator = listVehicle.iterator();
            } catch (Exception e) {
            }
            while (iterator.hasNext()) {
                Vehicle v = iterator.next();
                if (!v.getVehicleType().equalsIgnoreCase(type) || v.getStatus().equalsIgnoreCase("maintenance")) {
                    iterator.remove();
                }
            }
        }
        if (type!=null&&type.equalsIgnoreCase("all")) {
 Iterator<Vehicle> iterator = listVehicle.iterator();
            try {
                LocalDate pickupDate = LocalDate.parse(pick);
                LocalDate returnDate = LocalDate.parse(re);
                listVehicle = dao.listCarCanRentInRange(pickupDate, returnDate);
                iterator = listVehicle.iterator();
            } catch (Exception e) {
            }
            while (iterator.hasNext()) {
                Vehicle v = iterator.next();
                if (v.getStatus().equalsIgnoreCase("maintenance")) {
                    iterator.remove();
                }
            }
        }
        request.setAttribute("listVehicle", listVehicle);

        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    public static void main(String[] args) {
        DAO dao = new DAO();
        List<Vehicle> listVehicle = dao.getAllVehicles();
        for (int i = 0; i < listVehicle.size(); i++) {
            System.out.println(listVehicle.get(i));
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
