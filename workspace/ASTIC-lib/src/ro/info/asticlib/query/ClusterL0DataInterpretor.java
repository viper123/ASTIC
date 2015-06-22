package ro.info.asticlib.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.clustering.HAClusteering;
import ro.info.asticlib.db.Dao;
import ro.info.asticlib.io.parsers.PreviewHelper;
import ro.info.asticlib.tree.Node;
import ro.info.asticlib.tree.Tree;
import ro.info.asticlib.tree.Tree.OnNodeProcessListener;



public class ClusterL0DataInterpretor implements IDataInterpretor {

	private Dao dao;
	
	public ClusterL0DataInterpretor() {
		dao = new Dao();
		
	}
	
	@Override
	public QueryResult query(Query q) {
		
		switch(q.getLevel()){
		case Query.LEVEL_0://predictie
			return queryL0(q);
		case Query.LEVEL_2://arbore
			return queryL2(q);
		}
		
		return null;
	}
	
	/**
	 * return predictions for a letter;
	 * @param q
	 * @return
	 */
	private QueryResult queryL0(Query q){
		QueryResult result = new QueryResult(q);
		result.setPredictions(dao.getWordsStarting(q.getQuery()));
		return result;
	}
	
	private QueryResult queryL2(final Query q){
		QueryResult result = new QueryResult(q);
		List<Cluster> allSelectedClusters = new ArrayList<Cluster>();
		for(String wordQuery:q.getQueryArray()){
			List<Cluster> clusters = dao.selectInClusters(wordQuery,q.getIndex(),Query.COUNT,true);//index and count are ignored
			allSelectedClusters.addAll(clusters);
		}
		List<Cluster> fullClusters = new ArrayList<Cluster>();
		for(Cluster c:allSelectedClusters){
			Cluster cluster = dao.getCluster(c.id+"");
			fullClusters.add(cluster);
			//assign preview
			PreviewHelper.assignPreview(cluster, q.getQueryArray());
		}
		result.clusterList = new ArrayList<>();
		result.clusterList.addAll(fullClusters);
		HAClusteering logic = new HAClusteering(fullClusters);
		Tree<Cluster> tree = logic.applyLogic(q);
		result.distanceMatrix = logic.matrixDistance;
		
		tree.visitNodes(new OnNodeProcessListener<Cluster>() {

			@Override
			public void processNode(Node<Cluster> node) {
				if(node.value == null){
					return ;
				}
				node.value.getReprezentativeWords(10);
				if(node.value.previewMap.isEmpty()){
					
				}
				/*List<String> mostSignificantWords = getMostSignifiatWords(node.value.wordWeightMap);
				float weight1 = 0,weight2 = 0,weight3 = 0;
				if(mostSignificantWords.get(0) != null){
					 weight1 = node.value.wordWeightMap.get(mostSignificantWords.get(0));
				}
				if(mostSignificantWords.get(1) != null){
					weight2 = node.value.wordWeightMap.get(mostSignificantWords.get(1));
						
				}
				if(mostSignificantWords.get(2) != null){
					weight3 = node.value.wordWeightMap.get(mostSignificantWords.get(2));
				}
				node.value.wordWeightMap.clear();
				node.value.wordWeightMap.put(mostSignificantWords.get(1),weight1);
				node.value.wordWeightMap.put(mostSignificantWords.get(2),weight2);
				node.value.wordWeightMap.put(mostSignificantWords.get(0),weight3);
				for(String key:node.value.fileWordMap.keySet()){
					if(node.value.fileWordMap.get(key).containsAll(mostSignificantWords)){
						node.value.fileWordMap.put(key, new HashSet<String>());
						node.value.fileWordMap.get(key).addAll(mostSignificantWords);
					}else{
						node.value.fileWordMap.put(key, new HashSet<String>());
					}
				}*/
			}
		});
		
		result.setResultTree(tree);
		result.setSize(allSelectedClusters.size());
		return result;
	}
	
	@SuppressWarnings("all")
	private List<String> getMostSignifiatWords(HashMap<String, Float> map){
		String w1 = null,w2 = null,w3 = null;
		float max = 0;
		for(String key:map.keySet()){
			if(key.length()<3){
				continue;
			}
			if(map.get(key) > max){
				max = map.get(key);
				w1 = key;
			}
		}
		max = 0;
		for(String key:map.keySet()){
			if(map.get(key) > max && key!=w1){
				if(key.length()<4){
					continue;
				}
				max = map.get(key);
				w2 = key;
			}
		}
		max = 0;
		for(String key:map.keySet()){
			if(key.length()<4){
				continue;
			}
			if(map.get(key) > max && key != w1 && key != w2){
				max = map.get(key);
				w3 = key;
			}
		}
		
		return Arrays.asList(w1,w2,w3);
		
	}

}
