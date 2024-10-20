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
@WebServlet(name="OrderDetail", urlPatterns={"/Emp_OrderDetail"})
public class Emp_OrderDetail extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
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
                
                int order_id = Integer.parseInt(request.getParameter("id"));
                
                
                List<String> err = new ArrayList<>();
                DAO dao = new DAO();
                Map<Integer,RentalOrder> listOrders = dao.Emp_getListOrders();
                Boolean reload = (Boolean)request.getAttribute("reload");
                if(reload==null){
                    reload=false;
                }
                
                boolean check =false;
                for(int id:listOrders.keySet()){
                    if(id==order_id){
                        check=true;
                        break;
                    }
                }
                if(!check){
                    throw new Exception();
                }
                RentalOrder ro = listOrders.get(order_id);
                List<Vehicle> lv = dao.Emp_getVehicleInOrder(order_id);
                Customer c = dao.Emp_getListCustomers().get(ro.getCustomerId());
                Map<Integer,OrderVehicle> ov = dao.Emp_getOrderVehicles(order_id);
                
                for(Vehicle v:lv){
                    if(!v.getStatus().equalsIgnoreCase("Available")&&ro.getStatus().equalsIgnoreCase("pending")){
                        err.add(String.valueOf(v.getVehicleType())+" "+v.getBrand()+" "+v.getModel()+" is on "+v.getStatus());
                    }
                }
                if(reload&&err.size()==0){
                    Boolean ok = true;
                    request.setAttribute("ok", ok);
                    request.getRequestDispatcher("Emp_UpdateOrder").forward(request, response);
                }
                
                session.setAttribute("id", order_id);
                request.setAttribute("err", err);
                request.setAttribute("ov", ov);
                request.setAttribute("ro", ro);
                request.setAttribute("lv", lv);
                request.setAttribute("c", c);
                request.getRequestDispatcher("Emp_OrderDetail.jsp").forward(request, response);
                
                
                
               
                
            } catch (Exception e) {
                response.sendRedirect("Emp_ListOrder");
            }
        }
        
        
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

}
