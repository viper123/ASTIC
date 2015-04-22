package ro.info.asticlib.query;

public class Query {
	public static final int LEVEL_0 = 0; // predictie
	public static final int LEVEL_1 = 1; // ignorat
	public static final int LEVEL_2 = 2; // resultate sub forma de arbore
	
	public static final String QUERY_SEP = " ";
	public static final int COUNT = 50;
	
	private String query;
	private String [] queryArray;
	private int level;
	private int index;
	
	public Query(){}
	
	public Query( String[] queryArray,int level) {
		super();
		this.queryArray = queryArray;
		this.level = level;
	}
	
	public Query(String query, int level) {
		super();
		this.query = query;
		this.level = level;
	}
	
	public Query( String[] queryArray,int level,int index) {
		super();
		this.queryArray = queryArray;
		this.level = level;
		this.index = index;
	}
	
	public Query(String query, int level,int index) {
		super();
		this.query = query;
		this.level = level;
		this.index = index;
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
	
}
