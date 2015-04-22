package ro.info.asticlib.tree;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ro.info.asticlib.io.parsers.Base64Coder;

public class Tree<T> implements Serializable {
	private static final long serialVersionUID = 13434L;

	public static String ROOT_KEY = "root"; 

	private Node<T> root;
	
	public Tree(){
		root = new Node<T>(ROOT_KEY,null);
	}
	
	public Node<T> getNode(List<Node<T>> list,String key){
		Node<T> result = null;
		for(Node<T> node:list){
			if(node.key.equals(key)){
				result = node;
				break;
			}else{
				Node<T> temp = getNode(node.childrens,key);
				result = temp!=null?temp:result;
			}
		}
		return result;
	}
	
	public Node<T> getNode(String key){
		return getNode(root.childrens,key);
	}
	
	public List<Node<T>> getNodes(List<Node<T>> list,int level){
		List<Node<T>> res = new ArrayList<>();
		for(Node<T> node:list){
			if(node.level == level){
				res.add(node);
			}
			res.addAll(getNodes(node.childrens,level));
		}
		return res;
	}
	
	public Node<T> getRoot(){
		return root;
	}
	
	public List<Node<T>> getFirstCildrens(){
		return root.childrens;
	}
	
	@SuppressWarnings("unchecked")
	public void addFirstNodes(Node<T> ...nodes){
		root.addChildrens(nodes);
	}
	
	public void addFirstNode(Node<T> node){
		root.addChildren(node);
	}
	
	public void addNode(String parentKey,Node<T> node){
		Node<T> parentNode =  getNode(root.childrens,parentKey);
		if(parentNode!=null){
			parentNode.childrens.add(node);
		}
	}
	
	public void visitNodes(OnNodeProcessListener<T> listener){
		visitNodes(root,listener);
	}
	
	private void visitNodes(Node<T> parent,OnNodeProcessListener<T> listener){
		listener.processNode(parent);
		for(Node<T> node:parent.childrens){
			visitNodes(node,listener);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Tree fromString( String s ) throws IOException ,
	ClassNotFoundException {
		byte [] data = Base64Coder.decode( s );
		XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(  data ) );
		
		Object o  = decoder.readObject();
		decoder.close();
		return (Tree)o;
	}

	/** Write the object to a Base64 string. */
	public  String toString() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLEncoder encoder = new XMLEncoder(baos);
		encoder.writeObject(this);
		encoder.close();
		return new String( Base64Coder.encode( baos.toByteArray() ) );
	}
	
	public interface OnNodeProcessListener<T>{
		public void processNode(Node<T> node); 
	}
}
