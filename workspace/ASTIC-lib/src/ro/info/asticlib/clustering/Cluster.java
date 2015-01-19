package ro.info.asticlib.clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import ro.info.asticlib.math.Math;

public class Cluster implements Cloneable {

	public int id;
	
	public HashMap<String, Integer> wordWeightMap;
	public HashMap<String, Set<String>> fileWordMap;
	
	public Cluster(){
		fileWordMap = new HashMap<String, Set<String>>();
		wordWeightMap = new HashMap<String, Integer>();
	}
	
	public Cluster(int id){
		this();
		this.id = id;
	}
	
	/**
	 * Creez un cluster dintr-o mapare cuvant,greutate pentru un fisier
	 * @param file
	 * @param wordWeightMap
	 */
	public Cluster(int id,String file,HashMap<String,Integer> wordWeightMap){
		this(id);
		//creaza o lista cu toate cuvintele si adaugala la fileWordMap
		fileWordMap.put(file, wordWeightMap.keySet());
		//seteaza wordWeightMap
		this.wordWeightMap = wordWeightMap;
	}
	
	public Cluster add(Cluster other){
		Cluster newCluster = new Cluster();
		newCluster.fileWordMap.putAll(fileWordMap);
		newCluster.wordWeightMap.putAll(wordWeightMap);
		//adauga la fileWordMap
		for(String file:other.fileWordMap.keySet()){
			newCluster.fileWordMap.put(file, other.fileWordMap.get(file));
		}
		//adauga la wordWeightMap
		for(String word:other.wordWeightMap.keySet()){
			double currentWeight = getWeight(word, newCluster.wordWeightMap);
			newCluster.wordWeightMap.put(word,(int) (currentWeight + other.wordWeightMap.get(word)));
		}
		return newCluster;
	}
	
	public double getDistance(Cluster other,DistanceFormula formula){
		
		switch (formula) {
		case Cosine :
			return getDistanceCosine(other);

		}
		
		return 0f;
	}
	
	private double getDistanceCosine(Cluster other){
		
		Set<String> wordSet = getWordSet(wordWeightMap, other.wordWeightMap);
		
		double []c1Vect = new double[wordSet.size()];
		double []c2Vect = new double[wordSet.size()];
		int k = 0;
		for(String word:wordSet){
			c1Vect[k] = getWeight(word, wordWeightMap);
			c2Vect[k++] = getWeight(word, other.wordWeightMap);
		}
		return Math.computeCosine(c1Vect, c2Vect);
	}
	
	private Set<String> getWordSet(HashMap<String, Integer> map1,
			HashMap<String, Integer> map2){
		 
		Set<String> wordSet = new HashSet<String>();
		wordSet.addAll(map1.keySet());
		wordSet.addAll(map2.keySet());
		return wordSet;
	}
	
	private double getWeight(String word,HashMap<String, Integer> map){
		if(map.containsKey(word)){
			return map.get(word);
		}
		return 0;
	}
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		Cluster c = new Cluster();
		c.fileWordMap = fileWordMap;
		c.wordWeightMap = wordWeightMap;
		c.id = id;
		return c;
	}
	
	public enum DistanceFormula {
		Cosine
	}
}
