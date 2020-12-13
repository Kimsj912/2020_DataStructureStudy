package week07;

public class MatrixChain {

	int maxN;
	int[] p;
	
	int count;
	int[][] memo; // memoization 
	
	void reset() {
		count = 0;
		for (int i = 0 ; i<maxN+1;i++)
			for(int j = 0 ; j<maxN+1 ; j++)
				memo[i][j]=-1;
	}
	int getCount() {
		return count;
	}
	
	public MatrixChain (int nOfMatrix, int[] dimension) {
		maxN = nOfMatrix;
		p = new int[maxN+1];
		p = dimension;
		memo = new int[maxN+1][maxN+1];
	}
	private int sequentialMult(int i, int j) { //방법 1. seq
		int retVal;
		if(i==j) return 0;
		else retVal=1;
		for(int k=0;k<=j;k++)
			retVal *= p[k];
		return retVal;
	}
	private int rMatrixChain(int i, int j) {
		count ++;
		if(i==j) return 0;
		int min = 9999999;
		for(int k = i ; k<j ; k++) {
				int q=rMatrixChain(i, k)+rMatrixChain(k+1, j)+p[i-1]*p[k]*p[j];
				if (q<min) min=q;
		}
		return min;
	}
	
	int matrixChain1(int i , int j) { // memoization을 적용한 첫 matrix chain (seq algorithm+memo)
		count++; 
		if(i==j) {
			memo[i][j] = 0;
			return memo[i][j];
		}
		int min = 9999999;
		for(int k=i; k<j;k++) {
			int q1,q2;
			if(memo[i][k]==-1) q1=matrixChain1(i,k);
			else q1 = memo[i][k];
			if(memo[k+1][j]==-1) q2=matrixChain1(k+1,j);
			else q2 = memo[k+1][j];
			int q = q1+q2+p[i-1]*p[k]*p[j];
			if(q<min) min=q;
		}
		return min;
		//==> 순서때문에 성능이 부족했다.
	}
	int matrixChain2(int i , int j) {
		count++;
		if(i==j) {
			memo[i][j] = 0;
			return memo[i][j];
		}
		for(int k=i; k<=j;k++) {
			for(int l=1; l<=j-k;l++) {
				memo[k+l-1][k+1] = matrixChain1(k+l-1,k+l);
			}
		}
		return 0;
		// 대각선 방식으로 더해나가면 더 효율적.
	}

	
	public static void main(String[] args) {
		int numOfMatrix = 15;
		int[] dimension = {2,3,4,3,5,3,4,5,3,2,4,6,5,4,3,4};
		
		MatrixChain mm = new MatrixChain(numOfMatrix, dimension);
		for(int i = 1; i<=numOfMatrix;i++) {
			mm.reset();
			System.out.println("Seq : "+mm.sequentialMult(1,i));
			mm.reset();
			System.out.println("Recursion : "+mm.rMatrixChain(1,i)+ " Count : "+mm.getCount());
			mm.reset();
			System.out.println("	===> DP1 : "+mm.matrixChain1(1,i)+ " Count : "+mm.getCount());
			mm.reset();
			mm.matrixChain2(1,i);
			System.out.println("	===> DP2 : "+ " 	Count : "+mm.getCount());
		}
	}
	
}
