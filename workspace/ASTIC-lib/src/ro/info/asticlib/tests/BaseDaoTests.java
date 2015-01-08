package ro.info.asticlib.tests;

import java.sql.SQLException;

import ro.info.asticlib.db.BaseDao;
import ro.info.asticlib.db.Tables;

public class BaseDaoTests {

	BaseDao baseDao = new BaseDao();
	
	public void testTableExists(){
		
		try {
			System.out.println(baseDao.existTable(Tables.FILE_JURNAL));
			System.out.println(baseDao.existTable(Tables.BW));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
