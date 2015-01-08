/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dqm;

import astic.dpm.clusterlogic.ClusteringLogicL3;
import astic.dpm.parsers.lang.WordNet;
import astic.drm.BWClusterTable;
import astic.io.db.DBHelper;
import astic.io.db.IDBListener;
import astic.io.db.Request;
import astic.io.db.RequestQueue;
import astic.io.db.ResultParser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Elvis
 */
public class Query {
    
    public ArrayList<String> alg1(String word)
    {
        ArrayList<String> res  = new ArrayList<>();
        BWClusterTable bw = BWClusterTable.getInstance();
        HashMap<String,HashMap<String,Integer>> letterCluster = bw.reanimateCluster(word, false);
        for(String path:letterCluster.keySet())
        {
            Integer weight = letterCluster.get(path).get(word);
            weight = weight==null?0:weight;
            if(weight>0&&!res.contains(path))
                res.add(path);
        }
        return res;
    }
    
    public void alg2(String word,final long timestamp)
    {
        ArrayList<String> res  = new ArrayList<>();
        try{
            String sql = "select PATH from bw where word=?";
            PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
            pst.setString(1, word);
            Request r = new Request(pst, new IDBListener<ArrayList<String>>() {
                @Override
                public void onSucces(ArrayList<String> result) {
                    System.out.println(result.size() + " in "+(System.currentTimeMillis()-timestamp) + "ms");
                    for(String s:result)
                    {
                        System.out.println(s);
                    }
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            }, new ResultParser<ArrayList<String>>() {
                @Override
                public ArrayList<String> parse(ResultSet rs) {
                    try{
                    ArrayList<String>res = new ArrayList<>();
                    while(rs.next())
                    {
                        String path = rs.getString("PATH");
                        res.add(path);
                    }
                    return res;
                    }catch(Exception e){
                        e.printStackTrace();
                        return new ArrayList<>();
                    }
                }
            });
            DBHelper.getInstance().createRequestQueue().addRequest(r);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void alg3(String word)
    {
        ClusteringLogicL3 logic = new ClusteringLogicL3();
        logic.apply(word,null);
        
    }
    
    
}
