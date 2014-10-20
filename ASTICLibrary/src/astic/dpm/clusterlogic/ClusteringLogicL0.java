/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

import astic.dpm.parsers.lang.WordNet;
import astic.drm.Summary;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Elvis
 */
public class ClusteringLogicL0 extends ClusteringLogic<Summary, String[]> {

    
    @Override
    public Summary apply(String path,String[] param) 
    {
        Summary summary = new Summary();
        summary.document = path;
        summary.weightMatrix = new HashMap<>() ;
        for(String word:param)
        {
           double weight = 0;
            try{       
                weight =summary.weightMatrix.get(word.trim());
            }catch(Exception e){}
            summary.weightMatrix.put(word.trim(), ++weight);
        }
        return summary ;
    }
    
}
