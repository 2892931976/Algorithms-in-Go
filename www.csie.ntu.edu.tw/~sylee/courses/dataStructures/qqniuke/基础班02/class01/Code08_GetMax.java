package class01;

public class Code08_GetMax {

	public static int getMax(int[] arr) {
		if(arr == null || arr.length == 0) {
			//
		}
		return process(arr, 0, arr.length - 1);
	}

	// arr[L..R]范围上求最大值    		N
	public static int process(int[] arr, int L, int R) {
		if (L == R) { // arr[L..R]范围上只有一个数，直接返回，base case
			return arr[L];
		}
		// mid = (L+R) / 2
		// mid = L + (R-L)/2
		int mid = L + ((R - L) >> 1); // 中点
		int leftMax = process(arr, L, mid);
		int rightMax = process(arr, mid + 1, R);
		return Math.max(leftMax, rightMax);
	}


}
