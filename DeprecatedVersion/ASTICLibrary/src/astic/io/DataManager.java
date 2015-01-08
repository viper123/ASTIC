/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package astic.io;

import astic.config.Configurations;
import java.io.File;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Elvis Rusu
 * Singleton class that handles the object manipulation in the entire application
 */
public class DataManager {
    
    private final ConcurrentHashMap<String,Serializable> mCache ;
    private final int NR_THREADS = 3;
    private final int CACHE_LIMIT = 20;
    private final int CRITICAL_LENGTH = 3;
    private final int DELETE_LENGTH = 10;
    private static final Object CACHE_LOCK = new Object();
    private final ExecutorService mWorkers ;
    private static DataManager instance;
    
    /**
     * Get an instance of DataManager.
     * @return instance of DataManager.
     */
    public static DataManager getInstance()
    {
        if(instance ==null)
            instance = new DataManager();
        return instance;
    }
    
    private DataManager()
    {
        mWorkers = Executors.newFixedThreadPool(NR_THREADS);
        mCache = new ConcurrentHashMap<>();
        File root = new File(Configurations.DM_PATH);
        if(!root.exists())
            root.mkdirs();
        System.out.println("Constructing Data Manager");
    }
    /**
     * Add an object to internal object collection.
     * @param key <code>String</code> the unique key for the object ;
     * @param data <code>Serializable</code> the data that will be serialized ;
     */
    public void put(String key,Serializable data)
    {
        synchronized(CACHE_LOCK)
        {
            mCache.put(key, data);
//            if(mCache.size()+CRITICAL_LENGTH >=CACHE_LIMIT)
//                evictToDisk();
                
        }
    }
    /**
     * Get an object from the internal object collection.
     * @param key <code>String</code> the unique key for the object ;
     * @return <code>Serializabile</code> the saved object or <code>NULL</code> if the object does not exist in the collection;
     */
    public Serializable get(String key)
    {
        Serializable data;
        synchronized(CACHE_LOCK)
        {
             data= mCache.get(key);
        }
//        if(data ==null)
//        {
//            data = IOHelper.loadObject(makePathFromKey(key));
//            if(data!=null)
//               put(key, data);
//        }
        return data;
        
    }
    
    public void stop()
    {
        evictToDisk();
        mWorkers.shutdown();
    }
    
    private void evictToDisk()
    {
        synchronized(CACHE_LOCK)
        {
            
        int k = 0;
        for(final String key:mCache.keySet())
        {
            mWorkers.execute(new WriteObjectRunnable(makePathFromKey(key), mCache.get(key)));
            if(k<DELETE_LENGTH)
            {
                mCache.remove(key);
                k++;
            }
            System.out.println("Evict:"+key);
        }
        }
    }
    
    private String makePathFromKey (String key)
    {
        return Configurations.DM_PATH + key;
    }
    
    private class WriteObjectRunnable implements Runnable{
        
        private final Serializable data;
        private final String key;
        
        public WriteObjectRunnable(String key,Serializable data)
        {
            this.key = key;
            this.data = data;
        }
        
        @Override
        public void run() 
        {
            IOHelper.saveObject(key, data);
        }
        
    }
}
