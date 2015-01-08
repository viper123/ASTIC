/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.parsers.lang;

import edu.smu.tspell.wordnet.NounSynset;
import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Elvis
 */
public class WordNet {
    
    
    static{
        setupWordNet();
    }
    
    private static void setupWordNet()
    {//C:\\Program Files (x86)\\WordNet\\2.1\\dict
        System.setProperty("wordnet.database.dir","C:\\Program Files\\WordNet\\2.1\\dict"); //C:\\Program Files\\WordNet\\2.1\\dict
    }
    
    public static ArrayList<String> validate(String[] input)
    {
        ArrayList<String> output = new ArrayList<>();
        for(String s:input)
        {
            if(validate(s.toLowerCase()))
                output.add(s.toLowerCase());
        }
        return output;
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
    
    public static boolean validate(String s)
    {
        
        if(s.trim().length()<=1)
            return false;
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        return database.getSynsets(s).length>0 ;
    }
    
    public static boolean validate(String s,SynsetType type)
    {
        
        if(s.trim().length()<=1)
            return false;
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        return database.getSynsets(s,type).length>0 ;
    }
    
    public static ArrayList<String> expand(String word)
    {
        String _word = word.toLowerCase();
        WordNetDatabase database = WordNetDatabase.getFileInstance();
        Synset[] syn = database.getSynsets(_word);
        ArrayList<String> words = new ArrayList<>();
        for(Synset s:syn)
            for(String s2:s.getWordForms())
                if(!words.contains(s2))
                    words.add(s2);
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
}
