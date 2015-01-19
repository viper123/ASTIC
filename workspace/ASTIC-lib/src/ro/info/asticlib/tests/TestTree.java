package ro.info.asticlib.tests;

import ro.info.asticlib.tree.Node;
import ro.info.asticlib.tree.Tree;

public class TestTree {

	@SuppressWarnings("unchecked")
	public Tree<String> getTree(){
		Tree<String> tree = new Tree<>();
		Node<String> n1 = new Node<>("Node 1","1");
		Node<String> n2 = new Node<>("Node 2","2");
		Node<String> n3 = new Node<>("Node 3","3");
		tree.addFirstNodes(n1,n2,n3);
		for(Node<String> n:tree.getFirstCildrens()){
			for(int i=1;i<=4;i++){
				n.addChildren(new Node<String>(n.key+i,n.value+i));
			}
		}
		return tree;
	}
}
