package ro.info.asticlib.clustering;

import java.util.List;

import ro.info.asticlib.db.Dao;

public class TfIdfCalculator {
	
	public List<Document> computeTfIdf(Dao dao){
		List<Document> docs = dao.getAllDocuments();
		for(Document d:docs){
			System.out.println("Compute tfidf for :"+d.path);
			for(String word:d.tfMap.keySet()){
				double tf = (1+Math.log(d.weightMap.get(word))) ;
				double idf = Math.log(((float)docs.size()/(float)dao.getFileCountContaining(word)));
				d.tfidfMap.put(word, (float)(tf*idf));
				//System.out.println("\tword:"+word+"\n\ttf:"+tf+" idf:"+idf+" * :"+(tf*idf));
			}
		}
		
		return docs;
	}
}
