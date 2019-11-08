package advanced_class_05;

import java.util.Arrays;

public class Code_03_Min_Gold {

	public static int minGold1(int[] knights, int[] dragons) {
		Arrays.sort(knights);
		int res = 0;
		for (int i = 0; i < dragons.length; i++) {
			int cost = getMaxLeftmost(knights, dragons[i]);
			if (cost == Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			}
			res += cost;
		}
		return res;
	}

	public static int getMaxLeftmost(int[] sortedArr, int dragon) {
		int L = 0;
		int R = sortedArr.length - 1;
		int index = -1;
		while (L <= R) {
			int mid = (L + R) / 2;
			if (sortedArr[mid] < dragon) {
				L = mid + 1;
			} else {
				index = mid;
				R = mid - 1;
			}
		}
		return index == -1 ? Integer.MAX_VALUE : sortedArr[index];
	}

	// all values is positive.
	public static int minGold2(int[] knights, int[] dragons) {
		int sum = 0;
		for (int i = 0; i < knights.length; i++) {
			sum += knights[i];
		}
		int[] dp = new int[sum + 1];
		for (int i = 1; i <= sum; i++) {
			dp[i] = Integer.MAX_VALUE;
		}
		dp[knights[0]] = knights[0];
		// printArray(dp);
		for (int i = 1; i < knights.length; i++) {
			for (int j = 1; j <= sum; j++) {
				if (j - knights[i] >= 0
						&& dp[j - knights[i]] < Integer.MAX_VALUE) {
					dp[j] = Math.min(dp[j], dp[j - knights[i]] + knights[i]);
				}
			}
			// printArray(dp);
		}
		for (int i = dp.length - 2; i >= 0; i--) {
			dp[i] = Math.min(dp[i], dp[i + 1]);
		}
		// printArray(dp);
		int res = 0;
		for (int i = 0; i < dragons.length; i++) {
			int cost = getMaxLeftmost(dp, dragons[i]);
			if (cost == Integer.MAX_VALUE) {
				return Integer.MAX_VALUE;
			}
			res += cost;
		}
		return res;
	}

	public static void printArray(int[] dp) {
		for (int i = 0; i < dp.length; i++) {
			System.out.print((dp[i] == Integer.MAX_VALUE ? "X" : dp[i]) + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] knights1 = { 2, 10, 5 };
		int[] dragons1 = { 3, 8, 6 };
		System.out.println(minGold1(knights1, dragons1));

		int[] knights2 = { 2, 10, 5 };
		int[] dragons2 = { 3, 8, 6 };
		System.out.println(minGold2(knights2, dragons2));

	}

}
