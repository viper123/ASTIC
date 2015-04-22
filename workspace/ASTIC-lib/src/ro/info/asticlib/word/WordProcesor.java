package ro.info.asticlib.word;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import ro.info.asticlib.io.parsers.FileType;
import ro.info.asticlib.io.parsers.Parser;
import ro.info.asticlib.io.parsers.Parser.OnWordParsedListener;
import ro.info.asticlib.io.parsers.ParserFactory;

public class WordProcesor {
	
	protected Parser parser;
	protected File file;
	protected FileType fileType;
	
	public WordProcesor(File file){
		this.parser = ParserFactory.getParser(file);
	}
	
	public WordProcesor(String path){
		this.parser = ParserFactory.getParser(new File(path));
		this.fileType = this.parser.getFileType();
		this.file = new File(path);
	}
	
	public void getMapWordWeight(final Callback callback){
		getWordsWeightMap(new Callback() {
			public void onDone(HashMap<String, Float> map,int size) {
				callback.onDone(map,size);
			}
		});
	}
	
	public File getFile(){
		return file;
	}
	
	@SuppressWarnings("unused")
	private void removeArrayFromMap(ArrayList<String> list,HashMap<String, Integer> map){
		for(String key:list){
			map.remove(key);
		}
	}
	
	private int size;
	private void getWordsWeightMap(final Callback callback) {
		final HashMap<String, Float> map = new HashMap<>();
		size = 0;
		parser.parseWords(new OnWordParsedListener() {
			
			@Override
			public void onWordParsed(String word) {
				size++;
				if(map.containsKey(word)){
					map.put(word,map.get(word) +1) ;
				}else{
					map.put(word, 1f);
				}
			}
			
			@Override
			public void onParsedFinished() {
				// TODO Auto-generated method stub
				callback.onDone(map,size);
			}
		});
	}
	
	public FileType getFileType(){
		return fileType;
	}
	
	public interface Callback{
		public void onDone(HashMap<String, Float> map,int size);
	}
}
