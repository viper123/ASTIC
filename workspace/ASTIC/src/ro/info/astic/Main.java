package ro.info.astic;

import ro.info.asticlib.tests.ClutersTest;


public class Main {

	public static void main(String[] args) {
		//new TestWordNet().testNoun();
		//new TagsReaderTests().testGetTag();
		//new WordComparatorTests().testMatch();
		//new BWTests().testBWAdd();
		//new BWTests().testBWGet();
		//new TagsReaderTests().testGetTag();
		//new LanguageTests().testIsSeparator();
		//new ParserPerformanceTest().testWordListener();
		//new OracleTest().testConnection();
		//new BaseDaoTests().testTableExists();
		//new DaoTests().testSaveTags();
		//new ClutersTest().testGetLastId();
		//new ClutersTest().testAllClusters();
		//new ClutersTest().selectAllFromClusters();
		new Server().server();
	}

}
