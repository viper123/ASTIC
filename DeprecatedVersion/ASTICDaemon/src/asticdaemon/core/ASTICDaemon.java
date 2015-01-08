/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package asticdaemon.core;

import astic.dpm.ClusteringLevel;
import astic.dpm.CroiseRequest;
import astic.dpm.clusterlogic.*;
import astic.dpm.croisers.CroiserL0;
import astic.dpm.croisers.CroiserL1;
import astic.dpm.croisers.*;
import astic.dpm.parsers.TxtParser;
import astic.dpm.parsers.lang.WordNet;
import astic.dqm.IQueryresult;
import astic.dqm.PredictionGenerator;
import astic.dqm.Query;
import astic.drm.Cluster;
import astic.drm.Summary;
import astic.io.db.DBHelper;
import astic.io.db.IdGenerator;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author Elvis
 */
public class ASTICDaemon {

    private static String ROOT_NODE = "D:\\Documente";
    private static String test_node = "d://New Folder";
    private static String path   = "D:\\Documente\\Church_tools\\biblia\\NFO\\readme.txt";
    public static void main(String[] args) 
    {
        //prepareDataBase();
        //query();
        //testConnection();
        //testDB();
        //WordNet.testWordNet("fly");
        //testWordNet();
        //testSummary();
        //initLevel1();
        //installAnaliser();
        //testCosine();
        //testClusteringL2();
        //testIdGenerator();
        //testWordExpansion();
        //testClusteringLogicL3();
        testPredictions();
    }
    
    private static void installAnaliser()
    {
        final long time = System.currentTimeMillis() ;
        final InstallCroiser croiser = new InstallCroiser() ;
        croiser.add(new CroiseRequest(new File(ROOT_NODE), Integer.MAX_VALUE, null));
        croiser.setCroiseCallback(new ICroiseCallback() {

            @Override
            public void OnDone() {
                croiser.stopCustom();
                System.out.println("Croise complete in "+(System.currentTimeMillis() - time)+" ms");
            }

            @Override
            public void OnProgress(String filename) {
                System.out.println(filename);
            }
        });
        croiser.start();
    }
    
    private static void testSummary()
    {
        String path   = "D:\\Documente\\Church_tools\\biblia\\NFO\\readme.txt";
        TxtParser parser= new TxtParser();
        ClusteringLogic<Summary,String[]> logic = new ClusteringLogicL0();
        File file = new File(path);
        String [] words = parser.getWords(file);
        Summary summary = logic.apply(file.getAbsolutePath(), words);
        for(String s:summary.weightMatrix.keySet())
            System.out.println("Word:"+s+" "+summary.weightMatrix.get(s));
    }
    
    private static void prepareDataBase()
    {
        final long time = System.currentTimeMillis() ;
        final CroiserL0 croiser = new CroiserL0();
        croiser.start();
        String path   = "D:\\Documente\\documentul vietii.txt";
        croiser.add(new CroiseRequest(new File(ROOT_NODE), Integer.MAX_VALUE,ClusteringLevel.Level0));
        croiser.setCroiseCallback(new ICroiseCallback() {

            @Override
            public void OnDone() {
                croiser.stopCustom();
                System.out.println("Croise complete in "+(System.currentTimeMillis() - time)+" ms");
            }

            @Override
            public void OnProgress(String filename) {
                System.out.println(filename);
            }
        });
    }
    
    private static void initLevel1()
    {
        CroiserL1 croiser = new CroiserL1();
        croiser.croise();
        
    }
    
    private static void testDB()
    {
        System.out.println("Testing db");
        DBHelper db = DBHelper.getInstance();
        System.out.println("Have instance");
        db.getDbConnection() ;
        System.out.println("Have connection");
        
    }
    
    private static void query()
    {
        while(true)
        {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter query:\n");
        String word = sc.next();
        long time = System.currentTimeMillis();
        Query q= new Query();
        q.alg2(word,System.currentTimeMillis());
        }
    }
    
    private static void testWordNet()
    {
        
        while(true)
        {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter query:\n");
        String word = sc.next();
        long time = System.currentTimeMillis();
            boolean isValid = WordNet.validate(word);
            System.out.println(isValid+" in "+(System.currentTimeMillis()-time)+" ms");
        }
    } 
    
    private static void testConnection()
    {
        System.out.println("-------- Oracle JDBC Connection Testing ------");
 
		try {
 
			Class.forName("oracle.jdbc.driver.OracleDriver");
 
		} catch (ClassNotFoundException e) {
 
			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
			return;
 
		}
 
		System.out.println("Oracle JDBC Driver Registered!");
 
		Connection connection = null;
 
		try {
 
			connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "system",
					"trick123");
 
		} catch (SQLException e) {
 
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
 
		}
 
		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
	}
    
    public static void testCosine()
    {
        String [] words1 ={"July","loves","me","more","than","Linda","loves","me"};
        String [] words2 ={"Jane","likes","me","more","than","July","loves","me"};
        Summary s = new Summary();
        s.weightMatrix = new HashMap<>();
        for(String word:words1)
            if(s.weightMatrix.containsKey(word))
                s.weightMatrix.put(word, s.weightMatrix.get(word)+1);
            else
                s.weightMatrix.put(word, 1.0);
        Cluster c = new Cluster();
        c.files = new HashMap<>();
        c.files.put("file1", new HashMap<String,Double>());
        for(String word:words2)
            if(c.files.get("file1").containsKey(word))
                c.files.get("file1").put(word, c.files.get("file1").get(word) +1);
            else
                c.files.get("file1").put(word, 1.0);
        System.out.println(""+ClusteringLogicL2.cosine(s, c));
    }
    
    public static void testClusteringL2()
    {
        CroiserL2 croiser = new CroiserL2();
        croiser.croise();
    }
    
    public static void testIdGenerator()
    {
        for(int i=0;i<100;i++)
            System.out.println(""+IdGenerator.generateUniqueID());
    }
    
    public static void testWordExpansion()
    {
        
        Scanner sc = new Scanner(System.in);
        while(true)
        {
        String word = sc.next();
        ArrayList<String> words = WordNet.expand(word);
        for(String s:words)
            System.out.println(s);
        }
    }
    
    public static void testClusteringLogicL3()
    {
        while(true)
        {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter query:\n");
        String word = sc.next();
        long time = System.currentTimeMillis();
        Query q= new Query();
        q.alg2(word, System.currentTimeMillis());
        q.alg3(word);
        }
    }
    
    public static void testPredictions()
    {
        while(true)
        {
        Scanner sc = new Scanner(System.in);
        PredictionGenerator pg =new PredictionGenerator();
        System.out.println("Enter query:\n");
        String word = sc.next();
        long time = System.currentTimeMillis();
        pg.predict(new  IQueryresult() {
                @Override
                public void onResult(ArrayList<String> res) {
                    for(int i=0;i<res.size();i++)
                        System.out.println(""+res.get(i));
                }
            }, word);
        }
    }
    
    
    
    
    
   
    
    
    
    
    
    
    
    
}
