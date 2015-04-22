package ro.info.asticlib.tests;

import java.io.File;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.db.BaseDao;
import ro.info.asticlib.db.Dao;

public class ClutersTest {

	Dao dao = new Dao();
	
	public void testGetLastId(){
		
		int id = dao.getLastClusterId();
		System.out.println("Last id: "+id);
		id++;
		dao.saveCluster(id, "a file");
		id = dao.getLastClusterId();
		id++;
		dao.saveCluster(id, "another file");
		id++;
		dao.saveCluster(id, "ok i got it");
		System.out.println("last id = "+id);
		
	}
	
	public void testAllClusters(){
		List<Cluster> clusters = dao.getAllClusters();
		for(Cluster cluster:clusters){
			System.out.println("Cluster:"+cluster.id);
		}
	}
	
	public void selectAllFromClusters(){
		insertDummyData();
		String select = "select * from bw";
		Dao dao = new Dao();
		try{
			Statement stmt = BaseDao.connection.createStatement();
			ResultSet result = stmt.executeQuery(select);
			while(result.next()){
				System.out.println(result.getString("FILE_PATH"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void insertDummyData(){
		Dao dao = new Dao();
		int id = dao.getLastClusterId();
		dao.saveCluster(id++, "test1");
		dao.saveCluster(id, "test2");
		dao.saveCluster(id, "test3");
		HashMap<String, Float> wordWeightMap = new HashMap<String, Float>();
		wordWeightMap.put("android", 0.2f);
		wordWeightMap.put("audi", 0.4f);
		wordWeightMap.put("black", 0.6f);
		dao.saveWords("test1", wordWeightMap,6);
		dao.saveWords("test3", wordWeightMap,6);
		wordWeightMap = new HashMap<String, Float>();
		wordWeightMap.put("bojack", 0.4f);
		wordWeightMap.put("bujor", 0.8f);
		dao.saveWords("test2", wordWeightMap,2);
	}
}
