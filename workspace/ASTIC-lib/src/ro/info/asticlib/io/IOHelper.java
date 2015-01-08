/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ro.info.asticlib.io;

import java.io.*;
import java.util.Scanner;

/**
 * Class that serialize and de-serialize objects ;
 * @author Rusu Elvis
 */
public class IOHelper {
    private static boolean debug = false;
    /**
     * Serialize an object .
     * @param path <code>String</code> the path where the file will be saved ;
     * @param object <code> Serializable</code> the object that will be serialized ;
     */
    public static void saveObject(String path,Serializable object)
    {
        synchronized(path)
        {
        try
        {
            long time = System.currentTimeMillis();
            log("Start saving object "+path);
            File file = new File(path);
            if(!file.exists())
                file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(object);
            out.close();
            fileOut.close();
            log("Object saved in "+(System.currentTimeMillis()-time)+" ms");
            
        }catch(IOException i)
        {
          i.printStackTrace();
        }
        }
    }
    /**
     * De-Serialize an object.
     * @param path <code>String</code> the path where the file is saved ;
     * @return The serialized object or <code>NULL</code> if the object does not exist.
     */
    public static Serializable loadObject(String path)
    {
        synchronized(path)
        {
        log("Start loading object "+path);
        long time = System.currentTimeMillis();
        FileInputStream fileIn = null;
        ObjectInputStream in = null;
        try
        {
             fileIn= new FileInputStream(path);
             in= new ObjectInputStream(fileIn);
             
             log("Object loadead in "+(System.currentTimeMillis()-time) + " ms");
             return (Serializable) in.readObject();
        }catch(IOException i)
        {
             
        }catch(ClassNotFoundException c)
        {
            c.printStackTrace();
        }
        finally{
            try{
            in.close();
             fileIn.close();
            }catch(Exception e){
                
            }
        }
        return null;
        }
    }
    
    public static String readContentFromFile(File file)
    {
        synchronized(file.getAbsoluteFile())
        {
        log("Read content:"+file.getAbsolutePath());
        StringBuilder text = new StringBuilder();
        try (Scanner scanner = new Scanner(new FileInputStream(file))) 
        {
             while (scanner.hasNextLine()){
                 String line = scanner.nextLine() + "\n";
                 text.append( line);
             }
        }catch(Exception e){
            e.printStackTrace();
        }
            return text.toString();
        }
    }
    
    
    
    public static void log(String message)
    {
        if(debug)
            System.out.println("IOHelper:"+message);
    }
    
    public static void error(String message)
    {
        if(debug)
            System.err.println("IOHelper:"+message);
    }
}
