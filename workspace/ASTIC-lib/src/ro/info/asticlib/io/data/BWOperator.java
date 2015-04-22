package ro.info.asticlib.io.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.io.IOHelper;
import ro.info.asticlib.word.WordComparator;

public class BWOperator {
	
	private final String BW_ROOT = "bw";
	
	private File root;
	
	public BWOperator() {
		root = new File(Conf.BW_DB_ROOT+File.separator+BW_ROOT);
		if(!root.exists()){
			root.mkdirs();
		}
	}
	
	public synchronized void add(String filePath,HashMap<String, Integer> documentTags){
		add(new File(filePath), documentTags);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized void  add(File file,HashMap<String, Integer> documentTags){
		for(String word:documentTags.keySet()){
			// check if the letter folder exxists
			String letterPath = root.getAbsolutePath()+File.separator+word.substring(0,1);
			
			File fileLetter = new File(letterPath);
			if(!fileLetter.exists()){
				fileLetter.mkdirs();
			}
			String wordPath = letterPath + File.separator + word;
			Object wordObject = IOHelper.loadObject(wordPath);
			HashMap<String, Integer> wordMap = null;
			if(wordObject!=null){
				if(wordObject instanceof HashMap){
					wordMap = (HashMap<String, Integer>) wordObject ;
					wordMap.put(file.getAbsolutePath(), documentTags.get(word));
				}
				
			}else{
				wordMap = new HashMap<String, Integer>();
				wordMap.put(file.getAbsolutePath(), documentTags.get(word));
			}
			IOHelper.saveObject(wordPath, wordMap);
		}
	}
	
	@SuppressWarnings("unchecked")
	public synchronized HashMap<String, HashMap<String, Integer>> get(final String key){
		HashMap<String, HashMap<String, Integer>> result = null;
		File letterFolder = new File(root.getAbsoluteFile() + File.separator +
				key.substring(0,1).toLowerCase());
		if(letterFolder.exists()){
			File [] wordFiles = letterFolder.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					return WordComparator.match(key, name);
				}
			});
			if(wordFiles!=null&&wordFiles.length>0){
				result = new HashMap<String, HashMap<String,Integer>>();
				for(File file:wordFiles){
					HashMap<String, Integer> wordMap = 
							(HashMap<String, Integer>) 
							IOHelper.loadObject(file.getAbsolutePath());
					if(wordMap!=null){
						result.put(file.getName(), wordMap);
					}
				}
			}
		}
		return result;
	}
}
