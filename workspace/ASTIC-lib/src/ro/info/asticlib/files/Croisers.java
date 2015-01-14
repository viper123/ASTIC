package ro.info.asticlib.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Croisers {
	
	public static Croisers instance(){
		return instance;
	}
	private static Croisers instance ;
	static{
		instance = new Croisers();
	}
	
	public void processFileTree(String root,FileProcessor fileProcessor){
		Croiser c = new Croiser(root,fileProcessor);
		try {
			Files.walkFileTree(Paths.get(root), c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}