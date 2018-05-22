/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parts.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.naming.NamingException;

/**
 *
 * @author antonina
 */
public class DBProvider {
    public Connection con = null;
    
    
    
    public DBProvider() throws NamingException, SQLException, Exception{
       con = new DBConnection().getConnection();
    }
    
    
    public int getRecordCount() throws SQLException{
        int x = -1;
        Statement st = con.createStatement();
        st.execute(Queries.GET_RECORD_COUNT);
        ResultSet rs = st.getResultSet();
        if (rs.next()) {
            x = rs.getInt(1);
        }
        return x;
    }
    
}
