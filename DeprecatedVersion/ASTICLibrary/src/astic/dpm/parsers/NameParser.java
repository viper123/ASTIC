/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers;

import astic.dpm.parsers.lang.Language;
import astic.dpm.parsers.lang.SupportedLanguage1;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Elvis
 */
public class NameParser implements Parser {

    private Language lang ;
    public NameParser()
    {
        this.lang = new SupportedLanguage1();
    }
    
    @Override
    public String[] getWords(File file) {
        String fileName = file.getName();
        String [] words = fileName.split(lang.getWordRegex());
        ArrayList<String> res = new ArrayList<String>();
        for(String s:words)
        {
            if(s.length()>=2)
                res.add(s);
        }
        words = new String[res.size()];
        return res.toArray(words) ;
    }

    
    
}
