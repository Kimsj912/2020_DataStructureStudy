package week14;

import java.util.ArrayList;
import java.util.HashSet;

public class TSPDP {

	int [][] AdjacentMatrix, allDistance, allPath; // allDistance �� �ش� ������ �κУ����� ��캰�� � �Ÿ��� �������� ����Ϸ�����. allpaht ����... �̰�...? 
	int numberVertex;
	ArrayList<Integer> finalPath; // �׸� �߿��Ѱ� �Ƴ��,.

	public TSPDP(int[][] input) {
		AdjacentMatrix = input;
		numberVertex=AdjacentMatrix.length;
		allDistance=new int[numberVertex][(int) Math.pow(2, numberVertex)];
		allPath=new int[numberVertex][(int) Math.pow(2, numberVertex)];
		initDistAndPath(-1); // alldistance�� allpath�� ���� ���� -1�� ����.
		finalPath = new ArrayList<>();

	}

	public int initTravel(int start) {//�׳� TSP�� ����
		HashSet<Integer> thru = new HashSet<>(); 
		for (int i=0; i<numberVertex; i++) {
			if (i!=start)
				thru.add(i);
		}
		return minTravel(start, thru, start);
	}

	private int minTravel(int start, HashSet<Integer> thru, int returnPoint) { 
		if (allDistance[start][indexSet(thru)]!=-1)//�Ȱ��� ��ŸƮ�� �Ȱ��� �κ����տ� ���ؼ� �̹� �ѹ� �����̵Ǿ����ٸ�,
			return allDistance[start][indexSet(thru)]; // �״�� �ѱ�� ��. (�̷��� �ϴϱ� DP��!)
		if (thru.size()==0) { // �������̶���ص� ��Ʈ���� ���� ����Ǿ���ϴϱ� �Ʒ�ó�� ����
			allDistance[start][indexSet(thru)]=AdjacentMatrix[start][returnPoint];
			allPath[start][indexSet(thru)]=returnPoint; // start���� ������ ���ļ��� �����ϴ��� returnpoint���ٰ� ����ϱ�
			return AdjacentMatrix[start][returnPoint];
		}
		int min=9999;
		for (int i : thru) {
			HashSet<Integer> nextThru = reduce(thru,i);
			if (AdjacentMatrix[start][i]!=999) {
				int tempDist = AdjacentMatrix[start][i]+minTravel(i, nextThru, returnPoint); //recursive�ϰ� minTravel���ư�.
				if (tempDist<=min) {
					min=tempDist;
					allDistance[start][indexSet(thru)]=min;
					allPath[start][indexSet(thru)]=i;
				}
			}
		}
		System.out.println(start+" -> "+thru+" ("+min+")");
		return min;
	}

	private int indexSet(HashSet<Integer> thru) { 
		// minTravel���� thru��� ������ �����⶧���� �̰� index�� �ٲ��ִ� �� ������ ��. �̰� DP�ν� ���� ���ο����.
		int result=0;
		for (int i : thru) {
			for (int k=0; k<numberVertex;k++) { 
				if (i==k) { // i�� ���鿡 �´ٸ�? �ش�Ǹ�? 2�� �¼� ��ŭ�� ���ض�. 
					result += Math.pow(2,k); //2�� k���� �߰��Ѵ�.
				}
			}
		}
		return result;
	}

	private HashSet<Integer> reduce(HashSet<Integer> set, int i) { //TSP�� ����
		HashSet<Integer> result= new HashSet<>();
		for (int j : set) {
			if (j!=i) {
				result.add(j);
			}
		}
		return result;
	}
	 
	private void initDistAndPath(int val) { //-1�־��ִ� ����.
		for (int i=0; i<numberVertex;i++) {
			for (int j=0; j< Math.pow(2, numberVertex); j++) {
				allDistance[i][j]=val;
				allPath[i][j]=val;
			}
		}
		 
	}
	public void showAllDistAndPath() {
		System.out.println(">>> Distance Memoization \n");

		for (int i=0; i<numberVertex;i++) {
			for (int j=0; j< Math.pow(2, numberVertex); j++) {
				System.out.print(allDistance[i][j]+"  ");
			}
			System.out.println();
		}
		System.out.println(">>> Path \n");

		for (int i=0; i<numberVertex;i++) {
			for (int j=0; j< Math.pow(2, numberVertex); j++) {
				System.out.print(allPath[i][j]+"  ");
			}
			System.out.println();
		}
	}
	
	private void findPath(int start) {
		finalPath.add(start);
		System.out.println("***** "+start+" added");

		int indexI=start;
		int indexJ=getDistIndex(start);
		if (indexJ==-1) {
			System.out.println("Wrong Index ...");
			return ;
		}
		int dist = allDistance[start][indexJ];
		findPathRecursion(allPath[indexI][indexJ], dist-AdjacentMatrix[start][allPath[indexI][indexJ]], start);
	}

	private void findPathRecursion(int i, int dist, int endPoint) {
		finalPath.add(i);
		System.out.println("***** "+i+" added");

		if (i==endPoint)
			return;
		int indexJ=getDistIndex(i, dist);
		if (indexJ==-1) {
			System.out.println("Wrong Index ...");
			return ;
		}
		findPathRecursion(allPath[i][indexJ], dist-AdjacentMatrix[i][allPath[i][indexJ]], endPoint);	
	}
	private int getDistIndex(int i) {
		for (int j=0; j< Math.pow(2, numberVertex); j++) {
			if (allDistance[i][j]>0)
				return j;
		}
		return -1;
	}

	private int getDistIndex(int i, int dist) {
		for (int j=0; j< Math.pow(2, numberVertex); j++) {
			if (allDistance[i][j]==dist)
				return j;
		}
		return -1;
	}

	public static void main(String[] args) {
	
		int [][] input = { {0,10,10,30,25}, {10,0,14,21,10}, 
				{10,18,0,7,9}, {8,11,7,0,3}, {14,10,10,3,0}
		};

		TSPDP me = new TSPDP(input);
		int tsp = me.initTravel(0);
		System.out.println("Result = "+tsp);
		me.showAllDistAndPath();
		me.findPath(0);

	}


}