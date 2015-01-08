/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.io.db;

import java.sql.PreparedStatement;

/**
 *
 * @author Elvis
 */
public class Request <T>{
    private String mRequestString;
    private IDBListener mListener ;
    private PreparedStatement mPreparedStatement ;
    private boolean debug = false;
    private ResultParser<T> mParser;
    public Request(String request,IDBListener listner,ResultParser<T> parser)
    {
        this.mListener = listner;
        this.mRequestString = request;
        this.mParser = parser;
    }
    
    public Request(PreparedStatement pst,IDBListener listner,ResultParser<T> parser)
    {
        this.mListener = listner ;
        this.mPreparedStatement =pst; 
        this.mParser = parser;
    }
    
    @Deprecated
    public String getRequestString()
    {
        return mRequestString;
    }
    
    public PreparedStatement getPreparedStatement()
    {
        return mPreparedStatement;
    }
    
    public IDBListener getListner()
    {
        return mListener;
    }
    
    public ResultParser<T> getParser()
    {
        return mParser;
    }
    
    public void addLog(String message)
    {
        if(debug)
            System.out.println(message);
    }
    
}
