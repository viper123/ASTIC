package ro.info.asticlib.tags;

public class WordComparator {
	
	/**
	 * Testeaza daca s1 este un prefix pentr s2 
	 * @param s1
	 * @param s2
	 */
	public static boolean match(String s1,String s2){
		if(s1.length()<=s2.length()){
			if(s1.equals(s2.substring(0,s1.length()))){
				return true;
			}
		}
		return false;
	}
}
