
using System.Collections.Generic;
using System;
using Newtonsoft.Json;
namespace ASTIC_client{

public class Node<T>  {
	private const long serialVersionUID = -2721655138569191786L;
    [JsonProperty]
	public List<Node<T>> childrens;
    [JsonProperty]
	public int level;
    [JsonProperty]
	public T value;
    [JsonProperty]
	public String key;
	
	public Node(String key,T value){
		this.value = value;
		this.key = key;
		this.childrens = new List<Node<T>>();
	}
	
	public void addChildren(Node<T> node){
		node.level = level+1;
		this.childrens.Add(node);
	}
	
	public void addChildrens(List<Node<T>> nodes){
		foreach(Node<T> n in nodes){
			n.level = level+1;
			childrens.Add(n);
		}
	}

	
    }
}
