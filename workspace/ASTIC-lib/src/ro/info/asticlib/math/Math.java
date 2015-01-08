package ro.info.asticlib.math;

public class Math {
	public static int getApxlog(int base,int x){
		int power = 0;
		while(java.lang.Math.pow(base, power)<x){
			power++;
		}
		return power-1;
	}
	
	public static double computeCosine(double [] a,double []b){
        if(a.length!=b.length){
            return 0 ;
        }
        double topSum = 0,leftBSum=0,rightBSum=0;
        for(int i=0;i<a.length;i++){
            topSum+=(a[i]*b[i]);
            leftBSum+=java.lang.Math.pow(a[i],2) ;
            rightBSum+=java.lang.Math.pow(b[i], 2);
        }
        return topSum / (java.lang.Math.sqrt(leftBSum)*java.lang.Math.sqrt(rightBSum)) ;
    }
}
