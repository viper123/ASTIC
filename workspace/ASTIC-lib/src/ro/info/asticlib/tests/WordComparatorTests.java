package ro.info.asticlib.tests;

import ro.info.asticlib.tags.WordComparator;

public class WordComparatorTests {

	public void testMatch(){
		System.out.println(WordComparator.match("abr", "abracadabre"));
		System.out.println(WordComparator.match("abraca", "ab"));
		System.out.println(WordComparator.match("ssdfs", "gedgsfsdsfsfs"));
	}
}
