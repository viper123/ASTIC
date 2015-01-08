/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

import astic.dpm.parsers.lang.WordNet;
import astic.dqm.IQueryresult;
import astic.drm.Cluster;
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

/**
 *
 * @author Elvis
 */
public class ClusteringLogicL3 {
    
    RequestQueue mQueue ;
    int index = 0;
    IQueryresult callback;
    ArrayList<Cluster> clusters  = new ArrayList<>();
    Object CL_LOCK = new Object();
    
    public void apply(String _word,IQueryresult callback)
    {
        this.callback = callback;
        apply(_word, 0, 5);
    }
    
    private void apply(String _word,final int currentLevel,final int maxLevel)
    {
        if(currentLevel>=maxLevel)
        {
            return;
        }
        final ArrayList<String> words = WordNet.expand(_word);
        mQueue = DBHelper.getInstance().createRequestQueue();
        index = 0;
        for(String word:words)
        {
        try{
            String sql = "select c.code ,c.path, t.word, t.tf from ct c, tw t where c.path = t.path and t.word = ?";
            PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
            pst.setString(1, word);
            Request<ArrayList<Cluster>> request = new Request<ArrayList<Cluster>>(pst, new IDBListener<ArrayList<Cluster>>() {

                @Override
                public void onSucces(ArrayList<Cluster> result) 
                {
                    postResult(result, index++, words.size(),currentLevel,maxLevel);
                }

                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            }, new ResultParser<ArrayList<Cluster>>() {

                @Override
                public ArrayList<Cluster> parse(ResultSet rs) 
                {
                    ArrayList<Cluster> clusters = new ArrayList<Cluster>();
                    try{
                    while(rs.next())
                    {
                      
                        int code = rs.getInt("code");
                        String path = rs.getString("path");
                        String word = rs.getString("word");
                        int weight = rs.getInt("tf");
                        Cluster c = findCluster(code, clusters);
                        if(c==null)
                        {
                            c= new Cluster();
                            c.files = new HashMap<String, HashMap<String, Double>>();
                            c.files.put(path, new HashMap<String,Double>());
                            c.files.get(path).put(word, (double)weight);
                            c.code = code ;
                            clusters.add(c);
                        }
                        else
                        {
                            if(c.files.containsKey(path))
                            {
                                c.files.get(path).put(word,(double)weight);
                            }
                            else
                            {
                                c.files.put(path, new HashMap<String,Double>());
                                c.files.get(path).put(word, (double)weight);
                            }
                        }
                        
                    }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    return clusters;
                }
            });
            mQueue.addRequest(request);
        }catch(Exception e){
            e.printStackTrace();
        }
        }
    }
    
    private Cluster findCluster(int id,ArrayList<Cluster> list)
    {
        for(Cluster c:list)
            if(c.code == id)
                return c;
        return null;
    }
    
    private void postResult(ArrayList<Cluster> list,int index,int lenght,int currentLevel,int maxLevel)
    {
        for(Cluster c:list)
        {
             boolean found = false ;
             for(Cluster c2:clusters)
                 if(c2.code == c.code)
                    found = true ;
                    if(!found)
                       clusters.add(c);
        }
        if(index == lenght-1)
        {
            clusters.removeAll(excluded);
            if(clusters.size()>0)
                createDg(currentLevel,maxLevel);
        }
        
    }
    
    private double cosine(Cluster c1,Cluster c2)
    {
        ArrayList<String> wordSet = getWordSet(c1, c2);
        if(wordSet.size()>0)
        {
            double [] c1v = new double[wordSet.size()];
            double [] c2v = new double[wordSet.size()];
            for(int i=0;i<wordSet.size();i++)
            {
                c1v[i] = getFrquencyForWord(c1, wordSet.get(i));
                c2v[i] = getFrquencyForWord(c2, wordSet.get(i));
            }
            return computeCosine(c1v, c2v);
        }
        return 0;
    }
    
    public static double computeCosine(double [] a,double []b)
    {
        if(a.length!=b.length)
            return 0 ;
        double topSum = 0,leftBSum=0,rightBSum=0;
        for(int i=0;i<a.length;i++)
        {
            topSum+=(a[i]*b[i]);
            leftBSum+=Math.pow(a[i],2) ;
            rightBSum+=Math.pow(b[i], 2);
        }
        return topSum / (Math.sqrt(leftBSum)*Math.sqrt(rightBSum)) ;
    }
    
    public double getFrquencyForWord(Cluster c,String word)
    {
        double f  = 0;
        for(String file:c.files.keySet())
        {
            if(c.files.get(file).containsKey(word))
                f+=c.files.get(file).get(word) ;
        }
        return f;
    }
    
    public  ArrayList<String> getWordSet(Cluster c1,Cluster c2)
    {
        ArrayList<String> out = new ArrayList<>();
        for(String file:c1.files.keySet())
            for(String word:c1.files.get(file).keySet())
            {
                String _word = word.toLowerCase();
                if(!out.contains(_word)&&!out.contains(word))
                    out.add(word);
            }
        for(String file:c2.files.keySet())
            for(String word:c2.files.get(file).keySet())
            {
                String _word = word.toLowerCase();
                if(!out.contains(_word)&&!out.contains(word))
                    out.add(word);
            }
        return out;
    }
    public ArrayList<Cluster> excluded = new ArrayList<>();
    private void createDg(int currentLevel,int maxLevel)
    {
       synchronized(CL_LOCK)
       {
       ArrayList<Cluster> same = new ArrayList<>();
        for(Cluster c:clusters)
            for(Cluster c2:clusters)
                if(c!=c2)
                {
                    if(cosine(c2, c2)>= 0.5)
                    {
                        if(!same.contains(c))
                            same.add(c);
                        if(!same.contains(c2))
                            same.add(c2);
                    }
                        
                }
        if(!same.isEmpty())
        {
            excluded.addAll(same);
            for(Cluster c:same)
            {
                
                for(String file:c.files.keySet())
                {
                    System.out.println(currentLevel+createIdent(currentLevel)+file);
                    for(String word:c.files.get(file).keySet())
                    {
                        apply(word,currentLevel+1,maxLevel);
                    }
                }
            }
        }
        }
    }
    
    private String createIdent(int level)
    {
        String res = " ";
        for(int i=0;i<level;i++)
            res += " ";
        return res;
    }
}
