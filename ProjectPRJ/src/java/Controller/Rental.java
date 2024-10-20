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
import java.util.List;
import model.Vehicle;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "Rental", urlPatterns = {"/Rental"})
public class Rental extends HttpServlet {

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
        String vehicle = request.getParameter("vehicleId");
        int id = vehicleValid(vehicle, listVehicle);

        if (id==-1) {
            response.sendRedirect("home");
            return;
        }
        HttpSession session = request.getSession();
        request.setAttribute("listVehicle", listVehicle);
        request.setAttribute("vehicle", dao.getVehicleById(id));
        request.getRequestDispatcher("rental.jsp").forward(request, response);

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
