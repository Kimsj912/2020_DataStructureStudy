//package week09;
//
//import java.util.ArrayDeque;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Deque;
//import java.util.HashSet;
//import java.util.Stack;
//
//public class GraphinMatrix<T> {
//	// 던짐.
//	// 과제였음 ㅇㅇ
//	private class Matrix<T> {
//		ArrayList<ArrayList<T>> matrix;
//		public Matrix() {
//			this.matrix = new ArrayList<ArrayList<T>>();
//		}
//		public int size() {
//			return this.matrix.size();
//		}
//		public ArrayList<T> get(int index) {
//			return matrix.get(index);
//		}
//		public void add(ArrayList<T> at) {
//			this.matrix.add(at);
//		}
//		public void remove(int index) {
//			this.matrix.remove(index);
//		}
//		
//	}
//	private Matrix<T> adjacentTable;
//	int[][] visited;
//	
//	public void createGraph() {
//		adjacentTable = new Matrix<T>();
//	}
//
//	public boolean insertVertex(T v) {
//		int index = indexOf(v);
//		if(index>=0) return false; // vertex already  exist
//		ArrayList<T> at = new ArrayList<T>();
//		at.add(v);
//		adjacentTable.add(at);
//		return true;
//	}
//	
//	public boolean insertEdge(T u, T v) {
//		if(indexOf(u)<0 || indexOf(v)<0) return false; // edge from u to v
//		int index = indexOf(u);
//		adjacentTable.get(index).add(v);
//
//		int secindex = indexOf(v);
//		adjacentTable.get(secindex).add(u);
//		
//		return true;
//	}
//
//	private int indexOf(T u) {
//		for (int i=0;i<adjacentTable.size();i++) {
//			if(adjacentTable.get(i).get(0).equals(u)) return i;
//		}
//		return -1;
//	}
//	
//	public HashSet<T> adjacent(T v){
//		HashSet<T> result = new HashSet<T>();
//		System.out.println(">>> Adj. test : "+v);
//		
//		int index = indexOf(v);
//		if (index == -1) {
//			System.out.println(v+" not found.");
//			return result; // vertex not found.
//		}
//		T p = adjacentTable.get(index).get(0);
//		System.out.println(">>> Adj. text : p "+p);
//		
//		for(int i = 1 ; i<adjacentTable.get(index).size();i++) {
//			result.add(adjacentTable.get(index).get(i));
//		}
//		return result;
//	}
//	
//	public boolean deleteVertex(T v) { // ex. 서울을 지우면 서울이 가는 곳들과 서울로 오는 애들 모두를 지워줘야함.
//		int index = indexOf(v);
//		if(index < 0) return false; //Vertext is not found.
//		ArrayList<T> p = adjacentTable.get(index);
//		
//		if(p.size()>1) { // 인접한 무언가가 있을 경우.
//			for (int i = 1 ; i < p.size();i++) {
//				deleteEdge(p.get(0),p.get(i));
//			}
//		}
//		adjacentTable.remove(index);
//		return true;
//	}
//
//	public boolean deleteEdge(T u, T v) { // edge는 해당 엣지만 사라지는 거니까 상대적으로 쉬움.
//		int index = indexOf(u);
//		if(index < 0) return false; // Tail-Vertext is not found.
//		
//		ArrayList<T> a=adjacentTable.get(index);
//		a.remove(v);
//		ArrayList<T> b=adjacentTable.get(indexOf(v));
//		b.remove(u);
////		
////		
////		GraphNode p = adjacentTable.get(index);
////		GraphNode q = p.link;
////		while(!(q.data.equals(v))&&q.link!=null) {
////			p = p.link; q=q.link;
////		}
////		if (q.data.equals(v)) p.link = q.link; 
////		// if undirected case, deleteEdge(v,u) should be done; 
//		return true;
//	}
//	
//	public void showGraph() {
//		System.out.println(" [ Graph ] ");
//		
//		for (int i = 0; i<adjacentTable.size();i++) {
//			for (int j = 0; j<adjacentTable.get(0).size();j++) {
//				System.out.print(adjacentTable.get(i).get(j)+ " ");
//			}
//			System.out.println();
//		}
//	}
//	
//	public void BFS(T v) {
//		int index = indexOf(v);
//		
//		if(index>=0) {
//			Deque<Integer> que = new ArrayDeque<Integer>();
//			visited = new int [adjacentTable.size()];
//			Arrays.fill(visited, 0);
//			que.add(index);
//			BFSRecursion(que);
//		} else {
//			System.out.println("Starting vertex not found");
//		}
//	}
//	
//	public void BFSRecursion(Deque<Integer> que) {
//		while(que.peek()!=null) { // 없어도 괜찮은데 이해를 돕기위해 추가해둠.
//			int index = que.poll();
//			System.out.println(">>> Polled out from Queue : "+index +" "+ adjacentTable.get(index).data+" visited : "+visited[index]);
//			
//			if (visited[index] == 1) return; // 이미 방문했다면 끝냄.
//			else {
//				System.out.println(adjacentTable.get(index).data);
//				visited[index]=1;
//				GraphNode temp = adjacentTable.get(index).link;
//				while(temp != null) {
//					que.add(indexOf(temp.data));
//					System.out.println(">>> Added into Queue : "+indexOf(temp.data)+" "+temp.data);
//					temp = temp.link;
//				}
//				BFSRecursion(que);
//			}
//		}
//		return;
//	}
//
//	public void DFS(T v) {
//		int index = indexOf(v);
//		if(index>=0) {
//			Stack<Integer> st = new Stack<Integer>();
//			visited = new int [adjacentTable.size()];
//			Arrays.fill(visited, 0);
//			st.push(index);
//			DFSRecursion(st);
//		} else {
//			System.out.println("Starting vertex not found");
//		}
//	}
//	public void DFSRecursion(Stack<Integer> st) {
//		while(st.size()>0) {
//			int index = st.pop(); // 꺼냄.
//			System.out.println(">>> Poped out from Stack : "+index +" "+ adjacentTable.get(index).data+" visited : "+visited[index]);
//			if (visited[index] > 0) return; // 이미 방문했다면 끝냄.
//			visited[index]=1; // 방문기록 남김
//			
//			GraphNode temp = adjacentTable.get(index).link;
//			while(temp != null) {
//				st.push(indexOf(temp.data));
//				System.out.println(">>> Pushed into Stack : "+indexOf(temp.data)+" "+temp.data);
//				DFSRecursion(st); // 하나하나 찾아나가서 이렇게 구현됨.
//				temp = temp.link;
//			}
//			System.out.println(adjacentTable.get(index).data);
//		}
//	}
//	
//	public static void main(String[] args) {
//		String[] vertex = {"seoul","daejeon","daegu","busan","kwangju","incheon","ulsan","jeju"};
//		
//		GraphinMatrix<String> myG = new GraphinMatrix<String>();
//		
//		myG.createGraph();
//		for(int i = 0 ; i< vertex.length;i++) {
//			myG.insertVertex(vertex[i]);
//		}
//		
//		myG.insertEdge(vertex[0], vertex[3]);
//		myG.insertEdge(vertex[0], vertex[7]);
//		myG.insertEdge(vertex[3], vertex[1]);
//		myG.insertEdge(vertex[3], vertex[7]);
//		myG.insertEdge(vertex[1], vertex[4]);
//		myG.insertEdge(vertex[1], vertex[5]);
//		myG.insertEdge(vertex[5], vertex[2]);
//		myG.insertEdge(vertex[5], vertex[6]);
//		myG.insertEdge(vertex[5], vertex[3]);
//		myG.insertEdge(vertex[5], vertex[7]);
//		myG.insertEdge(vertex[6], vertex[2]);
//		myG.insertEdge(vertex[6], vertex[0]);
//		myG.insertEdge(vertex[7], vertex[0]);
//		
//		System.out.println(">>> Graph created. <<<");
//		myG.showGraph();
//		
////		System.out.println("--- Adjacent Vertices to : "+vertex[5]);
////		HashSet<String> adj = myG.adjacent(vertex[5]);
////		System.out.println(adj);
////		System.out.println("--- Adjacent Vertices to : "+vertex[0]);
////		adj = myG.adjacent(vertex[0]);
////		System.out.println(adj);
////		
////		int i = 0;
////		System.out.println("--- BFS & DFS Test --- Start from : "+vertex[i]);
////		
////		System.out.println("\n*** BFS *** \n");
////		myG.BFS(vertex[i]);
////		System.out.println("\n*** DFS *** \n");
////		myG.DFS(vertex[i]);
//	}
//}
