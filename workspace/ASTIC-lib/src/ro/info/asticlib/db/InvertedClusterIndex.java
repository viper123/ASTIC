package ro.info.asticlib.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class InvertedClusterIndex implements Serializable {
	
	private static final long serialVersionUID = 283468149803836530L;
	public HashMap<String,List<Integer>> map;
	
	public InvertedClusterIndex() {
		map = new HashMap<>();
	}
	
	public List<Integer> getClustersContainingWord(String word){
		if(map.containsKey(word)){
			return map.get(word);
		}
		return null;
	}

}
