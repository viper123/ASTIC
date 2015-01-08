/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.drm;

import astic.io.db.DBHelper;
import astic.io.db.IDBListener;
import astic.io.db.Request;
import astic.io.db.RequestQueue;
import astic.io.db.ResultParser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

/**
 *
 * @author Elvis
 */
public class TwClusterTable {
    
    private static TwClusterTable mInstance ;
    private RequestQueue mQueue ;
    private static final int PRECISION_SCALE = 1000; 
    static
    {
        mInstance = new TwClusterTable();
    }
    
    public static TwClusterTable getInstance()
    {
        return mInstance;
    }
    
    private  TwClusterTable()
    {
        mQueue = DBHelper.getInstance().createRequestQueue();
    }
    
    
    public void add(Summary s)
    {
        //System.out.println("Adding summary to tw");
        try{
            String sql = "INSERT INTO tw VALUES(?,?,?)";
            for(String word:s.weightMatrix.keySet())
            {
                PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
                pst.setString(1, s.document);
                pst.setString(2, word);
                double d = s.weightMatrix.get(word);
                int dd = (int)d;
                System.out.println("TF:"+word+":"+dd);
                pst.setInt(3,dd);
                Request<Void> r = new Request<Void>(pst, new IDBListener<Void>() {

                    @Override
                    public void onSucces(Void result) 
                    {
                        //System.out.println("Insert Success");
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                }, new ResultParser<Void>(){

                    @Override
                    public Void parse(ResultSet rs) {
                        return null;
                    }
                    
                });
                mQueue.addRequest(r);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
