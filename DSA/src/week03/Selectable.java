package week03;

public class Selectable {
	
	public static int selectRecursiveCount = 0;
	
	public int select(int[] data, int p, int r, int i) {
		
		selectRecursiveCount++;
		if(p>r) {
			System.out.println("invalid range... ");
			return -1;
		}
		if(p==r) return data[p];
		int q= partition(data,p,r);
		int k = q-p; // q가 해당 구간 내에선 몇번쨰인지확인.
		
		if(i<k) return select(data,p,q-1,i);
		else if(i==k) return data[q];
		else return select(data,q+1,r,i+p-q-1);
	}
	
	private int partition(int[] data, int p, int r) {
		int pivot = r;
		int left = p;
		int right = r;
		
		while(left<right) {
			while(data[left]<data[pivot]&& left<right)left++;
			while(data[right]>=data[pivot]&& left<right)right--;
			if(left<right)swapData(data,left,right);
		}
		swapData(data,pivot,right);
		return right;
	}

	private void swapData(int[] data, int i, int j) {
		int temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}
//==위는 그냥 select====아래는 개선된 select===============================
	
	public int linearSelect(int[] data, int p, int r, int i) {
		selectRecursiveCount++;
		if(p>r) {
			System.out.println("invalid range... "+p+" "+r);
			return -1;
		}
		if(p==r) return data[p];
		// partition 자체를 recursive하게 할 것 같아서 partition에 median구하는 로직 넣음. 
		// (여기넣어도 상관은 없다.)
		
		int q = linearPartition(data,p,r);
		int k = q-p;
		if(i<k) return linearSelect(data,p,q-1,i);
		else if(i==k) return data[q];
		else return select(data,q+1,r,i+p-q-1);
		
	}
	
	private int linearPartition(int[] data, int p, int r) {
		int pValue = median(data,p,r);
		int pivotIn = indexOf(data,p,r,pValue);
		swapData(data,pivotIn,r);
		
		int pivot = r;
		int left = p;
		int right = r;
		
		while(left<right) {
			while(data[left]<data[pivot]&& left<right)left++;
			while(data[right]>=data[pivot]&& left<right)right--;
			if(left<right)swapData(data,left,right);
		}
		swapData(data,pivot,right);
		return right;
	}

	private int indexOf(int[] data, int p, int r, int pValue) {
		for(int i=p; i<=r;i++) {
			if(data[i]==pValue) return i;
		}
		return -1;
	}

	private int median(int[] data, int p, int r) { // 중앙값 구하는 함수
		if(r-p+1<=5) return median5(data,p,r);
		float f = (r-p+1);
		int arrSize = (int)Math.ceil(f/5);
		int[] medianArray = new int[arrSize];
		for (int i=0; i<arrSize; i++) {
			medianArray[i]= median5(data,p+5*i,(int)Math.min(p+5*i+4,r));
		}
		return median(medianArray,0,arrSize-1); // 충분히 작아질때까지 쪼개나간다.
	}

	private int median5(int[] data, int p, int r) { // 5개 중 중앙값구하는 함수
		if(p==r) return data[p];
		sort5(data,p,r);
		return data[p+(int)((r-p+1)/2)];
	}

	private void sort5(int[] data, int p, int r) { // 5개짜리 sorting하는 함수
		//insertion sort 사용
		for(int i=p+1;i<=r;i++) {
			int j = p;
			while((data[j]<=data[i])&&(j<i)) j++; 
			if(j<i) {
				int temp = data[i];
				for(int k=i-1;k>=j;k--)
					data[k+1]=data[k];
				data[j]=temp;
			}
		}
	}

	public static void main(String[] args) {
		Selectable s = new Selectable();
//		int [] data = {5,27,24,6,35,3,7,8,18,71,77,9,11,32,21,4};
		int [] data = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		
		// Select
		for(int i=0;i<data.length;i++)
			System.out.print(s.select(data,0,data.length-1,i)+" ");
		System.out.println();
		System.out.println("# of Recursive calls of select ="+selectRecursiveCount);

		selectRecursiveCount=0;
		// lenear Select
		for(int i=0;i<data.length;i++)
			System.out.print(s.linearSelect(data,0,data.length-1,i)+" ");
		System.out.println();
		System.out.println("# of Recursive calls of select ="+selectRecursiveCount);
		
	}
}







