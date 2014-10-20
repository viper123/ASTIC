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
import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public class CTClusterTable {
    
    private static CTClusterTable mInstance;
    private RequestQueue mQueue ;
    static
    {
        mInstance = new CTClusterTable();
    }
    
    public static CTClusterTable getInstance()
    {
        return mInstance;
    }
    
    private CTClusterTable()
    {
        mQueue = DBHelper.getInstance().createRequestQueue();
    }
    
    public void add(ArrayList<Cluster> clusters)
    {
        String insertSql = "insert into ct values(?,?)";
        for(Cluster c:clusters)
        {
            for(String path:c.files.keySet())
            {
                try{
                    PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(insertSql);
                     pst.setDouble(1,c.code );
                     pst.setString(2, path);
                     Request<Void>  request = new Request<Void>(pst, new IDBListener<Void>() {
                        @Override
                        public void onSucces(Void result) {
                            
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
                     mQueue.addRequest(request);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
//            PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(insertSql);
//            pst.setDouble(1,c.code );
//            pst.setString(2, c.);
//            try{
//                Request<Void> request = new Request<Void>(null, null, null);
//            }catch(Exception e){
//                e.printStackTrace();
//            }
        }
    }
}
