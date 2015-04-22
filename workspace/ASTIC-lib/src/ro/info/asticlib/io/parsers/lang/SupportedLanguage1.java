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
public class SupportedLanguage1 extends Language {

    @Override
    public String getWordRegex() {
        return "[^a-zA-Z]+";
    }

    @Override
    public ArrayList<String> validate(String[] input) {
        return WordNet.validate(input);
    }

	@Override
	public boolean isValid(String word) {
		return WordNet.isValid(word);
	}

	@Override
	public ArrayList<String> validateUnique(String[] input) {
		return WordNet.validateUnique(input);
	}

	@Override
	public boolean isPowerNoun(String word,List<String> others) {
		
		return WordNet.validateV2(word,others);
	}

	@Override
	public boolean isWordSeparator(String ch) {
		return ch.matches(getWordRegex()) ;
	}

	@Override
	public String getNewLineReges() {
		return "\n";
	}
    
}
