package week04;

public class BinarySearchTree<E extends Comparable<E>> { // binary search tree

	/**
	 * 과제인 IPL을 recursion으로 구현하는 부분은 203줄에 있습니다.
	 * 
	 **/
	private class Node<E> {
		E key;
		Node<E> parent; // double linked list
		Node<E> left;
		Node<E> right;
		public Node(E k) {
			key = k;
			parent = null;
			left = null;
			right = null;
		}
		public String toString() {
			String returnval = "";
			return returnval + key;
		}
	}

	Node<E> root;
	
	public BinarySearchTree() {
		root = null;
	}
	
	public Node<E> insert(E x){
		Node<E> newNode = new Node<>(x);
		
		Node<E> inserted = insertNode(root, newNode); // insert되는게 newNode일 수도 있고 만일 같은 key값이 있으면 null일수도 있다. 
		
		if(inserted==null) System.out.println(">> key-duplication : Insert failed.");
		return root;
	}
	
	private Node<E> insertNode(Node<E> p, Node<E> x) {
		// 만일 p가 null이라면, 해당 트리에 노드가 하나가 없다. 그럼 x가 root가 된다.
		if(p==null) { // the only case : root
			root=x;
			return root;
		}
		if(x.key.compareTo(p.key)<0) { // x가 p보다 작을 경우
			if(p.left == null) {
				p.left=x; x.parent=p; return x;
			}
			else return insertNode(p.left,x);
		}
		else if(x.key.compareTo(p.key)>0){
			if(p.right == null) { // x가 p보다 클 경우
				p.right = x; x.parent = p; return x;
			}
			else return insertNode(p.right,x);
		}
		else{ //같은 경우
			return null; // key duplication
		}
	}

	public Node<E> search(Node<E> startNode, E k){
		Node<E> p = startNode;
		if(p==null) return null;
		else if (k.compareTo(p.key)<0) return search(p.left, k);
		else if (k.compareTo(p.key)>0) return search(p.right, k);
		else return p; // key값이 같은 경우
		
	}
	
	public Node<E> delete(E key){
		Node<E> x = search(root, key);
		if(x==null) {
			System.out.println(">> key "+key+" - Not Found : Delete failed.");
			return root;
		}
		if (x == root) {
			Node<E> r2 = deleteNode(x);
			// del한다는게 자신이 아닌 suc이나 pred라서, 다른 아이를 찾아야함. 
			// 실제 del하는 애는 따로 두고 어떤 애가 del되나 먼저 찾아보자.
			if (r2==null) {
				root=null; 
				return root;
			}
			if(r2.key.compareTo(root.key)<0) {
				r2.right = root.right;
				root=r2;
			}
			else if(r2.key.compareTo(root.key)>0) {
				r2.left = root.left;
				root=r2;
			}
			else {// 이부분은 발생하지 않을 거지만 모양을 위해 만들어주기.
				System.out.println(">> Wrong Situation.");
			}
		}
		//root가 x가 아니라면
		else if(x==x.parent.left) x.parent.left = deleteNode(x);
		else x.parent.right = deleteNode(x);
		return root;
	}
	
	private Node<E> deleteNode(Node<E> x) {
		// left나 right가 다 없으면 자기자신이 return 
		if(x.left == null && x.right==null) return null;
		else if (x.left==null && x.right !=null) return successor(x);
		else if (x.left!=null && x.right ==null) return predecessor(x);
		else { // 이 부분은 짜는 사람 맘이다.
			return successor(x);
		}
	}

	private Node<E> successor(Node<E> v){
		if(v==null || v.right == null) return null;
		Node<E> p = v.right;
		while(p.left!=null) p=p.left;
		return p;
	}
	private Node<E> predecessor(Node<E> v){
		if(v==null || v.left == null) return null;
		Node<E> p = v.left;
		while(p.right!=null) p=p.right;
		return p;
	}
	
//////////////////////////////////////////////////////////////
	public String toString() {
		// inorder traverse
		return inorder(root);
	}
	public String toString(Node<E> v) {
		// for printing subtree
		return inorder(v);
	}
	private String inorder(Node<E> v) {
		if(v==null) return "";
		else return inorder(v.left)+" "+ v.key+" "+inorder(v.right);
	}
	private String preorder(Node<E> v) {
		if(v==null) return "";
		else return  v.key+" "+ preorder(v.left)+" "+preorder(v.right);
	}
	private String postorder(Node<E> v) {
		if(v==null) return "";
		else return postorder(v.left)+" "+postorder(v.right)+" "+ v.key;
	}

////////////////////////////////////////
	private void rotateTest() {
		if(root!=null) {
			innerTest(root);
			innerTest(root);
			innerTest(root);
		}
	}
	private void innerTest(Node<E> v) {
		if(v.right!=null) rotateLeft(v);
		else if(v.left!=null) rotateRight(v);
	}

	private void rotateLeft(Node<E> v) {
		Node<E> u = v.right;
		u.parent = v.parent;
		if(u.parent==null) root=u; // u의 parent가 없으면 null이라는 의미.
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right = u;
		}
		v.parent = u;
		v.right = u.left;
		if(u.left!=null) u.left.parent = v;
		u.left = v;
	}
	private void rotateRight(Node<E> v) {
		Node<E> u = v.left;
		u.parent = v.parent;
		if(u.parent==null) root=u; // u의 parent가 없으면 null이라는 의미.
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right = u;
		}
		v.parent = u;
		v.left = u.right;
		if(u.right!=null) u.right.parent = v;
		u.right = v;
	}
	
///////////////////////////////////////
	public int height() {
		return evalMax(root,0);
	}
	
	private int evalMax(Node<E> p, int counter) {
		if(p==null) return counter;
		counter++;
		counter+=Math.max(evalMax(p.left,0), evalMax(p.right,0));
		return counter;
	}

///////////////////////////////////////
	public int IPL() { // homework 
		return IPLCounting(root,0);
		
	}
	private int IPLCounting(Node<E> p,int counter) {
		if(p==null) return 0;
		counter++;
		counter+=IPLCounting(p.left,counter)+IPLCounting(p.right,counter);
		return counter;
	}

///////////////////////////////////////
	public static void main(String[] args) {
		BinarySearchTree<Integer> mybst = new BinarySearchTree<>();
		
		for(int i = 0 ; i<20 ; i++) {
			mybst.insert(i);
			System.out.println(mybst.toString());
		}
		
		System.out.println(mybst.toString());
		System.out.println("Max, Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());
		mybst.rotateTest();
		System.out.println(mybst.toString());
		System.out.println("Max, Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());
		
		mybst.delete(3);
		System.out.println(mybst.toString());
		System.out.println("Max, Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());
		
	}
}
