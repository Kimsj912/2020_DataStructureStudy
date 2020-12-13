package week04;

public class RBSearchTree<E extends Comparable<E>> { 
	/**
	 * ������ fixupBlackNode�� right child�� 227�ٿ� �ֽ��ϴ�.
	 * 
	**/
	private class Node<E> {
		E key;
		String color; //"R" , "B"
		Node<E> parent; // double linked list
		Node<E> left;
		Node<E> right;
		
		public Node(E k) {
			key = k;
			color = "R";
			// BST���� null�� �� ���� - nilNode�� ����Ű�� ���� �׋��׶� ����������.
		}
		public String toString() {
			String returnval = "";
			return returnval + key+"("+color+")";
		}
	}

	Node<E> root;
	Node<E> nilNode;
	
	public RBSearchTree() {
		nilNode = new Node<>(null);
		nilNode.color="B";
		root = nilNode;
	}
/////////////////////////////////////////////////
	public Node<E> insert(E x){
		Node<E> newNode = new Node<>(x);
		newNode.left = nilNode;
		newNode.right = nilNode;
		
		Node<E> inserted = insertNode(root, newNode); // insert�Ǵ°� newNode�� ���� �ְ� ���� ���� key���� ������ null�ϼ��� �ִ�. 
		
		if(inserted==null) { 
			System.out.println(">> key-duplication : Insert failed.");
			return root;
		}
		insertFixup(inserted); // insert�� ������ �����ϴ��� �����ִ� �Լ�. // �̰͸� �����ϸ� BST�� ����.
		return root;
	}
	
	private Node<E> insertNode(Node<E> p, Node<E> x) {
		// ���� p�� null�̶��, �ش� Ʈ���� ��尡 �ϳ��� ����. �׷� x�� root�� �ȴ�.
		if(p==nilNode) { // the only case : root
			root=x;
			x.parent=nilNode;
			return root;
		}
		if(x.key.compareTo(p.key)<0) { // x�� p���� ���� ���
			if(p.left == nilNode) {
				p.left=x; x.parent=p; return x;
			}
			else return insertNode(p.left,x);
		}
		else if(x.key.compareTo(p.key)>0){
			if(p.right == nilNode) { // x�� p���� Ŭ ���
				p.right = x; x.parent = p; return x;
			}
			else return insertNode(p.right,x);
		}
		else{ //���� ���
			return null; // key duplication
		}
	}

	private void insertFixup(Node<E> x) {
		if(x==root) {
			x.color = "B";
			return;
		}
		if(x.parent.color == "B") return;
		else { //x.parent.color=="R", ���� x.paret.parent("B") ����
			if(x.parent == x.parent.parent.left) { 
				if(x.parent.parent.right.color=="R") {// sibling is "R"
					x.parent.parent.color="R";
					x.parent.parent.right.color="B";
					x.parent.color="B";
					insertFixup(x.parent.parent);
				}
				else {//sibling x.parent.parent.right.color=="B
					if(x==x.parent.right) {
						x=x.parent;
						rotateLeft(x);
					}
					// x==x.parent.left or from above...
					x.parent.color="B";
					x.parent.parent.color="R";
					rotateRight(x.parent.parent);
				}
			}
			else { // x.parent == x.parent.parent.right 
				if(x.parent.parent.left.color=="R") { // sibling is "R"
					x.parent.parent.color="R";
					x.parent.parent.left.color="B";
					x.parent.color="B";
					insertFixup(x.parent.parent);
				}
				else { // sibling x.parnet.parent.left.color =="B
					if(x==x.parent.left) {
						x=x.parent;
						rotateRight(x);
					}
					x.parent.color="B";
					x.parent.parent.color="R";
					rotateLeft(x.parent.parent);
				}
			}
		}
	}
	
	public Node<E> search(Node<E> startNode, E k){
		Node<E> p = startNode;
		if(p==nilNode) return null;
		else if (k.compareTo(p.key)<0) return search(p.left, k);
		else if (k.compareTo(p.key)>0) return search(p.right, k);
		else return p; // key���� ���� ���
		
	}
/////////////////////////////////////////////////////////////////////
	public Node<E> delete(E key){
		Node<E> x = search(root, key);
		if(x==null) {
			System.out.println(">> key "+key+" - Not Found : Delete failed.");
			return root;
		}
		// ���⼭���� �ٸ���
		Node<E> m = deleteNode(x);
		if(m!=nilNode) {
			x.key=m.key;
		}
		// x�� ��¥ �����Ǿ���ϴ� ��尡 �� ��.
		deleteNFixup(m);
		return root;
	}
	
	private void deleteNFixup(Node<E> m) {
		// m�� �ִ� �ϳ��� child�� ������. <= successor�Ǵ� predecessor�̹Ƿ�.
		
		if(m==nilNode) {
			System.out.println(">> Something wrong... ");
			return;
		}
		if(m.left==nilNode && m.right==nilNode) {
			executeDelete(m);
			return;
		}
		if(m.left!=nilNode) { // �ڽ��� �׻� �������� �ǵ��� ����
			m.right=m.left;
			m.left=nilNode;
		}
		if(m.color=="R") { // delete 1��
			executeDelete(m);
			return;
		}
		// Now, m.color = "B"
		if(m.right.color=="R") { // delete 2��
			m.right.color = "B";
			executeDelete(m);
			return;
		}
		Node<E> x = executeDelete(m); // m & m.right �� ��� "B", �����Ͽ�  black
		fixupBlackNode(x); // delete�� 3�� ó��(�����ϴٰ� �ߴ� ��.) 
	}
	
	private void fixupBlackNode(Node<E> x) {
		if(x==x.parent.left) {
			System.out.print("Left-");
			Node<E> p = x.parent;
			Node<E> s = p.right;
			Node<E> l = s.left;
			Node<E> r = s.right;
			// P�� Red�� ��� ~
			// case A
			if(p.color=="R" && l.color=="B" && r.color=="B") { // s�� ������ B 
				System.out.println("A");
				p.color="B"; s.color="R"; return;
			}
			// case B&C - l�� B/R�������.
			if(p.color=="R" && r.color=="R") { // s�� ������ B
				System.out.println("BC");
				rotateLeft(p);
				p.color ="B"; s.color="R"; r.color="B"; return;
			}
			// case D
			if(p.color=="R" && l.color=="R" && r.color=="B") { //s�� ������ B
				System.out.println("D");
				rotateRight(s);
				l.color ="B"; s.color="R";
				fixupBlackNode(x); return;
			}
			// P�� Black�� ���
			// case E
			if(p.color=="B" && s.color=="B" && l.color=="B" && r.color=="B") {
				System.out.println("E");
				s.color ="R"; 
				fixupBlackNode(p); return;
			}
			// case F & G
			if(p.color=="B" && s.color=="B" && r.color=="R") {
				System.out.println("FG");
				rotateLeft(p);
				r.color ="B"; return;
			}
			// case H
			if(p.color=="B" && s.color=="B" && l.color=="R" && r.color=="B") {
				System.out.println("H");
				rotateRight(s);
				l.color ="B"; s.color="R";
				fixupBlackNode(x); return;
			}
			// case I
			if(p.color=="B" && s.color=="R" && l.color=="B" && r.color=="B") {
				System.out.println("I");
				rotateLeft(p);
				p.color="R"; s.color="B";
				fixupBlackNode(x); return; // -> ���� �ִ� �˸´� ���� �� ��
			}
		}
		else { //x==x.parent.right ==> ����
			System.out.print("Right-");
			Node<E> p = x.parent;
			Node<E> s = p.left;
			Node<E> l = s.left;
			Node<E> r = s.right;
			// P�� Red�� ��� ~
			// case A (pslr=rbbb)
			if(p.color=="R" && l.color=="B" && r.color=="B") { // s�� ������ B 
				System.out.println("A");
				p.color="B"; s.color="R"; return;
			}
			// case B (rbbr)
			if(p.color=="R" && r.color=="R") { // s�� ������ B
				System.out.println("B");
				rotateRight(s);
				s.color="R"; r.color="B"; 
				fixupBlackNode(x); return;
			}
			// case C & D (rbrr & rbrb)
			if(p.color=="R" && l.color=="R" && r.color=="B") { //s�� ������ B
				System.out.println("CD");
				rotateRight(p);
				p.color = "B"; l.color ="B"; s.color="R"; return;
			}
			// P�� Black�� ���
			// case E (bbbb)
			if(p.color=="B" && s.color=="B" && l.color=="B" && r.color=="B") {
				System.out.println("E");
				s.color ="R"; 
				fixupBlackNode(p); return;
			}
			// case F (bbbr)
			if(p.color=="B" && s.color=="B" && l.color=="B" && r.color=="R") {
				System.out.println("F");
				rotateLeft(s);
				r.color ="B"; s.color="R";
				fixupBlackNode(x); return;
			}
			// case G & H (bbrr & bbrb)
			if(p.color=="B" && s.color=="B" && l.color=="R") {
				System.out.println("GH");
				rotateRight(p);
				l.color ="B"; return;
			}
			// case I (brbb)
			if(p.color=="B" && s.color=="R" && l.color=="B" && r.color=="B") {
				System.out.println("I");
				rotateLeft(p);
				p.color="R"; s.color="B";
				fixupBlackNode(x); return; // -> ���� �ִ� �˸´� ���� �� ��
				// s? p?
			}
		}
	}
	
	private Node<E> executeDelete(Node<E> m){
		// ������ child��, �Ǵ� �� ����.
		if(m==root){
			root = nilNode;
			return nilNode;
		}
		if(m==m.parent.left) { // parent�� �ަU
			if(m.right!=nilNode) {
				m.parent.left=m.right;
				m.right.parent = m.parent;
				return m.right;
			}
			else { // no child
				m.parent.left = nilNode;
				return nilNode;
			}
		}
		else { // m==m.parent.right // parent�� ������
			if(m.right!=nilNode) {
				m.parent.right=m.right;
				m.right.parent = m.parent;
				return m.right;
			}
			else{ //no child
				m.parent.right = nilNode;
				return nilNode;
			}
		}
	}
	
	private Node<E> deleteNode(Node<E> x) {
		// left�� right�� �� ������ �ڱ��ڽ��� return 
		if(x.left == nilNode && x.right==nilNode) return x;
		else if (x.left==nilNode && x.right !=nilNode) return successor(x);
		else if (x.left!=nilNode && x.right ==nilNode) return predecessor(x);
		else return successor(x); // �� �κ��� ¥�� ��� ���̴�.
	}

	private Node<E> successor(Node<E> v){
		if(v==nilNode || v.right == nilNode) return nilNode;
		Node<E> p = v.right;
		while(p.left!=nilNode) p=p.left;
		return p;
	}
	private Node<E> predecessor(Node<E> v){
		if(v==nilNode || v.left == nilNode) return nilNode;
		Node<E> p = v.left;
		while(p.right!=nilNode) p=p.right;
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
		if(v==nilNode) return "";
		else return inorder(v.left)+" "+ v.key+"("+v.color+") "+inorder(v.right);
	}
	private String preorder(Node<E> v) {
		if(v==nilNode) return "";
		else return  v.key+"("+v.color+") "+ preorder(v.left)+" "+preorder(v.right);
	}
	private String postorder(Node<E> v) {
		if(v==nilNode) return "";
		else return postorder(v.left)+" "+postorder(v.right)+" "+ v.key+"("+v.color+") ";
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
		if(u.parent==nilNode) root=u; // u�� parent�� ������ null�̶�� �ǹ�.
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right = u;
		}
		v.parent = u;
		v.right = u.left;
		if(u.left!=nilNode) u.left.parent = v;
		u.left = v;
	}
	private void rotateRight(Node<E> v) {
		Node<E> u = v.left;
		u.parent = v.parent;
		if(u.parent==nilNode) root=u; // u�� parent�� ������ null�̶�� �ǹ�.
		else {
			if(v==v.parent.left) v.parent.left=u;
			else v.parent.right = u;
		}
		v.parent = u;
		v.left = u.right;
		if(u.right!=nilNode) u.right.parent = v;
		u.right = v;
	}
	
///////////////////////////////////////
	public int height() {
		return evalMax(root,0);
	}
	
	private int evalMax(Node<E> p, int counter) {
		if(p==nilNode) return counter;
		counter++;
		counter+=Math.max(evalMax(p.left,0), evalMax(p.right,0));
		return counter;
	}

///////////////////////////////////////
	public int IPL() { // homework 
		return IPLCounting(root,0);
		
	}
	private int IPLCounting(Node<E> p,int counter) {
		if(p==nilNode) return 0;
		counter++;
		counter+=IPLCounting(p.left,counter)+IPLCounting(p.right,counter);
		return counter;
	}

///////////////////////////////////////
	public static void main(String[] args) {
		// ������ �׽�Ʈ�ڵ� �ִ��� �����غ��ҽ��ϴ�. 
		RBSearchTree<Integer> mybst = new RBSearchTree<>();
		
		for(int i = 0 ; i<20 ; i++) {
			mybst.insert(i);
			System.out.println(mybst.toString());
		}
		
		System.out.println(mybst.toString());
		System.out.println("Max, Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());
		
		mybst.delete(3);
		mybst.delete(7);
		mybst.delete(11);
		mybst.delete(18);
		System.out.println(mybst.toString());
		System.out.println("Max, Height : "+mybst.height());
		System.out.println("IPL : "+mybst.IPL());
		
	}
}
