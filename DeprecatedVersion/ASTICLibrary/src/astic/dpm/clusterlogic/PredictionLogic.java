/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public class PredictionLogic {
    
    private String mQuery ;
    public ArrayList<String> filterPredictions(String query,ArrayList<String> unfiltered)
    {
        mQuery = query;
        ArrayList<String> res = new ArrayList<>();
        for(String s:unfiltered)
        {
            if(s.toLowerCase().equals(mQuery.toLowerCase()))
                continue;
            if(s.contains(query))
            {
                if(!add(res, s,10))
                    break;
            }
        }
        
        return res;
    }
    
    private boolean add(ArrayList<String> array,String s,int limit)
    {
        if(array.size()>limit)
            return false ;
        if(!array.contains(s))
            array.add(s);
        return true;
    }
}
