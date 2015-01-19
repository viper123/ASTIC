package ro.info.asticlib.io.parsers;

import java.io.File;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFParser extends Parser{

	private PDDocument doc;
	private int totalPages ;
	
	public PDFParser(File file) {
		super(file);
		try{
			doc = PDDocument.load(file);
			totalPages = doc.getNumberOfPages();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<String> getAllValidWords() {
		return null;
	}

	@Override
	public void parseWords(OnWordParsedListener listener) {
		try{
			PDFTextStripper reader = new PDFTextStripper();
			for(int i=0;i<totalPages-1;i++){
				reader.setStartPage(i);
				reader.setEndPage(i+1);
				String page = reader.getText(doc);
				String [] words = page.split(lang.getWordRegex());
				for(int k = words.length-1;k>=0;k--){
					listener.onWordParsed(words[k].toLowerCase());
				}
			}
			listener.onParsedFinished();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

}
