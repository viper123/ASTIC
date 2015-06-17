package ro.info.asticlib.query;

import java.util.ArrayList;
import java.util.List;

import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.tree.Tree;

public class QueryResult  {
	
	private Query query;
	private List<Result> results;
	@SuppressWarnings("rawtypes")
	private Tree resultTree;
	public List<Cluster> clusterList;
	public double [][] distanceMatrix;
	/**
	 * Numarul de grupuri care a dus la crearea structurii arborescente
	 */
	private int size;
	private List<String> predictions;
	
	public QueryResult() {
		results = new ArrayList<>();
	}
	
	public QueryResult(Query q){
		super();
		this.query = q;
	}
	
	@SuppressWarnings("unchecked")
	public void add(QueryResult result){
		if(result.getResults() != null){
			this.results.addAll(result.getResults());
		}
		if(resultTree == null){
			resultTree = result.resultTree;
		}else{
			resultTree.addFirstNode(resultTree.getRoot());
		}
		if(predictions == null){
			predictions = result.predictions;
		}else{
			predictions.addAll(result.predictions);
		}
		if(result.clusterList!=null){
			if(clusterList == null){
				clusterList = new ArrayList<>();
			}
			clusterList.addAll(result.clusterList);
		}
		if(result.distanceMatrix!=null){
			distanceMatrix =result.distanceMatrix;
		}
	}

	public Query getQuery() {
		return query;
	}

	public void setQuery(Query query) {
		this.query = query;
	}
	
	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}

	@SuppressWarnings("rawtypes")
	public Tree getResultTree() {
		return resultTree;
	}

	public void setResultTree(Tree<?> resultTree) {
		this.resultTree = resultTree;
	}

	public List<String> getPredictions() {
		return predictions;
	}

	public void setPredictions(List<String> predictions) {
		this.predictions = predictions;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	
	
	

	
	
}
