package week05;

public class hashChaining {
	
	static int nOfHops =0; // 몇번을 찾아야 원하는걸 얻는지.
	static double loadFactor;	
	
	private class HashNode {
		int key;
		HashNode next;
		public HashNode(int i) {
			key = i;
			next=null;
		}
		public String toString() {
			String res = "";
			return res+" -> "+key;
		}
	}
		
	HashNode [] table;
	int tableSize;
	int numberOfItems; // 적재된 key의 개수
	
	public hashChaining(int n) {
		tableSize= n;
		numberOfItems=0;
		table = new HashNode[n];
		for (int i =0;i<tableSize;i++)
			table[i]=null;
	}
	private int hashFunction(int d) {
    // 정수인 아닌 경우에는  hashcode() 정의 필요
	// 나누기 방법
//		return (int)d%tableSize;
	// 곱하기 방법
		double temp = (double) (d*0.6180339887);
		int res = (int) Math.floor(temp);
		return res%tableSize;
	}

	public int hashInsert(int d) {
		int hashCode=hashFunction(d);
		HashNode newNode = new HashNode(d);
		newNode.next = table[hashCode];
		table[hashCode]=newNode;
		numberOfItems++;
		loadFactor =(double)(numberOfItems/tableSize);
		nOfHops =1; // open addressing과 비교하려고 그냥 넣는것. 알고리즘에 의미 없음.
		return nOfHops; // nOfHops =1 always..
	}
	
	public int hashSearch(int d) {
		int hashCode=hashFunction(d);
		HashNode p = table[hashCode];
		nOfHops=1;
		while (p!=null) {
			if (p.key==d)
				return nOfHops;
			else {
				nOfHops++;
				p=p.next;
			}
		}
		return -nOfHops; // -nOfHops= 검색 실패시 조사 횟수 // 성능분석용 매커니즘. 알고리즘에 의미없음.
	}

	public int hashDelete(int d) {
		int hashCode=hashFunction(d);
		HashNode p = table[hashCode];
		HashNode q = p.next;
		
		nOfHops=1;
		if (p==null)
			return -nOfHops;
		else if(p.key==d) { // 맨앞인 경우
			nOfHops++;
			table[hashCode]=p.next; // table[hashCode]은 tree의 루트같은 경우.
			numberOfItems--;
			loadFactor =(double)(numberOfItems/tableSize);
			return nOfHops;
		}
		while (q!=null) { // 중간의 경우
			nOfHops++;
			if (q.key==d) {
				p.next = q.next;
				numberOfItems--;
				loadFactor =(double)(numberOfItems/tableSize);
				return nOfHops;
			}
			else {
				p=q;
				q=q.next;
			}
		}
		return -nOfHops; // -nOfHops= 검색 실패시 조사 횟수
	}

	public void showTable() {
		System.out.println("<< Current Status of Table  >> ");
		for (int i=0; i<tableSize; i++) {
			HashNode p = table[i];
			if (p!=null) System.out.print("\n "+i+" : ");
			while (p!=null) {
			 System.out.print(p);
			 p=p.next;
			}

		}
	}
	public double loadfactor() {
		loadFactor = ((double)numberOfItems/tableSize);
		return loadFactor;
	}
		
}
