package week05;

public class hashOpenAddrQuadratic {

	static int nOfHops =0;
	static double loadFactor;
	static double threshold = 0.99;//open addressing은 1이 넘을 수 없음. 1이 넘었을때의 성능을 보고자 0.99까지 높게 잡아 넣은 것.
	
	
	int [] table;
	int tableSize;
	int numberOfItems;
	
	public hashOpenAddrQuadratic(int n) {
		tableSize= n;
		table = new int[tableSize];
		for (int i=0; i<tableSize; i++)
			table[i]=-1; // -1 means null, -999=deleted // 삭제되고 비어있으면 deleted를 넣도록 함.
	}
	private int hashFunction(int d) {
	    // 정수인 아닌 경우에는  hashcode() 정의 필요
		// 나누기 방법
//			return (int)d%tableSize;
		// 곱하기 방법
		double temp = (double)d * 0.6180339887;
		int res = (int) Math.floor(temp);
		return res%tableSize;
		}

	public int hashInsert(int d) {
		loadFactor =((double)numberOfItems)/tableSize; // 적재율
		if (loadFactor>=threshold)
			enlargeTable(); 
		int hashCode=hashFunction(d);
		nOfHops =1;
		if (table[hashCode]==-1) { //비어있다면
			table[hashCode]=d;
			numberOfItems++;
			return nOfHops;
		}
		else { // 비어있지않다면
			int h=1;
			int probeIndex=(hashCode+ h * h++)%tableSize;
//			int probeIndex=(hashCode+1)%tableSize;
			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // 빈칸이거나 deleted가 나올때까지 돌음 (빈칸 찾을 때까지.) // 우리는 enlarge해주는 함수가 있어서 사이즈 비교가 필요없다.
				nOfHops++;
				probeIndex=(probeIndex+ h * h++)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			table[probeIndex]=d;
			numberOfItems++;
			loadFactor =(double)(numberOfItems/tableSize); 
			return nOfHops; 	
		}
	}
	private void enlargeTable() {
		int oldSize=tableSize;
		tableSize=2*tableSize;
		int [] tempTable = new int [tableSize];
		for (int i=0; i<tableSize;i++)
			tempTable[i]=-1;
		for (int i=0; i<oldSize; i++) // rehashing
			if (table[i]>0)
				tempTable[hashFunction(table[i])]= table[i];
		table =tempTable;
	}
		
	public int hashSearch(int d) {
		int hashCode=hashFunction(d);
		nOfHops =1;
		if (table[hashCode]==d) {
			return nOfHops;
		}
		else {
			int h = 1;
			int probeIndex=(hashCode+ h * h++)%tableSize;
			while(table[probeIndex]!=d && table[probeIndex]!=-1) { 
				nOfHops++;
				probeIndex=(probeIndex+ h*h++)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			if (table[probeIndex]==d) return nOfHops; // success
			else return -nOfHops; 	// failure
		}
	}
	public int hashDelete(int d) {
		int hashCode=hashFunction(d);
		nOfHops =1;
		if (table[hashCode]==d) {
			numberOfItems--;
			table[hashCode]=-999;
			return nOfHops;
		}
		else {
			int h = 1;
			int probeIndex=(hashCode+ h * h++)%tableSize;
			while(table[probeIndex]!=d && table[probeIndex]!=-1) { 
				nOfHops++;
				probeIndex=(probeIndex+ h*h++)%tableSize;
				if (probeIndex==hashCode)
					return 0; // cannot happen..
			}
			if (table[probeIndex]==d) { // 이부분만 delete가 search와 다르다. 
				table[hashCode]=-999;
				numberOfItems--;
				return nOfHops; // success
			}
			else return -nOfHops; 	// failure
		}
	}
	
	public void showTable() {
		System.out.println("Current Hash Table : ");
		for (int i = 0; i<tableSize; i++)
			System.out.print(table[i]+"  ");
		System.out.println();
	}
	public double loadfactor() {
		loadFactor = ((double)numberOfItems/tableSize);
		return loadFactor;
	}
		

}
