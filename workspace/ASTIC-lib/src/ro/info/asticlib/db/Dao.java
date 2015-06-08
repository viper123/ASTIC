package ro.info.asticlib.db;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ro.info.asticlib.clustering.AcceptanceRule;
import ro.info.asticlib.clustering.Cluster;
import ro.info.asticlib.clustering.Document;
import ro.info.asticlib.query.Result;


/**
 * This will contain only the methods that will be used to retrive objects,
 * no sql utilitary methods will be present here
 * @author Elvis
 *
 */
public class Dao extends BaseDao {

	private InvertedClusterIndex invertedClusterIndex;
	
	public Dao(){
		super();
	}
	
	public void saveWords(String path,HashMap<String, Float> wordWeightMap,int size){
		String insertSQL = Tables.BW.insertSQL;
		try{
			for(String word:wordWeightMap.keySet()){
				PreparedStatement pst = connection.prepareStatement(insertSQL);
				String wordTrimed = trim(word,40);
				pst.setString(1, wordTrimed.substring(0,1));
				pst.setString(2, wordTrimed);
				pst.setInt(3,wordWeightMap.get(word).intValue());
				pst.setFloat(4, wordWeightMap.get(word)/size);
				pst.setFloat(5, 0.0f);
				pst.setString(6, path);
				pst.setInt(7, 0);
				pst.executeUpdate();
				pst.close();
			}
			logFileTagging(path);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void saveWords(File file,HashMap<String, Float> wordWeightMap,int size){
		saveWords(file.getAbsolutePath(), wordWeightMap,size);
	}
	
	public int getLastClusterId(){
		String selectSQL = "select id from clusters where id = (select max(id) from clusters)";
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = connection.createStatement();
			result = stmt.executeQuery(selectSQL);
			while(result.next()){
				return result.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch(Exception e){}
			try{
				result.close();
			}catch(Exception e){
			}
		}
		return 0;
	}
	
	public void saveCluster(int id,String file){
		String insertSQL = Tables.CLUSTERS.insertSQL;
		PreparedStatement pst = null;
		try{
			pst = connection.prepareStatement(insertSQL);
			pst.setInt(1, id);
			pst.setString(2, file);
			pst.executeUpdate();
			pst.close();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try {
				pst.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public List<Cluster> getAllClusters(){
		String selectFromClusters = "select b.id, a.file_path, a.word,a.weight, a.tf "+
				"from bw a,clusters b where a.file_path = b.file_path ";
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = connection.createStatement();
			result = stmt.executeQuery(selectFromClusters);
			HashMap<Integer,Cluster> map = new HashMap<Integer, Cluster>();
			List<Cluster> allClusters = new ArrayList<Cluster>();
			while(result.next()){
				int id = result.getInt("ID");
				String word = result.getString("WORD");
				int weight = result.getInt("WEIGHT");
				//float tf = result.getFloat("TF");
				String file_path = result.getString("FILE_PATH");
				Cluster c = map.get(id);
				if(c==null){
					c = new Cluster(id);
					map.put(id,c);
					allClusters.add(c);
				}
				Set<String> words = c.fileWordMap.get(file_path);
				if(words==null){
					words = new HashSet<>();
					c.fileWordMap.put(file_path, words);
				}
				words.add(word);
				Float savedWeight = c.wordWeightMap.get(word);
				savedWeight = savedWeight==null?0:savedWeight;
				weight+=savedWeight;
				c.wordWeightMap.put(word, (float)weight);
			}
			stmt.close();
			result.close();
			return allClusters;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch(Exception e){}
			try{
				result.close();
			}catch(Exception e){
			}
		}
		return null;
	}
	
	public List<Result> selectInClusters(String wordQuery){
		String joinClusterBW = "select b.id, a.file_path, a.word, a.weight "+
				"from bw a,clusters b where a.file_path = b.file_path AND a.word = "
				+wordQuery.toLowerCase();
		Statement stmt = null;
		ResultSet result = null;
		try{
			List<Result> results = new ArrayList<>();
			stmt = connection.createStatement();
			result = stmt.executeQuery(joinClusterBW);
			while(result.next()){
				int id = result.getInt("ID");
				int weight = result.getInt("WEIGHT");
				String filePath = result.getString("FILE_PATH");
				results.add(new Result(filePath, weight, true, id+""));
			}
			stmt.close();
			result.close();
			return results;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch(Exception e){}
			try{
				result.close();
			}catch(Exception e){
			}
		}
		return null;
	}
	
	public List<Cluster> selectInClusters(String wordQuery,int index,int count,
			boolean returnCluster){
		String joinClusterBW = "select b.id, a.file_path, a.word, a.tfidf "+
				"from bw a,clusters b where a.file_path = b.file_path AND ( LOWER(word) LIKE ?"+
				" OR LOWER(word) LIKE ?" + 
				" OR LOWER(word) LIKE ?" + 
				" OR LOWER(word) LIKE ?)";
		/*String paginationWrapper = "select * from ( select rownum rnum, aa.* "+
				"from ("+ joinClusterBW +") aa where rownum < ? )"+
				"where rnum >= ? ";*/
		/*String joinClusterBW = "select b.id, a.file_path, a.word, a.weight "+
				"from bw a,clusters b where a.file_path = b.file_path AND LOWER(word) = ?";*/
		PreparedStatement stmt = null;
		ResultSet result = null;
		try {
			stmt = connection.prepareStatement(joinClusterBW);
			String q = wordQuery.toLowerCase();
			stmt.setString(1, q);
			//stmt.setInt(5, index);
			stmt.setString(2,q+"%");
			stmt.setString(3,"%"+q+"%");
			stmt.setString(4,"%"+q);
			//stmt.setInt(4, index + count);
			result = stmt.executeQuery();
			HashMap<Integer,Cluster> map = new HashMap<Integer, Cluster>();
			List<Cluster> allClusters = new ArrayList<Cluster>();
			while(result.next()){
				int id = result.getInt("ID");
				String word = result.getString("WORD");
				
				float weight = result.getFloat("TFIDF");
				String file_path = result.getString("FILE_PATH");
				Cluster c = map.get(id);
				if(c==null){
					c = new Cluster(id);
					allClusters.add(c);
					map.put(id, c);
				}
				Set<String> words = c.fileWordMap.get(file_path);
				if(words==null){
					words = new HashSet<>();
					c.fileWordMap.put(file_path, words);
				}
				words.add(word);
				Float savedWeight = c.wordWeightMap.get(word);
				savedWeight = savedWeight==null?0:savedWeight;
				weight+=savedWeight;
				c.wordWeightMap.put(word, weight);
			}
			stmt.close();
			result.close();
			return allClusters;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch(Exception e){}
			try{
				result.close();
			}catch(Exception e){
			}
		}
		return null;
	}
	
	public Cluster getCluster(String id){
		String selectFromClusters = "select b.id, a.file_path, a.word, a.weight "+
				"from bw a,clusters b where a.file_path = b.file_path AND b.id = "+id;
		Statement stmt = null;
		ResultSet result = null;
		try {
			stmt = connection.createStatement();
			result = stmt.executeQuery(selectFromClusters);
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
				Float savedWeight = c.wordWeightMap.get(word);
				savedWeight = savedWeight==null?0:savedWeight;
				weight+=savedWeight;
				c.wordWeightMap.put(word, (float)weight);
			}
			stmt.close();
			result.close();
			return c;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch(Exception e){}
			try{
				result.close();
			}catch(Exception e){
			}
		}
		return null;
	}
	
	public List<Cluster> getClustersContaining(String word){
		restoreInvertedClusterIndex();
		List<Cluster> clusters = new ArrayList<>();
		List<Integer> clustersId = invertedClusterIndex.getClustersContainingWord(word);
		if(clustersId == null){
			return clusters;
		}
		for(Integer id:clustersId){
			Cluster c = getCluster(id.toString());
			clusters.add(c);
		}
		return clusters;
	}
	
	public List<String> getWordsStarting(String q){
		if(q.length()<2){
			return new ArrayList<>();
		}
		Set<String> list = new HashSet<String>();
		String select = "select word from bw where LOWER(word) LIKE ? OR LOWER(word) LIKE ?"+
							" OR LOWER(word) LIKE ?";
		PreparedStatement stmt = null;
		ResultSet result = null;
		try{
			stmt = connection.prepareStatement(select);
			q = q.toLowerCase();
			stmt.setString(1,q+"%");
			stmt.setString(2,"%"+q+"%");
			stmt.setString(3,"%"+q);
			result = stmt.executeQuery();
			while(result.next()){
				String word = result.getString("WORD");
				list.add(word);
			}
			stmt.close();
			result.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				stmt.close();
			}catch(Exception e){}
			try{
				result.close();
			}catch(Exception e){
			}
		}
		return new ArrayList<>(list);
	}
	
	public HashMap<String,Float> getWordsWeightForFile(String path){
		HashMap<String, Float> wordsWeightMap = new HashMap<String, Float>();
		String select = "select word,weight from bw where file_path=? AND deleted = 0";
		PreparedStatement pst = null;
		ResultSet results = null;
		try{
			pst = connection.prepareStatement(select);
			pst.setString(1, path);
			results = pst.executeQuery();
			while(results.next()){
				String word = results.getString("WORD");
				int weight = results.getInt("WEIGHT");
				wordsWeightMap.put(word, (float)weight);
			}
			results.close();
			pst.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();
			}catch(Exception e){}
			try{
				results.close();
			}catch(Exception e){
			}
		}
		return wordsWeightMap;
	}
	
	public List<Document> getAllDocuments(){
		Map<String,Document> documents = new HashMap<String, Document>();
		String select = "select * from bw";
		PreparedStatement pst = null;
		ResultSet results = null;
		try{
			pst = connection.prepareStatement(select);
			results = pst.executeQuery();
			while(results.next()){
				String path = results.getString("FILE_PATH");
				float tf = results.getFloat("TF");
				int weight = results.getInt("WEIGHT");
				String word = results.getString("WORD");
				Document d = documents.get(path);
				if(d == null){
					d = new Document(path);
					documents.put(path, d);
				}
				d.addWord(word, tf,weight);
			}
			results.close();
			pst.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();
			}catch(Exception e){}
			try{
				results.close();
			}catch(Exception e){
			}
		}
		return new ArrayList<Document>(documents.values());
	}
	
	public int getFileCountContaining(String word){
		String select = "select count(distinct file_path) as count from bw  where word = ?";
		PreparedStatement pst = null;
		ResultSet result = null;
		try{
			pst = connection.prepareStatement(select);
			pst.setString(1, word);
			result = pst.executeQuery();
			while(result.next()){
				return result.getInt("COUNT");
			}
			result.close();
			pst.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst.close();
			}catch(Exception e){}
			try{
				result.close();
			}catch(Exception e){
			}
		}
		return 0;
	}
	
	public int getClusterCountContaining(String word){
		return -1;
	}
	
	public void updateInvertedClusterIndex(int clusterId,HashMap<String,Float> words){
		restoreInvertedClusterIndex();
		if(invertedClusterIndex == null){
			invertedClusterIndex = new InvertedClusterIndex();
		}
		for(String word:words.keySet()){
			List<Integer> clusters = invertedClusterIndex.map.get(word);
			if(clusters == null){
				clusters = new ArrayList<>();
				invertedClusterIndex.map.put(word, clusters);
			}
			if(!clusters.contains(clusterId)){
				clusters.add(clusterId);
			}
		}
		saveInvertedClusterIndex();
	}
	
	private InvertedClusterIndex restoreInvertedClusterIndex(){
		if(invertedClusterIndex == null){
			invertedClusterIndex = load(InvertedClusterIndex.class.getName(),InvertedClusterIndex.class);
		}
		return invertedClusterIndex;
	}
	
	private void  saveInvertedClusterIndex(){
		save(InvertedClusterIndex.class.getName(),invertedClusterIndex);
	}
	
	private void deleteInvertedClusterIndex(){
		save(InvertedClusterIndex.class.getName(),null);
	}
	
	public void updateTfIdf(Map<String,Float> tfIdfMap,Integer N,AcceptanceRule rule){
		String updateSQL = "update bw set tfidf = ?, deleted = ? where word = ?";
		PreparedStatement pst = null;
		try{
			for(String word:tfIdfMap.keySet()){
				float tfidf = tfIdfMap.get(word);
				boolean deletable = !rule.apply(tfidf,N);
				pst = connection.prepareStatement(updateSQL);
				pst.setFloat(1, tfidf);
				pst.setInt(2, deletable?1:0);
				pst.setString(3, word);
				pst.executeUpdate();
				pst.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteFileFromTables(String path){
		String deleteBW = "delete from bw where file_path = ?";
		String deleteClusters = "delete from clusters where file_path = ?";
		String deleteFileLoging = "delete from file_jurnal where file_path = ?";
		PreparedStatement pst_bw =null;
		PreparedStatement pst_clusters = null;
		PreparedStatement pst_file_log = null;
		try{
			pst_bw = connection.prepareStatement(deleteBW);
			pst_bw.setString(1, path);
			pst_bw.executeUpdate();
			pst_bw.close();
			pst_clusters = connection.prepareStatement(deleteClusters);
			pst_clusters.setString(1, path);
			pst_clusters.executeUpdate();
			pst_clusters.close();
			pst_file_log = connection.prepareStatement(deleteFileLoging);
			pst_file_log.setString(1, path);
			pst_file_log.executeUpdate();
			pst_file_log.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				pst_bw.close();
			}catch(Exception e){
			}
			try{
				pst_clusters.close();
			}catch(Exception e){
			}
			try{
				pst_file_log.close();
			}catch(Exception e){
			}
		}
		
	}
	
	public void dropTables(){
		deleteInvertedClusterIndex();
		ResultSet result = null;
		for(Tables table:Tables.values()){
			try {
				result = executeSQLCommand(table.deletionSQL);
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try{
					result.close();
				}catch(Exception e){
					
				}
			}
		}
	}
	
	private void logFileTagging(String file_path) throws SQLException{
		String insertSQL = Tables.FILE_JURNAL.insertSQL;
		PreparedStatement pst = connection.prepareStatement(insertSQL);
		pst.setString(1, file_path);
		File file = new File(file_path);
		pst.setLong(2, file.lastModified());
		pst.executeUpdate();
		pst.close();
	}
	
	public long getLastModified(String file){
		String selectSQL = "select last_modified from "+Tables.FILE_JURNAL.name() + 
				" where file_path = ?";
		PreparedStatement stmt = null;
		ResultSet result = null;
		try{
			stmt = connection.prepareStatement(selectSQL);
			stmt.setString(1, file);
			result = stmt.executeQuery();
			while(result.next()){
				return result.getLong(1);
			}
			stmt.close();
			result.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				result.close();
			}catch(Exception e){
				
			}
			try{
				stmt.close();
			}catch(Exception e){
				
			}
		}
		return 0;
	}
	
	private String trim(String word,int length){
		if(word.length()>length){
			return word.substring(0,length-1);
		}else{
			return word;
		}
	}
}
