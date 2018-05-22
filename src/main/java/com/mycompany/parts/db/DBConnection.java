/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.parts.db;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author antonina
 * Connects to database, described in context.xml
 */

public class DBConnection {
    
    Connection connection = null;
    
    public DBConnection() throws NamingException, SQLException, Exception{
          
            Context context = new InitialContext();
            DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/testDB");
            connection = ds.getConnection();
  
    }
         
    public Connection getConnection(){
        return connection;
    }
}
