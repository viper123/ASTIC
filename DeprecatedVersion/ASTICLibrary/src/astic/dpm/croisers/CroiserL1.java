/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.croisers;

import astic.dpm.CroiseRequest;
import astic.dpm.analisers.Analiser;
import astic.dpm.analisers.AnaliserFactory;
import astic.dpm.analisers.AnaliserL1;
import astic.dpm.clusterlogic.ClusteringLogic;
import astic.dpm.clusterlogic.ClusteringLogicL1;
import astic.drm.Summary;
import astic.io.db.DBHelper;
import astic.io.db.IDBListener;
import astic.io.db.Request;
import astic.io.db.RequestQueue;
import astic.io.db.ResultParser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Elvis
 */
public class CroiserL1 extends DbCroiser{

    private final int THREAD_LIMIT = 0;
    private boolean debug =true;
    
    @Override
    protected int getThreadsLimit() 
    {
        return THREAD_LIMIT;
    }
    
    @Override
    protected void croise(CroiseRequest request)
    {
        
    }
    
    public void croise()
    {
        //cleanDb();
        String sql = "SELECT * from bw";
        try{
        PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
        Request<ArrayList<Summary>> r = new Request<ArrayList<Summary>>(pst, new IDBListener<ArrayList<Summary>>() {

            @Override
            public void onSucces(ArrayList<Summary> result) 
            {
                log("Success:"+result.size());
                AnaliserL1 analiser = new AnaliserL1(new ClusteringLogicL1());
                for(Summary s:result)
                {
                    analiser.analyse(s.document, result);
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        }, new ResultParser<ArrayList<Summary>>(){

            @Override
            public ArrayList<Summary> parse(ResultSet rs) {
                //log("parsing");
                ArrayList<Summary> list = new ArrayList<>();
                try{
                    HashMap<String,HashMap<String,Double>> hash = new HashMap<String,HashMap<String,Double>>();
                    while(rs.next())
                    {
                        String path,word;
                        double weight;
                        path = rs.getString("PATH");
                        word = rs.getString("WORD");
                        weight = rs.getInt("WEIGHT");
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
        mDbQueue.addRequest(r);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void croise2()
    {
        
    }
    
    private void cleanDb()
    {
        try{
            String sql = "delete from tw where tfidf>=0";
            PreparedStatement st = DBHelper.getInstance().getPreparedStatement(sql);
            mDbQueue.addRequest(new Request(st,null,null));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    private void log(String message)
    {
        if(debug)
            System.out.println("Croiser:"+message);
    }

    
    
}
