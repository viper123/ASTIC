package ro.info.asticlib.io.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ro.info.asticlib.io.IOHelper;
import ro.info.asticlib.io.parsers.lang.Language;
import ro.info.asticlib.io.parsers.lang.SupportedLanguage1;

public abstract class Parser {
	
	protected File parsableFile;
	protected Language lang;
	protected FileType fileType;
	protected OnWordParsedListener wordParsedListener ;
	
	public Parser(File file){
		this.parsableFile = file;
		lang = new SupportedLanguage1();
	}
	
	public abstract ArrayList<String> getAllValidWords();
	
	public abstract List<String> getLines();
	
	public abstract void parseWords(OnWordParsedListener listener);
	public void parseWords(){
		parseWords(wordParsedListener);
	}
	
	public String getContentAsString(){
		return IOHelper.readContentFromFile(parsableFile);
	}
	
	public void setParsableFile(File file){
		this.parsableFile = file;
	}
	
	public Language getLanguage(){
		return lang;
	}
	
	public void setOnWordParsedListener(OnWordParsedListener listener){
		this.wordParsedListener = listener;
	}
	
	public FileType getFileType(){
		return this.fileType;
	}
	
	public String getFileName(){
		String name = parsableFile.getName();
		if(name.contains(".")){
			name = name.substring(0,name.lastIndexOf('.'));
		}
		return name;
	}
	
	public interface OnWordParsedListener{
		public void onWordParsed(String word);
		public void onParsedFinished();
	}

}
