/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.info.asticlib.io.parsers.lang;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Elvis
 */
public abstract class Language {
    
    public abstract String getWordRegex();
    public abstract String getNewLineReges();
    public abstract ArrayList<String> validate(String[] input);
    public abstract ArrayList<String> validateUnique(String [] input);
    public abstract boolean isValid(String word);
    public abstract boolean isPowerNoun(String word,List<String> others);
    public abstract boolean isWordSeparator(String ch);
}
