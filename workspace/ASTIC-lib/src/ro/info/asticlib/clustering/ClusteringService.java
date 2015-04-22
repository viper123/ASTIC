package ro.info.asticlib.clustering;

public interface ClusteringService {
	public void clusterizeFilePath(String filePath);
	public boolean isWorking();
	public void onFinish(Exception e);
}
