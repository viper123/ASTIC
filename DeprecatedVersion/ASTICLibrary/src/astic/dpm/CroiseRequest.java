/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm;

import java.io.File;

/**
 *
 * @author Elvis
 */
public class CroiseRequest {
    public File file;
    public int depth;
    public final int maxDepth ;
    public ClusteringLevel level ;
    
    public CroiseRequest(File file,int maxDepth,ClusteringLevel level)
    {
        this.maxDepth = maxDepth;
        this.depth = 0;
        this.file = file;
        this.level = level;
    }
}
