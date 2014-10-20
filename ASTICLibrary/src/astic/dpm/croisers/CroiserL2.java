/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.croisers;

import astic.dpm.CroiseRequest;
import astic.dpm.clusterlogic.ClusteringLogicL2;
import astic.drm.Cluster;
import astic.drm.ClusterSerialiser;
import astic.drm.Summary;
import astic.io.db.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Elvis
 */
public class CroiserL2 extends DbCroiser {

    @Override
    protected void croise(CroiseRequest request) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected int getThreadsLimit() {
        return 0;
    }
    @Override
    public void croise()
    {
        String sql = "select * from tw";
        try{
            PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
            Request<ArrayList<Summary>> request = new Request<ArrayList<Summary>>(pst, 
                    new IDBListener<ArrayList<Summary>>() {

                @Override
                public void onSucces(final ArrayList<Summary> summarys) 
                {
                    //have all summayes ;
                    try{
                    String sql2 = "select * from ct";
                    PreparedStatement pst2 = DBHelper.getInstance().getPreparedStatement(sql2);
                    Request<HashMap<Double,String>> r = 
                            new Request<HashMap<Double, String>>(pst2, new IDBListener<HashMap<Double,String>>() 
                            {

                            @Override
                            public void onSucces(HashMap<Double,String> result) {
                                
                                ArrayList<Cluster> clusters = new ArrayList<Cluster>();
                                for(double code:result.keySet())//Constructing the arrayList for Cluster ;
                                {
                                    Cluster c = new Cluster();
                                    c.code = code ;
                                    c.files = new HashMap<String, HashMap<String, Double>>();
                                    //c.files.put(result.get(code), )
                                    for(Summary s:summarys)
                                    {
                                        if(s.document.equals(result.get(code)))
                                        {
                                            c.files.put(result.get(code), s.weightMatrix);
                                            break;
                                        }
                                    }
                                    clusters.add(c);
                                }
                                int goodSummaries = 0 ;
                                for(Summary s:summarys)//loop in Summarys
                                {
                                    //System.out.println(""+(counter++)+" "+summarys.size());
                                    if (s.weightMatrix.isEmpty())
                                        continue;
                                    else
                                        goodSummaries++;
                                    if(clusters.isEmpty())//no clusters ? cretea one ;
                                    {
                                        clusters.add(createCluster(s));
                                    }
                                    else
                                    {
                                        boolean found = false ;
                                        for(Cluster c:clusters)
                                        {
                                            double cosine = ClusteringLogicL2.cosine(s, c);
                                            //System.out.println(+ClusteringLogicL2.cosine(s, c));
                                            if(cosine>0.5)
                                            {
                                                addToCluster(c, s);
                                                found = true ;
                                                System.out.println(""+cosine);
                                            }
                                            
                                        }
                                        if(!found)
                                            clusters.add(createCluster(s));
                                        
                                    }
                                }
                                System.out.println("TotalClusters:"+clusters.size()+" from "+goodSummaries);
                                ClusterSerialiser.addToCT(clusters);
                                
                            }
                            
                            @Override
                            public void onError(Exception e) {
                                e.printStackTrace();
                            }
                        }
                            , new ResultParser<HashMap<Double, String>>() {

                            @Override
                            public HashMap<Double, String> parse(ResultSet rs) {
                                HashMap<Double,String> res = new HashMap<>();
                                try{
                                    while(rs.next())
                                    {
                                        double code = rs.getDouble("CODE");
                                        String path = rs.getString("PATH");
                                        res.put(code, path);
                                    }  
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                                return res;
                            }
                        });
                    mDbQueue.addRequest(r);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                }

                @Override
                public void onError(Exception e) {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            },new ResultParser<ArrayList<Summary>>() {

                @Override
                public ArrayList<Summary> parse(ResultSet rs) 
                {
                    ArrayList<Summary> list = new ArrayList<>();
                try{
                    HashMap<String,HashMap<String,Double>> hash = new HashMap<String,HashMap<String,Double>>();
                    while(rs.next())
                    {
                        String path,word;
                        double weight;
                        path = rs.getString("PATH");
                        word = rs.getString("WORD");
                        weight = rs.getInt("TF");
                        HashMap<String,Double> wm ;
                        if(hash.containsKey(path))
                        {
                            wm = hash.get(path);
                            if(wm.containsKey(word))
                            {
                                wm.put(word, wm.get(word)+weight);
                            }
                            else
                            {
                                wm.put(word, weight);
                            }
                        }
                        else
                        {
                            hash.put(path, new HashMap<String,Double>());
                        }   
                    }
                    for(String path:hash.keySet())
                    {
                        Summary s = new Summary();
                        s.document = path;
                        s.weightMatrix = hash.get(path);
                        list.add(s);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                return list;
                }
            });
            mDbQueue.addRequest(request);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private Cluster createCluster(Summary s)
    {
        Cluster c = new Cluster();
        c.code = IdGenerator.generateUniqueID();
        c.files = new HashMap<>();
        c.files.put(s.document, s.weightMatrix);
        return c;
              
    }
    
    private void addToCluster(Cluster c,Summary s)
    {
        if(!c.files.containsKey(s.document))
            c.files.put(s.document, s.weightMatrix);
    }
    
}
