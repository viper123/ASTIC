package ro.info.asticlib.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Node<T> implements Serializable {
	private static final long serialVersionUID = -2721655138569191786L;
	public List<Node<T>> childrens;
	public Node<T> parent;
	public int level;
	public T value;
	public String key;
	
	public Node(String key,T value){
		this.value = value;
		this.key = key;
		this.childrens = new ArrayList<>();
	}
	
	public void addChildren(Node<T> node){
		node.level = level+1;
		node.parent = this;
		this.childrens.add(node);
	}
	
	@SuppressWarnings("unchecked")
	public void addChildrens(Node<T> ... nodes){
		for(Node<T> n:nodes){
			n.level = level+1;
			childrens.add(n);
		}
	}
	
	public void remove(Node node){
		childrens.remove(node);
	}

	
}
