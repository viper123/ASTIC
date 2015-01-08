package ro.info.asticlib.tags;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

import ro.info.asticlib.io.parsers.Parser;
import ro.info.asticlib.io.parsers.Parser.OnWordParsedListener;
import ro.info.asticlib.io.parsers.ParserFactory;
import ro.info.asticlib.math.Math;

public class TagsReader {
	
	protected Parser parser;
	protected File file;
	
	public TagsReader(File file){
		this.parser = ParserFactory.getParser(file);
	}
	
	public TagsReader(String path){
		this.parser = ParserFactory.getParser(new File(path));
	}
	
	public void getMapTagWeight(final Callback callback){
		getWordsWeightMap(new Callback() {
			public void onDone(HashMap<String, Integer> map) {
				TreeMap<Integer, ArrayList<String>> tree = new TreeMap<>();
				ArrayList<String> ignored = new ArrayList<>();
				for(String word:map.keySet()){
					if(!parser.getLanguage().isNoun(word)){
						ignored.add(word);
						continue;
					}
					Integer weight = map.get(word);
					if(tree.containsKey(weight)){
						tree.get(weight).add(word);
					}else{
						ArrayList<String> words = new ArrayList<String>();
						words.add(word);
						tree.put(weight, words);
					}
				}
				removeArrayFromMap(ignored, map);
				//aleg un parametru de stergere
				int k = Math.getApxlog(2, tree.keySet().size()); 
				while(k>0){
					Integer removableKey = tree.lastKey();
					ArrayList<String> removableWords = tree.get(removableKey);
					removeArrayFromMap(removableWords, map);
					tree.remove(removableKey);
					removableKey = tree.firstKey();
					removableWords = tree.get(removableKey);
					removeArrayFromMap(removableWords, map);
					tree.remove(removableKey);
					k--;
				}
				callback.onDone(map);
			}
		});
	}
	
	public File getFile(){
		return file;
	}
	
	private void removeArrayFromMap(ArrayList<String> list,HashMap<String, Integer> map){
		for(String key:list){
			map.remove(key);
		}
	}
	

	private void getWordsWeightMap(final Callback callback) {
		final HashMap<String, Integer> map = new HashMap<>();
		parser.parseWords(new OnWordParsedListener() {
			
			@Override
			public void onWordParsed(String word) {
				if(map.containsKey(word)){
					map.put(word,map.get(word) +1) ;
				}else{
					map.put(word, 1);
				}
			}
			
			@Override
			public void onParsedFinished() {
				// TODO Auto-generated method stub
				callback.onDone(map);
			}
		});
	}
	
	public interface Callback{
		public void onDone(HashMap<String, Integer> map);
	}
}
