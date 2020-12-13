package week11;

import java.util.ArrayList;

public class TopologicalSort {
	class Vertex{
		// attribute
		boolean visited;
		private String value;
		private ArrayList<Vertex> directList;
		private ArrayList<Vertex> callVList;

		// Constructor
		public Vertex(String v) {
			this.value = v;
			this.visited = false;
			this.callVList = new ArrayList<Vertex>();
			this.directList = new ArrayList<Vertex>();
		}
		
		public String toString() {
			return this.value;
		}

		// get & set
		public String getValue() {return value;}
		public void setValue(String value) {this.value = value;}
		public boolean isVisited() {return visited;}
		public void setVisited(boolean visited) {this.visited = visited;}
		public ArrayList<Vertex> getNearList() {return directList;}
	}

	// attribute
	ArrayList<Vertex> vertexs;
	private ArrayList<Vertex> sorted;

	// get & set
	public ArrayList<Vertex> getVertexs() {return vertexs;}
	public ArrayList<Vertex> getSorted() {return sorted;}

	
	// Constructor
	public TopologicalSort() {
		this.vertexs = new ArrayList<Vertex>();
		this.sorted = new ArrayList<Vertex>();
	}
	
	// method
	public void addVertex(String s) {
		Vertex v = new Vertex(s);
		this.vertexs.add(v); 
	}
	public void addEdge(String s, String d) {
		int s1Idx = -1;
		int s2Idx = -1;
		int idx = 0;
		for (Vertex v : this.vertexs) {
			if (v.getValue().equals(s)) { s1Idx = idx;}
			else if (v.getValue().equals(d)) { s2Idx = idx;}
			idx++;
		}
		if (s1Idx != -1 && s2Idx != -1) {
			Vertex s1V = this.vertexs.get(s1Idx);
			Vertex s2V = this.vertexs.get(s2Idx);
			
			s1V.directList.add(s2V);
			s2V.callVList.add(s1V);
		}
	}
	
	private void topologicalSorting() {
		System.out.println("--------- [Test]Sorting>>> ");
		System.out.println();
		this.sorted.clear();
		
		for (Vertex u : vertexs) {
			if (u.callVList.isEmpty() && u.visited ==false) {
				this.sorted.addAll(recTPS(u));
			}
		}
		System.out.println("\n");
		System.out.println("------------------------- ");
	}
	
	private ArrayList<Vertex> recTPS(Vertex v) {
		ArrayList<Vertex> a = new ArrayList<Vertex>();
		v.visited = true;
		a.add(v);
		
		System.out.print(v+"(");
		for (Vertex u : v.directList) {
			if (u.visited == false) {
				a.addAll(recTPS(u));
				break;
			}
		}
		System.out.print(")");
		return a;
	}
	
	public void showGraph() {
		String retVal = "";
		for(Vertex v : vertexs) {
			for(Vertex u : v.directList) {
				retVal += "("+v.toString() +")->"+u.toString()+"\n";
			}
		}
		System.out.println("--------- Show Graph >>> ");
		System.out.println(retVal);
		System.out.println("------------------------- ");
		
	}
	public void showSorted() {
		String retVal = "";
		for(Vertex v : this.sorted) {
			retVal += v.toString() +"->";
		}
		retVal = retVal.substring(0,retVal.length()-2);
		System.out.println("--------- Show Sorted >>> ");
		System.out.println(retVal);
		System.out.println("------------------------- ");
		
	}
	
	public static void main(String[] args) {
		TopologicalSort t = new TopologicalSort();
		
		t.addVertex("a");
		t.addVertex("b");
		t.addVertex("c");
		t.addVertex("d");
		t.addVertex("e");

		t.addEdge("a","b");
		t.addEdge("a","e");
		t.addEdge("b","c");
		t.addEdge("d","b");
		t.addEdge("d","e");
		t.addEdge("e","b");
		t.addEdge("e","c");
		
		t.showGraph();		
		t.topologicalSorting();
		t.showSorted();
		
		
		
	}
}
