package week14;

import java.util.ArrayList;
import java.util.HashSet;

public class TSPDP {

	int [][] AdjacentMatrix, allDistance, allPath; // allDistance 는 해당 집합의 부분짒합의 경우별로 어떤 거리가 나오는지 기억하려고함. allpaht 였나... 이게...? 
	int numberVertex;
	ArrayList<Integer> finalPath; // 그리 중요한건 아녔다,.

	public TSPDP(int[][] input) {
		AdjacentMatrix = input;
		numberVertex=AdjacentMatrix.length;
		allDistance=new int[numberVertex][(int) Math.pow(2, numberVertex)];
		allPath=new int[numberVertex][(int) Math.pow(2, numberVertex)];
		initDistAndPath(-1); // alldistance와 allpath의 값을 전부 -1로 만듬.
		finalPath = new ArrayList<>();

	}

	public int initTravel(int start) {//그냥 TSP와 동일
		HashSet<Integer> thru = new HashSet<>(); 
		for (int i=0; i<numberVertex; i++) {
			if (i!=start)
				thru.add(i);
		}
		return minTravel(start, thru, start);
	}

	private int minTravel(int start, HashSet<Integer> thru, int returnPoint) { 
		if (allDistance[start][indexSet(thru)]!=-1)//똑같은 스타트와 똑같은 부분집합에 대해서 이미 한번 산출이되었었다면,
			return allDistance[start][indexSet(thru)]; // 그대로 넘기면 됨. (이렇게 하니까 DP다!)
		if (thru.size()==0) { // 공집합이라고해도 매트릭스 값은 저장되어야하니까 아래처럼 들어가고
			allDistance[start][indexSet(thru)]=AdjacentMatrix[start][returnPoint];
			allPath[start][indexSet(thru)]=returnPoint; // start에서 공집합 거쳐서는 도착하느게 returnpoint였다고 기록하기
			return AdjacentMatrix[start][returnPoint];
		}
		int min=9999;
		for (int i : thru) {
			HashSet<Integer> nextThru = reduce(thru,i);
			if (AdjacentMatrix[start][i]!=999) {
				int tempDist = AdjacentMatrix[start][i]+minTravel(i, nextThru, returnPoint); //recursive하게 minTravel돌아감.
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
		// minTravel에서 thru라는 집합이 들어오기때문에 이걸 index로 바꿔주는 이 과정이 들어감. 이게 DP로써 들어온 새로운거임.
		int result=0;
		for (int i : thru) {
			for (int k=0; k<numberVertex;k++) { 
				if (i==k) { // i가 노드들에 맞다면? 해당되면? 2의 승수 만큼을 더해라. 
					result += Math.pow(2,k); //2의 k승을 추가한다.
				}
			}
		}
		return result;
	}

	private HashSet<Integer> reduce(HashSet<Integer> set, int i) { //TSP랑 동일
		HashSet<Integer> result= new HashSet<>();
		for (int j : set) {
			if (j!=i) {
				result.add(j);
			}
		}
		return result;
	}
	 
	private void initDistAndPath(int val) { //-1넣어주는 거임.
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