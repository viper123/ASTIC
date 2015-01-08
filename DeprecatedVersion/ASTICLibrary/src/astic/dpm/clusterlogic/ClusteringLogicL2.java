/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

import astic.drm.Cluster;
import astic.drm.Summary;
import java.util.ArrayList;

/**
 *
 * @author Elvis
 */
public class ClusteringLogicL2 {
    public static double cosine(Summary s,Cluster c)
    {
        ArrayList<String> wordSet = ClusteringLogicL2.getWordSet(s, c);
        if(wordSet.size()>0)
        {
            double [] sv = new double[wordSet.size()];
            double [] cv = new double[wordSet.size()];
            for(int i=0;i<wordSet.size();i++)
            {
                sv[i] = s.weightMatrix.containsKey(wordSet.get(i))?s.weightMatrix.get(wordSet.get(i)):0;
                cv[i] = getFrquencyForWord(c, wordSet.get(i));
            }
            return computeCosine(sv, cv);
        }
        return 0;
    }
    
    public static ArrayList<String> getWordSet(Summary s,Cluster c)
    {
        ArrayList<String> out = new ArrayList<>();
        for(String word:s.weightMatrix.keySet())
        {
            String _word = word.toLowerCase();
            if(!out.contains(_word)&&!out.contains(word))
                out.add(word);
        }
        for(String file:c.files.keySet())
            for(String word:c.files.get(file).keySet())
            {
                String _word = word.toLowerCase();
                if(!out.contains(_word)&&!out.contains(word))
                    out.add(word);
            }
        return out;
    }
    
    public static double getFrquencyForWord(Cluster c,String word)
    {
        double f  = 0;
        for(String file:c.files.keySet())
        {
            if(c.files.get(file).containsKey(word))
                f+=c.files.get(file).get(word) ;
        }
        return f;
    }
    
    public static double computeCosine(double [] a,double []b)
    {
        if(a.length!=b.length)
            return 0 ;
        double topSum = 0,leftBSum=0,rightBSum=0;
        for(int i=0;i<a.length;i++)
        {
            topSum+=(a[i]*b[i]);
            leftBSum+=Math.pow(a[i],2) ;
            rightBSum+=Math.pow(b[i], 2);
        }
        return topSum / (Math.sqrt(leftBSum)*Math.sqrt(rightBSum)) ;
    }
}
