using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using ASTIC_client.clustering;


namespace ASTIC_client.query
{
    class QueryResult
    {
        private Query query;
	    private List<Result> results;
	    private Tree<Cluster> resultTree;
        private List<String> predictions;
	
	    public QueryResult() {
	    }
	
	    public QueryResult(Query q){
		    this.query = q;
	    }
	
	    public void add(QueryResult result){
		
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

	    public Tree<Cluster> getResultTree() {
		    return resultTree;
	    }

	    public void setResultTree(Tree<Cluster> resultTree) {
		    this.resultTree = resultTree;
	    }

        public List<String> getPredictions()
        {
            return predictions;
        }

        public void setPredictions(List<String> predictions)
        {
            this.predictions = predictions;
        }
    }
}
