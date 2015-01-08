/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package astic.drm;

import astic.io.DataManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Rusu Elvis
 */
public class BlackBox {
    
    private HashMap<String,Record> mData ;
    private final String DATA_KEY = "blackbox";
    private static BlackBox mInstance;
    
    public static BlackBox getInstance()
    {
        if(mInstance ==null)
            mInstance = new BlackBox();
        return mInstance ;
    }
    
    private BlackBox()
    {
        mData = (HashMap<String, Record>)DataManager.getInstance().get(DATA_KEY);
        if(mData ==null)
            mData = new HashMap<>();
    }
    
    public void putFile(String filepath,long timestamp)
    {
        Record r = mData.get(filepath);
        if(r!=null)
        {
            long summaryTimestamp = r.summryTimestamp;
            mData.put(filepath, new Record(filepath,timestamp,summaryTimestamp));
        }
        else
        {
            mData.put(filepath, new Record(filepath,timestamp,-1));
        }
    }
    
    public void putFile(File file)
    {
        putFile(file.getAbsolutePath(),file.lastModified());
    }
    
    public void putSummary(String filepath,long timestamp)
    {
        Record r = mData.get(filepath);
        if(r!=null)
        {
            long fileTimestamp = r.fileTimestamp;
            mData.put(filepath, new Record(filepath,fileTimestamp,timestamp));
        }
        else
        {
            mData.put(filepath, new Record(filepath,-1,timestamp));
        }
    }
    
    public void putSummary(File file)
    {
        putSummary(file.getAbsolutePath(), file.lastModified());
    }
    
    public void toReportFile(File file)
    {
        String report = "Report File:--generated at "+new Date(System.currentTimeMillis()).toString()+"\n";
        report = report + "FilePath,FileTimeStamp,SummaryTimeStamp\n";
        for(String key:mData.keySet())
        {
            Record r = mData.get(key);
            if(r!=null)
                report = report+r.toString();
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
    
    public void saveState()
    {
        DataManager.getInstance().put(DATA_KEY, mData);
    }
    
    private class Record implements Serializable{
        public String filePath;
        long fileTimestamp;
        long summryTimestamp;
        
        public Record(String filepath,long fileTimestamp,long summarytimestamp)
        {
            this.filePath = filepath;
            this.fileTimestamp = fileTimestamp ;
            this.summryTimestamp = summarytimestamp;
        }

        @Override
        public String toString() {
            return filePath+","+fileTimestamp+","+summryTimestamp+"\n";
        }
        
        
    }
}
