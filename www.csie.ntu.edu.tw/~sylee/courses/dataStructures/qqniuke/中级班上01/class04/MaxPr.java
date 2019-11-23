package class04;

public class MaxPr {

	public static double getMax(double[] arr) {
		if(arr == null || arr.length ==0) {
			return 0D;
		}
		double max = arr[0]; // i-1结尾情况下的最大累乘积
		double min = arr[0];// i-1结尾情况下的最小累乘积
		double ans = arr[0];
		for(int i = 1;i < arr.length;i++) {
			double p1 =  arr[i];
			double p2 = max * arr[i];
			double p3 = min * arr[i];
			max = Math.max(Math.max(p1, p2),p3);
			min = Math.min(Math.min(p1, p2),p3);
			ans = Math.max(ans, max);
		}
		return ans;
	}
	
}
