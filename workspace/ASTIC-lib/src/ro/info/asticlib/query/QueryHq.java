package ro.info.asticlib.query;

import java.util.ArrayList;
import java.util.List;

public class QueryHq {
	
	List<IDataInterpretor> dataInterpretors;
	
	public QueryHq(){
		dataInterpretors = new ArrayList<>();
	}
	
	public void addDataInterpretor(IDataInterpretor interpretor){
		this.dataInterpretors.add(interpretor);
	}
	
	public QueryResult query(Query q){
		QueryResult result = new QueryResult(q);
		for(IDataInterpretor interpretor:dataInterpretors){
			result.add(interpretor.query(q));
		}
		return result;
	}

}
