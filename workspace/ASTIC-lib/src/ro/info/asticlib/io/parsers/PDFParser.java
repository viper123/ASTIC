package ro.info.asticlib.io.parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	public void setParsableFile(File file) {
		try{
			doc = PDDocument.load(file);
			totalPages = doc.getNumberOfPages();
		}catch(Exception e){
			System.out.println("Eror:"+file.getAbsolutePath());
		}
		super.setParsableFile(file);
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
					if(words[k]!=null&&!("".equals(words[k]))&&words[k].length()>1){
						listener.onWordParsed(words[k].toLowerCase());
					}
				}
			}
			listener.onParsedFinished();
			
			doc.close();
		}catch(Exception e){
			System.out.println("Error parsing:"+parsableFile.getAbsolutePath());
			try {
				doc.close();
			} catch (IOException e1) {}
		}
	}

	@Override
	public List<String> getLines() {
		List<String> allLines = new ArrayList<>();
			try{
			
			PDFTextStripper reader = new PDFTextStripper();
			for(int i=0;i<totalPages-1;i++){
				reader.setStartPage(i);
				reader.setEndPage(i+1);
				String page = reader.getText(doc);
				String [] lines = page.split(lang.getNewLineReges());
				for(int k = lines.length-1;k>=0;k--){
					if(lines[k]!=null&&!("".equals(lines[k]))&&lines[k].length()>1){
						allLines.add(lines[k]);
					}
				}
			}
			doc.close();
		}catch(Exception e){
			System.out.println("Error parsing:"+parsableFile.getAbsolutePath());
			try {
				doc.close();
			} catch (IOException e1) {}
		}
		return allLines;
	}
	
	

}
