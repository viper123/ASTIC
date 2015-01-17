package ro.info.asticlib.query;

public class Result{
	private String filePath;
	private int weight;
	private boolean isPrimary;
	private String clusterName;
	
	public Result(String filePath, int weight, boolean isPrimary,
			String clusterName) {
		super();
		this.filePath = filePath;
		this.weight = weight;
		this.isPrimary = isPrimary;
		this.clusterName = clusterName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public boolean isPrimary() {
		return isPrimary;
	}
	public void setPrimary(boolean isPrimary) {
		this.isPrimary = isPrimary;
	}
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	
	
}
