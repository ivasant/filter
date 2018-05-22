/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parts.servlets;

import com.mycompany.parts.db.DBProvider;
import com.mycompany.parts.filter.Filter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author antonina
 */
public class FilterServlet extends HttpServlet {

    DBProvider db = null;

    String pnumber;
    String pname;
    String vendor;
    Integer qty;
    Date shippedDateAfter;
    Date shippedDateBefore;
    Date receiveDateAfter;
    Date receiveDateBefore;
    int sortField;
    int sortOrder;
    int pageNumber;
    int itemsPerPage;
    int itemsCount;

    public FilterServlet() throws NamingException, SQLException, Exception {
        db = new DBProvider();
    }

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

        getRequestParams(request);

        Filter filter = new Filter(db.con, pnumber, pname, vendor, qty,
                shippedDateAfter, shippedDateBefore, receiveDateAfter,
                receiveDateBefore, sortField, sortOrder, pageNumber, itemsPerPage);

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            String jsonString = filter.getData();

            out.print(jsonString);
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

    private void getRequestParams(HttpServletRequest request) {

        pnumber = request.getParameter("pnumber");
        pname = request.getParameter("pname");
        vendor = request.getParameter("vendor");

        String q = request.getParameter("qty");
        try {
            qty = Integer.parseUnsignedInt(q);
        } catch (NumberFormatException ex) {
            qty = null;
        }

        shippedDateAfter = null;
        String s = request.getParameter("shipped_date_after");

        if (s != null) {
            try {
                long time = Long.parseLong(s);
                shippedDateAfter = new Date();
                shippedDateAfter.setTime(time);
            } catch (NumberFormatException ex) {
            }
        }

        shippedDateBefore = null;
        s = request.getParameter("shipped_date_before");

        if (s != null) {
            try {
                long time = Long.parseLong(s);
                shippedDateBefore = new Date();
                shippedDateBefore.setTime(time);
            } catch (NumberFormatException ex) {
            }
        }

        receiveDateAfter = null;
        s = request.getParameter("receive_date_after");

        if (s != null) {
            try {
                long time = Long.parseLong(s);
                receiveDateAfter = new Date();
                receiveDateAfter.setTime(time);
            } catch (NumberFormatException ex) {
            }
        }

        receiveDateBefore = null;
        s = request.getParameter("receive_date_before");

        if (s != null) {
            try {
                long time = Long.parseLong(s);
                receiveDateBefore = new Date();
                receiveDateBefore.setTime(time);
            } catch (NumberFormatException ex) {
            }
        }

        s = request.getParameter("sort_field");
        if (s != null) {
            try {
                sortField = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                sortField = -1;
            }
        }

        s = request.getParameter("sort_order");
        if (s != null) {
            try {
                sortOrder = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                sortField = 1;
            }
        }

        s = request.getParameter("page_number");
        try {
            pageNumber = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            pageNumber = -1;
        }

        s = request.getParameter("items_per_page");
        try {
            itemsPerPage = Integer.parseInt(s);
        } catch (NumberFormatException ex) {
            itemsPerPage = -1;
        }
    }

}
