/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.io.db;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Elvis
 */
public class RequestQueue {
    
    ConcurrentLinkedQueue<Request> mQueue;
    ArrayList<Dispacher> mDispachers ;
    boolean debug = false;
    
    public RequestQueue(int dispachers)
    {
        mQueue = new ConcurrentLinkedQueue<>();
        mDispachers = new ArrayList<>(dispachers);
        for(int i=0;i<dispachers;i++)
            mDispachers.add(new Dispacher(DBHelper.getInstance().getDbConnection(),this));
    }
    
    public Request take()
    {
        return mQueue.poll();
    }
    
    public synchronized void addRequest(Request r)
    {
        log("request-added-to-queue");
        mQueue.add(r);
        boolean asigned = false;
        int index=0;
        while(!asigned)
        {
            Dispacher d = mDispachers.get(index);
            if(!d.isWorking())
            {
                d.signal();
                log("dispacher-"+index+"-signaled");
                asigned = true;
            }
            index = ++index%mDispachers.size();
        }
    }
    
    public void close()
    {
        for(Dispacher d:mDispachers)
            d.stopDispacher();
    }
    
    private void log(String message)
    {
        if(debug)
            System.out.println("RequestQueue: "+message);
    }
    
}
