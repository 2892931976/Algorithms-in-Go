package advanced_class_05;

public class Code_02_Max_Damage {

	public static int maxDamage(int[] arr, int threshold) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] dp = new int[arr.length][threshold + 1];
		if (arr[0] <= threshold) {
			dp[0][arr[0]] = arr[0];
		}
		for (int i = 1; i < arr.length; i++) {
			for (int j = 0; j <= threshold; j++) {
				int no = dp[i - 1][j];
				int only = j - arr[i] == 0 ? arr[i] : 0;
				int part = j - arr[i] > 0 ? dp[i - 1][j - arr[i]] * arr[i] : 0;
				dp[i][j] = Math.max(no, Math.max(only, part));
			}
		}
		// printMatrix(dp); // 可以打印dp看看
		return dp[dp.length - 1][dp[0].length - 1];
	}

	public static void printMatrix(int[][] dp) {
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 3, 4, 5 };
		int threshold = 10;
		System.out.println(maxDamage(arr, threshold));
	}

}
