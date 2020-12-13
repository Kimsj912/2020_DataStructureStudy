package week14;

import java.util.Arrays;

import week12.GraphInMatrix;

public class Coloring extends GraphInMatrix {

	int [] color ;
	int numberOfVertex, maxColor;
	public Coloring(int size, int k) {
		numberOfVertex=size;
		maxColor = k;
		createGraph(numberOfVertex);
		color = new int[numberOfVertex];
		Arrays.fill(color, 0);
	}
	
	public boolean kcoloring(int i, int c) {
		boolean result;
		if (valid(i, c)) {
			color[i]=c;
			if (i==numberOfVertex-1)
				return true;
			else {
				result=false;
				int colorScan =1;
				while (result==false && colorScan<=maxColor) {
					result=kcoloring(i+1,colorScan);
					colorScan++;
				}
			}
			return result;
		}
		else
			return false;
	}
	
	private boolean valid(int i, int c) {
		for (int k=1; k<i; k++) {
			if (adjacentMatrix[k][i]==1 && color[k]==c)
				return false;
		}
		return true;
	}
	
	public void showColor() {
		for (int i=1; i<numberOfVertex; i++)
			System.out.print(i+" : "+color[i]+",  ");
	}

	public static void main(String[] args) {
		int k=3; // max. number of color
		int numVertex = 6 ;
		Coloring me = new Coloring(numVertex+1, k);
		
		me.insertEdge(1,2);
		me.insertEdge(1,3);
		me.insertEdge(1,4);
		me.insertEdge(1,6);
		me.insertEdge(2,5);
		me.insertEdge(2,6);
		me.insertEdge(3,6);
		me.insertEdge(4,5);
		me.insertEdge(4,6);
		me.insertEdge(5,6);
		
		System.out.println("Number of Vertices : "+numVertex);
		System.out.println("Number of Colors : "+k);
		System.out.println(me.kcoloring(1,1)); // 1번 노드에 1번 컬러를 넣어 // kcoloring은 위에서 셋팅한 k만큼의 색으로 칠하기가 가능하냐는 거같음.
		me.showColor();
		me.showGraph();
		


	}

}
