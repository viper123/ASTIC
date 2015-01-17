package ro.info.asticlib.query;

import java.util.ArrayList;
import java.util.List;

public class QueryResult  {
	
	private Query query;
	private List<Result> results;
	
	public QueryResult() {
		results = new ArrayList<>();
	}
	
	public QueryResult(Query q){
		super();
		this.query = q;
	}
	
	public void add(QueryResult result){
		if(result.getResults() == null){
			return ;
		}
		this.results.addAll(result.getResults());
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

	
	
}
