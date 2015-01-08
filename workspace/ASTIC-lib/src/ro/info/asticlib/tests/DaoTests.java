package ro.info.asticlib.tests;

import java.io.File;
import java.util.HashMap;

import ro.info.asticlib.db.Dao;

public class DaoTests {

	Dao dao =new Dao();
	public void testSaveTags(){
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("android", 56);
		map.put("auto", 200);
		map.put("money", 46);
		File file = new File("D://test.txt");
		dao.saveTags(file,map);
	}
	
}
