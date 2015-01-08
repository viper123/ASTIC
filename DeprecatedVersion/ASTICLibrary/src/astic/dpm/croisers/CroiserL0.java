
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.croisers;

import astic.dpm.CroiseRequest;
import astic.dpm.FileType;
import astic.dpm.analisers.Analiser;
import astic.dpm.analisers.AnaliserFactory;
import astic.dpm.parsers.Parser;
import astic.dpm.parsers.ParserFactory;
import java.io.File;

/**
 *
 * @author Elvis
 */
public class CroiserL0 extends Croiser{
    private boolean debug = false ;
    private final int THREAD_LIMIT = 0;

    
    @Override
    protected int getThreadsLimit() {
        return THREAD_LIMIT ;
    }

    @Override
    protected void asignThread(CroiseRequest request) {
        croise(request);
        if(callback!=null)
               callback.OnDone();
    }
    
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
                CroiseRequest r = new CroiseRequest(file,request.maxDepth,request.level);
                r.depth = request.depth+1;
                if(r.file.isDirectory())
                {
                    if(r.depth<=r.maxDepth)
                        croise(r); 
                }
                else
                {
                    croise(r);
                }
            }
        }
        else
        {
            FileType fileType = FileType.fromFile(request.file);
            if(fileType == FileType.Text)
            {
                Parser parser = ParserFactory.fromFileType(fileType);
                Analiser analiser = AnaliserFactory.createAnaliser(request.level);
                analiser.analyse(request.file, parser);
                //sleepCustom(500);
            }
        }
        if(callback!=null)
            callback.OnProgress(request.file.getAbsolutePath());
    }
    
    private void log(String message)
    {
        if(debug)
            System.out.println("Croiser:"+message);
    }

   
}
