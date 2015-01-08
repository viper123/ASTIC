/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers;

import astic.dpm.parsers.lang.Language;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Elvis
 */
public abstract class TextDocumentParser implements Parser {
    
    protected Language lang;
    public TextDocumentParser(Language lang)
    {
        this.lang = lang;
    }
    
    public abstract String parseContent(File file);
    
    @Override
    public String[] getWords(File file)
    {
        String content = parseContent(file);
        ArrayList<String> temp = lang.validate(content.split(lang.getWordRegex()));
        String res [] = new String[temp.size()];
        return temp.toArray(res);
    }
}
