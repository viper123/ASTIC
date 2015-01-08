/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm;

import astic.dpm.parsers.Parser;
import astic.drm.Summary;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Elvis
 */
@Deprecated
public class SumarizerM1 extends Summariser{
    
    @Override
    public Summary summaryse(File file,Parser parser) 
    {
        Summary summary = new Summary();
        summary.duration = System.currentTimeMillis();
        summary.document = file.getAbsolutePath();
        summary.weightMatrix = new HashMap<>();
        return null;
    }
    
    private Summary computeWeightMatrix(Summary summary,String content,Parser parser)
    {
//        for(String word:parser.getWords(content))
//        {
//            if(!preValidateWord(word))
//                continue;
//            word = word.toLowerCase();
//            Integer weight = summary.weightMatrix.get(word);
//            weight = weight ==null?0:weight;
//            summary.weightMatrix.put(word, ++weight);
//        }
//        summary.duration = System.currentTimeMillis() - summary.duration;
//        summary.timestamp = System.currentTimeMillis();
        return summary;
    }
    
    private boolean preValidateWord(String word)
    {
        if(word.length()>1)
            return true;
        return false ;
    }
    
    
    
    private int computeMinimumThreshold(int minWeight,int maxWeight,int nrWords,int nrDistinctWords)
    {
        return minWeight;
    }
    
    private int computeMaximumThreshold(int minWeight,int maxWeight,int nrWords,int nrDistinctWords)
    {
        return maxWeight;
    }

    

    
    
    
}
