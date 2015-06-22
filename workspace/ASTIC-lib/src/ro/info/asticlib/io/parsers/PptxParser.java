package ro.info.asticlib.io.parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xslf.XSLFSlideShow;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.xmlbeans.XmlException;

public class PptxParser extends Parser {

	public PptxParser(File file) {
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
		XSLFPowerPointExtractor we = null;
		try {
			we = new XSLFPowerPointExtractor(new XSLFSlideShow(getFileName()));
			return we.getText();
		} catch (IOException | XmlException | OpenXML4JException e) {
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
