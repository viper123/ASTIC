package ro.info.asticlib.clustering;

import java.text.DecimalFormat;
import java.util.List;

import ro.info.asticlib.clustering.Cluster.DistanceFormula;
import ro.info.asticlib.db.Dao;
import ro.info.asticlib.query.Query;
import ro.info.asticlib.tree.Node;
import ro.info.asticlib.tree.Tree;

public class HAClusteering {
	
	private List<Cluster> input;
	private Dao dao;
	private int lastClusterId = 0;
	public double[][] matrixDistance;
	
	
	public HAClusteering(List<Cluster> input) {
		super();
		this.input = input;
		dao = new Dao();
		lastClusterId = dao.getLastClusterId();
	}

	public Tree<Cluster> applyLogic(Query q){
		Tree<Cluster> tree = new Tree<Cluster>();
		new TfIdfCalculator().computeTfIdf(input, dao);
		//pune input ca frunze la tree;
		for(Cluster c:input){
			tree.addFirstNode(new Node<Cluster>(c.id+"", c));
			c.computeScor(q);
		}
		
		boolean stopCondition = input.size()>2;
		if(!stopCondition){
			matrixDistance = new double[input.size()][input.size()];
			for(int i=input.size()-1;i>=0;i--){
				Cluster selected = input.get(i);
				matrixDistance[i][i] = 1; // distanta dintre un cluster si el insusi este 0  1 - cosine 
				//insa pentru ca urmeaza sa calculam maximul este de preferat sa fie valuarea cea mai mica
				for(int j = input.size()-1; j>=0; j--){
					if(i!=j){
						Cluster other = input.get(j);
						double cosine = selected.getDistance(other, DistanceFormula.CosineRep);
						matrixDistance[i][j] = 1f - cosine;
					}
				}
			}
		}
		while(stopCondition){
			double [][] distances = new double[input.size()][input.size()];
			for(int i=input.size()-1;i>=0;i--){
				Cluster selected = input.get(i);
				distances[i][i] = 1; // distanta dintre un cluster si el insusi este 0  1 - cosine 
				//insa pentru ca urmeaza sa calculam maximul este de preferat sa fie valuarea cea mai mica
				for(int j = input.size()-1; j>=0; j--){
					if(i!=j){
						Cluster other = input.get(j);
						double cosine = selected.getDistance(other, DistanceFormula.CosineRep);
						distances[i][j] = 1f - cosine;
					}
				}
			}
			showMatrix(input,distances);
			if(matrixDistance == null){
				matrixDistance = new double[input.size()][input.size()];
				for(int i=0;i<matrixDistance.length;i++){
					for(int j=0;j<matrixDistance.length;j++){
						matrixDistance[i][j] = distances[i][j];
					}
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
			meargedNode.level = nodeOne.level + 1;
			meargedNode.addChildren(nodeOne);
			meargedNode.addChildren(nodeTwo);
			parent.addChildren(meargedNode);
			int max = Math.max(indexs[0], indexs[1]);
			int min = Math.min(indexs[0], indexs[1]);
			input.remove(max);//prima data remove index-ul mai mare pt ca in caz contrar se va da index-ul pestecap;
			input.remove(min);
			input.add(mearged);
			stopCondition = input.size()>2;
		}
		return tree;
	}
	
	/**
	 * Returneaza minimul celei  mai mici valori din matrice
	 * @param matrix
	 * @return
	 */
	private int[] minIndex(double [][]matrix){
		int k = 0;
		double min = 2;
		int minIIndex = -1;
		int minJIndex = -1;
		for(int i=matrix.length*matrix.length-1;i>=0;i--){
			k = i/matrix.length;
			if(min>matrix[k][i%matrix.length]){
				min = matrix[k][i%matrix.length];
				minIIndex = i%matrix.length;
				minJIndex = k;
			}
		}
		return new int[]{minIIndex,minJIndex};
	}
	private void showMatrix(List<Cluster> list,double [][]matrix){
		System.out.println("Matrix["+list.size()+"]["+list.size()+"]");
		for(Cluster c:list){
			System.out.println(c.fileWordMap.keySet().toArray()[0]);
		}
		System.out.print("    ");
		for(Cluster s:list){
			System.out.print(s.id+"   ");
		}
		System.out.println();
		for(int i=0;i<matrix.length;i++){
			System.out.print(" "+list.get(i).id+" ");
			for(int j=0;j<matrix.length;j++){
				System.out.print(format(matrix[i][j])+" ");
			}
			System.out.println();
		}
	}
	
	private String format(double nr){
		String str =new DecimalFormat("#.##").format(nr);
		if(str.length()<4){
			for(int i = str.length();i<4;i++){
				str+=" ";
			}
		}
		return str;
	}
	
	private int getClusterId(){
		return ++lastClusterId;
	}
	
	/*HashMap<String,Cluster> map = new HashMap<String, Cluster>();
	//sparge input-ul in clusteri cu un singur fisier
	for(Cluster c : input){
		if(c.fileWordMap.size()>1){//mai mult de un fisier;
			for(String file:c.fileWordMap.keySet()){
				Cluster newCluster = map.get(file);
				if(newCluster == null){
					newCluster = new Cluster();
					newCluster.fileWordMap.put(file, c.fileWordMap.get(file));
					newCluster.id = getClusterId();
					for(String word:newCluster.fileWordMap.get(file)){
						newCluster.wordWeightMap.put(word, c.wordWeightMap.get(word));
						map.put(file, newCluster);
					}
				}
			}
		}else{
			if(!map.containsKey(c.fileWordMap.keySet().toArray()[0])){
				map.put((String)c.fileWordMap.keySet().toArray()[0], c);
			}
		}
	}
	
	//convertesc din map in lista de clusteri
	List<Cluster> newInput = new ArrayList<Cluster>();
	for(String key:map.keySet()){
		newInput.add(map.get(key));
	}
	input.clear();
	input.addAll(newInput);*/
}
