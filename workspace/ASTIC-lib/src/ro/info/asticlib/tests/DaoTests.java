package ro.info.asticlib.tests;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.clustering.AcceptanceRule;
import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.clustering.Document;
import ro.info.asticlib.clustering.TfIdfAcceptanceRule;
import ro.info.asticlib.clustering.TfIdfCalculator;
import ro.info.asticlib.db.Dao;

public class DaoTests {

	Dao dao =new Dao();
	public void testSaveTags(){
		HashMap<String, Float> map = new HashMap<String, Float>();
		map.put("android", 0.56f);
		map.put("auto", 0.200f);
		map.put("money", 0.46f);
		File file = new File("D://test.txt");
		dao.saveWords(file,map,56);
	}
	
	public void testGetAllDocuments(){
		List<Document> docs = dao.getAllDocuments();
		for(Document d:docs){
			System.out.println(d.toString());
		}
	}
	
	public void testGetFileContaining(){
		String word = "ana";
		System.out.println(dao.getFileCountContaining(word));
	}
	
	public void testUpdateTFIDF(){
		List<Document> docs;
		TfIdfCalculator calc = new TfIdfCalculator();
		docs = calc.computeTfIdf(dao);
		System.out.println("TFIDF computed");
		AcceptanceRule rule = new TfIdfAcceptanceRule();
		for(Document d:docs){
			System.out.println("update tfidf for: "+d.path);
			dao.updateTfIdf(d.tfidfMap, docs.size(), rule);
		}
	}
	
	public void testSelect(){
		Dao dao = new Dao();
		List<Cluster> cls = dao.selectInClusters(new String []{"numere","prime"}, 0, 0, true);
		System.out.println(cls.size());
	}
	
}
