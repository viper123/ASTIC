package ro.info.asticlib.io.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

public class DocParser extends Parser {

	public DocParser(File file) {
		super(file);
	}

	@Override
	public ArrayList<String> getAllValidWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getLines() {
		String [] lines = getContentAsString().split(lang.getNewLineReges());
		return Arrays.asList(lines);
	}

	@Override
	public void parseWords(OnWordParsedListener listener) {
		String [] words = getContentAsString().split(lang.getWordRegex());
		for(String word:words){
			listener.onWordParsed(word);
		}
		listener.onParsedFinished();		   
	}
	
	@Override
	public String getContentAsString() {
		HWPFDocument docx;
		WordExtractor we = null;
		try {
			docx = new HWPFDocument(
					   new FileInputStream(getFileName()));
			we = new WordExtractor(docx);
			return we.getText();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				we.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
