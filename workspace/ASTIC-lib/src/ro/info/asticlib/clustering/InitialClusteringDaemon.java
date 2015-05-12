package ro.info.asticlib.clustering;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributes;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.info.asticlib.conf.Conf;
import ro.info.asticlib.db.Dao;
import ro.info.asticlib.io.FileSystemWatcher;
import ro.info.asticlib.io.parsers.FileType;
import ro.info.asticlib.io.parsers.FileVisitor;
import ro.info.asticlib.io.parsers.FileVisitor.FileVisitCallback;
import ro.info.asticlib.io.parsers.FileVisitor.FileVisitObserver;
import ro.info.asticlib.word.WordProcesor;
import ro.info.asticlib.word.WordProcesor.Callback;

public abstract class InitialClusteringDaemon extends Thread implements FileVisitObserver,FileVisitCallback,
ClusteringService {
	private int SLEEP_TIME = 1*1000;
	private String root;
	private List<String> filesBuffer;
	private TfIdfCalculator tfidfCalc = new TfIdfCalculator();
	private Dao dao ;
	private State state;
	private FileSystemWatcher fileWatcher ;
	private FileVisitor visitor;
	private boolean forcedStoped = false;
	
	public  InitialClusteringDaemon(String root,FileSystemWatcher watcher){
		this.root = root;
		this.filesBuffer = new ArrayList<String>(Conf.DOC_SET_SIZE);
		this.dao = new Dao();
		this.fileWatcher = watcher;
	}
	
	

	@Override
	public void run() {
		initialClustering();
		int sleepCounter = 0;
		try{
			while(sleepCounter<=3){
				sleepCounter = isWorking()?0:sleepCounter+1;
				sleep(SLEEP_TIME);
			}
			System.out.println("Final processing");
			//tfidfCalc.computeTfIdf(dao);
			processBuffer();
			onFinish(null);
		}catch(Exception e){
			onFinish(e);
		}
	}
	
	private void changeState(State state){
		this.state = state;
	}
	
	public synchronized boolean isWorking(){
		return state == State.Clustering;
	}
	
	public  void stopD(){
		if(visitor != null){
			visitor.stop();
		}
		forcedStoped = true;
		Thread.currentThread().interrupt();
	}
	
	public boolean isForcedStoped(){
		return forcedStoped;
	}
	
	public void initialClustering(){
		//dao.dropTables();
		visitor = new FileVisitor(root,this,this);
		visitor.startVisitor();
		
	}
	
	
	public void clusterizeFilePath(final String file){
		WordProcesor reader = new WordProcesor(file);
		if(reader.getFileType() != FileType.Text){
			//System.out.println(file + " was skiped "+reader.getFileType().name());
			return ;
		}
		File f = new File(file);
		long modifiedDate = f.lastModified();
		long lastModifiedDate = dao.getLastModified(file);
		if(modifiedDate == lastModifiedDate){
			//System.out.println(file + " was skiped already parsed");
			return ;
		}
		reader.getMapWordWeight(new Callback() {
			public void onDone(HashMap<String, Float> map,int size) {
				if(map.size() < Conf.ACCEPTABLE_MAP_SIZE ){
					//System.out.println(file + " was skiped too small");
					return ;
				}
				System.out.println("File:("+file+")");
				//System.out.println(map);
				dao.saveWords(file, map, size);
				filesBuffer.add(file);
				if(filesBuffer.size()>Conf.DOC_SET_SIZE){
					
					updateTfIdfForDocs();
					processBuffer();
				}
			}
		});
	}
	
	private void updateTfIdfForDocs(){
		System.out.println("Compute & Update TFIDF for "+Conf.DOC_SET_SIZE + " docs");
		List<Document> docs = tfidfCalc.computeTfIdf(dao);
		AcceptanceRule rule = new TfIdfAcceptanceRule();
		for(Document d:docs){
			dao.updateTfIdf(d.tfidfMap, docs.size(), rule);
		}
		System.out.println("Finished updating TFIDF");
	}
	
	private void processBuffer(){
		for(String file:filesBuffer){
			Clusters.instance().processFileWords(file);
		}
		filesBuffer.clear();
	}
	
	@Override
	public void visitFile(final Path file, BasicFileAttributes attrs) {
		DosFileAttributes dosAttrs = (DosFileAttributes) attrs;
		boolean hiden = false;
		if(dosAttrs != null){
			hiden = dosAttrs.isHidden();
		}
		if(attrs.isRegularFile()&&!hiden){
			clusterizeFilePath(file.toFile().getAbsolutePath());
		}
		//Daca este director atunci inregistreaza un Watcher la director;
		Path parentPath = file.getParent();
		File parentFile = parentPath!=null?parentPath.toFile():null;
		if(parentFile!=null&&parentFile.isDirectory()){
			fileWatcher.registerWatcher(parentFile.getAbsolutePath());
		}
	}
	
	@Override
	public void onProbablyDone() {
		changeState(State.Sleep);
	}

	@Override
	public void onStartVisitFile() {
		changeState(State.Clustering);
	}
	
	@Override
	public void onFinish(Exception e) {
		
	}
	
	enum  State{
		Sleep,
		Clustering,
	}
	

	
}
