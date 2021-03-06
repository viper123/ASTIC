package ro.info.asticlib.db;

public enum Triggers {
	FILE_TAGGING_LOGGER("CREATE OR REPLACE TRIGGER file_taging_logger "+
			"BEFORE INSERT "+
			"ON BW "+
			"FOR EACH ROW "+
			"DECLARE "+
			"BEGIN "+
			"insert into FILE_JURNAL(file_path,tag_extraction) values(:new.file_path,sysdate); "+
			"END; ",
			"drop trigger file_taging_logger");
	
	private Triggers(String creationSQL,String deletionSQL){
		this.creationSQL = creationSQL;
		this.deletionSQL = deletionSQL;
	}
	
	public String creationSQL ;
	public String deletionSQL;
}
