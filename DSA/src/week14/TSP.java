package week14;

import java.util.HashSet;
 
public class TSP { // 복잡도가 놀다는거지 돌아가는 원리가 어려운것은 아니다.
	int [][] AdjacentMatrix;
	int numberVertex;
	
	public TSP(int[][] input) {
		AdjacentMatrix = input;
		numberVertex=AdjacentMatrix.length;
	}
	
	public int initTravel(int start) {
		HashSet<Integer> thru = new HashSet<>(); // int set만들기
		for (int i=0; i<numberVertex; i++) { // 모든 노드에 대해서
			if (i!=start) thru.add(i); // 스타트 뺸 나머지를 넣어야하니까!
		}
		return minTravel(start, thru, start); // ex) a0. {a1,a2,a3,a4}, a0 을 넣어준다. 
	}

	private int minTravel(int start, HashSet<Integer> thru, int returnPoint) { // 이게 메인룹
		if (thru.size()==0) { // 공집합이라면 그냥 스타트에서  리턴포인트까지의 가중치를 리턴하면 됨.
			return AdjacentMatrix[start][returnPoint];
		}
		 // 공집합이 아니라면,
		int min=9999;
		for (int i : thru) { // start에서ㅜ i까지 를 다시 돌리고 하는 거지. thru의 원소만큼 돌면서 작은 걸 찾는거지.
			HashSet<Integer> next = reduce(thru,i); 
			if (AdjacentMatrix[start][i]!=999) {
				int tempDist = AdjacentMatrix[start][i]+minTravel(i, next, returnPoint);
				if (tempDist<=min) {
					min=tempDist;
				}
			}
		}
		System.out.println(start+" -> "+thru+" ("+min+")");
		return min; 
	}


	private HashSet<Integer> reduce(HashSet<Integer> set, int i) { // 하나씩 읽으면서 i를 뺴고 새로운걸 넣어줌. 
		HashSet<Integer> result= new HashSet<>();
		for (int j : set) {
			if (j!=i) {
				result.add(j);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		int [][] input = { {0,10,10,30,25}, {10,0,14,21,10}, // 5x5매트릭스 
				{10,18,0,7,9}, {8,11,7,0,3}, {14,10,10,3,0}
		};
		
		TSP me = new TSP(input);
		System.out.println("Result = "+me.initTravel(0));

	}
}
