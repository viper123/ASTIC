package ro.info.asticlib.io.parsers;

import java.io.File;
import java.util.List;

import ro.info.asticlib.clustering.Cluster;

public class PreviewHelper {

	public static List<String> getPreview(String file,String [] query){
		Parser parser = ParserFactory.getParser(new File(file));
		return parser.getPreview(query);
		
	}
	
	public static void assignPreview(Cluster c,String[]query){
		for(String file:c.fileWordMap.keySet()){
			List<String> preview = getPreview(file, query);
			c.previewMap.put(file, preview);
		}
	}
}
