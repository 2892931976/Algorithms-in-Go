package class2;

import java.util.HashMap;

public class SameOneNumber {

	public static int maxLength(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null || arr1.length != arr2.length) {
			return 0;
		}
		for (int i = 0; i < arr1.length; i++) {
			arr1[i] += (arr2[i] == 1 ? -1 : 0);
		}
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		map.put(0, -1); // important
		int len = 0;
		int sum = 0;
		for (int i = 0; i < arr1.length; i++) {
			sum += arr1[i];
			if (map.containsKey(sum)) {
				len = Math.max(i - map.get(sum), len);
			}
			if (!map.containsKey(sum)) {
				map.put(sum, i);
			}
		}
		return len;
	}

	public static void main(String[] args) {

	}

}
