/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.info.asticlib.io.parsers.lang;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.smu.tspell.wordnet.WordSense;

/**
 *
 * @author Elvis
 */
public class WordNet {
    
	static WordNetDatabase database;
    
    static{
        setupWordNet();
    }
    
    private static void setupWordNet()
    {//C:\\Program Files (x86)\\WordNet\\2.1\\dict ...C:\Program Files (x86)\WordNet\2.1\
        System.setProperty("wordnet.database.dir","C:\\Program Files\\WordNet\\2.1\\dict"); //C:\\Program Files\\WordNet\\2.1\\dict
    }
    
    public static ArrayList<String> validate(String[] input)
    {
    	ArrayList<String > set = new ArrayList<>();
        for(String s:input)
        {
            if(isValid(s.toLowerCase()))
            	set.add(s.toLowerCase());
        }
        return  set;
    }
    
    public static ArrayList<String> validateUnique(String [] input){
    	HashSet<String > set = new HashSet<>();
        for(String s:input)
        {
            if(isValid(s.toLowerCase()))
            	set.add(s.toLowerCase());
        }
        return  new ArrayList<>(set);
    }
    
    public static ArrayList<String> validate(String[] input,SynsetType type)
    {
        ArrayList<String> output = new ArrayList<>();
        for(String s:input)
        {
            if(validate(s.toLowerCase(),type))
                output.add(s.toLowerCase());
        }
        return output;
    }
    
    public static boolean isValid(String s)
    {
        if(s.trim().length()<=1)
            return false;
        if(database == null){
        	database = WordNetDatabase.getFileInstance();
        }
        return database.getSynsets(s).length>0 ;
    }
    
    public static boolean validate(String s,SynsetType type)
    {
        if(s.trim().length()<=1)
            return false;
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        return database.getSynsets(s,type).length>0 ;
    }
    
    public static boolean validateV2(String word,List<String> otherForms){
    	String word_ = word.trim().toLowerCase();
    	if(word_.length()<=1){
    		return false;
    	}
    	WordNetDatabase database = WordNetDatabase.getFileInstance();
    	Synset[] synsets =  database.getSynsets(word_);
    	boolean isPowerNoun = false;
    	for(Synset s:synsets){
    		for(String form:s.getWordForms()){
   			 	if(s.getType().equals(SynsetType.NOUN) && 
   			 			form.trim().toLowerCase().equals(word_)){
   			 		isPowerNoun = true;
   			 	}
   			 	if(!otherForms.contains(form)){
   			 		otherForms.add(form);
   			 	}
    		}
    		WordSense[] senses = s.getDerivationallyRelatedForms(word_);
            for(WordSense sense:senses){
            	if(!otherForms.contains(sense.getWordForm())){
            		otherForms.add(sense.getWordForm());
            	}
            }
   	 	}
    	return isPowerNoun;
    }
    
    public static boolean isWordSignifiant(String word){
    	int nouns = 0;
    	int verbs = 0;
    	int adverbs = 0;
    	int adjectives = 0;
    	WordNetDatabase database = WordNetDatabase.getFileInstance();
    	Synset[] syn = database.getSynsets(word);
    	for(Synset s:syn){
    		if(s.getType() == SynsetType.ADJECTIVE || s.getType() == SynsetType.ADJECTIVE_SATELLITE){
    			adjectives++;
    		}
    		if(s.getType() == SynsetType.ADVERB){
    			adverbs++;
    		}
    		if(s.getType() == SynsetType.NOUN){
    			nouns++;
    		}
    		if(s.getType() == SynsetType.VERB){
    			verbs++;
    		}
    	}
    	return nouns > verbs && nouns > adverbs && nouns > adjectives ;
    }
    
    /*private static List<String> remove(List<String> list,String word){
    	List<String> ignored
    }*/
    
    public static List<String> getOtherForms(String word){
    	List<String> results = new ArrayList<String>();
    	word = word.trim().toLowerCase();
    	WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] syn = database.getSynsets(word);
        for(Synset s:syn){
            for(String s2:s.getWordForms()){
            	if(!results.contains(s2)){
                    results.add(s2);
            	}
            }
            WordSense[] senses = s.getDerivationallyRelatedForms(word);
            for(WordSense sense:senses){
            	if(!results.contains(sense.getWordForm())){
                    results.add(sense.getWordForm());
            	}
            }
        }
        
    	return results;
    }
    
    public static ArrayList<String> expand(String word)
    {
        String _word = word.toLowerCase();
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] syn = database.getSynsets(_word);
        ArrayList<String> words = new ArrayList<>();
        for(Synset s:syn){
            for(String s2:s.getWordForms()){
                if(!words.contains(s2))
                    words.add(s2);
                
            }
            WordSense senses [] = s.getDerivationallyRelatedForms(_word);
            for(WordSense sense:senses){
            	if(!words.contains(sense.getWordForm()))
                    words.add(sense.getWordForm());
            }
        }
        
        return words ;
    }
    
    public static void testWordNet(String word)
    {
        NounSynset nounSynset; 
        NounSynset[] hyponyms; 
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] synsets = database.getSynsets(word, SynsetType.NOUN); 
        
        for (int i = 0; i < synsets.length; i++) { 
            nounSynset = (NounSynset)(synsets[i]); 
            hyponyms = nounSynset.getHyponyms(); 
            System.err.println(nounSynset.getWordForms()[0] + 
                ": " + nounSynset.getDefinition() + ") has " + hyponyms.length + " hyponyms"); 
        }
     }
    
     public static void showSynsets(String word){
    	 WordNetDatabase database = WordNetDatabase.getFileInstance();
    	 Synset[] synsets =  database.getSynsets(word);
    	 for(Synset s:synsets){
    		 System.out.println(translateSynsetType(s.getType())+":");
    		 for(String form:s.getWordForms()){
    			 System.out.println("\t"+form);
    		 }
    	 }
     }
     
     private static String translateSynsetType(SynsetType type){
    	 if(type.equals(SynsetType.NOUN)){
    		 return "noun";
    	 }else if(type.equals(SynsetType.ADJECTIVE)){
    		 return "adjective";
    	 } else if(type.equals(SynsetType.ADJECTIVE_SATELLITE)){
    		 return "adjective satelite";
    	 } else if(type.equals(SynsetType.ADVERB)){
    		 return "adverb";
    	 } else if(type.equals(SynsetType.VERB)){
    		 return "verb";
    	 }
    	 return "none";
     }
}
