/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers;

import astic.dpm.FileType;
import java.util.HashMap;

/**
 *
 * @author Elvis
 */
public class ParserFactory {
    
    private static HashMap<Integer,Parser> mParserPool;
    public static Parser fromFileType(FileType type)
    {
        Parser parser = null;
        ensureParsersPoolExistance();
        switch(type)
        {
            case App:
                parser = retrive(type, new NameParser());
                break;
            case Video:
                parser = retrive(type, new NameParser());
                break;
            case Audio:
                parser = retrive(type, new NameParser());
                break;
            case Photo:
                parser = retrive(type, new NameParser());
                break;
            case Text:
                parser = retrive(type, new TxtParser());
                break;
            case Other:
                parser = retrive(type, new NameParser());
                break;
        }
        return parser ;
    }
    
    private static void ensureParsersPoolExistance()
    {
        mParserPool=mParserPool==null?new HashMap<Integer,Parser>():mParserPool;
    }
    
    private static Parser retrive(FileType type,Parser backup)
    {
        Parser p = mParserPool.get(type.ordinal());
        if(p!=null)
            return p;
        mParserPool.put(type.ordinal(), backup);
        return backup;
    }
}
