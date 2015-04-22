package ro.info.asticlib.clustering;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro.info.asticlib.math.Math;

public class Cluster implements Cloneable {

	public int id;
	
	public HashMap<String, Float> wordWeightMap;
	public HashMap<String, Set<String>> fileWordMap;
	public List<String> preview;
	
	public Cluster(){
		fileWordMap = new HashMap<String, Set<String>>();
		wordWeightMap = new HashMap<String, Float>();
	}
	
	public Cluster(int id){
		this();
		this.id = id;
	}
	
	/**
	 * Creez un cluster dintr-o mapare cuvant,greutate pentru un fisier
	 * @param file
	 * @param wordTfMap
	 */
	public Cluster(int id,String file,HashMap<String,Float> wordTfMap){
		this(id);
		//creaza o lista cu toate cuvintele si adaugala la fileWordMap
		fileWordMap.put(file, wordTfMap.keySet());
		//seteaza wordWeightMap
		this.wordWeightMap = wordTfMap;
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
			float currentTf = getTF(word, newCluster.wordWeightMap);
			newCluster.wordWeightMap.put(word, (currentTf + other.wordWeightMap.get(word)));
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
	
	public boolean containsTheSameFiles(Cluster other){
		if(fileWordMap.size()<=other.fileWordMap.size()){
			for(String file:fileWordMap.keySet()){
				if(!other.fileWordMap.keySet().contains(file)){
					return false;
				}
			}	
		}else{
			for(String file:other.fileWordMap.keySet()){
				if(!fileWordMap.keySet().contains(file)){
					return false;
				}
			}
		}
		
		return true;
	}
	
	private double getDistanceCosine(Cluster other){
		
		Set<String> wordSet = getWordSet(wordWeightMap, other.wordWeightMap);
		
		double []c1Vect = new double[wordSet.size()];
		double []c2Vect = new double[wordSet.size()];
		int k = 0;
		for(String word:wordSet){
			c1Vect[k] = getTF(word, wordWeightMap);
			c2Vect[k++] = getTF(word, other.wordWeightMap);
		}
		return Math.computeCosine(c1Vect, c2Vect);
	}
	
	private Set<String> getWordSet(HashMap<String, Float> map1,
			HashMap<String, Float> map2){
		 
		Set<String> wordSet = new HashSet<String>();
		wordSet.addAll(map1.keySet());
		wordSet.addAll(map2.keySet());
		return wordSet;
	}
	
	private float getTF(String word,HashMap<String, Float> map){
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
