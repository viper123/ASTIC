/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

import astic.dpm.parsers.lang.WordNet;
import astic.drm.Summary;
import astic.io.db.DBHelper;
import astic.io.db.IDBListener;
import astic.io.db.Request;
import astic.io.db.RequestQueue;
import astic.io.db.ResultParser;
import edu.smu.tspell.wordnet.SynsetType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Elvis
 */
public class ClusteringLogicL1 extends ClusteringLogic<Summary,ArrayList<Summary> > {

    final double THRESHOLD = 0.8 ;
    RequestQueue mQueue ;
    Summary current ;
    public void setCurrent(Summary s)
    {
        this.current = s;
    }
    @Override
    public Summary apply(String path, ArrayList<Summary> param) 
    {
        
        Summary s2 = new Summary();
        s2.document = current.document;
        s2.weightMatrix = new HashMap<>();
        for(String word:current.weightMatrix.keySet())
        {
            if(!WordNet.validate(word,SynsetType.NOUN))
                continue ;
            int dw,D ;
            double tf ;
            dw = findDw(word, param);
            tf = findTf(word, current.weightMatrix);
            D = param.size();
            double tfidf = (double)(tf*Math.log((double)D/dw)) ;
            //System.out.println("TFIDF:"+word+" "+tfidf);
            if(tfidf>=THRESHOLD)
               s2.weightMatrix.put(word, current.weightMatrix.get(word)); 
         }
         return s2;
    }
    
    public double findTf(String word,HashMap<String,Double> hash)
    {
        double weight = 0;
        weight =hash.get(word);
        return weight /hash.size();
    }
    
    public int findDw(String word,ArrayList<Summary> param)
    {
        int k = 0;
        for(Summary s:param)
        {
            for(String w:s.weightMatrix.keySet())
            {
                if(w.equals(word))
                {
                    k++;
                }
            }
        }
        return k;
    }
    
//     mQueue = DBHelper.getInstance().createRequestQueue();
//        try{
//            String sql = "SELECT distinct(path) from bw";
//            PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
//            Request<ArrayList<String>> r = new Request<ArrayList<String>>(pst,new IDBListener<ArrayList<String>>() {
//
//                @Override
//                public void onSucces(ArrayList<String> result) 
//                {
//                    
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    e.printStackTrace();
//                }
//            },new ResultParser<ArrayList<String>>(){
//
//                @Override
//                public ArrayList<String> parse(ResultSet rs) 
//                {
//                    ArrayList<String> files  = new ArrayList<String>();
//                    try{
//                    while(rs.next())
//                    {
//                        files.add(rs.getString("PATH"));
//                    }
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                    return files;
//                }
//                
//            });
//            mQueue.addRequest(r);
//        }catch(Exception e){
//            e.printStackTrace();
//        }
    
    public void haveFiles(ArrayList<String> files)
    {
//        for(String file:files)
//        {
//            String sql = "SELECT word,weight from bw where path=?";
//            PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
//            pst.setString(1, file);
//            Request<HashMap<String,Integer>> r = new Request<HashMap<String, Integer>>(pst, new IDBListener<HashMap<String,Integer>>() {
//
//                @Override
//                public void onSucces(HashMap<String,Integer> result) {
//                    
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    e.printStackTrace();
//                }
//            }, new ResultParser<HashMap<String, Integer>>() {
//                @Override
//                public HashMap<String, Integer> parse(ResultSet rs) {
//                    
//                    HashMap<String,Integer> res = new HashMap<>();
//                    try{
//                        while(rs.next())
//                        {
//                            res.put(rs.getString("WORD"), rs.getInt("WEIGHT"));
//                        }
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//                    return res;
//                }
//            });
//            mQueue.addRequest(r);
//        }
    }
    public void haveWm(HashMap<String,Integer> wm)
    {
        
    }
}
