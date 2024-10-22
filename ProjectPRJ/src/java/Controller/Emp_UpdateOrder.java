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
import java.time.temporal.ChronoUnit;
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
    int i=0;
    String NamXanl = "";
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
            List<String> err = (List<String>)request.getAttribute("err");
            List<Integer> err_id = (List<Integer>)request.getAttribute("err_id");
            
            
            String update = (String)request.getAttribute("up");
            HttpSession session = request.getSession();
            
            try {
                int id = (Integer) request.getAttribute("id");
                if(update==null){
                    throw new Exception();
                }
                int vehicle_id = 0;
                
                if(listOrders.get(id).getStatus().equalsIgnoreCase("on going")&&!err.isEmpty()&&update.contains("return")){
                    
                    vehicle_id = Integer.parseInt(update.split(" ")[2]);
                    update=update.split(" ")[0];
                }
                
                
                List<Vehicle> listVehicles = dao.Emp_getVehicleInOrder(id);
                if (update.equalsIgnoreCase("cancel")) {
                    dao.Emp_updateOrderStatus("cancelled", id);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                } else if (update.equalsIgnoreCase("confirm")) {
                        if(err_id.size()>0){
                            response.sendRedirect("Emp_OrderDetail?id="+id);
                        }else{
                        dao.Emp_updateOrderStatus("confirmed", id);
                        response.sendRedirect("Emp_OrderDetail?id=" + id);
                        }
                } else if (update.equalsIgnoreCase("on going")) {
                    for (Vehicle v : listVehicles) {
                        dao.Emp_updateVehicleStatus("rented", v.getVehicleId());
                        dao.Emp_updatePickupDate(v.getVehicleId(),id);
                    }
                    dao.Emp_updateOrderStatus("on going", id);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                } else if (update.equalsIgnoreCase("completed")) {
                    dao.Emp_updateOrderStatus("completed", id);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                }else if(update.equalsIgnoreCase("deposited")&&!listOrders.get(id).getStatus().equalsIgnoreCase("completed")&&!listOrders.get(id).getStatus().equalsIgnoreCase("cancelled")){
                    dao.Emp_updateDeposit(id,1);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                }else if(update.equalsIgnoreCase("undeposited")&&listOrders.get(id).getStatus().equalsIgnoreCase("confirmed")){
                    dao.Emp_updateDeposit(id,0);
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                }else if(update.equalsIgnoreCase("return")){
                    
                    dao.Emp_updateVehicleStatus("available", vehicle_id);
                    
                    dao.Emp_updateReturnDate(vehicle_id,id);
                    double total=0;
                    boolean check = true;
                    listVehicles=dao.Emp_getVehicleInOrder(id);
                    for(Vehicle v:listVehicles){
                        if(v.getStatus().equalsIgnoreCase("rented")){
                            check = false;
                            break;
                        }
                        RentalOrder ro = dao.Emp_getListOrders().get(id);
                        OrderVehicle ov = dao.Emp_getOrderVehicles(id).get(v.getVehicleId());
                        total += (ChronoUnit.DAYS.between(ro.getStartDate(),ov.getReturnDate() )+1)*v.getPricePerDay(); 
                    }
                    if(check){
                        dao.Emp_updateOrderTotal(total, id);
                    }
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                }
                else {
                    response.sendRedirect("Emp_OrderDetail?id=" + id);
                }
            } catch (Exception e) {
                response.sendRedirect("Emp_ListOrder");
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
