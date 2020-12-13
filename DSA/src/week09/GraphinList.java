package week09;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.Stack;

public class GraphinList<T> {
	
	private class GraphNode{
		T data;
		GraphNode link;
		
		public GraphNode(T input) {
			data = input;
			link = null;
		}
	}
	
	ArrayList<GraphNode> adjacentList;
	int[] visited;
	
	public void createGraph() {
		adjacentList = new ArrayList<GraphNode>();
	}

	public boolean insertVertex(T v) {
		int index = indexOf(v);
		if(index>=0) return false; // vertex already  exist
		adjacentList.add(new GraphNode(v));
		return true;
	}
	
	public boolean insertEdge(T u, T v) {
		if(indexOf(u)<0 || indexOf(v)<0) return false; // edge from u to v
		int index = indexOf(u);
		GraphNode newNode = new GraphNode(v);
		newNode.link = adjacentList.get(index).link; // insert at the first position // 첫번째거 바로 뒤에 붙인다.
		adjacentList.get(index).link = newNode;
		// if undirected case, insertEdge(v,u) should be done; 
		return true;
	}

	private int indexOf(T u) {
		for (int i=0;i<adjacentList.size();i++) {
			if(adjacentList.get(i).data.equals(u)) return i;
		}
		return -1;
	}
	
	public HashSet<T> adjacent(T v){
		HashSet<T> result = new HashSet<T>();
		System.out.println(">>> Adj. test : "+v);
		
		int index = indexOf(v);
		if (index == -1) {
			System.out.println(v+" not found.");
			return result; // vertex not found.
		}
		GraphNode p = adjacentList.get(index);
		System.out.println(">>> Adj. text : p "+p.data);
		
		while(p.link != null) {
			result.add(p.link.data);
			p=p.link;
		}
		return result;
	}
	
	public boolean deleteVertex(T v) { // ex. 서울을 지우면 서울이 가는 곳들과 서울로 오는 애들 모두를 지워줘야함.
		int index = indexOf(v);
		if(index < 0) return false; // Vertext is not found.
		GraphNode p = adjacentList.get(index);
		
		if(p.link != null) { // 인접한 무언가가 있을 경우.
			GraphNode q = p.link;
			while(q!=null) {
				deleteEdge(q.data,v);
				q=q.link;
			}
		}
		adjacentList.remove(index);
		return true;
	}

	public boolean deleteEdge(T u, T v) { // edge는 해당 엣지만 사라지는 거니까 상대적으로 쉬움.
		int index = indexOf(u);
		if(index < 0) return false; // Tail-Vertext is not found.
		GraphNode p = adjacentList.get(index);
		GraphNode q = p.link;
		while(!(q.data.equals(v))&&q.link!=null) {
			p = p.link; q=q.link;
		}
		if (q.data.equals(v)) p.link = q.link; 
		// if undirected case, deleteEdge(v,u) should be done; 
		return true;
	}
	
	public void showGraph() {
		System.out.println(" [ Graph ] ");
		
		for (int i = 0; i<adjacentList.size();i++) {
			System.out.print(adjacentList.get(i).data + " ");
			GraphNode p = adjacentList.get(i).link;
			while(p!=null) {
				System.out.print(" => "+p.data);
				p=p.link;
			}
			System.out.println();
		}
		
		
	}
	
	public void BFS(T v) {
		int index = indexOf(v);
		
		if(index>=0) {
			Deque<Integer> que = new ArrayDeque<Integer>();
			visited = new int [adjacentList.size()];
			Arrays.fill(visited, 0);
			que.add(index);
			BFSRecursion(que);
		} else {
			System.out.println("Starting vertex not found");
		}
	}
	
	public void BFSRecursion(Deque<Integer> que) {
		while(que.peek()!=null) { // 얘는 있어야함.
			int index = que.poll();
			System.out.println(">>> Polled out from Queue : "+index +" "+ adjacentList.get(index).data+" visited : "+visited[index]);
			
			if (visited[index] == 1) return; // 이미 방문했다면 끝냄.
			else {
				System.out.println(adjacentList.get(index).data);
				visited[index]=1;
				GraphNode temp = adjacentList.get(index).link;
				while(temp != null) {
					que.add(indexOf(temp.data));
					System.out.println(">>> Added into Queue : "+indexOf(temp.data)+" "+temp.data);
					temp = temp.link;
				}
				BFSRecursion(que);
			}
		}
		return;
	}

	public void DFS(T v) {
		int index = indexOf(v);
		if(index>=0) {
			Stack<Integer> st = new Stack<Integer>();
			visited = new int [adjacentList.size()];
			Arrays.fill(visited, 0);
			st.push(index);
			DFSRecursion(st);
		} else {
			System.out.println("Starting vertex not found");
		}
	}
	public void DFSRecursion(Stack<Integer> st) {
		while(st.size()>0) { // 얘는 이거 필요없음.
			int index = st.pop(); // 꺼냄.
			System.out.println(">>> Poped out from Stack : "+index +" "+ adjacentList.get(index).data+" visited : "+visited[index]);
			if (visited[index] > 0) return; // 이미 방문했다면 끝냄.
			visited[index]=1; // 방문기록 남김
			
			GraphNode temp = adjacentList.get(index).link;
			while(temp != null) {
				st.push(indexOf(temp.data));
				System.out.println(">>> Pushed into Stack : "+indexOf(temp.data)+" "+temp.data);
				DFSRecursion(st); // 하나하나 찾아나가서 이렇게 구현됨.
				temp = temp.link;
			}
			System.out.println(adjacentList.get(index).data);
		}
	}
	
	public static void main(String[] args) {
		String[] vertex = {"seoul","daejeon","daegu","busan","kwangju","incheon","ulsan","jeju"};
		
		GraphinList<String> myG = new GraphinList();
		
		myG.createGraph();
		for(int i = 0 ; i< vertex.length;i++) {
			myG.insertVertex(vertex[i]);
		}
		
		myG.insertEdge(vertex[0], vertex[3]);
		myG.insertEdge(vertex[0], vertex[7]);
		myG.insertEdge(vertex[3], vertex[1]);
		myG.insertEdge(vertex[3], vertex[7]);
		myG.insertEdge(vertex[1], vertex[4]);
		myG.insertEdge(vertex[1], vertex[5]);
		myG.insertEdge(vertex[5], vertex[2]);
		myG.insertEdge(vertex[5], vertex[6]);
		myG.insertEdge(vertex[5], vertex[3]);
		myG.insertEdge(vertex[5], vertex[7]);
		myG.insertEdge(vertex[6], vertex[2]);
		myG.insertEdge(vertex[6], vertex[0]);
		myG.insertEdge(vertex[7], vertex[0]);
		
		System.out.println(">>> Graph created. <<<");
		myG.showGraph();
		
		System.out.println("--- Adjacent Vertices to : "+vertex[5]);
		HashSet<String> adj = myG.adjacent(vertex[5]);
		System.out.println(adj);
		System.out.println("--- Adjacent Vertices to : "+vertex[0]);
		adj = myG.adjacent(vertex[0]);
		System.out.println(adj);
		
		int i = 0;
		System.out.println("--- BFS & DFS Test --- Start from : "+vertex[i]);
		
		System.out.println("\n*** BFS *** \n");
		myG.BFS(vertex[i]);
		System.out.println("\n*** DFS *** \n");
		myG.DFS(vertex[i]);
	}
}
