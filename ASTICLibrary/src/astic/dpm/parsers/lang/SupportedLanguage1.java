/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers.lang;

import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public class SupportedLanguage1 extends Language {

    @Override
    public String getWordRegex() {
        return "[^a-zA-Z]+";
    }

    @Override
    public ArrayList<String> validate(String[] input) {
        return WordNet.validate(input);
    }
}
