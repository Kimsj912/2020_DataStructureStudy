package week14;

import java.util.HashSet;
 
public class TSP { // ���⵵�� ��ٴ°��� ���ư��� ������ �������� �ƴϴ�.
	int [][] AdjacentMatrix;
	int numberVertex;
	
	public TSP(int[][] input) {
		AdjacentMatrix = input;
		numberVertex=AdjacentMatrix.length;
	}
	
	public int initTravel(int start) {
		HashSet<Integer> thru = new HashSet<>(); // int set�����
		for (int i=0; i<numberVertex; i++) { // ��� ��忡 ���ؼ�
			if (i!=start) thru.add(i); // ��ŸƮ �A �������� �־���ϴϱ�!
		}
		return minTravel(start, thru, start); // ex) a0. {a1,a2,a3,a4}, a0 �� �־��ش�. 
	}

	private int minTravel(int start, HashSet<Integer> thru, int returnPoint) { // �̰� ���η�
		if (thru.size()==0) { // �������̶�� �׳� ��ŸƮ����  ��������Ʈ������ ����ġ�� �����ϸ� ��.
			return AdjacentMatrix[start][returnPoint];
		}
		 // �������� �ƴ϶��,
		int min=9999;
		for (int i : thru) { // start������ i���� �� �ٽ� ������ �ϴ� ����. thru�� ���Ҹ�ŭ ���鼭 ���� �� ã�°���.
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


	private HashSet<Integer> reduce(HashSet<Integer> set, int i) { // �ϳ��� �����鼭 i�� ���� ���ο�� �־���. 
		HashSet<Integer> result= new HashSet<>();
		for (int j : set) {
			if (j!=i) {
				result.add(j);
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		int [][] input = { {0,10,10,30,25}, {10,0,14,21,10}, // 5x5��Ʈ���� 
				{10,18,0,7,9}, {8,11,7,0,3}, {14,10,10,3,0}
		};
		
		TSP me = new TSP(input);
		System.out.println("Result = "+me.initTravel(0));

	}
}
