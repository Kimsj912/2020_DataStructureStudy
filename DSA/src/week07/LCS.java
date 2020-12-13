package week07;

import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;

public class LCS {
	private int[][] table;
	
	private int strALength;
	private int strBLength;

	private char[] strAchar;
	private char[] strBchar;

	private String retVal="";
	
	// Constructor
	public LCS (String strA, String strB) {
		this.strAchar = strA.toCharArray();
		this.strBchar = strB.toCharArray();
	
		this.strALength = strA.length();
		this.strBLength = strB.length();
		
		this.table = new int[strBLength+1][strALength+1]; // 0�ڸ� ����

	}
	
	private void LCSmemo() {
		for(int i=1; i<strBLength+1; i++) {
			for(int j=1; j<strALength+1; j++) {
				if(strBchar[i-1] != strAchar[j-1]) 
						table[i][j] = table[i-1][j] > table[i][j-1] ? table[i-1][j] : table[i][j-1];
					else
						table[i][j] = table[i-1][j-1] + 1;
			}
		}
		
		int i = strBLength;
		int j = strALength;
		Stack<Character> stack = new Stack<>();
		while(i >=1 && j >= 1) {
			// ���� ���� --> ���� �̵�
			if(table[i][j] == table[i-1][j]) i--;
			// ���ʰ� ���� -->  �������� �̵�
			else if(table[i][j] == table[i][j-1]) j--;
			else {
				// �밢������ ��ġ
				stack.push(strBchar[i-1]);
				i--; j--;
			}		
		}
		
		while(!stack.isEmpty()) retVal += stack.pop();
	}
	
	private void showResult() {
		System.out.println(" <Longest Common Sequence> ");
		System.out.println("-------------------------");
		for(int k=0;k<table.length;k++) {
			System.out.println("  "+Arrays.toString(table[k]));
		}
		System.out.println();
		System.out.println("long sequence's length is... " + table[strBLength][strALength]);
		System.out.println("long sequence is ... "+ retVal);
	}
	
	public static void main(String[] args) throws IOException{
		LCS lcs = new LCS("CAPCAK","ACAYKP");
		lcs.LCSmemo();
		lcs.showResult();
		
	}

	
}
