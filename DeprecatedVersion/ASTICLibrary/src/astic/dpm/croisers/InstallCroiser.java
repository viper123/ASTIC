/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.croisers;

import astic.dpm.CroiseRequest;
import astic.dpm.FileType;
import astic.dpm.analisers.InstallAnaliser;
import astic.dpm.parsers.NameParser;
import java.io.File;

/**
 *
 * @author Elvis
 */
public class InstallCroiser extends Croiser {
    
    private final int THREAD_LIMIT = 2;
    private boolean debug = true;
    
    
    
    @Override
    protected void croise(CroiseRequest request) 
    {
        if(request.file.isDirectory())
        {
            File[] files = request.file.listFiles();
            if(files==null)
                return ;
            for(File file:files)
            {
                if(file.isHidden())
                    continue;
                this.addToMaxFiles(1);
                CroiseRequest r = new CroiseRequest(file,request.maxDepth,request.level);
                r.depth = request.depth+1;
                asignThread(r);
            }
            NameParser parser = new NameParser();
            InstallAnaliser analiser = new InstallAnaliser();
            analiser.analyse(request.file, parser);
            progress();
        }
        else
        {
            FileType fileType = FileType.fromFile(request.file);
            if(fileType != FileType.Other)
            {
                NameParser parser = new NameParser();
                InstallAnaliser analiser = new InstallAnaliser();
                analiser.analyse(request.file, parser);
            }
            progress();
            sleepCustom(100);
        }
    }

    @Override
    protected int getThreadsLimit() 
    {
        return THREAD_LIMIT;
    }
    
    private void log(String message)
    {
        if(debug)
            System.out.println("Croiser:"+message);
    }
    
}
