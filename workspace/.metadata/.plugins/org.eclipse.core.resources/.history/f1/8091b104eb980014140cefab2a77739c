package ro.info.asticlib.io.parsers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class TxtParser extends Parser {

	
	@SuppressWarnings("unused")
	private final int BIGSIZE = 1048576;
	private final int SIZE = 8192;
	
	public TxtParser(File file) {
		super(file);
	}
	
	/**
	 * Returneaza  multimea de cuvinte valide din fisierul care trebuie parsat
	 */
	@Override
	public ArrayList<String> getAllValidWords(){
		return getAllWords();
	}

	
	public ArrayList<String> getUniqueValidWords() {
		String content = getContentAsString();
		return lang.validateUnique(content.split(lang.getWordRegex()));
	}
	
	/**
	 * Methoda care parseaza un cuvant si il returneaza printr-un listener
	 */
	public void parseWords(OnWordParsedListener listener){
		try{
			FileInputStream f = new FileInputStream( parsableFile );
			FileChannel channel = f.getChannel( );
			MappedByteBuffer mb = channel.map( FileChannel.MapMode.READ_ONLY,
			    0L, channel.size( ) );
			byte[] barray = new byte[SIZE];
			long checkSum = 0L;
			int nGet;
			String composedWord = null;
			while( mb.hasRemaining( ) )
			{
			    nGet = Math.min( mb.remaining( ), SIZE );
			    mb.get( barray, 0, nGet );
			    String buffer = new String(barray,Charset.forName("UTF-8"));
			    
			    for(int i=1;i<buffer.length()-1;i++){
			    	String ch = buffer.substring(i-1,i);
			    	if(lang.isWordSeparator(ch)){
			    		if(composedWord!=null&&listener!=null){
			    			listener.onWordParsed(composedWord.toLowerCase());
			    		}
			    		composedWord = null;
			    	}else{
		    			if(composedWord == null){
		    				composedWord = "";
		    			}
		    			composedWord+=ch;
		    		}
			    	
			    }
			}
			if(listener!=null){
				listener.onParsedFinished();
			}
			f.close();
			}catch(Exception e){
				if(listener!=null){
					listener.onParsedFinished();
				}
			}
	}
	
	private ArrayList<String> getAllWords(){
		return lang.validate(getContentAsString().split(lang.getWordRegex()));
	}

}
