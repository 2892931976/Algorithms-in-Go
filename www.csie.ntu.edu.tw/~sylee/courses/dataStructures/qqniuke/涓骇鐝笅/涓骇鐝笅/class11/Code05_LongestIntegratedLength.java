package class11;

import java.util.Arrays;
import java.util.HashSet;

public class Code05_LongestIntegratedLength {

	public static int getLIL1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int len = 0;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				if (isIntegrated(arr, i, j)) {
					len = Math.max(len, j - i + 1);
				}
			}
		}
		return len;
	}

	public static boolean isIntegrated(int[] arr, int left, int right) {
		int[] newArr = Arrays.copyOfRange(arr, left, right + 1); // O(N)
		Arrays.sort(newArr); // O(N*logN)
		for (int i = 1; i < newArr.length; i++) {
			if (newArr[i - 1] != newArr[i] - 1) {
				return false;
			}
		}
		return true;
	}

	public static int getLIL2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int len = 0;
		int max = 0;
		int min = 0;
		HashSet<Integer> set = new HashSet<Integer>();
		for (int L = 0; L < arr.length; L++) { // L 左边界
			set.clear();
			max = Integer.MIN_VALUE;
			min = Integer.MAX_VALUE;
			for (int R = L; R < arr.length; R++) { //  R 右边界
				//  arr[L..R]这个子数组在验证
				
				if (set.contains(arr[R])) {
					// arr[L..R]上开始 出现重复值了，arr[L..R往后]不需要验证了，
					// 一定不是可整合的
					break;
				}
				// arr[L..R]上无重复值
				set.add(arr[R]);
				max = Math.max(max, arr[R]);
				min = Math.min(min, arr[R]);
				if (max - min == R - L) { // L..R 是可整合的
					len = Math.max(len, R - L + 1);
				}
			}
		}
		return len;
	}

	public static void main(String[] args) {
		int[] arr = { 5, 5, 3, 2, 6, 4, 3 };
		System.out.println(getLIL1(arr));
		System.out.println(getLIL2(arr));

	}

}
