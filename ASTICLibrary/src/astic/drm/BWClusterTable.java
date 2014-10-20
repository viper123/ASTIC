/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.drm;

import astic.io.DataManager;
import astic.io.db.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Elvis
 */
public class BWClusterTable {
    
    //private ConcurrentHashMap<String,HashMap<String,Integer>> cluster;
    private ConcurrentHashMap<String,String> clusters ;
    private static  BWClusterTable mInstance;
    private RequestQueue mQueue;
    private boolean debug=true;
    private BWClusterTable()
    {
        clusters = new ConcurrentHashMap<>();
        mQueue = DBHelper.getInstance().createRequestQueue();
    }
    
    public static BWClusterTable getInstance()
    {
        if(mInstance==null)
            mInstance = new BWClusterTable();
        
        return mInstance;
    }
    
    public synchronized void  add(Summary summary)
    {
        for(String word:summary.weightMatrix.keySet())
            this.addToDb(summary.document, word,summary.weightMatrix.get(word));
    }
    
    private void addToDb(final String path,final String word,final double initialWeight)
    {
        //log("Adding to DB "+word +","+initialWeight);
        try{
        String sql = "INSERT INTO bw VALUES (?,?,?,?)";
        PreparedStatement pst = DBHelper.getInstance().
                getDbConnection().prepareStatement(sql);
        pst.setString(1, makeKey(word));
        pst.setString(2, path);
        pst.setString(3, word);
        pst.setDouble(4, initialWeight);
        Request r = new Request<Void>(pst, new IDBListener() {
                @Override
                public void onSucces(Object result) {
                    
                }
                @Override
                public void onError(Exception e) {
                    e.printStackTrace();
                }
            }, new ResultParser<Void>() {

                @Override
                public Void parse(ResultSet rs) {
                    return null;
                }
            });
        mQueue.addRequest(r);
        }catch(SQLException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public synchronized boolean add(String path,String word,int initialWeight)
    {
        System.out.println("Adding word: "+word);
        word = word.toLowerCase();
        HashMap<String,HashMap<String,Integer>> cluster = reanimateCluster(word,false);
        HashMap<String,Integer> wordsWeightMap = cluster.get(path);
        if(wordsWeightMap==null)
        {
            wordsWeightMap = new HashMap<>();
            System.out.println("    construct words weight matrix");
        }
        else
        {
            System.out.println("    words matrix of "+wordsWeightMap.size());
        }
        try{
            int weight = wordsWeightMap.get(word);
            int newWeight = initialWeight+weight;
            wordsWeightMap.put(word,newWeight );
            System.out.println(" new weight added "+newWeight);
        }catch(Exception e){
            System.out.println("    first word apperance "+initialWeight);
            wordsWeightMap.put(word, initialWeight);
        }
        cluster.put(path, wordsWeightMap);
        cryogenize(cluster, word);
        clusters.put(makeKey(word), makeKey(word));
        return true;
    }
    
    public HashMap<String,HashMap<String,Integer>> reanimateCluster(String word,boolean isLetter )
    {
        try{
            HashMap<String,HashMap<String,Integer>> c =  (HashMap<String, HashMap<String, Integer>>) 
                DataManager.getInstance().get(isLetter?word:makeKey(word));
            if(c == null)
            {
                c = new HashMap<>();
                System.out.println("    reanimation fail "+makeKey(word));
            }
            else
            {
                System.out.println("    reanimation success "+makeKey(word));
            }
            return c ;
        }catch(Exception e){
            System.out.println("    reanimation exception "+e.getMessage());
            return new HashMap<>();
        }
    }
    
    private void cryogenize(HashMap<String,HashMap<String,Integer>> cluster,String word)
    {
        DataManager.getInstance().put(makeKey(word),cluster );
        System.out.println("    cryogenization complete");
    }
    
    private void saveClusters()
    {
//        for(String clKey:clusters.keySet())
//        {
//            HashMap<String,HashMap<String,Integer>> cluster = reanimateCluster(clKey, true);
//            for(String path:)
//        }
    }
    
    private  String makeKey(String word)
    {
        
        return word.substring(0, 1).toLowerCase();
    }
    
    public void toReportFile(File file)
    {
        String report = "Report File:--generated at "+new Date(System.currentTimeMillis()).toString()+"\n";
        report = report + "Cluster,FilePath,word,weight";
        System.out.println("Clusters:"+clusters.size());
        for(String key:clusters.keySet())
        {
            HashMap<String,HashMap<String,Integer>> c = 
                    (HashMap<String,HashMap<String,Integer>>) DataManager.getInstance().get(key);
            if(c==null)
                continue;
            System.out.println("WordCluster: key"+key+" "+c.size());
            for(String path:c.keySet())
            {
                HashMap<String,Integer> weightMatrix = c.get(path);
                for(String word:weightMatrix.keySet())
                {
                    int weight = weightMatrix.get(word);
                    report += key+","+path+","+word+","+weight+"\n";
                }
            }
        }
        
        try{
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            PrintStream stream = new PrintStream(out);
            stream.print(report);
            stream.close();
            out.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Report File writed.");
    }
    
    private void log(String message)
    {
        if(debug)
            System.out.println(message);
    }
}
