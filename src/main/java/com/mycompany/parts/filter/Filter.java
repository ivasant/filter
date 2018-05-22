/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parts.filter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author antonina
 */
public class Filter {

    Connection con;
    String partNumber;
    String partName;
    String vendor;
    Integer qty;
    Date shippedDateAfter;
    Date shippedDateBefore;
    Date receiveDateAfter;
    Date receiveDateBefore;
    int sortFieldNum;
    SortField sortField;
    int sortOrder;
    int pageNumber;
    int itemsPerPage;

    public Filter(Connection inCon, String inPartNumber, String inPartName,
            String inVendor, Integer inQty,
            Date inShippedDateAfter, Date inShippedDateBefore,
            Date inReceiveDateAfter, Date inReceiveDateBefore,
            int inSortField, int inSortOrder,
            int inPageNumber, int inItemsPerPage) {
        con = inCon;
        partNumber = inPartNumber;
        partName = inPartName;
        vendor = inVendor;
        qty = inQty;
        shippedDateAfter = inShippedDateAfter;
        shippedDateBefore = inShippedDateBefore;
        receiveDateAfter = inReceiveDateAfter;
        receiveDateBefore = inReceiveDateBefore;
        sortFieldNum = inSortField;
        sortOrder = inSortOrder;
        pageNumber = inPageNumber;
        itemsPerPage = inItemsPerPage;
    }

    public String getIncorrectParamsMsg() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean prepareParams(StringBuilder errorMsg) {
        Boolean isOk = true;

        if (partNumber != null && partNumber.equalsIgnoreCase("")) {
            partNumber = null;
        }
        if (partName != null && partName.equalsIgnoreCase("")) {
            partName = null;
        }
        if (vendor != null && vendor.equalsIgnoreCase("")) {
            vendor = null;
        }

        if (qty != null && qty < 0) {
            qty = null;
        }

        if (shippedDateAfter == null) {
            shippedDateAfter = new Date();
            shippedDateAfter.setTime(0);
        }

        if (shippedDateBefore == null) {
            shippedDateBefore = new Date();
            shippedDateBefore.setTime(Long.MAX_VALUE);
        }

        if (receiveDateAfter == null) {
            receiveDateAfter = new Date();
            receiveDateAfter.setTime(0);
        }

        if (receiveDateBefore == null) {
            receiveDateBefore = new Date();
            receiveDateBefore.setTime(Long.MAX_VALUE);
        }

        if (itemsPerPage <= 0) {
            isOk = false;
            errorMsg.append("Parameter itemsPerPage should be bigger than 0");
        }

        if (pageNumber <= 0) {
            isOk = false;
            errorMsg.append("Parameter pageNumber should be bigger than 0");
        }

        if (shippedDateAfter.getTime() > shippedDateBefore.getTime()) {
            isOk = false;
            errorMsg.append("Parameter shippedDateAfter should be less than shippedDateBefore");
        }

        if (receiveDateAfter.getTime() > receiveDateBefore.getTime()) {
            isOk = false;
            errorMsg.append("Parameter receiveDateAfter should be less than receiveDateBefore");
        }

        sortField = SortField.from(sortFieldNum);

        return isOk;
    }

    public String getData() {

        JSONObject obj = new JSONObject();

        StringBuilder msg = new StringBuilder();
        if (prepareParams(msg)) {

            try {
                JSONArray resultArr;
                resultArr = getFilterData();
                obj.append("data", resultArr);
                
            } catch (SQLException ex) {
                obj.append("error", ex.getMessage());
                Logger.getLogger(Filter.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            obj.append("error", msg);
        }

        return obj.toString();
    }

    private JSONArray getFilterData() throws SQLException {

        String query = "select * from parts where ";
        if (partNumber != null) {
            query += " part_number like ? and ";
        }
        if (partName != null) {
            query += " part_name like ? and ";
        }
        if (vendor != null) {
            query += " vendor like ? and ";
        }
        if (qty != null) {
            query += " qty >= ? ";
        }
        query += " shipped between ? and ? and ";
        query += " receive between ? and ? and ";
        query += " order by " + sortField.getFieldName() + (sortOrder >= 0 ? "" : " DESC ");

        PreparedStatement statement = con.prepareStatement(query);

        int pn = 0;
        if (partNumber != null) {
            statement.setString(pn++, "%" + partNumber + "%");
        }
        if (partName != null) {
            statement.setString(pn++, "%" + partName + "%");
        }
        if (vendor != null) {
            statement.setString(pn++, "%" + vendor + "%");
        }
        if (qty != null) {
            statement.setInt(pn++, qty);
        }
        statement.setDate(pn++, (java.sql.Date) shippedDateAfter);
        statement.setDate(pn++, (java.sql.Date) shippedDateBefore);
        statement.setDate(pn++, (java.sql.Date) receiveDateAfter);
        statement.setDate(pn++, (java.sql.Date) receiveDateBefore);

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
