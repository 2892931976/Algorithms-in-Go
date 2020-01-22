package class13;

public class Code01_SortedRatateArrayFindMin {

	public static int getMin(int[] arr) {
		int L = 0;
		int R = arr.length - 1;
		int mid = 0;
		// 潜台词 ：  L..R 有全局min
		while (L < R) {
			if (L == R - 1) {
				break;
			}
			// arr[L] < arr[R]   L..R范围上， 没有旋转
			if (arr[L] < arr[R]) {
				return arr[L];
			}
			// [L] >= [R]
			mid = (L + R) / 2;
			// arr[L] > arr[mid]
			if (arr[L] > arr[mid]) {
				R = mid;
				continue;
			}
			// [L] >= [R]  &&  [Mid] >= [R]
			if (arr[mid] > arr[R]) {
				L = mid;
				continue;
			}
			// [L] >= [R]  &&  [mid] >= [R] && [mid] <= [R]
			while (L < mid) {
				if (arr[L] == arr[mid]) {
					L++;
				} else if (arr[L] < arr[mid]) {
					return arr[L];
				} else { // arr[low] > arr[mid]
					R = mid;
					break;
				}
			}
		}
		return Math.min(arr[L], arr[R]);
	}

	public static void main(String[] args) {
		int[] test = { 4, 5, 5, 5, 1, 2, 3 };
		System.out.println(getMin(test));

	}

}
