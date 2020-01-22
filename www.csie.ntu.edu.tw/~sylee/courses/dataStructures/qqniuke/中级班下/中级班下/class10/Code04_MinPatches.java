package class10;

public class Code04_MinPatches {

	public static int minPatches(int[] arr, int range) {
		int patches = 0; // 缺多少个数字
		long touch = 0; // 已经完成了1 ~ touch的目标
		for (int i = 0; i != arr.length; i++) {
			// 1~touch
			// arr[i]
			// 1~arr[i]-1
			while (arr[i] > touch + 1) { // arr[i]   1 ~ arr[i]-1
				touch += touch + 1; // touch + 1 是缺的数字
				patches++;
				if (touch >= range) {
					return patches;
				}
			}
			touch += arr[i];
			if (touch >= range) {
				return patches;
			}
		}
		while (range >= touch + 1) {
			touch += touch + 1;
			patches++;
		}
		return patches;
	}

	public static void main(String[] args) {
		int[] test = { 1, 2, 31, 33 };
		int n = 2147483647;
		System.out.println(minPatches(test, n));

	}

}
