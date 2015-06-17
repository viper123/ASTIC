package ro.info.asticlib.clustering;

import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.db.Dao;

public class TfIdfCalculator {
	
	public List<Document> computeTfIdf(Dao dao){
		List<Document> docs = dao.getAllDocuments();
		int size = docs.size();
		int index = 0;
		for(Document d:docs){
			System.out.println("\t\t"+(index++)+" from "+ size);
			for(String word:d.tfMap.keySet()){
				double tf = (1+Math.log(d.weightMap.get(word))) ;
				double idf = Math.log(((float)docs.size()/(float)dao.getFileCountContaining(word)));
				d.tfidfMap.put(word, (float)(tf*idf));
			}
		}
		
		return docs;
	}
	
	public void computeTfIdf(List<Cluster> clusters,Dao dao){
		List<Cluster> allClusters = dao.getAllClusters();
		int size = allClusters.size();
		int k=0;
		for(Cluster c:clusters){
			k = 0;
			for(String word:c.wordWeightMap.keySet()){
				System.out.println(c.id+":"+k++);
				double tf = (1+Math.log(c.wordWeightMap.get(word))) ;
				double idf = Math.log(((float)size/(float)dao.getClusterCountContaining(word)));
				if(c.reprezentativeWordsMap == null){
					c.reprezentativeWordsMap = new HashMap<String, Float>();
				}
				if(!Double.isInfinite(tf*idf)){
					c.reprezentativeWordsMap.put(word, (float)(tf*idf));
				}else{
					System.out.println("is infite "+word);
				}
				
			}
		}
	}
}
