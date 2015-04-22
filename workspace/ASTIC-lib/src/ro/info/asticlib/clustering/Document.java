package ro.info.asticlib.clustering;

import java.util.HashMap;
import java.util.Map;

public class Document {
	public Map<String,Float> tfidfMap;
	public String path;
	public Map<String,Float> tfMap;
	public Map<String,Float> weightMap;
	
	public Document(){
		this.tfMap = new HashMap<String, Float>();
		this.tfidfMap = new HashMap<String, Float>();
		this.weightMap = new HashMap<String, Float>();
	}
	
	public Document(String path){
		this();
		this.path = path;
	}
	
	public void addWord(String word,float tf,int weight){
		tfMap.put(word, tf);
		weightMap.put(word, (float)weight);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Document: ");
		builder.append(path);
		builder.append("\n");
		builder.append("TfMap:");
		for(String word:tfMap.keySet()){
			builder.append("<");
			builder.append(word);
			builder.append(",");
			builder.append(tfMap.get(word));
			builder.append(">");
			builder.append(", ");
		}
		builder.append("\n");
		builder.append("TfIdfMap:");
		for(String word:tfidfMap.keySet()){
			builder.append("<");
			builder.append(word);
			builder.append(",");
			builder.append(tfidfMap.get(word));
			builder.append(">");
			builder.append(", ");
		}
		builder.append("\n");
		return builder.toString();
	}
}
