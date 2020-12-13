package week11;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * 어렵다 졸리다. ㅇ라고리즘 고치는 부분은 한정된 건 맍는데 조건이 너무 많다.
 * 해결 못한 조건은 연속으로 다 이어진 그래프가 불가능하다면 어떻게 해야하는가. 그래프니까 그냥 아무 연결없이 둬도 괜찮은가 -- 이게 해결된다면 거의다왔다.
 * 
 * **/
public class Dijkstra {
	public static int infinite = -99999;
	class Vertex{
		// attribute
		private int  value;
		private String name;
		private ArrayList<Vertex> directList;
		private ArrayList<Vertex> callVList;
		private Vertex prev;

		// Constructor
		public Vertex(String name) {
			this.value = infinite;
			this.name = name;
			this.callVList = new ArrayList<Vertex>();
			this.directList = new ArrayList<Vertex>();
			this.prev = null;
		}
		
		public String toString() {
			return this.name+"";
		}
	}
	
	private class Edge {
		private Vertex src;

		private Vertex dest;
		private int weight;
		
		public Edge(Vertex src, Vertex dest, int weight) {
			this.src = src;
			this.dest = dest;
			this.weight = weight;
		}
		
		public Vertex getSrc() {return src;}
		public void setSrc(Vertex src) {this.src = src;}
		public Vertex getDest() {return dest;}
		public void setDest(Vertex dest) {this.dest = dest;}
		public int getWeight() {return weight;}
		public void setWeight(int weight) {this.weight = weight;}
	}
	private class EdgeList {
		private ArrayList<Edge> edgeList;
		public EdgeList() {
			this.edgeList = new ArrayList<Edge>();
		}
		
		public Edge get(Vertex src, Vertex dest) {
			for(Edge e : edgeList) {
				if (e.src.equals(src) && e.dest.equals(dest)) return e;
			}
			return null;
		}
		
		public ArrayList<Edge> getEdgeFromSrc(Vertex src){
			ArrayList<Edge> list = new ArrayList<Edge>();
			for(Edge e : edgeList) {
				if (e.src.equals(src)) list.add(e);
			}
			return list;
		}
		
		public ArrayList<Edge> getEdgeFromDest(Vertex dest){
			ArrayList<Edge> list = new ArrayList<Edge>();
			for(Edge e : edgeList) {
				if (e.dest.equals(dest)) list.add(e);
			}
			return list;
		}
		
		public void add(Edge e) {
			this.edgeList.add(e);
		}
	}

	// attribute
	private ArrayList<Vertex> vertexs;
	private ArrayList<Vertex> sorted;
	private EdgeList EdgeList;

	// get & set
	public ArrayList<Vertex> getVertexs() {return vertexs;}
	public ArrayList<Vertex> getSorted() {return sorted;}

	
	// Constructor
	public Dijkstra() {
		this.vertexs = new ArrayList<Vertex>();
		this.sorted = new ArrayList<Vertex>(); 
		this.EdgeList = new EdgeList(); 
	}
	
	// method
	public Vertex addVertex(String s) {
		Vertex v = new Vertex(s);
		this.vertexs.add(v); 
		return v;
	}
	
	public void addEdge(Vertex s, Vertex d, int w) {
		s.directList.add(d);
		d.callVList.add(s);
		
		Edge e = new Edge(s, d, w);
		this.EdgeList.add(e);
	}
	
	public void showGraph() {
		String retVal = "";
		for(Vertex v : vertexs) {
			for(Vertex u : v.directList) {
				Edge e = this.EdgeList.get(v, u);
				retVal += "("+v.toString() +")-"+e.getWeight()+"->("+u.toString()+")\n";
			}
		}
		System.out.println("--------- Show Graph >>> ");
		System.out.println(retVal);
		System.out.println("------------------------- ");
		
	}
	public void showSorted(Vertex start) {
		String retVal = "";
	
		ArrayList<Vertex> vList = showSorted_Inner(start);
		for(Vertex v : vList) {
			if(start.directList.contains(v)) retVal+="\n"+start.toString()+"->";
			if (!start.equals(v)) retVal += v.toString() + "->";
		}
		retVal = retVal.replace("->\n", "\n");
		retVal = retVal.substring(1,retVal.length()-2);
		System.out.println("--------- Show Sorted >>> ");
		System.out.println(retVal);
		System.out.println("------------------------- ");
		
	}
	
	private ArrayList<Vertex> showSorted_Inner(Vertex start) {
		ArrayList<Vertex> vList = new ArrayList<Vertex>();
		vList.add(start);
		for(Vertex v : start.directList) {
			if(v.prev.equals(start)) {
				ArrayList<Vertex> innerList = showSorted_Inner(v);
				if (!innerList.isEmpty()) {
					vList.addAll(innerList);
				}
			}
		}
		return vList;
	}
	
	private Vertex dijkstraSorting() {
		System.out.println("--------- [Test]Sorting>>> ");
		System.out.println();
		this.sorted.clear();
		
		int idx = s(0);
		
		System.out.println("\n");
		System.out.println("------------------------- ");
		return vertexs.get(idx);
		
	}

	private int s (int idx) {
		Vertex u = vertexs.get(idx);
		if (!u.callVList.isEmpty()) {
			System.out.println("callVList is not empty...");
			s(idx+1);
		}
		u.value = 0;
		recDijkStra(u);
		
		for (Vertex v : vertexs) {
			if (v.value == infinite) {
				System.out.println("infinite is exist... ");
				s(idx+1);
			}
		}
		return idx;
	}
	private void recDijkStra(Vertex src) {
		int weight = 0;
		Vertex dest = null;
		
		for (Edge edges : EdgeList.getEdgeFromSrc(src)) {
			weight = edges.getWeight();
			dest = edges.getDest();
		
			// test
			System.out.println("Src : "+src.toString());
			System.out.println("dest : "+dest.toString());
			System.out.println("weight : "+weight);

			if(dest.value==infinite || dest.value > src.value+weight) {
				dest.value = src.value + weight;
				dest.prev = src;
				
				//test
				System.out.println("changed Weight : "+ dest.value);
				recDijkStra(dest);
			}
		}
		
		
	}
	
	public static void main(String[] args) {
		Dijkstra t = new Dijkstra();
		
		Vertex a = t.addVertex("a");
		Vertex b = t.addVertex("b");
		Vertex c = t.addVertex("c");
		Vertex d = t.addVertex("d");
		Vertex e = t.addVertex("e");
		Vertex f = t.addVertex("f");
		Vertex g = t.addVertex("g");
		Vertex h = t.addVertex("h");

		t.addEdge(a,b,8);
		t.addEdge(a,f,9);
		t.addEdge(a,e,11);

		t.addEdge(b,c,10);
		
		t.addEdge(c,d,2);
		
		t.addEdge(d,h,4);
		
		t.addEdge(e,g,9);
		t.addEdge(e,h,8);

		t.addEdge(f,c,1);
		t.addEdge(f,b,6);
		t.addEdge(f,e,3);

		t.addEdge(g,f,12);
		t.addEdge(g,d,5);
		
		t.addEdge(h,g,7);
		
		t.showGraph();		
		Vertex start = t.dijkstraSorting();
		t.showSorted(start);
		
		
		
	}
}
