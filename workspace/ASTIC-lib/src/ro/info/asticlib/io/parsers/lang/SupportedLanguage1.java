/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.info.asticlib.io.parsers.lang;

import java.util.ArrayList;

import org.basex.query.regex.RegExp;
import org.basex.query.regex.parse.RegExLexer;

import edu.smu.tspell.wordnet.SynsetType;

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
	public boolean isNoun(String word) {
		
		return WordNet.validate(word, SynsetType.NOUN);
	}

	@Override
	public boolean isWordSeparator(String ch) {
		return ch.matches(getWordRegex()) ;
	}
    
}
