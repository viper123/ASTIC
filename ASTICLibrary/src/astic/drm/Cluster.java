/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.drm;

import java.util.HashMap;

/**
 *
 * @author Elvis
 */
public class Cluster {
    public HashMap<String,HashMap<String,Double>> files ;
    public double code;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Cluster)
        {
            return ((Cluster)obj).code == code ;
        }
        return false ;
    }
    
    
}
