package class07;

public class Code02_CoinsMin {

	public static int minCoins1(int[] arr, int aim) {
		return process1(arr, 0, aim);
	}

	// arr[index...] 组成出rest这么多钱，最少的硬币数量返回
	public static int process1(int[] arr, int index, int rest) {
		if (rest < 0) {
			return -1;
		}
		if (rest == 0) {
			return 0;
		}
		// rest > 0
		if (index == arr.length) {
			return -1;
		}
		// rest > 0 并且 也有硬币
		int p1 = process1(arr, index + 1, rest);
		int p2Next = process1(arr, index + 1, rest - arr[index]);
		if (p1 == -1 && p2Next == -1) {
			return -1;
		} else {
			if (p1 == -1) {
				return p2Next + 1;
			}
			if (p2Next == -1) {
				return p1;
			}
			return Math.min(p1, p2Next + 1);
		}
	}

	public static int minCoins2(int[] arr, int aim) {
		int[][] dp = new int[arr.length + 1][aim + 1];
		for (int i = 0; i <= arr.length; i++) {
			for (int j = 0; j <= aim; j++) {
				dp[i][j] = -2;
			}
		}

		return process2(arr, 0, aim, dp);
	}

	// arr[index...] 组成出rest这么多钱，最少的硬币数量返回
	public static int process2(int[] arr, int index, int rest, int[][] dp) {
		if (rest < 0) {
			return -1;
		}
		if (dp[index][rest] != -2) {
			return dp[index][rest];
		}
		if (rest == 0) {
			dp[index][rest] = 0;
		} else if (index == arr.length) {
			dp[index][rest] = -1;
		} else {
			int p1 = process2(arr, index + 1, rest, dp);
			int p2Next = process2(arr, index + 1, rest - arr[index], dp);
			if (p1 == -1 && p2Next == -1) {
				dp[index][rest] = -1;
			} else {
				if (p1 == -1) {
					dp[index][rest] = p2Next + 1;
				} else if (p2Next == -1) {
					dp[index][rest] = p1;
				} else {
					dp[index][rest] = Math.min(p1, p2Next + 1);
				}
			}

		}
		return dp[index][rest];
	}

	public static int minCoins3(int[] arr, int aim) {
		int N = arr.length;
		int[][] dp = new int[N + 1][aim + 1];
		for (int row = 0; row <= N; row++) {
			dp[row][0] = 0;
		}
		for (int col = 1; col <= aim; col++) {
			dp[N][col] = -1;
		}
		for (int index = N - 1; index >= 0; index--) {
			for (int rest = 1; rest <= aim; rest++) {
				int p1 = dp[index + 1][rest];
				int p2Next = -1;
				if (rest - arr[index] >= 0) {
					p2Next = dp[index + 1][rest - arr[index]];
				}
				if (p1 == -1 && p2Next == -1) {
					dp[index][rest] = -1;
				} else {
					if (p1 == -1) {
						dp[index][rest] = p2Next + 1;
					} else if (p2Next == -1) {
						dp[index][rest] = p1;
					} else {
						dp[index][rest] = Math.min(p1, p2Next + 1);
					}
				}
			}
		}
		return dp[0][aim];
	}

	// for test
	public static int[] generateRandomArray(int len, int max) {
		int[] arr = new int[(int) (Math.random() * len) + 1];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * max) + 1;
		}
		return arr;
	}

	public static void main(String[] args) {
		int len = 10;
		int max = 10;
		int testTime = 10000;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(len, max);
			int aim = (int) (Math.random() * 3 * max) + max;
			if (minCoins1(arr, aim) != minCoins2(arr, aim)
					&& minCoins2(arr, aim) != minCoins3(arr, aim)) {
				System.out.println("ooops!");
				break;
			}
		}
	}
}
