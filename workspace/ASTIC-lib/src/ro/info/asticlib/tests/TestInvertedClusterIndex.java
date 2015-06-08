package ro.info.asticlib.tests;

import java.util.List;
import java.util.Scanner;

import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.db.Dao;

public class TestInvertedClusterIndex {
	
	
	public void test(){
		Dao dao = new Dao();
		Scanner sc = new Scanner(System.in);
		while(true){
		System.out.println("Word:\n");
		String word = sc.next();
		List<Cluster> clusters = dao.getClustersContaining(word);
		for(Cluster c:clusters){
			System.out.println(c.toString()+"\n");
			
		}
		}
	}

}
