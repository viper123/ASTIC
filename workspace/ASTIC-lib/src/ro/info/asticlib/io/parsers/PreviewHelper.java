package ro.info.asticlib.io.parsers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.info.asticlib.clustering.Cluster;

public class PreviewHelper {
	
	private Map<String,List<String>> cache;
	
	public PreviewHelper() {
		cache = new HashMap<String, List<String>>();
	}

	public  List<String> getPreview(String file,String [] query){
		if(cache.containsKey(file)){
			return cache.get(file);
		}
		Parser parser = ParserFactory.getParser(new File(file));
		List<String> preview  = parser.getPreview(query);
		cache.put(file, preview);
		return preview;
	}
	
	public  boolean assignPreview(Cluster c,String[]query){
		boolean found = false;
		for(String file:c.fileWordMap.keySet()){
			List<String> preview = getPreview(file, query);
			if(!preview.isEmpty()){
				found = true;
			}
			c.previewMap.put(file, preview);
		}
		return found;
	}
}
