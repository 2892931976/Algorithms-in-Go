package class07;

public class Problem05_StrangePointer {

	public static int strangePrinter(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int N = str.length;
		int[][] dp = new int[N][N];
		// L        R
		dp[N - 1][N - 1] = 1;
		for (int i = 0; i < N - 1; i++) {
			dp[i][i] = 1;
			dp[i][i + 1] = str[i] == str[i + 1] ? 1 : 2;
		}
		for (int i = N - 2; i >= 0; i--) { // 从下往上行，填
			for (int j = i + 2; j < N; j++) { //(i,i) (i, i+1)
				// i..j
				dp[i][j] = j - i + 1;
				for (int k = i + 1; k <= j; k++) { // 右部分的第一个位置
					dp[i][j] = Math.min(dp[i][j],
							dp[i][k - 1] + dp[k][j] - (str[i] == str[k] ? 1 : 0));
				}
			}
		}
		return dp[0][N - 1];
	}

}
