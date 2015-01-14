package ro.info.asticlib.tests;

import java.sql.SQLException;

import ro.info.asticlib.db.Dao;
import ro.info.asticlib.db.Tables;

public class DropTablesTest {

	public void dropAllTables(){
		Dao dao = new Dao();
		for(Tables t:Tables.values()){
			try {
				dao.executeSQLCommand(t.deletionSQL);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
