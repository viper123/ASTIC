package ro.info.asticlib.io.parsers;

import java.io.File;
import java.util.ArrayList;

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
		throw new RuntimeException("Not available for this parser");
	}

}
