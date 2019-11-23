package class07;

import java.util.ArrayList;
import java.util.HashSet;

public class Problem01_SplitArrayWithSameAverage {

	public static boolean splitArraySameAverage(int[] arr) {
		int n = arr.length;
		int limit = n / 2;
		int sum = 0;
		for (int i = 0; i < n; i++) {
			sum += arr[i];
		}
		boolean maybe = false;
		for (int i = 1; i <= limit; i++) {
			if ((sum * i) % n == 0) {
				maybe = true;
				break;
			}
		}
		if (!maybe) {
			return false;
		}
		ArrayList<HashSet<Integer>> dp = new ArrayList<>();
		for (int i = 0; i <= limit; i++) {
			dp.add(new HashSet<>());
		}
		dp.get(0).add(0);
		for (int i = 0; i < n; i++) {
			for (int j = limit; j >= 1; j--) {
				for (int leftUp : dp.get(j - 1)) {
					dp.get(j).add(leftUp + arr[i]);
				}
			}
		}
		for (int i = 1; i <= limit; i++) {
			if ((sum * i) % n == 0 && dp.get(i).contains((sum * i) / n)) {
				return true;
			}
		}
		return false;
	}

}
