/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.croisers;

import astic.dpm.CroiseRequest;
import astic.io.db.RequestQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Elvis
 */

public abstract class Croiser extends Thread{
    private ConcurrentLinkedQueue<CroiseRequest> mQueue;
    protected ExecutorService mWorkers ;
    private boolean ON = false;
    protected ICroiseCallback callback;
    private int maxFiles,kFiles ;
    protected CroiseRequest currentRequest;
    
    public Croiser()
    {
        int threads = getThreadsLimit();
        if(threads>0)
            mWorkers = Executors.newFixedThreadPool(threads);
        mQueue = new ConcurrentLinkedQueue<>();
        ON = true ;
    }
    
    public synchronized void add(CroiseRequest request)
    {
        mQueue.add(request);
    }
    
    public void setCroiseCallback(ICroiseCallback callback)
    {
        this.callback = callback;
    }

    @Override
    public void run() 
    {
        while(ON)
        {
            if(!mQueue.isEmpty())
            {
                CroiseRequest request = mQueue.poll();
                this.currentRequest = request;
                asignThread(request);
            }
        }
    }
    
    protected void asignThread(final CroiseRequest request)
    {
        mWorkers.execute(new Runnable() {
            @Override
            public void run() {
                croise(request);
                if(callback!=null)
                    callback.OnProgress(request.file.getAbsolutePath());
            }
        });
    }
    
    protected synchronized void addToMaxFiles(int nr)
    {
        this.maxFiles+=nr;
        //System.out.println("From "+this.maxFiles);
    }
    
    protected synchronized void progress()
    {
        //System.out.println("Progress "+this.kFiles);
        this.kFiles ++;
        if(this.maxFiles ==this.kFiles)
            callback.OnDone();
                    
    }
    
    protected void sleepCustom(long miliseconds)
    {
        try{
            Thread.sleep(miliseconds);
        }catch(InterruptedException e){
            
        }
    }
    
    public void stopCustom()
    {
        this.ON = false ;
        if(mWorkers!=null)
            this.mWorkers.shutdown();
    }
    
    protected abstract void croise(CroiseRequest request);
    protected abstract int getThreadsLimit();
    
    
    
}
