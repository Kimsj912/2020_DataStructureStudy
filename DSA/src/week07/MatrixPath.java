package week07;

public class MatrixPath {
//	int[][] table = { // 1의 루트 따라가면 min path 결과가 9가 나오는 계산간단용 테스트 어레이
//			{ 1, 9, 9, 9, 9 }, 
//			{ 1, 9, 9, 9, 9 }, 
//			{ 1, 1, 1, 1, 9 }, 
//			{ 9, 9, 9, 1, 1 }, 
//			{ 9, 9, 9, 9, 1 } 
//			};
	int[][] table = { 
			{ 5, 6, 7, 12, 3 }, 
			{ 2, 6, 8, 9, 14 }, 
			{ 3, 1, 3, 7, 8 }, 
			{ 4, 5, 8, 1, 2 }, 
			{ 6, 5, 7, 1, 9 } 
	};
	int[][] sumMemo;
	int count;
	private int nullNum =-999;

	public MatrixPath() {
		this.sumMemo = new int[table[0].length][table.length];

		for(int y = 0 ; y< table.length ; y++) {
			for(int x = 0 ; x< table[0].length ; x++) {
				System.out.println(x+","+y);
				this.sumMemo[x][y]=nullNum;
			}
		}
	}
	
	private int getCount() {return count;}
	
	private String MatrixPathMemoInit() {
		// 값 셋팅
		int min = MatrixPathMemo(this.table[0].length-1, this.table.length-1);
		String retVal = "minimum sum path  is "+min+ ", Count : "+getCount();
		return retVal;
	}
	
	private int MatrixPathMemo(int x, int y) {
		count++;
		
		int newNum = 0;
		// 새값계산
		if (x==0 && y==0) return table[x][y];
		else if (x==0) newNum = table[x][y] +MatrixPathMemo(x,y-1) ;
		else if (y==0) newNum = table[x][y] +MatrixPathMemo(x-1,y) ;
		else newNum = table[x][y] + Math.min(MatrixPathMemo(x-1,y),MatrixPathMemo(x,y-1));
		
		// memoization하기
		if (sumMemo[x][y]==nullNum) sumMemo[x][y] = newNum;
		else sumMemo[x][y] = sumMemo[x][y]<=newNum ? sumMemo[x][y]:newNum ;
		
		return sumMemo[x][y];
	}
	

	public static void main(String[] args) {
		//start Point = (0,0) / end Point = (4,4)
		MatrixPath myMP = new MatrixPath();
		System.out.println(myMP.MatrixPathMemoInit());
	}

	
	
}
