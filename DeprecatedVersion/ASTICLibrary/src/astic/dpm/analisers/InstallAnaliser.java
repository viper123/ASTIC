/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.analisers;

import astic.dpm.parsers.Parser;
import astic.drm.ClusterSerialiser;
import astic.drm.Summary;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Elvis
 */
public class InstallAnaliser extends Analiser {

    
    public InstallAnaliser()
    {
        super(null);
    }
    
    @Override
    public void analyse(File file, Parser parser) 
    {
        String []words = parser.getWords(file);
        if(words!=null&&words.length>0)
        {
            Summary s = new Summary();
            s.document = file.getAbsolutePath();
            s.weightMatrix = new HashMap<>();
            for(String word:words)
            {
                if(s.weightMatrix.containsKey(word))
                {
                    s.weightMatrix.put(word, s.weightMatrix.get(word)+1);
                }
                else
                {
                    s.weightMatrix.put(word, 1.0d);
                }
            }
            ClusterSerialiser.add(s);
        }
    }
    
}
