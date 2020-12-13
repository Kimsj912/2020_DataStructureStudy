package week04;

public class BinarySearchTree<E extends Comparable<E>> { // binary search tree

	/**
	 * ������ IPL�� recursion���� �����ϴ� �κ��� 203�ٿ� �ֽ��ϴ�.
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
		
		Node<E> inserted = insertNode(root, newNode); // insert�Ǵ°� newNode�� ���� �ְ� ���� ���� key���� ������ null�ϼ��� �ִ�. 
		
		if(inserted==null) System.out.println(">> key-duplication : Insert failed.");
		return root;
	}
	
	private Node<E> insertNode(Node<E> p, Node<E> x) {
		// ���� p�� null�̶��, �ش� Ʈ���� ��尡 �ϳ��� ����. �׷� x�� root�� �ȴ�.
		if(p==null) { // the only case : root
			root=x;
			return root;
		}
		if(x.key.compareTo(p.key)<0) { // x�� p���� ���� ���
			if(p.left == null) {
				p.left=x; x.parent=p; return x;
			}
			else return insertNode(p.left,x);
		}
		else if(x.key.compareTo(p.key)>0){
			if(p.right == null) { // x�� p���� Ŭ ���
				p.right = x; x.parent = p; return x;
			}
			else return insertNode(p.right,x);
		}
		else{ //���� ���
			return null; // key duplication
		}
	}

	public Node<E> search(Node<E> startNode, E k){
		Node<E> p = startNode;
		if(p==null) return null;
		else if (k.compareTo(p.key)<0) return search(p.left, k);
		else if (k.compareTo(p.key)>0) return search(p.right, k);
		else return p; // key���� ���� ���
		
	}
	
	public Node<E> delete(E key){
		Node<E> x = search(root, key);
		if(x==null) {
			System.out.println(">> key "+key+" - Not Found : Delete failed.");
			return root;
		}
		if (x == root) {
			Node<E> r2 = deleteNode(x);
			// del�Ѵٴ°� �ڽ��� �ƴ� suc�̳� pred��, �ٸ� ���̸� ã�ƾ���. 
			// ���� del�ϴ� �ִ� ���� �ΰ� � �ְ� del�ǳ� ���� ã�ƺ���.
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
			else {// �̺κ��� �߻����� ���� ������ ����� ���� ������ֱ�.
				System.out.println(">> Wrong Situation.");
			}
		}
		//root�� x�� �ƴ϶��
		else if(x==x.parent.left) x.parent.left = deleteNode(x);
		else x.parent.right = deleteNode(x);
		return root;
	}
	
	private Node<E> deleteNode(Node<E> x) {
		// left�� right�� �� ������ �ڱ��ڽ��� return 
		if(x.left == null && x.right==null) return null;
		else if (x.left==null && x.right !=null) return successor(x);
		else if (x.left!=null && x.right ==null) return predecessor(x);
		else { // �� �κ��� ¥�� ��� ���̴�.
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
		if(u.parent==null) root=u; // u�� parent�� ������ null�̶�� �ǹ�.
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
		if(u.parent==null) root=u; // u�� parent�� ������ null�̶�� �ǹ�.
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
