package class04;

public class Problem01_MinBoat {

	// 请保证arr有序
	public static int minBoat(int[] arr, int limit) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int lessR = -1;
		for (int i = arr.length - 1; i >= 0; i--) {
			if (arr[i] <= (limit / 2)) {
				lessR = i;
				break;
			}
		}
		if (lessR == -1) {
			return arr.length;
		}
		int L = lessR;
		int R = lessR + 1;
		int lessUnused = 0;
		while (L >= 0) {
			int solved = 0;
			while (R < arr.length && arr[L] + arr[R] <= limit) {
				R++;
				solved++;
			}
			if (solved == 0) {
				lessUnused++;
				L--;
			} else {
				L = Math.max(-1, L - solved);
			}
		}
		int lessAll = lessR + 1;// 左半区总个数  <= limit /2 的区域
		int lessUsed = lessAll - lessUnused; // 画对号的量
		int moreUnsolved = arr.length - lessR - 1 - lessUsed; // > limit/2 区中，没搞定的数量
		return lessUsed + ((lessUnused + 1) >> 1) + moreUnsolved;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 4, 4, 4, 4, 5 };
		int weight = 6;
		System.out.println(minBoat(arr, weight));
	}

}
