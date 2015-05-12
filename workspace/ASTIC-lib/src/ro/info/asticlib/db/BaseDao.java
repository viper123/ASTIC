package ro.info.asticlib.db;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class contains all method used for working with DB
 * @author Elvis
 *
 */
public class BaseDao {
		
	public static Connection connection;
	
	public BaseDao(){
		if(connection == null){
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				connection = DriverManager.getConnection(
						"jdbc:oracle:thin:@localhost:1521:XE", "system",
						"ppp123");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		//create tables if they do not exist
		createTables();
	}
	
	public ResultSet executeSQLCommand(String command) throws SQLException{
		if(connection==null){
			return null;
		}
		Statement stmt = connection.createStatement();
	    ResultSet result =  stmt.executeQuery(command);
	    stmt.close();
	    return result;
	}
	
	private final void createTables(){
		for(Tables t:Tables.values()){
			try{
				if(!existTable(t)){
					ResultSet result = executeSQLCommand(t.creationSQL);
					result.close();
				}
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public final boolean existTable(Tables t) throws SQLException{
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables = dbm.getTables(null, null, t.name(), null);
		boolean exist =  tables.next();
		tables.close();
		return exist;
	}
	
	@SuppressWarnings("unchecked")
	public final <T>T load(String key, Class<T> clazz){
		File file = new File(System.getProperties().getProperty("user.dir"),key);
		if(!file.exists()){
			return null;
		}
		ObjectInputStream stream = null;
		try{
			stream = new ObjectInputStream(new FileInputStream(file));
			return (T) stream.readObject();
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			try{
				stream.close();
			}catch(Exception e){}
		}
	}
	
	public final void save(String key,Object object){
		File file = new File(System.getProperties().getProperty("user.dir"),key);
		ObjectOutputStream stream = null;
		try{
			stream = new ObjectOutputStream(new FileOutputStream(file));
			stream.writeObject(object);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				stream.close();
			}catch(Exception e){			}
		}
	}
}
