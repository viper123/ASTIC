package ro.info.asticlib.tests;

import java.io.File;
import java.util.HashMap;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.word.WordProcesor;
import ro.info.asticlib.word.WordProcesor.Callback;

public class TagsReaderTests {
	
	public void testGetTag(){
		final String filePath = "D:\\test.txt";
		final File file = new File(filePath);
		WordProcesor reader = new WordProcesor(file);
		reader.getMapWordWeight(new Callback() {
			@Override
			public void onDone(HashMap<String, Float> map,int size) {
				for(String key:map.keySet()){
					System.out.println(key+":"+map.get(key));
				}
				System.out.println("File size:"+file.length()/1024);
				System.out.println("Total:"+map.size());
			}
		});
	}
	
	public void testGetTagPDFFile(){
		String filePath = "D:\\Docs\\TEST\\birds1.txt";
		File file = new File(filePath);
		WordProcesor reader = new WordProcesor(file);
		reader.getMapWordWeight(new Callback() {
			
			@Override
			public void onDone(HashMap<String, Float> map,int size) {
				for(String key:map.keySet()){
					System.out.println(key+":"+map.get(key));
				}
			}
		});
	}
	
	private WordProcesor optainTagsReader(){
		String path = Conf.TEST_FILE_2;
		return new WordProcesor(new File(path));
	}
}
