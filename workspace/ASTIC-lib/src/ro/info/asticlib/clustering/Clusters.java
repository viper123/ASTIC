package ro.info.asticlib.clustering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.clustering.Cluster.DistanceFormula;
import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.db.Dao;

public class Clusters {
	
	
	public static Clusters instance(){
		return instance;
	}
	private static Clusters instance ;
	static{
		instance = new Clusters();
	}
	
	private Dao dao;
	
	private Clusters(){
		dao = new Dao();
	}
	
	/**
	 * Creez un cluster nou pentru fisier si maparea dintre cuvant,greutate
	 * parcurg toti clusterii si incerc sa integre noul cluster creat
	 * in caz ca nu exista alti clusteri sau nici unul nu este apropiat introduc clusterul 
	 * in colectie ca cluster idependent
	 * @param file
	 * @param wordMapWeight
	 */
	public void processFileWords(String file){
		HashMap<String, Float> wordWeightMap= dao.getWordsWeightForFile(file);
		//int totalWordsCount = wordWeightMap.size();
		Cluster newCluster = new Cluster(generateID(), file, wordWeightMap);
		boolean integrated = false;
		List<Cluster> allClusters = getAllClusters();
		allClusters = allClusters == null?new ArrayList<Cluster>():allClusters;
		//dao.saveWords(file, wordWeightMap,totalWordsCount); the words are already saved
		System.out.println("File:"+file);
		for(Cluster c:allClusters){
			double distance = c.getDistance(newCluster, DistanceFormula.Cosine);
			System.out.println("\tDistance from "+c.id +"=" + distance);	
			if(distance > Conf.ACCEPTABLE_DISTANCE){
				integrated = true;
				dao.saveCluster(c.id, file);
				dao.updateInvertedClusterIndex(c.id, wordWeightMap);
			}
		}
		if(!integrated){
			dao.saveCluster(newCluster.id, file);
			dao.updateInvertedClusterIndex(newCluster.id, wordWeightMap);
		}
		
	}

	
	public int generateID(){
		return dao.getLastClusterId() + 1;
	}
	
	/**
	 * Returneaza toti clusterii din baza de date.
	 * @return
	 */
	private List<Cluster> getAllClusters(){
		return dao.getAllClusters();
	}
	
	
}
