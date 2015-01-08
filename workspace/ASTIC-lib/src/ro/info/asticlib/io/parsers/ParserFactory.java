package ro.info.asticlib.io.parsers;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class ParserFactory {
    
    private static HashMap<Integer,Parser> mParserPool;
    public static Parser getParser(File inputFile)
    {
    	FileType type = FileType.fromFile(inputFile);
        Parser parser = null;
        ensureParsersPoolExistance();
        switch(type)
        {
            case App:
                parser = retrive(type, NameParser.class ,inputFile);
                break;
            case Video:
                parser = retrive(type, NameParser.class ,inputFile);
                break;
            case Audio:
                parser = retrive(type, NameParser.class ,inputFile);
                break;
            case Photo:
                parser = retrive(type, NameParser.class ,inputFile);
                break;
            case Text:
                parser = retrive(type, TxtParser.class ,inputFile);
                break;
            case Other:
                parser = retrive(type, NameParser.class ,inputFile);
                break;
        }
        return parser ;
    }
    
    private static void ensureParsersPoolExistance(){
        mParserPool=mParserPool==null?new HashMap<Integer,Parser>():mParserPool;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private static Parser retrive(FileType type,Class parserClass,File inputFile){
        Parser p = mParserPool.get(type.ordinal());
        if(p!=null){
        	p.setParsableFile(inputFile);
            return p;
        }
        Parser parser;
		try {
			parser = (Parser) parserClass.getConstructor(File.class).newInstance(inputFile);
			mParserPool.put(type.ordinal(), parser);
	        return parser;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
    }
}
