package week12;

import java.util.Arrays;

public class BellmanFord extends GraphInMatrix{
	int numberOfVertex;
	int[] distance;
	
	public BellmanFord(int size) {
		super();
		numberOfVertex = size;
		createGraph(numberOfVertex);
		distance = new int[numberOfVertex];
	}
	
	public boolean insertEdge(int u , int v, int weight) {
		if(u<adjacentMatrix.length && v<adjacentMatrix.length) {
			adjacentMatrix[u][v] = weight; // GraphInMatrix와의 차이점.
			return true;
		}else return false;
	}
	
	public int[] BFSShortedPath(int startVertex) {
		int [] prev = new int[numberOfVertex];
		Arrays.fill(distance, 999);
		
		distance[startVertex] = 0;
		prev[startVertex] = startVertex;
		for(int iter=1 ; iter<numberOfVertex;iter++) { // that is, iterate 'numberOfVertex-1' times...
			for(int i = 0;i<numberOfVertex;i++) {
				for(int j = 0 ; j<numberOfVertex;j++) {
					int temp = distance[i] + adjacentMatrix[i][j];
					if(j!=startVertex && adjacentMatrix[i][j]!=0 &&distance[j]>temp) {
						System.out.println(">>> "+iter+"-th Iteration : from "+i+" to "+j);
						System.out.println("Relax : "+j+"("+distance[j]+") by prev "+ i +" to "+temp);
						distance[j] = temp;
						prev[j] = i;
					}
				}
			}
		}
		// 음의 싸이클 확인
		for(int i=0;i<numberOfVertex;i++) {
			for(int j = 0; j<numberOfVertex;j++) {
				if(adjacentMatrix[i][j]!=0 &&distance[i]+adjacentMatrix[i][j]<distance[j]) {
					System.out.println("음의 싸이클 발견 ");
					return null;
				}
			}
		}
		return prev;
	}
	
	public int[] getDistance() {return distance;}
	
	public static void main(String[] args) {
		int numOfVertex = 8;
		BellmanFord myG = new BellmanFord(numOfVertex);
		
		myG.insertEdge(0, 1, 5);
		myG.insertEdge(0, 2, 4);
		myG.insertEdge(1, 3, 2);
		myG.insertEdge(1, 4, -6);
		myG.insertEdge(3, 4, 7);
		myG.insertEdge(4, 2, 1);
		myG.insertEdge(4, 5, 9);
		myG.insertEdge(5, 7, -10);
		myG.insertEdge(7, 6, 8);
		myG.insertEdge(6, 4, -11);
		myG.insertEdge(2, 5, 4);
		
		
//		myG.insertEdge(0, 1, 5);
//		myG.insertEdge(1, 2, 2);
//		myG.insertEdge(2, 3, 4);
//		myG.insertEdge(3, 4, 7);
//		myG.insertEdge(5, 6, 1);
//		myG.insertEdge(4, 5, 9);
//		myG.insertEdge(6, 7, 10);
//		myG.insertEdge(7, 6, 8);
		
		myG.showGraph();
		
		int start = 0;
		int[] shortestPath = myG.BFSShortedPath(start);
		if(shortestPath!=null) {
			int[] shortestDistance = myG.getDistance();
			System.out.println();
			for(int k=0;k<numOfVertex;k++)
				System.out.println("From "+start+" to "+k+" : distance = "+shortestDistance[k]+" previous Vertex = "+ shortestPath[k]);
			System.out.println();
		}
	}

}
