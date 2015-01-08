/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.drm;

/**
 *
 * @author Elvis
 */
public class Clusters {
    
    public static void add(Summary s)
    {
        BWAlphabetClusters bw  = BWAlphabetClusters.getInstance();
        bw.add(s);
    }
}
