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
import java.util.HashMap;
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
@WebServlet(name = "OrderDetail", urlPatterns = {"/Emp_OrderDetail"})
public class Emp_OrderDetail extends HttpServlet {

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
            try {
                HttpSession session = request.getSession();

                String up = request.getParameter("up");

                int order_id = Integer.parseInt(request.getParameter("id"));

                List<Integer> err_id = new ArrayList<>();
                List<String> err = new ArrayList<>();
                DAO dao = new DAO();
                Map<Integer, RentalOrder> listOrders = dao.Emp_getListOrders();

                boolean check = false;
                for (int id : listOrders.keySet()) {
                    if (id == order_id) {
                        check = true;
                        break;
                    }
                }
                if (!check) {
                    throw new Exception();
                }
                RentalOrder ro = listOrders.get(order_id);
                List<Vehicle> lv = dao.Emp_getVehicleInOrder(order_id);

                Customer c = dao.Emp_getListCustomers().get(ro.getCustomerId());
                Map<Integer, OrderVehicle> ov = dao.Emp_getOrderVehicles(order_id);

                if (ro.getDepositPaid() == 0 && !ro.getStatus().equalsIgnoreCase("completed") && !ro.getStatus().equalsIgnoreCase("cancelled")) {
                    err.add("Deposit fee not yet paid");
                }
                
                if(ro.getStatus().equalsIgnoreCase("pending")||ro.getStatus().equalsIgnoreCase("comfirmed")){
                    for(Vehicle v:lv){
                        if ((v.getStatus().equalsIgnoreCase("maintenance"))) {
                            err.add(v.getBrand() + " " + v.getModel()+" is under maintenance!");
                            err_id.add(v.getVehicleId());
                        }
                    }
                }    


                for (Vehicle v : lv) {
                    int check_id = dao.Emp_checkConfirm(order_id, v.getVehicleId());
                    if (check_id > 0 && ro.getStatus().equalsIgnoreCase("pending")) {
                        err_id.add(v.getVehicleId());
                        String error = "";
                        error += v.getVehicleType() + " " + v.getBrand() + " " + v.getModel();
                        error += " is not available between " + ro.getStartDate() + "";
                        error += " and " + ro.getEndDate() + "(Order ID: " + check_id + ")";
                        err.add(error);
                    }
                }
                if (ro.getStatus().equalsIgnoreCase("on going")) {
                    Boolean check_vehicle = false;
                    List<Vehicle> vehicles = dao.Emp_getVehicleInOrder(order_id);
                    double total = 0;
                    for (Vehicle v : vehicles) {
                        if (v.getStatus().equalsIgnoreCase("rented")) {
                            err.add("Return " + v.getBrand() + " " + v.getModel() + " to complete order!");
                            err_id.add(v.getVehicleId());
                            check_vehicle = true;
                        } else {
                            long daysBetween = 0;
                            if (ov.get(v.getVehicleId()).getReturnDate().compareTo(ro.getEndDate()) > 0) {
                                daysBetween = ChronoUnit.DAYS.between(ro.getEndDate(), ov.get(v.getVehicleId()).getReturnDate());
                                err.add(v.getBrand() + " " + v.getModel() + " has been lated for " + daysBetween + " days");
                            }

                        }
                    }

                }
                request.setAttribute("err_id", err_id);

                request.setAttribute("id", order_id);

                request.setAttribute("err", err);

                request.setAttribute("ov", ov);

                request.setAttribute("ro", ro);

                request.setAttribute("lv", lv);

                request.setAttribute("c", c);

                request.setAttribute("up", up);

                if (!check) {

                } else if (up != null) {
                    request.getRequestDispatcher("Emp_UpdateOrder").forward(request, response);
                } else {
                    request.getRequestDispatcher("Emp_OrderDetail.jsp").forward(request, response);
                }
            } catch (Exception e) {
                response.sendRedirect("Emp_ListOrder?");
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
