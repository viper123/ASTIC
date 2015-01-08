/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asticdaemon.core;

import astic.dpm.CroiseRequest;
import astic.dpm.croisers.CroiserL0;
import astic.dpm.analisers.Analiser;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elvis
 */
@Deprecated
public class FileSystemWatcher {
    
    private static FileSystemWatcher mInstance ;
    private CroiserL0 mCroiser;
    private Analiser mAnaliser;
    private ArrayList<WatchService> mWatchers;
    private final Object WATCHERS_LOCK = new Object();
    private boolean ON = false;
    public static FileSystemWatcher getInstance()
    {
        if(mInstance ==null)
            mInstance = new FileSystemWatcher();
        return mInstance;
    }
    
    private FileSystemWatcher()
    {
        mCroiser = new CroiserL0();
        mWatchers = new ArrayList<>();
        //mAnaliser = new HighAnaliser();
    }
    
    public synchronized void start()
    {
        if(ON)
            return ;
        ON = true;
        Thread worker = new Thread(new Runnable() {
            public void run() 
            {
                loop();
            }
        });
    }
    
    public synchronized void stop()
    {
        ON = false;
    }
    
    public void registerWatcher(String path)
    {
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
    
    public void setAnaliser(Analiser analiser)
    {
        this.mAnaliser = analiser;
    }
    
    private void loop()
    {
        while(ON)
        {
            try{
                synchronized(WATCHERS_LOCK)
                {
                    for(WatchService watcher:mWatchers)
                    {
                        WatchKey key = watcher.poll();
                        if(key!=null)
                            handle(key);
                    }
                } 
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    private void handle(WatchKey key)
    {
        List<WatchEvent<?>> events = key.pollEvents();
        for (WatchEvent event : events) 
        {
            if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                createRequest(event.context().toString());
            }
            if (event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                createRequest(event.context().toString());
            }
            if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                createRequest(event.context().toString());
            }
        }
    }
    
    private void createRequest(String path)
    {
        //CroiseRequest request = new CroiseRequest(new File(path),mAnaliser ,2);
        //mCroiser.add(request);
    }
    
}
