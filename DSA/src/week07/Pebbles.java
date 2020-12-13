package week07;

public class Pebbles {

	int[][] table = {
			{0,6,7,12,-5,5,3,11,3,7,-2,5,4},
			{0,-8,10,14,9,7,13,8,5,6,1,3,9},
			{0,11,12,7,4,8,-2,9,4,-4,3,7,10}
	};
	int [][] pattern = {
		{0,0,0},
		{2,3,0}, // p=1, q is ... pattern 2,3
		{1,3,4}, // p=2, q is ... pattern 1,3,4
		{1,2,0}, // p=3, q is ... pattern 1,2
		{2,0,0} // p=4, q is... pattern 2
	};
	
	int [][] memo = new int [5][20];// p가 1~4로 변하니까.
	int count ;
	
	void reset() {
		count = 0;
		for (int mi = 0 ; mi<5;mi++)
			for(int mj = 0 ; mj<20 ; mj++)
				memo[mi][mj]=-999;
	}
	int getCount() {
		return count;
	}
	
	int pebbleMemo(int i, int p) {
		count ++;
		if (i==1) {
			memo[p][i] = pebbleSum(i,p);
			return memo[p][i];
		}else {
			int max = -999;
			int k = 0 ; 
			while (k<3 && pattern[p][k] != 0) {
				int q = pattern[p][k];
				if(memo[q][i-1]<=-999)
					memo[q][i-1] = pebbleMemo(i-1,q);
				max = Math.max(memo[q][i-1], max);
				k++;
			}
			memo[p][i] = max+pebbleSum(i,p);
			return memo[p][i];
		}
	}
	
	int pebble (int i , int p) {
		count ++;
		if(i==1) return pebbleSum(i,p);
		else {
			int max = -999;
			int k = 0 ;  
			while (k<3 && pattern[p][k] != 0) { // k<3 --> q의 패턴을 보기 위함.
				int q = pattern[p][k];
				max = Math.max(pebble(i-1,q), max);
				k++;
			}
			return max+pebbleSum(i,p);
		}
	}
	
	private int pebbleSum(int i , int p) {
		int retVal = 0 ;
		switch(p) {
		case 1: retVal = table[0][i];
		break;
		case 2: retVal = table[1][i];
		break;
		case 3: retVal = table[2][i];
		break;
		case 4: retVal = table[0][i]+table[2][i];
		break;
		}
		return retVal;
	}
	public static void main(String[] args) {
		Pebbles myPeb = new Pebbles();
		for(int j= 1; j<=4 ;j++) // 패턴별로 
			for (int i =1 ; i <=12 ; i++) { //12개의 col에 대해서 
				myPeb.reset();
				System.out.println(">>> "+i+","+j+" : "+myPeb.pebble(i, j)+", Count = "+myPeb.getCount());
//				myPeb.reset();
//				System.out.println("===> "+myPeb.pebbleMemo(i, j)+", Count = "+myPeb.getCount());
			}
	}
}
