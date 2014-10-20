/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.analisers;

import astic.dpm.clusterlogic.ClusteringLogic;
import astic.dpm.parsers.Parser;
import astic.drm.ClusterSerialiser;
import astic.drm.Summary;
import java.io.File;

/**
 *
 * @author Elvis
 */
public class AnaliserL0 extends Analiser {    
    
    public AnaliserL0(ClusteringLogic<?,?> logic)
    {
        super(logic);
    }
    
    @Override
    public void analyse(File file, Parser parser) {
        ClusteringLogic<Summary,String[]> logic = (ClusteringLogic<Summary,String[]>) mClusteringLogic;
        String [] words = parser.getWords(file);
        Summary summary = logic.apply(file.getAbsolutePath(), words);
        ClusterSerialiser.add(summary);
    }
    
}
