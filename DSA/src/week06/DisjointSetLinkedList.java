package week06;


public class DisjointSetLinkedList {
	class Node {
		int data;
		Node head;
		Node next;
		int weight;
		
		public Node(int i) {
			data = i;
			head = this;
			next = null;
			weight = 1;
		}
		
		public String toString() {
			return ""+data+"("+weight+")";
		}
	}
	
	Node tail;
	public DisjointSetLinkedList() {
		tail = null;
	}
	public DisjointSetLinkedList makeSet (int i ) {
		Node newNode = new Node(i);
		DisjointSetLinkedList newSet = new DisjointSetLinkedList();
		newSet.tail = newNode;
		return newSet;
	}

	public Node findSet() {
		if(this.tail == this.tail.head) return this.tail;
		else return this.tail.head;
	}
	
	public DisjointSetLinkedList union(DisjointSetLinkedList another) {
		if(this.tail == null) return another;
		if(another.tail==null) return this;
		Node x = this.tail.head;
		Node y = another.tail.head;
		if(x.weight >= y.weight) {
			Node tmp =y;
			while(tmp!=null) {
				tmp.head = x;
				tmp = tmp.next;
			}
			this.tail.next = y;
			this.tail = another.tail;
			x.weight += y.weight;
			y.weight = 1;
			return this;
		}else {
			Node tmp = x;
			while(tmp!=null) {
				tmp.head = y;
				tmp = tmp.next;
			}
			another.tail.next = x;
			another.tail = this.tail;
			return another;
		}
	}
	public String toString () {
		if(this.tail == null) return null;
		Node tmp = tail.head;
		String returnVal = ""+tmp.toString();
		while(tmp.next!=null) {
			tmp = tmp.next;
			returnVal = returnVal+ " -> "+tmp.toString();
		}
		return returnVal;
	}
	
	public static void main(String[] args) {
		DisjointSetLinkedList a = new DisjointSetLinkedList();
		DisjointSetLinkedList set1 = a.makeSet(1);
		DisjointSetLinkedList set2 = a.makeSet(2);
		DisjointSetLinkedList set3 = a.makeSet(3);
		DisjointSetLinkedList set4 = a.makeSet(4);
		DisjointSetLinkedList set5 = a.makeSet(5);
		
		System.out.println("head of set5 : "+set5.findSet());
		System.out.println(set1);
		set1.union(set2);
		System.out.println(set1);
		System.out.println(set2);
				
		set3.union(set4);
		System.out.println(set3);
		System.out.println(set4);
		set1.union(set3);
		System.out.println(set1);
		System.out.println(set2);
		System.out.println(set3);
		System.out.println(set4);
		
		System.out.println("head of set5 : "+set5.findSet());
		System.out.println("head of set4 : "+set4.findSet());
		
	}
}
