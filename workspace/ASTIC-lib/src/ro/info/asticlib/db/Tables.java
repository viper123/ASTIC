package ro.info.asticlib.db;

public enum Tables {
	BW("create table BW (letter varchar2(1),word varchar2(40),weight integer, file_path varchar2(200))",
			"drop table BW",
			"insert into BW(letter,word,weight,file_path) values(?,?,?,?)"
			),
	FILE_JURNAL("create table FILE_JURNAL(file_path varchar2(200),tag_extraction date)",
			"drop table FILE_JURNAL",
			"insert into FILE_JURNAL(file_path,tag_extraction) values(?,?)"),
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
