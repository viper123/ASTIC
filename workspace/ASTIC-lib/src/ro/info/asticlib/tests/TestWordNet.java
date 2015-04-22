package ro.info.asticlib.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.smu.tspell.wordnet.SynsetType;
import ro.info.asticlib.io.parsers.lang.WordNet;

public class TestWordNet {

	public void testNoun(){
		System.out.println(WordNet.validate("frogs", SynsetType.NOUN));
	}
	
	public void testOnlyNoun(){
		List<String> others = new ArrayList<String>();
		System.out.println("was ?"+WordNet.validateV2("super",others));
		for(String s:others){
			System.out.println(s);
		}
	}
	
	public void testOtherForms(){
		List<String> others = WordNet.getOtherForms("tension");
		for(String s:others){
			System.out.println(s);
		}
	}
	
	public void testAWord(){
		while(true){
		Scanner scanner = new Scanner(System.in);
		String str = scanner.next();
		System.out.println(WordNet.isWordSignifiant(str));
		WordNet.showSynsets(str);
		}
	}
	
	public void testExpandWord(){
		while(true){
			Scanner scanner = new Scanner(System.in);
			String str = scanner.next();
			String regex="[^a-zA-Z]+";
			String [] nameParts = str.split(regex);
				for(String word:nameParts){
					System.out.println("Word:"+word);
					System.out.println(WordNet.expand(word));
				}
			}
	}
}
