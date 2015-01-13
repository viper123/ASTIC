package ro.info.asticlib.clustering;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.util.HashMap;

import ro.info.asticlib.files.FileProcessor;
import ro.info.asticlib.io.parsers.FileType;
import ro.info.asticlib.tags.TagsReader;
import ro.info.asticlib.tags.TagsReader.Callback;

public class ClusterizerL0 extends FileProcessor {
	
	/**
	 * Procesare fisier:
	 * extrag taguri din el apoi introduc tagurile in universul de clusteri
	 */
	@Override
	public void processFile(File file) {
		System.out.println(file.getAbsolutePath());
		TagsReader tagsReader = new TagsReader(file);
		tagsReader.getMapTagWeight(new TagingListener(file.getAbsolutePath()));
	}

	/**
	 * Conditii ca un fisier sa fie procesat:
	 * sa aibe un tip cunoscut (extensie)
	 * sa nu fie ascuns sau fisier de sistem
	 */
	@Override
	public boolean isFileAcceptable(File file, BasicFileAttributes attrs) {
		DosFileAttributes dosAttrs = (DosFileAttributes)attrs;
		boolean isHidden = false;
		if(attrs!=null){
			isHidden = dosAttrs.isHidden() ; 
		}
		FileType type = FileType.fromFile(file);
		return !isHidden && type != FileType.Other;
	}
	
	class TagingListener implements Callback {
		
		private String file;
		
		public TagingListener(String file) {
			this.file = file;
		}
		
		@Override
		public void onDone(HashMap<String, Integer> map) {
			Clusters.instance().processFileTaging(file, map);
		}
		
	}

}
