package ro.info.asticlib.tests;

import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.db.Dao;

public class QueryTest {
	
	public  void queryTest(){
		Dao dao = new Dao();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("amic", 2);
		map.put("ana", 2);
		map.put("amar", 2);
		map.put("autor", 2);
		map.put("android", 2);
		map.put("arc", 2);
		map.put("ancora", 2);
		//dao.saveTags("undeva", map);
		//dao.saveCluster(0, "undeva");
		List<Cluster> results = dao.selectInClusters("a", 2, 5, true);
		for(Cluster c:results){
			System.out.println(""+c.id);
		}
	}

}
