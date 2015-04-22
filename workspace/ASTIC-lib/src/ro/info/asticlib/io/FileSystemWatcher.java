/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.info.asticlib.io;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.clustering.ClusteringService;
import ro.info.asticlib.db.Dao;

/**
 *
 * @author Elvis
 */

public class FileSystemWatcher extends Thread {
    public static final int CS_POOL_SIZE = 4;
    public static final int SLEEP_TIME = 10*1000;
    private static FileSystemWatcher mInstance ;
    private ArrayList<WatchService> mWatchers;
    private ClusteringService clusteringService;
    private final Object WATCHERS_LOCK = new Object();
    private boolean ON = true;
    private HashMap<String, Boolean> registeredDirs = new HashMap<String, Boolean>();
    private Dao dao;
    public static FileSystemWatcher getInstance()
    {
        if(mInstance ==null)
            mInstance = new FileSystemWatcher();
        return mInstance;
    }
    
    private FileSystemWatcher()
    {
        mWatchers = new ArrayList<>();
        dao = new Dao();
    }
    
    @Override
    public void run() {
    	setPriority(MIN_PRIORITY);
    	loop();
    }
    
    public void setClusteringService(Class<? extends ClusteringService> clazz){
    	for(int i=0;i<CS_POOL_SIZE;i++){
    		try {
    			this.clusteringService = clazz.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void registerWatcher(String path)
    {
    	if(registeredDirs.containsKey(path)){
    		return ;
    	}
    	registeredDirs.put(path, true);
    	System.out.println("Watcher: register watcher for:"+path);
        Path dir = Paths.get(path);
        try{
            WatchService watcher = dir.getFileSystem().newWatchService();
            dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE, 
                StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
            synchronized(WATCHERS_LOCK)
            {
                mWatchers.add(watcher);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    private void loop(){
    	System.out.println("Watcher started");
        while(ON){
            try{
                synchronized(WATCHERS_LOCK){
                    for(WatchService watcher:mWatchers)
                    {
                        WatchKey key = watcher.poll();
                        if(key!=null){
                            handle(key);
                            key.reset();
                        }
                    }
                } 
                sleep(SLEEP_TIME);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        System.out.println("Watcher finished");
    }
    
    @SuppressWarnings("all")
    private void handle(WatchKey key) throws InterruptedException{
    	System.out.println("handle event>>>>");
        List<WatchEvent<?>> events = key.pollEvents();
        Path dir = (Path)key.watchable();
    	
        for (WatchEvent event : events) {
        	Path fullPath = dir.resolve((Path) event.context());
        	String file = fullPath.toString();
        	boolean processed = false;
			while(!processed){
				if(!clusteringService.isWorking()){
					if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY ){
						dao.deleteFileFromTables(file);
						clusteringService.clusterizeFilePath(file);
					}
					if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE ){
						clusteringService.clusterizeFilePath(file);
					}
					if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE ){
						dao.deleteFileFromTables(file);
					}
					System.out.println("Request processed:"+file);
					processed = true;
				}else{
					sleep(SLEEP_TIME/6);
				}
			}
        }
    }
    
    public synchronized void stopD(){
    	ON = false;
    }
    
}
