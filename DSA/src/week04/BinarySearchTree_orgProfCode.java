//package week04;
//
//public class BinarySearchTree_orgProfCode<E extends Comparable<E>> { // binary search tree
//
//	private class Node<E> {
//		E key;
//		Node<E> parent;
//		Node<E> left;
//		Node<E> right;
//		public Node(E k) {
//			key = k;
//			parent = null;
//			left = null;
//			right = null;
//		}
//		public String toString() {
//			String returnval = "";
//			return returnval + key;
//		}
//	}
//
//	Node<E> root;
//	
//	public BinarySearchTree_orgProfCode() {
//		root = null;
//	}
//	
//	public Node<E> insert(E x){
//		
//	}
//	
//	public Node<E> search(Node<E> startNode, E k){
//		Node<E> p = startNode;
//		if(p==null) return null;
//		else if (k.compareTo(p.key)<0) return search(p.left, k);
//		else if (k.compareTo(p.key)>0) return search(p.right, k);
//		else return p; // key값이 같은 경우
//		
//	}
//	
//	public Node<E> delete(Node<E> startNode, E x){
//		
//	}
//	
//	private Node<E> successor(Node<E> v){
//		if(v==null || v.right == null) return null;
//		Node<E> p = v.right;
//		while(p.left!=null) p=p.left;
//		return p;
//	}
//	private Node<E> predecessor(Node<E> v){
//		if(v==null || v.left == null) return null;
//		Node<E> p = v.left;
//		while(p.right!=null) p=p.right;
//		return p;
//	}
//	
////////////////////////////////////////////////////////////////
//	public String toString() {
//		// inorder traverse
//		return inorder(root);
//	}
//	public String toString(Node<E> v) {
//		// for printing subtree
//		return inorder(v);
//	}
//	private String inorder(Node<E> v) {
//		if(v==null) return "";
//		else return inorder(v.left)+" "+ v.key+" "+inorder(v.right);
//	}
//	private String preorder(Node<E> v) {
//		if(v==null) return "";
//		else return  v.key+" "+ preorder(v.left)+" "+preorder(v.right);
//	}
//	private String postorder(Node<E> v) {
//		if(v==null) return "";
//		else return postorder(v.left)+" "+postorder(v.right)+" "+ v.key;
//	}
//
//////////////////////////////////////////
//	private void rotateLeft(Node<E> v) {
//		
//	}
//	private void rotateRight(Node<E> v) {
//		
//	}
//	
/////////////////////////////////////////
//	public int height() {
//		
//	}
//	
/////////////////////////////////////////
//	public int IPL() {
//		
//	}
//
/////////////////////////////////////////
//	public static void main(String[] args) {
//		BinarySearchTree_orgProfCode<Integer> mybst = new BinarySearchTree_orgProfCode<>();
//		
//		for(int i = 0 ; i<20 ; i++) {
//			mybst.insert(i);
//			System.out.println(mybst.toString());
//		}
//		
//		System.out.println(mybst.toString());
//		System.out.println("Max, Height : "+mybst.height());
//		System.out.println("IPL : "+mybst.IPL());
////		mybst.rotateTest();
//		System.out.println(mybst.toString());
//		System.out.println("Max, Height : "+mybst.height());
//		System.out.println("IPL : "+mybst.IPL());
//		
//		mybst.delete(3);
//		System.out.println(mybst.toString());
//		System.out.println("Max, Height : "+mybst.height());
//		System.out.println("IPL : "+mybst.IPL());
//		
//	}
//}
