package astic.io.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Elvis
 */
public class DBHelper {
    
    /**
     * Create a table call Bag of Words
     * Path , Word, Weight
     */
    private static DBHelper mInstance ;
    private Connection connection;
    private final int DISPACHERS = 4;
    
    public static DBHelper getInstance()
    {
        //System.out.println("GET DB instance");
        if(mInstance==null)
            mInstance = new DBHelper();
        return mInstance;
    }
            
    
    private DBHelper()
    {
        //System.out.println("Createing connection");
        connection = getDbConnection();
        //System.out.println("Creation End");
    }
    
    public final Connection getDbConnection()
    {
        //System.out.println("Getting db connection");
        if(connection!=null)
        {
            //System.out.println("Already have connection"+connection.toString());
            return connection;
        }
        else
        {
            //System.out.println("Connection is NULL");
        }
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system",
					"trick123");
            if(connection!=null)
            {
                //System.out.println("Connection !=null");
                return connection ;
            }
            else
            {
                //System.out.println("Connection is null");
                return connection;
            }
            
        }catch(SQLException e){
            System.out.println("SQLEXCEption"+e.getMessage());
            return null;
        } catch (ClassNotFoundException e) 
        {
            System.out.println("Class Not found Exception "+e.getMessage());
            return null;
        }
    }
    
    public RequestQueue createRequestQueue()
    {
        return new RequestQueue(DISPACHERS);
    }
    
    
    public Object wrap(Object o)
    {
        if(o instanceof String)
        {
            String s = (String)o;
            s="\'"+s+"\'";
            return s ;
        }
        return o;
    }
    
    public String createSelect(String table,String column,String where)
    {
        String select = "select "+column+" from "+table+" where "+where+";";
        return select;
    }
    
    public PreparedStatement getPreparedStatement(String sql) throws SQLException
    {
        return connection.prepareStatement(sql);
    }
    
    
}
