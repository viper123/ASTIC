package ro.info.asticlib.tests;

import java.util.HashMap;

import ro.info.asticlib.word.WordProcesor;
import ro.info.asticlib.word.WordProcesor.Callback;

public class WordProcessorTest {

	public void test(){
		WordProcesor wp = new WordProcesor("d://Docs//TEST//birds1.txt");
		wp.getMapWordWeight(new Callback() {
			
			@Override
			public void onDone(HashMap<String, Float> map, int size) {
				// TODO Auto-generated method stub
				for(String word:map.keySet()){
					System.out.println(word+":"+map.get(word)+" "+size);
				}
				System.out.println("Total words:"+map.size());	
			}
		});
	}
}
