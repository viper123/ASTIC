package ro.info.asticlib.tests;

import java.io.File;
import java.util.HashMap;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.tags.TagsReader;
import ro.info.asticlib.tags.TagsReader.Callback;

public class TagsReaderTests {
	
	public void testGetTag(){
		
	}
	
	public void testGetTagPDFFile(){
		String filePath = "D://ROOT/06-LVintan.pdf";
		File file = new File(filePath);
		TagsReader reader = new TagsReader(file);
		reader.getMapTagWeight(new Callback() {
			
			@Override
			public void onDone(HashMap<String, Integer> map) {
				for(String key:map.keySet()){
					System.out.println(key+":"+map.get(key));
				}
			}
		});
	}
	
	private TagsReader optainTagsReader(){
		String path = Conf.TEST_FILE_2;
		return new TagsReader(new File(path));
	}
}
