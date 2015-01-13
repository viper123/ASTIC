package ro.info.asticlib.clustering;

import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.util.HashMap;

import ro.info.asticlib.db.Dao;
import ro.info.asticlib.files.FileProcessor;
import ro.info.asticlib.io.parsers.FileType;
import ro.info.asticlib.tags.TagsReader;
import ro.info.asticlib.tags.TagsReader.Callback;

public class InitialFileProcesing extends FileProcessor{

	@Override
	public void processFile(File file) {
		System.out.println("Process file:\n\t"+file.getAbsolutePath());
		TagsReader reader = new TagsReader(file);
		reader.getMapTagWeight(new TagingListener(file.getAbsolutePath()));
	}

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
			Dao dao = new Dao();
			dao.saveTags(file, map);
		}
		
	}

}
