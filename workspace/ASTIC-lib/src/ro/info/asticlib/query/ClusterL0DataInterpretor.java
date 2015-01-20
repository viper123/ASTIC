package ro.info.asticlib.query;

import java.util.ArrayList;
import java.util.List;

import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.clustering.HAClusteering;
import ro.info.asticlib.db.Dao;
import ro.info.asticlib.tree.Tree;



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
	
	private QueryResult queryL2(Query q){
		List<Cluster> allSelectedClusters = new ArrayList<Cluster>();
		for(String wordQuery:q.getQueryArray()){
			allSelectedClusters.addAll(dao.selectInClusters(wordQuery,true));
		}
		HAClusteering logic = new HAClusteering(allSelectedClusters);
		Tree<Cluster> tree = logic.applyLogic();
		QueryResult result = new QueryResult(q);
		result.setResultTree(tree);
		return result;
	}

}
