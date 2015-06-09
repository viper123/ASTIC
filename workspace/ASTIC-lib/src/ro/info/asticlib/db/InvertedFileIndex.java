package ro.info.asticlib.db;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class InvertedFileIndex implements Serializable {

	private static final long serialVersionUID = 283468149803836530L;
	public HashMap<String,List<String>> map;
	
	public InvertedFileIndex() {
		map = new HashMap<>();
	}
	
	public List<String> getFilesContainingWord(String word){
		if(map.containsKey(word)){
			return map.get(word);
		}
		return null;
	}
}
