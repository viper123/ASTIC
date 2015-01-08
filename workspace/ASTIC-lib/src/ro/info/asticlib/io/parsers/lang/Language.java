/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.info.asticlib.io.parsers.lang;

import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public abstract class Language {
    
    public abstract String getWordRegex();
    public abstract ArrayList<String> validate(String[] input);
    public abstract ArrayList<String> validateUnique(String [] input);
    public abstract boolean isValid(String word);
    public abstract boolean isNoun(String word);
    public abstract boolean isWordSeparator(String ch);
}
