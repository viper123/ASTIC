package ro.info.asticlib.tests;

import ro.info.asticlib.io.parsers.lang.SupportedLanguage1;

public class LanguageTests {

	public void testIsSeparator(){
		System.out.println(new SupportedLanguage1().isWordSeparator("."));
		System.out.println(new SupportedLanguage1().isWordSeparator(","));
		System.out.println(new SupportedLanguage1().isWordSeparator(" "));
		System.out.println(new SupportedLanguage1().isWordSeparator("-"));
		System.out.println(new SupportedLanguage1().isWordSeparator("?"));
		System.out.println(new SupportedLanguage1().isWordSeparator("!"));
		System.out.println(new SupportedLanguage1().isWordSeparator("a"));
		System.out.println(new SupportedLanguage1().isWordSeparator("Z"));
		System.out.println(new SupportedLanguage1().isWordSeparator("&"));
		System.out.println(new SupportedLanguage1().isWordSeparator(";"));
		System.out.println(new SupportedLanguage1().isWordSeparator(":"));
	}
	
}
