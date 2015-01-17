package ro.info.asticlib.query;

import ro.info.asticlib.db.Dao;

public class ClusterL0DataInterpretor implements IDataInterpretor {

	private Dao dao;
	
	public ClusterL0DataInterpretor() {
		dao = new Dao();
	}
	
	@Override
	public QueryResult query(Query q) {
		
		return null;
	}

}
