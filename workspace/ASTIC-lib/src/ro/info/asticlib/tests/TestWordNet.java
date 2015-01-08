package ro.info.asticlib.tests;

import edu.smu.tspell.wordnet.SynsetType;
import ro.info.asticlib.io.parsers.lang.WordNet;

public class TestWordNet {

	public void testNoun(){
		System.out.println(WordNet.validate("frogs", SynsetType.NOUN));
	}
}
