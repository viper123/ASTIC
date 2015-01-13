package ro.info.asticlib.tests;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;

import ro.info.asticlib.files.Croisers;
import ro.info.asticlib.files.FileProcessor;

public class CroisersTests {

	public void testCroisers(){
		Croisers.instance().processFileTree("D:\\",new FileProcessor() {
			
			@Override
			public void processFile(File file) {
				System.out.println(file.getAbsolutePath());
			}
			
			@Override
			public boolean isFileAcceptable(File file, BasicFileAttributes attrs) {
				return true;
			}
		});
	}
}
