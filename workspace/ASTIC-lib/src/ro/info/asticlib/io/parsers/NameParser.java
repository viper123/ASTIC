package ro.info.asticlib.io.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class NameParser extends Parser {
	
	public NameParser(File file) {
		super(file);
	}
	
	@Override
	public ArrayList<String> getAllValidWords() {
		return lang.validate(parsableFile.getName().split(lang.getWordRegex()));
	}

	@Override
	public void parseWords(OnWordParsedListener listener) {
		String [] words = parsableFile.getName().split(lang.getWordRegex());
		List<String> validWords = lang.validate(words);
		
		for(String word : validWords){
			if(word != null){
				listener.onWordParsed(word.toLowerCase());
			}
		}
		
		
		listener.onParsedFinished();
		
	}

	@Override
	public List<String> getLines() {
		return null;
	}

}
