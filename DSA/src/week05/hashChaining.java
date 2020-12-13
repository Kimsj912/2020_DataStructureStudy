package week05;

public class hashChaining {
	
	static int nOfHops =0; // ����� ã�ƾ� ���ϴ°� �����.
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
	int numberOfItems; // ����� key�� ����
	
	public hashChaining(int n) {
		tableSize= n;
		numberOfItems=0;
		table = new HashNode[n];
		for (int i =0;i<tableSize;i++)
			table[i]=null;
	}
	private int hashFunction(int d) {
    // ������ �ƴ� ��쿡��  hashcode() ���� �ʿ�
	// ������ ���
//		return (int)d%tableSize;
	// ���ϱ� ���
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
		nOfHops =1; // open addressing�� ���Ϸ��� �׳� �ִ°�. �˰��� �ǹ� ����.
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
		return -nOfHops; // -nOfHops= �˻� ���н� ���� Ƚ�� // ���ɺм��� ��Ŀ����. �˰��� �ǹ̾���.
	}

	public int hashDelete(int d) {
		int hashCode=hashFunction(d);
		HashNode p = table[hashCode];
		HashNode q = p.next;
		
		nOfHops=1;
		if (p==null)
			return -nOfHops;
		else if(p.key==d) { // �Ǿ��� ���
			nOfHops++;
			table[hashCode]=p.next; // table[hashCode]�� tree�� ��Ʈ���� ���.
			numberOfItems--;
			loadFactor =(double)(numberOfItems/tableSize);
			return nOfHops;
		}
		while (q!=null) { // �߰��� ���
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
		return -nOfHops; // -nOfHops= �˻� ���н� ���� Ƚ��
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
