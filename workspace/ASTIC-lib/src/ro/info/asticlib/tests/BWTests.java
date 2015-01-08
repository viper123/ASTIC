package ro.info.asticlib.tests;

import java.util.HashMap;
import java.util.Scanner;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.io.data.DataWarehouse;
import ro.info.asticlib.tags.TagsReader;
import ro.info.asticlib.tags.TagsReader.Callback;

public class BWTests {

	public void testBWAdd(){
		final long time = System.currentTimeMillis();
		TagsReader reader = new TagsReader(Conf.TEST_FILE_2);
		DataWarehouse.activateOperators();
		reader.getMapTagWeight(new Callback() {
			
			@Override
			public void onDone(HashMap<String, Integer> map) {
				// TODO Auto-generated method stub
				DataWarehouse.BW.add(Conf.TEST_FILE_2,map);
				System.out.println("Done :"+(System.currentTimeMillis() - time));
			}
		});
	}
	
	public void testBWGet(){
		DataWarehouse.activateOperators();
		while(true){
			try{
				Runtime.getRuntime().exec("cls");
			}catch(Exception e){
			}
			System.out.println("Enter a key:");
			Scanner sc = new Scanner(System.in);
			String key = sc.next();
			long time = System.currentTimeMillis();
			HashMap<String, HashMap<String,Integer>> result = DataWarehouse.BW.get(key);
			if(result !=null&&result.size()>0){
				System.out.println(result.size()+" in " + (System.currentTimeMillis() - time) +"ms");
				for(String word:result.keySet()){
					for(String path:result.get(word).keySet()){
						System.out.println(word+":"+path+":"+result.get(word).get(path));
					}
				}
			}else{
				System.out.println("No results");
			}
		}
	}
}
