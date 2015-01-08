/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.analisers;

import astic.dpm.clusterlogic.ClusteringLogic;
import astic.dpm.parsers.Parser;
import java.io.File;

/**
 *
 * @author Elvis
 */
public abstract class Analiser {
    protected ClusteringLogic<?,?> mClusteringLogic ;
    public Analiser(ClusteringLogic<?,?> logic)
    {
        this.mClusteringLogic = logic;
    }
    
    public abstract void analyse(File file,Parser parser);
}
