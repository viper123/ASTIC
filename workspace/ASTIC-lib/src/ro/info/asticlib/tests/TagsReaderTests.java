package ro.info.asticlib.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.tags.TagsReader;

public class TagsReaderTests {
	
	public void testGetTag(){
		
	}
	
	private TagsReader optainTagsReader(){
		String path = Conf.TEST_FILE_2;
		return new TagsReader(new File(path));
	}
}
