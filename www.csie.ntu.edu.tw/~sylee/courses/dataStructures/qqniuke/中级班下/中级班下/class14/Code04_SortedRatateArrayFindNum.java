package class14;

public class Code04_SortedRatateArrayFindNum {

	public static boolean isContains(int[] arr, int num) {
		int L = 0;
		int R = arr.length - 1;
		int M = 0;
		// 在L...R范围上，找num
		// 潜台词：L..R之外的范围上，一定没有num
		//  L......M......R
		while (L <= R) {
			M = (L + R) / 2;
			if (arr[M] == num) {
				return true;
			}
			// [M] != num
			if (arr[L] == arr[M] && arr[M] == arr[R]) {
				while (L != M && arr[L] == arr[M]) {
					L++;
				}
				// L == M  或者  L 在运动的过程中，找到了和arr[M]不一样的值
				if (L == M) { // 整个左侧的值，等于中点值，等于右侧值，而且不是num
					L = M + 1;
					continue;
				}
			}
			// [L] [M] [R]不都相等  [M] != num
			if (arr[L] != arr[M]) {
				if (arr[M] > arr[L]) { // L..M 无断点
					if (num >= arr[L] && num < arr[M]) {
						R = M - 1;
					} else {
						L = M + 1;
					}
				} else { // arr[M] < arr[L]   M..R无断点
					if (num > arr[M] && num <= arr[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				}
			} else { // arr[M] != arr[R]
				if (arr[M] < arr[R]) {
					if (num > arr[M] && num <= arr[R]) {
						L = M + 1;
					} else {
						R = M - 1;
					}
				} else {
					if (num >= arr[L] && num < arr[M]) {
						R = M - 1;
					} else {
						L = M + 1;
					}
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		int[] arr = { 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 1, 2, 3, 4, 5 };
		int num = 10;
		System.out.println(isContains(arr, num));

	}
}
