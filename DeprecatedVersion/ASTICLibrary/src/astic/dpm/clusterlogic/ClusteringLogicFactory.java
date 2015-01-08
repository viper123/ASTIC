/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

import astic.dpm.ClusteringLevel;

/**
 *
 * @author Elvis
 */
public class ClusteringLogicFactory {
    
    public static ClusteringLogic<?,?> getClusteringLogic(ClusteringLevel level)
    {
        switch(level)
        {
            case Level0:
                return new ClusteringLogicL0() ;
            case Level1:
                return null;
            case Level2 :
                return null;
            default:
                return null;
        }
    }
}
