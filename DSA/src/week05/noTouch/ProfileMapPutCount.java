//package week05.noTouch;
//
//import java.util.HashSet;
//
//import org.jfree.data.xy.XYSeries;
//
//import week05.hashOpenAddrLinear;
//import week05.noTouch.ProfilerCount.Figurable;
//
//public class ProfileMapPutCount {
//
//	static int dataSize = 500;
//	static int chainingTableSize = 512;
//	static int openTableSize = 523;
//	static int interval = 20;
//	static int maxKeyValue = 100000;
//	static int [] data = new int[dataSize];
//	static hashOpenAddrLinear myHash = new hashOpenAddrLinear(chainingTableSize);
//
//
//	private static void generateTestData(int dataSize2) {
//		HashSet<Integer> rdata = new HashSet<Integer>();
//		
//		while (rdata.size()<dataSize) {
//			rdata.add((int)(Math.random()*maxKeyValue));
//		}
//		int k=0;
//		for (int d : rdata) {
//			data[k]=d; k++; 
//		}
//	}
//	
//	public static double addBloack(int n) {
//		int start = n*interval;
//		int end =(n+1)*interval;
//		int sumOfInvestSuccess=0;
//		for (int i=start; i<end; i++) {
//			int count= myHash.hashInsert(data[i]);
//			sumOfInvestSuccess += count;
//		}
//		return (double)sumOfInvestSuccess/interval;
//	}	
//	
//	
//	public static void main(String[] args) {
//		generateTestData(dataSize);
//		
//		estimate("Hash Open Addressing Algorithm Performance Evaluation");  
//	}
//
//	/**
//	 * Characterize the run time of putting a key in fibo.
//	 */
//	public static void estimate(String title) {
//		Figurable rundata = new Figurable() { // define interface method
//			double value;
//			public void setup(int n) {
//				value=0.0;
//			}
//			
//			public double evalMe(int n) {
//				testedRoutine(n);
//				return value;
//			}
//
//			private void testedRoutine(int n) {
//				value = addBloack(n);
//				return;
//			}
//
//
//		};
//
//		int startN = 1;
//		int endCount = dataSize/interval; 
//		runProfiler(title, rundata, startN, endCount);
//	}
//
//	
//	/**
//	 * Runs the profiles and displays results.
//	 * 
//	 * @param startN
//	 * @param endMillis
//	 */
//	private static void runProfiler(String title, Figurable rundata, int startN, int endCount) {
//		ProfilerCount profiler = new ProfilerCount(title, rundata);
//		XYSeries series = profiler.countingLoop(startN, endCount);
//		profiler.plotResults(series);
//	}
//}