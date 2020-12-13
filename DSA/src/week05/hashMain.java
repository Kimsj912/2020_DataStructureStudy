package week05;

import java.util.HashSet;

public class hashMain {

	static int dataSize = 500;
	static int chainingTableSize = 512;
	static int openTableSize = 523;
	static int interval = 50; // 성능을 보기위해 50씩 더함.
	static int maxKeyValue = 100000;

	public static void main(String[] args) {
		int[] data = new int[dataSize];
		HashSet<Integer> rdata = new HashSet<Integer>(); // 중복없이 집합만들기 위함.

		while (rdata.size() < dataSize) { // rdata의 사이즈가 500개가 될때까지 random으로 값을 넣어줌.
			rdata.add((int) (Math.random() * maxKeyValue));
		}
		int k = 0;
		for (int d : rdata) {
			data[k] = d;
			k++;
		}
		int sumOfInvestSuccess = 0;
		int sumOfInvestFailure = 0;
		int delBegin = dataSize / 3;
		int delEnd = 2 * dataSize / 3;

		System.out.println(">>> Chaining");
		hashChaining myHash = new hashChaining(chainingTableSize);
		int repeat = dataSize / interval;
		for (int j = 0; j < repeat; j++) {
			int start = j * interval;
			int end = (j + 1) * interval;
			sumOfInvestSuccess = 0;
			sumOfInvestFailure = 0;
			int maxCount = 0;
			for (int i = start; i < end; i++) {
				int count = myHash.hashInsert(data[i]);
			}
			for (int i = start; i < end; i++) {
				int count = myHash.hashSearch(data[i]);
				if (count >= 0) {
					sumOfInvestSuccess += count;
					if (count > maxCount)
						maxCount = count;
				} else
					sumOfInvestFailure += count;
			}
			System.out.print(" Number of investigation : Success ( ~ " + (j + 1) + " * " + interval + ") = "
					+ sumOfInvestSuccess + "  Max. Hop Count = " + maxCount);
			System.out.println("  Load Factor = " + myHash.loadfactor() + "  Average Hop Count = "
					+ ((double) sumOfInvestSuccess / interval));
		}
		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		int successCount = 0, failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {// 500개를 다시 체크하는 것.
				sumOfInvestFailure += count;
				failCount++;
			}
		}
		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));

		for (int i = delBegin; i < delEnd; i++) {
			myHash.hashDelete(data[i]);
		}
		System.out.println("\n  < After Deleting 1/3 keys...> ");

		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		successCount = 0;
		failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {
				sumOfInvestFailure += count;
				failCount++;
			}
		}
		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));

/////////////////////////////////////////////////////////////////////////////////////////////////////			
		System.out.println("\n\n>>> Open-Addressing : linear probing ");
		hashOpenAddrLinear myHash2 = new hashOpenAddrLinear(chainingTableSize);
		for (int j = 0; j < repeat; j++) {
			int start = j * interval;
			int end = (j + 1) * interval;
			sumOfInvestSuccess = 0;
			sumOfInvestFailure = 0;
			int maxCount = 0;
			for (int i = start; i < end; i++) {
				int count = myHash2.hashInsert(data[i]);
			}
			for (int i = start; i < end; i++) {
				int count = myHash2.hashSearch(data[i]);
				if (count >= 0) {
					sumOfInvestSuccess += count;
					if (count > maxCount)
						maxCount = count;
				} else
					sumOfInvestFailure += count;
			}
			System.out.print(" Number of investigation : Success ( ~ " + (j + 1) + " * " + interval + ") = "
					+ sumOfInvestSuccess + "  Max. Hop Count = " + maxCount);
			System.out.println("  Load Factor = " + myHash2.loadfactor() + "  Average Hop Count = "
					+ ((double) sumOfInvestSuccess / interval));
		}
		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		successCount = 0;
		failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash2.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {
				sumOfInvestFailure += count;
				failCount++;
			}
		}

		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));

		for (int i = delBegin; i < delEnd; i++) {
			myHash2.hashDelete(data[i]);
		}
		System.out.println("\n  < After Deleting 200 keys...> ");
		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		successCount = 0;
		failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash2.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {
				sumOfInvestFailure += count;
				failCount++;
			}
		}

		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));

///////////////////////////////////////////////////////////////////////////////////////////////////////			
		System.out.println("\n\n>>> Open-Addressing : Quadra probing ");
		hashOpenAddrQuadratic myHash3 = new hashOpenAddrQuadratic(chainingTableSize);
		for (int j = 0; j < repeat; j++) {
			int start = j * interval;
			int end = (j + 1) * interval;
			sumOfInvestSuccess = 0;
			sumOfInvestFailure = 0;
			int maxCount = 0;
			for (int i = start; i < end; i++) {
				int count = myHash3.hashInsert(data[i]);
			}
			for (int i = start; i < end; i++) {
				int count = myHash3.hashSearch(data[i]);
				if (count >= 0) {
					sumOfInvestSuccess += count;
					if (count > maxCount)
						maxCount = count;
				} else
					sumOfInvestFailure += count;
			}
			System.out.print(" Number of investigation : Success ( ~ " + (j + 1) + " * " + interval + ") = "
					+ sumOfInvestSuccess + "  Max. Hop Count = " + maxCount);
			System.out.println("  Load Factor = " + myHash3.loadfactor() + "  Average Hop Count = "
					+ ((double) sumOfInvestSuccess / interval));
		}
		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		successCount = 0;
		failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash3.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {
				sumOfInvestFailure += count;
				failCount++;
			}
		}

		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));

		for (int i = delBegin; i < delEnd; i++) {
			myHash3.hashDelete(data[i]);
		}
		System.out.println("\n  < After Deleting 200 keys...> ");
		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		successCount = 0;
		failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash3.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {
				sumOfInvestFailure += count;
				failCount++;
			}
		}

		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));
		
////////////////////////////////////////////////////////////////////////////////////		
		System.out.println("\n\n>>> Open-Addressing : Double Hashing ");
		hashOpenAddrDouble myHash4 = new hashOpenAddrDouble(chainingTableSize);
		for (int j = 0; j < repeat; j++) {
			int start = j * interval;
			int end = (j + 1) * interval;
			sumOfInvestSuccess = 0;
			sumOfInvestFailure = 0;
			int maxCount = 0;
			for (int i = start; i < end; i++) {
				int count = myHash4.hashInsert(data[i]);
			}
			for (int i = start; i < end; i++) {
				int count = myHash4.hashSearch(data[i]);
				if (count >= 0) {
					sumOfInvestSuccess += count;
					if (count > maxCount)
						maxCount = count;
				} else
					sumOfInvestFailure += count;
			}
			System.out.print(" Number of investigation : Success ( ~ " + (j + 1) + " * " + interval + ") = "
					+ sumOfInvestSuccess + "  Max. Hop Count = " + maxCount);
			System.out.println("  Load Factor = " + myHash4.loadfactor() + "  Average Hop Count = "
					+ ((double) sumOfInvestSuccess / interval));
		}
		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		successCount = 0;
		failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash4.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {
				sumOfInvestFailure += count;
				failCount++;
			}
			System.out.println("HashMain-Double-Count : "+count);
		}
		
		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));
		
		for (int i = delBegin; i < delEnd; i++) {
			myHash4.hashDelete(data[i]);
		}
		System.out.println("\n  < After Deleting 200 keys...> ");
		sumOfInvestSuccess = 0;
		sumOfInvestFailure = 0;
		successCount = 0;
		failCount = 0;
		for (int j = 0; j < dataSize; j++) {
			int count = myHash4.hashSearch((int) (Math.random() * maxKeyValue));
			if (count >= 0) {
				sumOfInvestSuccess += count;
				successCount++;
			} else {
				sumOfInvestFailure += count;
				failCount++;
			}
		}
		
		System.out.println("\n Average number of investigation : Success = " + sumOfInvestSuccess + "(" + successCount
				+ ")" + "  Average Hop Count = " + ((double) sumOfInvestSuccess / successCount) + "  Failure = "
				+ (-sumOfInvestFailure) + "(" + failCount + ")" + "  Average Hop Count = "
				+ ((double) -sumOfInvestFailure / failCount));

	}

}
