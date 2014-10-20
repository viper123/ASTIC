/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers.lang;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Elvis
 */
public abstract class Language {
    
    public abstract String getWordRegex();
    public abstract ArrayList<String> validate(String[] input);
}
