package ro.info.asticlib.clustering;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.db.Dao;
import ro.info.asticlib.io.parsers.FileType;
import ro.info.asticlib.word.WordProcesor;
import ro.info.asticlib.word.WordProcesor.Callback;

public class BaseClusteringService implements ClusteringService{

	private State state;
	private Dao dao;
	
	private TfIdfCalculator tfidfCalc = new TfIdfCalculator();
	
	public BaseClusteringService(){
		dao = new Dao();
	}
	
	private void changeState(State state){
		this.state = state;
	}
	
	public synchronized boolean isWorking(){
		return state == State.Clustering;
	}

	public void clusterizeFilePath(final String file){
		changeState(State.Clustering);
		WordProcesor reader = new WordProcesor(file);
		if(reader.getFileType() != FileType.Text){
			System.out.println(file + " was skiped "+reader.getFileType().name());
			changeState(State.Sleep);
			return ;
		}
		
		reader.getMapWordWeight(new Callback() {
			public void onDone(HashMap<String, Float> map,int size) {
				if(map.size() < Conf.ACCEPTABLE_MAP_SIZE ){
					System.out.println(file + " was skiped too small");
					changeState(State.Sleep);
					return ;
				}
				System.out.println("File:("+file+")");
				System.out.println(map);
				dao.saveWords(file, map, size);
				updateTfIdfForDocs();
				Clusters.instance().processFileWords(file);
				changeState(State.Sleep);
			}
		});
	}
	
	private void updateTfIdfForDocs(){
		List<Document> docs = tfidfCalc.computeTfIdf(dao);
		AcceptanceRule rule = new TfIdfAcceptanceRule();
		for(Document d:docs){
			System.out.println("update tfidf for: "+d.path);
			dao.updateTfIdf(d.tfidfMap, docs.size(), rule);
		}
	}
	
	
	@Override
	public void onFinish(Exception e) {
		
	}
	
	enum  State{
		Sleep,
		Clustering,
	}

}
