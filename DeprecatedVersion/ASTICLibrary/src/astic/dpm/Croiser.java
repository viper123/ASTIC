/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm;

import astic.dpm.analisers.Analiser;
import astic.dpm.parsers.Parser;
import astic.dpm.parsers.ParserFactory;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Elvis
 */
@Deprecated
public class Croiser implements Runnable{
    private String mRoot ; 
    private Analiser mAnaliser;
    private static final int NR_TREADS = 4;
    private ExecutorService mWorkers ;
    public Croiser(String path,Analiser analiser)
    {
        this.mRoot = path;
        this.mAnaliser = analiser;
        mWorkers = Executors.newFixedThreadPool(NR_TREADS);
    }

    @Override
    public void run() 
    {
        croise(new File(mRoot));
    }
    
    public void croise(File dir)
    {
        if(dir==null)
            return ;
        File[] files = dir.listFiles();
        if(files==null)
            return ;
        for(File file:files)
        {
            if(file.isDirectory())
                asignThread(file);
            else
            {
                FileType fileType = FileType.fromFile(file);
                Parser parser = ParserFactory.fromFileType(fileType);
            }
            
        }
    }
    
    private void asignThread(final File file){
        mWorkers.execute(new Runnable() {
            @Override
            public void run() {
                croise(file);
            }
        });
    }
    
    private void sleep()
    {
         try {
             Thread.sleep(1000);
         } catch (InterruptedException ex) {
             Logger.getLogger(Croiser.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
   
    
    
    
}
