package ro.info.asticlib.db;

public enum Tables {
	BW("create table BW (letter varchar2(1),"
			+ "word varchar2(40),"
			+ "weight integer,"
			+ "tf float(10),"
			+ "tfidf float(10), "
			+ "file_path varchar2(200), "
			+ "deleted number(1))",
			"drop table BW",
			"insert into BW(letter,word,weight,tf,tfidf,file_path,deleted) values(?,?,?,?,?,?,?)"
			),
	FILE_JURNAL("create table FILE_JURNAL(file_path varchar2(200),last_modified number(38))",
			"drop table FILE_JURNAL",
			"insert into FILE_JURNAL(file_path,last_modified) values(?,?)"),
	CLUSTERS("create table CLUSTERS(id integer,file_path varchar2(200))",
			"drop table CLUSTERS",
			"insert into CLUSTERS(id,file_path) values(?,?)");
	
	private Tables(String creationSQL,String deletionSQL,String insertSQL){
		this.creationSQL = creationSQL;
		this.deletionSQL = deletionSQL;
		this.insertSQL = insertSQL;
	}
	
	public String creationSQL;
	public String deletionSQL;
	public String insertSQL;
}
