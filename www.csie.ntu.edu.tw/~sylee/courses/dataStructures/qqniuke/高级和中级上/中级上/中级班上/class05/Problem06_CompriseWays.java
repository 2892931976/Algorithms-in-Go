package class05;

import java.util.HashMap;

public class Problem06_CompriseWays {

	public static int hows(int[] arr, int bag) {
		return process1(arr, 0, bag);
	}

	// 剩余空间为rest的情况下，
	// 可以自由使用arr[index...]所有零食的情况下，
	// 返回有多少种方法
	public static int process1(int[] arr, int index, int rest) {
		if (rest < 0) { // 剪枝
			return -1;
		}
		// rest >= 0
		if (index == arr.length) { // 之前做的决定是有效的
			return 1;
		}
		int ways = 0;
		int next = process1(arr, index + 1, rest);
		ways += next == -1 ? 0 : next;
		next = process1(arr, index + 1, rest - arr[index]);
		ways += next == -1 ? 0 : next;
		return ways;
	}
	
	
	public static int hows2(int[] arr, int bag) {
		HashMap<String, Integer> map = new HashMap<>();
		return process2(arr, 0, bag, map);
	}
	
	public static int process2(int[] arr, int index, int rest,
			HashMap<String, Integer> map) {
		String key = String.valueOf(index +"_" + rest);
		if(map.containsKey(key)) {
			return map.get(key);
		}
		if (rest < 0) { // 剪枝
			map.put(key, -1);
			return -1;
		}
		// rest >= 0
		if (index == arr.length) { // 之前做的决定是有效的
			map.put(key, 1);
			return 1;
		}
		int ways = 0;
		int next = process1(arr, index + 1, rest);
		ways += next == -1 ? 0 : next;
		next = process1(arr, index + 1, rest - arr[index]);
		ways += next == -1 ? 0 : next;
		map.put(key, ways);
		return ways;
	}
	
	
	
	

	// 请保证arr里面都是正数, w也是正数
	public static int ways(int[] arr, int w) {
		if (arr == null || arr.length == 0 || w < 0) {
			return 0;
		}
		int[][] dp = new int[arr.length][w + 1];
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		for (int j = 1; j <= w; j++) {
			dp[0][j] = j >= arr[0] ? 2 : 1;
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= w; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - arr[i] >= 0) {
					dp[i][j] += dp[i - 1][j - arr[i]];
				}
			}
		}
		return dp[arr.length - 1][w];
	}

	// arr[0..i]自由使用的情况下，容量不超过limit的方法数有多少种
	// public static int process(int[] arr, int i, int limit) {
	// if(limit == 0) {
	// return 1;
	// }
	//
	// int ways = process(arr, i-1,limit); // 我决定不要i位置的商品
	// ways += process(arr, i-1,limit - arr[i]); // 我决定要i位置的商品
	//
	// }

	public static void main(String[] args) {
		int[] arr = { 4, 3, 2, 9 };
		int w = 8;
		System.out.println(hows(arr, w));
		System.out.println(ways(arr, w));
	}

}
