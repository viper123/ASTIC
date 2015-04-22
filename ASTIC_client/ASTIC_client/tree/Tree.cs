using System;
using System.Collections.Generic;
using System.Xml.Serialization;
using System.IO;
using Newtonsoft.Json;
namespace ASTIC_client {

public class Tree<T>  {

	public static String ROOT_KEY = "root";
    [JsonProperty]
	private Node<T> root;

    public Tree()
    {
    }
	
	public Tree(T t){
		root = new Node<T>(ROOT_KEY,t);
	}
	
	public Node<T> getNode(List<Node<T>> list,String key){
		foreach(Node<T> node in list){
			if(node.key.Equals(key)){
				return node;
			}else{
				return getNode(node.childrens,key);
			}
		}
		return null;
	}
	
	public List<Node<T>> getNodes(List<Node<T>> list,int level){
		List<Node<T>> res = new List<Node<T>>();
		foreach(Node<T> node in list){
			if(node.level == level){
				res.Add(node);
			}
			res.AddRange(getNodes(node.childrens,level));
		}
		return res;
	}
	
	public List<Node<T>> getFirstCildrens(){
		return root.childrens;
	}
	
	public void addFirstNodes(List<Node<T>> nodes){
		root.addChildrens(nodes);
	}
	
	public void addNode(String parentKey,Node<T> node){
		Node<T> parentNode =  getNode(root.childrens,parentKey);
		if(parentNode!=null){
			parentNode.childrens.Add(node);
		}
	}

    public static Tree<T> fromString(String s)
    {
        //byte[] data = Base64Coder.decode(s);
        byte[] data = Convert.FromBase64String(s);
        XmlSerializer reader =
            new XmlSerializer(typeof(Tree<T>));
        
        return (Tree<T>)reader.Deserialize(new MemoryStream(data));
    }

    ///** Write the object to a Base64 string. */
    //public  String toString() {
    //    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    //    XMLEncoder encoder = new XMLEncoder(baos);
    //    encoder.writeObject(this);
    //    encoder.close();
    //    return new String( Base64Coder.encode( baos.toByteArray() ) );
    //}
}
}
