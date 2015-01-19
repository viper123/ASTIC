using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace ASTIC_client.query
{
    class Query
    {
        public static  int LEVEL_0 = 0; // predictie
	    public static  int LEVEL_1 = 1; // ignorat
	    public static  int LEVEL_2 = 2; // resultate sub forma de arbore
	
	    public static String QUERY_SEP = " ";
	
	    private String query;
	    private String [] queryArray;
	    private int level;
	
	    public Query(){}
	
	    public Query( String[] queryArray,int level) :this() {
		    this.queryArray = queryArray;
		    this.level = level;
	    }   
	
	    public Query(String query, int level) :this() {
		    this.query = query;
	    	this.level = level;
	    }
	
	    public String getQuery() {
		    return query;
	    }

	    public void setQuery(String query) {
		    this.query = query;
	    }

	    public String[] getQueryArray() {
		    return queryArray;
	    }

	    public void setQueryArray(String[] queryArray) {
		    this.queryArray = queryArray;
	    }

	    public int getLevel() {
		    return level;
	    }

	    public void setLevel(int level) {
		    this.level = level;
	    }
    }
}
