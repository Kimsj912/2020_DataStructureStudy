package week05;

public class hashOpenAddrDouble {

	static int nOfHops = 0;
	static double loadFactor;
	static double threshold = 0.99;// open addressing은 1이 넘을 수 없음. 1이 넘었을때의 성능을 보고자 0.99까지 높게 잡아 넣은 것.

	int[] table;
	int tableSize;
	int numberOfItems;
	private int primeScale;

	public hashOpenAddrDouble(int n) {
		
		tableSize = n;
		table = new int[tableSize];
		for (int i = 0; i < tableSize; i++)
			table[i] = -1; // -1 means null, -999=deleted // 삭제되고 비어있으면 deleted를 넣도록 함.
		primeScale = getPrime();
	}

	private int hashFunction(int d) {
		return hashFunctionA(d) + hashFunctionB(d);
	}

	private int hashFunctionA(int d) {
		int hashVal = (int) d % tableSize;
		if (hashVal < 0)
			hashVal += tableSize;
		return hashVal;
	}

	private int hashFunctionB(int d) {
		int hashVal = (int) d % tableSize;
		if (hashVal < 0)
			hashVal += tableSize;
		return primeScale - hashVal % primeScale;
	}

	public int hashInsert(int d) {
		loadFactor = ((double) numberOfItems) / tableSize; // 적재율
		if (loadFactor >= threshold)
			enlargeTable();
		int hashCode = hashFunctionA(d);
		nOfHops = 1;
		if (table[hashCode] == -1) { // 비어있다면
			table[hashCode] = d;
			numberOfItems++;
			return nOfHops;
		} else { // 비어있지않다면
			hashCode = hashFunction(d);
			int probeIndex = hashCode % tableSize;
			while (table[probeIndex] != -1 && table[probeIndex] != -999) { // 빈칸이거나 deleted가 나올때까지 돌음 (빈칸 찾을 때까지.) //
				nOfHops++;
				probeIndex = (hashFunction(probeIndex)) % tableSize;
				if (probeIndex == hashCode)
					return 0; // cannot happen..
			}
			table[probeIndex] = d;
			numberOfItems++;
			loadFactor = (double) (numberOfItems / tableSize);
			return nOfHops;
		}
	}

	private void enlargeTable() {
		int oldSize = tableSize;
		tableSize = 2 * tableSize;
		int[] tempTable = new int[tableSize];
		for (int i = 0; i < tableSize; i++)
			tempTable[i] = -1;
		for (int i = 0; i < oldSize; i++) // rehashing
			if (table[i] > 0)
				tempTable[hashFunctionA(table[i])] = table[i];
		table = tempTable;
	}

	public int hashSearch(int d) {
		int hashCode = hashFunctionA(d);
		nOfHops = 1;
		if (table[hashCode] == d) {
			return nOfHops;
		} else {
			hashCode = hashFunction(d);
			int probeIndex = hashCode % tableSize;
			while (table[probeIndex] != d && table[probeIndex] != -1) {
				nOfHops++;
				probeIndex = (hashFunction(probeIndex)) % tableSize;
				if (probeIndex == hashCode)
					return 0; // cannot happen..
			}
			if (table[probeIndex] == d)
				return nOfHops; // success
			else
				return -nOfHops; // failure
		}
	}

	public int hashDelete(int d) {
		int hashCode = hashFunctionA(d);
		nOfHops = 1;
		if (table[hashCode] == d) {
			numberOfItems--;
			table[hashCode] = -999;
			return nOfHops;
		} else {
			hashCode = hashFunction(d);
			int probeIndex = (hashCode) % tableSize;
			while (table[probeIndex] != d && table[probeIndex] != -1) {
				nOfHops++;
				probeIndex = (hashFunction(probeIndex)) % tableSize;
				if (probeIndex == hashCode)
					return 0; // cannot happen..
			}
			if (table[probeIndex] == d) { // 이부분만 delete가 search와 다르다.
				table[hashCode] = -999;
				numberOfItems--;
				return nOfHops; // success
			} else
				return -nOfHops; // failure
		}
	}

	public int getPrime() {
		for (int i = tableSize - 1; i >= 1; i--) {
			int fact = 0;
			for (int j = 2; j <= (int) Math.sqrt(i); j++)
				if (i % j == 0)
					fact++;
			if (fact == 0)
				return i;
		}
		return 3;
	}

	public void showTable() {
		System.out.println("Current Hash Table : ");
		for (int i = 0; i < tableSize; i++)
			System.out.print(table[i] + "  ");
		System.out.println();
	}

	public double loadfactor() {
		loadFactor = ((double) numberOfItems / tableSize);
		return loadFactor;
	}

}
