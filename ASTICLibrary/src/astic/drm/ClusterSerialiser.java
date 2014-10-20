/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.drm;

import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public class ClusterSerialiser {
    
    public static void add(Summary s)
    {
        BWClusterTable bw  = BWClusterTable.getInstance();
        bw.add(s);
    }
    
    public static void addToTw(Summary s)
    {
        TwClusterTable tw = TwClusterTable.getInstance();
        tw.add(s);
    }
    
    public static void addToCT(ArrayList<Cluster> clusters)
    {
        CTClusterTable ct = CTClusterTable.getInstance();
        ct.add(clusters);
    }
    
}
