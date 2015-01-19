package ro.info.asticlib.clustering;

import java.util.ArrayList;
import java.util.List;

import ro.info.asticlib.clustering.Cluster.DistanceFormula;
import ro.info.asticlib.db.Dao;
import ro.info.asticlib.tree.Node;
import ro.info.asticlib.tree.Tree;

public class HAClusteering {
	
	private List<Cluster> input;
	private Dao dao;
	private int lastClusterId = 0;
	
	
	public HAClusteering(List<Cluster> input) {
		super();
		this.input = input;
		dao = new Dao();
		lastClusterId = dao.getLastClusterId();
	}

	public Tree<Cluster> applyLogic(){
		Tree<Cluster> tree = new Tree<Cluster>();
		//sparge input-ul in clusteri cu un singur fisier
		List<Cluster> newInput = new ArrayList<Cluster>();
		for(Cluster c : input){
			if(c.fileWordMap.size()>1){//mai mult de un fisier;
				for(String file:c.fileWordMap.keySet()){
					Cluster newCluster = new Cluster();
					newCluster.fileWordMap.put(file, c.fileWordMap.get(file));
					newCluster.id = dao.getLastClusterId() + 1;
					for(String word:newCluster.fileWordMap.get(file)){
						newCluster.wordWeightMap.put(word, c.wordWeightMap.get(word));
					}
				}
			}
		}
		input.clear();
		input.addAll(newInput);
		//pune input ca frunze la tree;
		for(Cluster c:input){
			tree.addFirstNode(new Node<Cluster>(c.id+"", c));
		}
		boolean stopCondition = true;
		do{
			double [][] distances = new double[input.size()][input.size()];
			for(int i=input.size();i>=0;i--){
				Cluster selected = input.get(i);
				distances[i][i] = 0; // distanta dintre un cluster si el insusi este 1 
				//insa pentru ca urmeaza sa calculam maximul este de preferat sa fie valuarea cea mai mica
				for(int j = input.size(); j>=0&&j!=i; j--){
					Cluster other = input.get(j);
					distances[i][j] = 1 - selected.getDistance(other, DistanceFormula.Cosine);
				}
			}
			int [] indexs = minIndex(distances);
			Cluster one = input.get(indexs[0]);
			Cluster two = input.get(indexs[1]);
			Cluster mearged = one.add(two);
			mearged.id = getClusterId();
			Node<Cluster> parent = tree.getNode(one.id+"").parent;
			Node<Cluster> nodeOne = tree.getNode(one.id+"");
			Node<Cluster> nodeTwo = tree.getNode(two.id+"");
			parent.remove(nodeOne);
			parent.remove(nodeTwo);
			Node<Cluster> meargedNode = new Node<Cluster>(mearged.id+"",mearged);
			meargedNode.addChildren(nodeOne);
			meargedNode.addChildren(nodeTwo);
			parent.addChildren(meargedNode);
			input.remove(indexs[0]);
			input.remove(indexs[1]);
			input.add(mearged);
			stopCondition = input.size()>1;
		}while(stopCondition);
		return tree;
	}
	
	private int[] minIndex(double [][]matrix){
		int k = 0;
		double min = 1;
		int minIIndex = -1;
		int minJIndex = -1;
		for(int i=matrix.length*2;i>0;i--){
			if(min<matrix[k][i%matrix.length]){
				min = matrix[k][i%matrix.length];
				minIIndex = i;
				minJIndex = k;
				k = i/matrix.length;
			}
		}
		return new int[]{minIIndex,minJIndex};
	}
	
	private int getClusterId(){
		return ++lastClusterId;
	}
}
