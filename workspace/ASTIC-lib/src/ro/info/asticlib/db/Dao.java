package ro.info.asticlib.db;

import java.io.File;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.query.QueryResult;
import ro.info.asticlib.query.Result;


/**
 * This will contain only the methods that will be used to retrive objects,
 * no sql utilitary methods will be present here
 * @author Elvis
 *
 */
public class Dao extends BaseDao {
	
	public Dao(){
		super();
	}
	
	public void saveTags(String path,HashMap<String, Integer> wordWeightMap){
		String insertSQL = Tables.BW.insertSQL;
		try{
			for(String word:wordWeightMap.keySet()){
				PreparedStatement pst = connection.prepareStatement(insertSQL);
				pst.setString(1, word.substring(0,1));
				pst.setString(2, word);
				pst.setInt(3,wordWeightMap.get(word));
				pst.setString(4, path);
				pst.executeUpdate();
			}
			logFileTagging(path);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void saveTags(File file,HashMap<String, Integer> wordWeightMap){
		saveTags(file.getAbsolutePath(), wordWeightMap);
	}
	
	public int getLastClusterId(){
		String selectSQL = "select id from clusters where id = (select max(id) from clusters)";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectSQL);
			while(result.next()){
				return result.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public void saveCluster(int id,String file){
		String insertSQL = Tables.CLUSTERS.insertSQL;
		try{
			PreparedStatement pst = connection.prepareStatement(insertSQL);
			pst.setInt(1, id);
			pst.setString(2, file);
			pst.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public List<Cluster> getAllClusters(){
		
		String selectFromClusters = "select b.id, a.file_path, a.word, a.weight "+
				"from bw a,clusters b where a.file_path = b.file_path ";
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectFromClusters);
			HashMap<Integer,Cluster> map = new HashMap<Integer, Cluster>();
			List<Cluster> allClusters = new ArrayList<Cluster>();
			while(result.next()){
				int id = result.getInt("ID");
				String word = result.getString("WORD");
				int weight = result.getInt("WEIGHT");
				String file_path = result.getString("FILE_PATH");
				Cluster c = map.get(id);
				if(c==null){
					c = new Cluster(id);
				}
				Set<String> words = c.fileWordMap.get(file_path);
				if(words==null){
					words = new HashSet<>();
					c.fileWordMap.put(file_path, words);
				}
				words.add(word);
				Integer savedWeight = c.wordWeightMap.get(word);
				savedWeight = savedWeight==null?0:savedWeight;
				weight+=savedWeight;
				c.wordWeightMap.put(word, weight);
				allClusters.add(c);
			}
			return allClusters;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Result> selectInClusters(String wordQuery){
		String joinClusterBW = "select b.id, a.file_path, a.word, a.weight "+
				"from bw a,clusters b where a.file_path = b.file_path AND a.word = "
				+wordQuery.toLowerCase();
		try{
			List<Result> results = new ArrayList<>();
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(joinClusterBW);
			while(result.next()){
				int id = result.getInt("ID");
				int weight = result.getInt("WEIGHT");
				String filePath = result.getString("FILE_PATH");
				results.add(new Result(filePath, weight, true, id+""));
			}
			return results;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Cluster> selectInClusters(String wordQuery,boolean returnCluster){
		String joinClusterBW = "select b.id, a.file_path, a.word, a.weight "+
				"from bw a,clusters b where a.file_path = b.file_path AND a.word = "
				+wordQuery.toLowerCase();
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(joinClusterBW);
			HashMap<Integer,Cluster> map = new HashMap<Integer, Cluster>();
			List<Cluster> allClusters = new ArrayList<Cluster>();
			while(result.next()){
				int id = result.getInt("ID");
				String word = result.getString("WORD");
				int weight = result.getInt("WEIGHT");
				String file_path = result.getString("FILE_PATH");
				Cluster c = map.get(id);
				if(c==null){
					c = new Cluster(id);
				}
				Set<String> words = c.fileWordMap.get(file_path);
				if(words==null){
					words = new HashSet<>();
					c.fileWordMap.put(file_path, words);
				}
				words.add(word);
				Integer savedWeight = c.wordWeightMap.get(word);
				savedWeight = savedWeight==null?0:savedWeight;
				weight+=savedWeight;
				c.wordWeightMap.put(word, weight);
				allClusters.add(c);
			}
			return allClusters;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Cluster getCluster(String id){
		String selectFromClusters = "select b.id, a.file_path, a.word, a.weight "+
				"from bw a,clusters b where a.file_path = b.file_path AND b.id = "+id;
		try {
			Statement stmt = connection.createStatement();
			ResultSet result = stmt.executeQuery(selectFromClusters);
			Cluster c = new Cluster();
			c.id = Integer.parseInt(id);
			while(result.next()){
				String word = result.getString("WORD");
				int weight = result.getInt("WEIGHT");
				String file_path = result.getString("FILE_PATH");
				Set<String> words = c.fileWordMap.get(file_path);
				if(words==null){
					words = new HashSet<>();
					c.fileWordMap.put(file_path, words);
				}
				words.add(word);
				Integer savedWeight = c.wordWeightMap.get(word);
				savedWeight = savedWeight==null?0:savedWeight;
				weight+=savedWeight;
				c.wordWeightMap.put(word, weight);
			}
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void logFileTagging(String file) throws SQLException{
		String insertSQL = Tables.FILE_JURNAL.insertSQL;
		PreparedStatement pst = connection.prepareStatement(insertSQL);
		pst.setString(1, file);
		pst.setDate(2, new Date(System.currentTimeMillis()));
		pst.executeUpdate();
	}
}
