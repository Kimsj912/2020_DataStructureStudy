package week10;

import java.util.Vector;

public class DisjointSet<T> {

	// Node
	private class Node {
		T data;
		Node head;
		Node next;
		int weight;

		public Node(T i) {
			data = i;
			head = this;
			next = null;
			weight = 1;
		}

		public String toString() {
			return "" + data + "(" + weight + ")";
		}
	}

	Node tail;
	public DisjointSet() {
		tail =null;
	}
	public DisjointSet<T> makeSet(T i){
		Node newNode = new Node(i);
		DisjointSet<T> newSet = new DisjointSet<>();
		newSet.tail = newNode;
		return newSet;
	}
	
	public Node findSet() {
		if(this.tail == this.tail.head) return this.tail;
		else return this.tail.head;
	}
	
	public DisjointSet<T> union (DisjointSet<T> another){
		if(this.tail == null) return another;
		if(another.tail == null) return this;
		Node x = this.tail.head;
		Node y = another.tail.head;
		if(x.weight>=y.weight) {
			Node temp = y; 
			while(temp!=null) {
				temp.head=x;
				temp=temp.next;
			}
			this.tail.next = y;
			this.tail = another.tail;
			x.weight += y.weight;
			y.weight = 1;
			return this;
		} else {
			Node temp = x;
			while (temp!=null) {
				temp.head =y;
				temp=temp.next;
			}
			another.tail.next = x;
			another.tail = this.tail;
			return another;
		}
	}
	
	public String toString() {
		if(this.tail == null) return null;
		Node temp = tail.head;
		String retVal = ""+temp.toString();
		while(temp.next!=null) {
			temp =temp.next;
			retVal = retVal +" -> "+temp.toString();
		}
		return retVal;
	}
}