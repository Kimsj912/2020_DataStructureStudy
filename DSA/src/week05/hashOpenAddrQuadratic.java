package week05;

public class hashOpenAddrQuadratic {

	static int nOfHops =0;
	static double loadFactor;
	static double threshold = 0.99;//open addressing�� 1�� ���� �� ����. 1�� �Ѿ������� ������ ������ 0.99���� ���� ��� ���� ��.
	
	
	int [] table;
	int tableSize;
	int numberOfItems;
	
	public hashOpenAddrQuadratic(int n) {
		tableSize= n;
		table = new int[tableSize];
		for (int i=0; i<tableSize; i++)
			table[i]=-1; // -1 means null, -999=deleted // �����ǰ� ��������� deleted�� �ֵ��� ��.
	}
	private int hashFunction(int d) {
	    // ������ �ƴ� ��쿡��  hashcode() ���� �ʿ�
		// ������ ���
//			return (int)d%tableSize;
		// ���ϱ� ���
		double temp = (double)d * 0.6180339887;
		int res = (int) Math.floor(temp);
		return res%tableSize;
		}

	public int hashInsert(int d) {
		loadFactor =((double)numberOfItems)/tableSize; // ������
		if (loadFactor>=threshold)
			enlargeTable(); 
		int hashCode=hashFunction(d);
		nOfHops =1;
		if (table[hashCode]==-1) { //����ִٸ�
			table[hashCode]=d;
			numberOfItems++;
			return nOfHops;
		}
		else { // ��������ʴٸ�
			int h=1;
			int probeIndex=(hashCode+ h * h++)%tableSize;
//			int probeIndex=(hashCode+1)%tableSize;
			while(table[probeIndex]!=-1 && table[probeIndex]!=-999) { // ��ĭ�̰ų� deleted�� ���ö����� ���� (��ĭ ã�� ������.) // �츮�� enlarge���ִ� �Լ��� �־ ������ �񱳰� �ʿ����.
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
			if (table[probeIndex]==d) { // �̺κи� delete�� search�� �ٸ���. 
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
