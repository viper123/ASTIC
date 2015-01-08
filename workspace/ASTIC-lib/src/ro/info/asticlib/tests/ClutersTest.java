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
		//insertDummyData();
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
		HashMap<String, Integer> wordWeightMap = new HashMap<String, Integer>();
		wordWeightMap.put("android", 2);
		wordWeightMap.put("audi", 4);
		wordWeightMap.put("black", 6);
		dao.saveTags("test1", wordWeightMap);
		wordWeightMap = new HashMap<String, Integer>();
		wordWeightMap.put("bojack", 4);
		wordWeightMap.put("bujor", 8);
		dao.saveTags("test2", wordWeightMap);
	}
}