package week10;

import java.util.ArrayList;

public class GraphInArray<T> {

	@SuppressWarnings("hiding")
	public class EdgeUnit<T> {
		T sourceVertex;
		T destVertex;
		int weight;
		
		public EdgeUnit(T sourceVertex, T destVertex, int weight) {
			this.sourceVertex = sourceVertex;
			this.destVertex = destVertex;
			this.weight = weight;
		}
		public String toString() {
			return"<"+sourceVertex+"("+weight+")"+destVertex+">";
		}
		public int compareTo(EdgeUnit<T> other) {
			if (this.weight>other.weight) return 1;
			else if (this.weight == other.weight) return 0;
			else return -1;
		}
	}
	
	public class VertextUnit{
		T data;
		int value;
		ArrayList<EdgeUnit<T>> adjList;
		
		public VertextUnit(T data) {
			this.data = data;
			this.value = 0;
			adjList = new ArrayList<>();
		}
		public String toString() {
			String retStr = ""+data;
			for (int i = 0;i<adjList.size();i++) retStr +=" :-("+adjList.get(i).weight+")-> "+adjList.get(i).destVertex;
			return retStr;
		}
	}
	ArrayList<VertextUnit> adjacentArray;
	public void createGraph() {
		this.adjacentArray = new ArrayList<>();
	}
	
	public boolean insertVertex(T u) {
		int index = indexOf(u);
		if (index >= 0) return false; // 있는 경우엔 더 안만들도록함.
		this.adjacentArray.add(new VertextUnit(u));
		return true;
	}

	public int indexOf(T u) {
		for (int i=0; i<adjacentArray.size() ; i++) if(adjacentArray.get(i).data.equals(u)) return i;
		return -1;
	}
	
	public boolean insertEdge(T u, T v, int weight) { // edge grom u to v
		int indexU = indexOf(u);
		int indexV = indexOf(v);
		if (indexU<0||indexV<0) return false; // 엣지를 추가하려는데 노드가 없는거니까 false리턴하고 끝.
		//undirected graph
		adjacentArray.get(indexU).adjList.add(new EdgeUnit<T>(u, v, weight)); //vertex하나를 가져와서 리스트를 가져오고 그 리스트에 새 엣지를 더함.
		adjacentArray.get(indexU).value++;//adjacentList의 갯수를 알기 위함. //u의 갯수증가
		adjacentArray.get(indexV).adjList.add(new EdgeUnit<T>(v, u, weight));
		adjacentArray.get(indexV).value++;	// v의 갯수추가
		// --> u랑 v모두 하나씩 올려줘야함.
		return true;
	}
	
	public void showGraph() {
		for (int i =0;i<adjacentArray.size();i++) System.out.println(adjacentArray.get(i));
	}
	
	public static void main(String[] args) {
		String[] vertex = {"seoul","daejeon","daegu","busan","kwangju","incheon","ulsan","jeju"};
		GraphInArray<String> myG = new GraphInArray<>();
		myG.createGraph();
		for(int i = 0 ; i< vertex.length;i++) myG.insertVertex(vertex[i]);
		
		myG.insertEdge(vertex[0], vertex[3], 3);
		myG.insertEdge(vertex[0], vertex[7], 4);
		myG.insertEdge(vertex[3], vertex[1], 2);
		myG.insertEdge(vertex[3], vertex[7], 6);
		myG.insertEdge(vertex[1], vertex[4], 4);
		myG.insertEdge(vertex[1], vertex[5], 7);
		myG.insertEdge(vertex[5], vertex[2], 3);
		myG.insertEdge(vertex[5], vertex[6], 9);
		myG.insertEdge(vertex[5], vertex[3], 1);
		myG.insertEdge(vertex[5], vertex[7], 11);
		myG.insertEdge(vertex[6], vertex[2], 3);
		myG.insertEdge(vertex[6], vertex[0], 8);
		myG.insertEdge(vertex[7], vertex[0], 10);
	
		System.out.println("*** Graph created ***");
		myG.showGraph();
	
	}
	
}

