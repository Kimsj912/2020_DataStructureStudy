package week05;

public class hashOpenAddrDouble {

	static int nOfHops = 0;
	static double loadFactor;
	static double threshold = 0.99;// open addressing�� 1�� ���� �� ����. 1�� �Ѿ������� ������ ������ 0.99���� ���� ��� ���� ��.

	int[] table;
	int tableSize;
	int numberOfItems;
	private int primeScale;

	public hashOpenAddrDouble(int n) {
		
		tableSize = n;
		table = new int[tableSize];
		for (int i = 0; i < tableSize; i++)
			table[i] = -1; // -1 means null, -999=deleted // �����ǰ� ��������� deleted�� �ֵ��� ��.
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
		loadFactor = ((double) numberOfItems) / tableSize; // ������
		if (loadFactor >= threshold)
			enlargeTable();
		int hashCode = hashFunctionA(d);
		nOfHops = 1;
		if (table[hashCode] == -1) { // ����ִٸ�
			table[hashCode] = d;
			numberOfItems++;
			return nOfHops;
		} else { // ��������ʴٸ�
			hashCode = hashFunction(d);
			int probeIndex = hashCode % tableSize;
			while (table[probeIndex] != -1 && table[probeIndex] != -999) { // ��ĭ�̰ų� deleted�� ���ö����� ���� (��ĭ ã�� ������.) //
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
			if (table[probeIndex] == d) { // �̺κи� delete�� search�� �ٸ���.
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
