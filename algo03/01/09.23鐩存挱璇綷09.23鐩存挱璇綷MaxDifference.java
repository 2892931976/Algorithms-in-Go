package class1;

public class MaxDifference {

	public static int getMax1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int res = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			for (int j = i + 1; j < arr.length; j++) {
				res = Math.max(res, arr[j] - arr[i]);
			}
		}
		return res;
	}

	public static int getMax2(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int min = Integer.MAX_VALUE;
		int res = 0;
		for (int i = 0; i < arr.length; i++) {
			min = Math.min(min, arr[i]);
			res = Math.max(res, arr[i] - min);
		}
		return res;
	}

	// for test
	public static int[] getRandomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 100);
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] arr = getRandomArray(100);
		int times = 100000;
		for (int i = 0; i < times; i++) {
			if (getMax1(arr) != getMax2(arr)) {
				System.out.println("what a fucking day!!!");
				break;
			}
		}
	}
}
