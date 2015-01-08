/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.io.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Elvis
 */
public class Dispacher extends Thread {

    private boolean busy = false;
    private boolean on = true;
    private boolean signal = false;
    private final int SLEEP_TIME = 100;
    private RequestQueue mQueue;
    private boolean debug = false;
    private Connection mConnection;
    public Dispacher(Connection con,RequestQueue queue)
    {
        this.mQueue = queue ;
        this.mConnection = con;
    }
    
    @Override
    public void run() 
    {
        log("started");
        while(on)
        {
            if(!signal)
            {
                try{
                    Thread.sleep(SLEEP_TIME);
                }catch(Exception e){
                    on=false;
                }
            }
            else
            {
                Request r = mQueue.take();
                if(r!=null)
                {
                    r.addLog("request-taken");
                    execute(r);
                    r.addLog("request-completed");
                }
                else
                    log("request-null");
                busy = false;
                signal = false;
            }
        }
        log("stoped");
    }
    
    public synchronized void stopDispacher()
    {
        on = false;
    }
    
    public synchronized void signal()
    {
        log("signaled");
        if(!isAlive())
            start();
        signal = true;
        busy = true;
    }
    
    public boolean isWorking()
    {
        return busy&&isAlive() ;
    }
    
    private void execute(Request r)
    {
        IDBListener listner = r.getListner();
        try {
            long time = System.nanoTime();
            ResultSet rs = r.getPreparedStatement().executeQuery();
            r.addLog("request-executed-in-"+((System.nanoTime()-time)/1000000)+"ms");
            if(listner!=null)
            {
                listner.onSucces(r.getParser().parse(rs));
                r.addLog("request-success-delivered");
            }
            r.getPreparedStatement().close();
            
        } catch (SQLException ex) {
            if(listner!=null)
            {
                listner.onError(ex);
                r.addLog("request-error-delivered");
                
            }
            try{
                r.getPreparedStatement().close();
            }catch(Exception e){
                
            }
        }
    }
    
    private void log(String message)
    {
        if(debug)
            System.out.println("Dispacher: "+message);
    }
    
    
}
