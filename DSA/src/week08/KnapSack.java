package week08;

public class KnapSack {
	
	 private static int[][] table;
	private int knap_recursive(int n, int w, int[] wt, int[] val) {
         if (n <= 0 || w <= 0)  return 0;
         if (wt[n] > w)  return knap_recursive(n - 1, w, wt, val);
         else return Math.max(knap_recursive(n - 1, w, wt, val), knap_recursive(n - 1, w - wt[n], wt, val) + val[n]);
	 }

	 private int knap_table(int n, int w, int[] wt, int[] val, int[][] table) {
         if (n <= 0 || w <= 0)  return 0;
         if ((n>1) && (table[n - 1][w] == 0)) table[n - 1][w] = knap_table(n - 1, w, wt, val, table);
         if ((n>1 && w>wt[n]) && (table[n - 1][w - wt[n]] == 0))  table[n - 1][w - wt[n]] = knap_table(n - 1, w - wt[n], wt, val, table);
         if ((wt[n] <= w) && (table[n - 1][w] < table[n - 1][w - wt[n]] + val[n])) table[n][w] = table[n - 1][w - wt[n]] + val[n];
         else table[n][w] = table[n - 1][w];
         return table[n][w];
	 }
	 
	 private int getMaxValue_Recursive(int N, int W, int[] wt, int[] val) {
         return knap_recursive(N, W, wt, val);
	 }
	 private int getMaxValue_DP(int N, int W, int[] wt, int[] val) {
		 int max = knap_table(N, W, wt, val, table);
		 return max;
	 }

	private static void print() {
        System.out.println("Table------->");
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table[i].length; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println("\n");
		}
	}
	 public static void main(String[] args) throws Exception {
         int N = 5; 
         int W = 5; 
         int[] weight = {1,3,5,9,2,1,4,5,6,3,10,50}; // 물건의 무게
         int[] price = {10,30,20,50,20,12,40,70,80,95,130,77}; // 물건의 가치
         int maxValue; // 최상의 price
         
         KnapSack k = new KnapSack();
         // recursive
         maxValue = k.getMaxValue_Recursive(N, W, weight, price);
         System.out.println("Recursive --> MaxValue = " + maxValue);

         // DP
         table = new int[N + 1][W + 1];
         maxValue = k.getMaxValue_DP(N, W, weight, price);
         System.out.println("DP --> MaxValue = " + maxValue);
         print();
	 }
}
