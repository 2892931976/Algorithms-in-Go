package class10;

public class Code05_ProbabilityXPowerK {

	public static double randXPower2() {
		return Math.max(Math.random(), Math.random());
	}

	public static double randXPowerK(int k) {
		if (k < 1) {
			return 0;
		}
		double res = -1;
		for (int i = 0; i != k; i++) {
			res = Math.max(res, Math.random());
		}
		return res;
	}

	
	public static double random() {
		return Math.max(Math.random(), Math.random());
	}
	
	public static void main(String[] args) {
		
		int testTime = 10000000;
		double x = 0.6;  // [0, 0.6)
		int count = 0;
		for(int i = 0; i < testTime;i++) {
			if( Math.max( Math.random(), Math.random() )< x) {
				count++;
			}
		}
		System.out.println((double)(count) / (double)(testTime));
		
		
		
		
		
		
//		double range = 0.8; // [0, 0.8)
//		int times = 5000000;
//		int count = 0;
//		for (int i = 0; i != times; i++) {
//			if (Math.max(Math.random(), Math.random()) < range) {
//				count++;
//			}
//		}
//		double p = (double) count / (double) times;
//		System.out.println("range [0," + range + "), probability: " + p);
	}
}
