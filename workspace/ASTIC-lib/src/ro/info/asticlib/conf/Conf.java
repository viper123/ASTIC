package ro.info.asticlib.conf;

public class Conf {

	public static String BW_DB_ROOT = "E://Licenta//data" ; 
	public static String TEST_FILE = "D://Test";
	public static String TEST_FILE_2 = "D://reptiles.txt"; 
	public static String ROOT = "D://StudPlace";
	public static String ROOT_TEST = "D://Docs";
	public static String ROOT_TEST_2 = "D://TestDocumente";
	public static String ROOT_TEST_3 = "D://Projects//VisioMed//Docs";
	public static String ROOT_TEST_4 = "D://Projects//VisioMed//Docs";
	
	public static final double ACCEPTABLE_SIMILARITY = 0.3f;
	public static final double ACCEPTABLE_SIMILARITY_MAX = 0.49f;
	public static final int ACCEPTABLE_MAP_SIZE = 10;
	public static final int DOC_SET_SIZE = 10;
	public static final int CLUSTER_REPREZENTATIVE_WORDS_COUNT = 100;
	public static final double DISTANCE_INCREMENT_FACTOR = 0.002f;
	
	public static final int SERVER_PORT_NUMBER = 6789;
	
	private static double acceptableSimilarity = ACCEPTABLE_SIMILARITY;
	
	public static double getAcceptableSimilarity(){
		acceptableSimilarity+=DISTANCE_INCREMENT_FACTOR;
		acceptableSimilarity=acceptableSimilarity>ACCEPTABLE_SIMILARITY_MAX?ACCEPTABLE_SIMILARITY_MAX:acceptableSimilarity;
		return acceptableSimilarity;
	}
}
