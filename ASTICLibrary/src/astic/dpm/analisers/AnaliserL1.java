/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.analisers;

import astic.dpm.clusterlogic.ClusteringLogic;
import astic.dpm.clusterlogic.ClusteringLogicL1;
import astic.dpm.parsers.Parser;
import astic.drm.ClusterSerialiser;
import astic.drm.Summary;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public class AnaliserL1 extends Analiser {

    ArrayList<Summary> bw ;
     
    public AnaliserL1(ClusteringLogic<?,?> logic)
    {
        super(logic);
    }
    
    @Override
    public void analyse(File file, Parser parser) 
    {
        String sql = "SELECT * from bw";
    }
    
    public void analyseDb()
    {
        
    }
    
    public void analyse(String file,ArrayList<Summary> param)
    {
        ClusteringLogicL1 logic = (ClusteringLogicL1) mClusteringLogic ;
        Summary current = null;
        for(Summary s:param)
        {
            //System.out.println(file+" = "+s.document);
            if(s.document.equals(file))
                current = s;
        }
        logic.setCurrent(current);
        Summary s2 = logic.apply(file, param);
        ClusterSerialiser.addToTw(s2);
    }
    
}
