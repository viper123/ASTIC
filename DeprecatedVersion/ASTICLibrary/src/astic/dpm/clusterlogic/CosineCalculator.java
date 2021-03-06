/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package astic.dpm.clusterlogic;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Elvis
 */
public class CosineCalculator {
    
    
    public  double computeCosine(double [] a,double []b)
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
    
    public double computeCosine(String a,String b)
    {
        if(a.length()>b.length())
            a = a.substring(0,b.length());
        if(b.length()>a.length())
            b = b.substring(0,b.length());
        String []aWordSet = a.split("");
        String []bWordSet = b.split("");
        String[] meargedList = meargeLeaterSet(aWordSet, bWordSet);
        double [] aWeightVector = toWeightVector(aWordSet, meargedList);
        double [] bWeightVector = toWeightVector(bWordSet, meargedList);
        return computeCosine(aWeightVector, bWeightVector);
    }
    
    private double[] toWeightVector(String[] wordleterset,String []letterSet)
    {
        double[] res = new double[letterSet.length];
        for(int i=0;i<letterSet.length;i++)
        {
            int weight = 0;
            for(int j=0;j<wordleterset.length;j++)
            {
                if(wordleterset[j].toLowerCase().equals(letterSet[i].toLowerCase()))
                    weight ++;
                res[i] = weight ;
            }
        }
        
        return res;
    }
    
    private String [] meargeLeaterSet(String[] aLetterSet,String [] bLetterSet)
    {
        ArrayList<String> res = new ArrayList<>();
        res.addAll(Arrays.asList(aLetterSet));
        for(int i=0;i<bLetterSet.length;i++)
            if(!res.contains(bLetterSet[i].toLowerCase()))
                res.add(bLetterSet[i].toLowerCase());
        String [] array = new String[res.size()];
        return res.toArray(array);
    }
}
