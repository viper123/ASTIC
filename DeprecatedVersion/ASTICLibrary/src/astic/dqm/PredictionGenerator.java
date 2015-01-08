/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dqm;

import astic.dpm.clusterlogic.PredictionLogic;
import astic.io.db.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public class PredictionGenerator extends ResultParser<ArrayList<String>> implements IDBListener<ArrayList<String>>  {
    private IQueryresult mCallback;
    private String mQuery ;
    
    public void predict(IQueryresult result,String query)
    {
        this.mCallback = result;
        this.mQuery = query;
        RequestQueue rq = DBHelper.getInstance().createRequestQueue();
        String sql = "select word from bw where letter=?";
        try{
            PreparedStatement pst = DBHelper.getInstance().getPreparedStatement(sql);
            pst.setString(1, query.substring(0,1));
            Request<ArrayList<String>> r = new Request<ArrayList<String>>(pst, this, this) ;
            rq.addRequest(r);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void onSucces(ArrayList<String> result) 
    {
       mCallback.onResult(new PredictionLogic().filterPredictions(mQuery, result));
    }

    public void onError(Exception e) 
    {
        e.printStackTrace();
        mCallback.onResult(new ArrayList<String>());
    }

    @Override
    public ArrayList<String> parse(ResultSet rs) 
    {
        ArrayList<String> res = new ArrayList<>();
        try{
        while(rs.next())
        {
            String word = rs.getString("word");
            res.add(word);
        }
        }catch(Exception e){}
        return res;
    }
}
