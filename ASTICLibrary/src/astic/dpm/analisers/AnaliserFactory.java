/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.analisers;

import astic.dpm.ClusteringLevel;
import astic.dpm.clusterlogic.ClusteringLogicFactory;
import astic.dpm.parsers.Parser;

/**
 *
 * @author Elvis
 */
public class AnaliserFactory {
    
    public static Analiser createAnaliser(ClusteringLevel level)
    {
        switch(level)
        {
            case Level0:
                return new AnaliserL0(ClusteringLogicFactory.getClusteringLogic(level));
            default:
                return null;
            
        }
    }
}
