package ro.info.asticlib.tests;

import java.io.File;
import java.util.ArrayList;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.io.parsers.Parser;
import ro.info.asticlib.io.parsers.Parser.OnWordParsedListener;
import ro.info.asticlib.io.parsers.ParserFactory;
import ro.info.asticlib.io.parsers.TxtParser;

public class ParserPerformanceTest {

	public void testTxtParserGetAllValidWords(){
		long time = System.currentTimeMillis();
		ArrayList<String> words = optainParser().getAllValidWords();
		time = System.currentTimeMillis() - time ;
		System.out.println(words.size()+" words in "+time +"ms");
		for(String word:words){
			System.out.println(word);
		}
	}
	
	public void testWordListener(){
		TxtParser parser = new TxtParser(new File(Conf.TEST_FILE_2));
		final long time = System.currentTimeMillis();
		parser.setOnWordParsedListener(new OnWordParsedListener() {
			
			@Override
			public void onWordParsed(String word) {
				System.out.println(word);
			}

			@Override
			public void onParsedFinished() {
				System.out.println("done in "+(System.currentTimeMillis() - time));
			}
		});
		parser.parseWords();
	}
	
	
	
	private Parser optainParser(){
		String path = "d://test.txt";
		return ParserFactory.getParser(new File(path));
	}
}
