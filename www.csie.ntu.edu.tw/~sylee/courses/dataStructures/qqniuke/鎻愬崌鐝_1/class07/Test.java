package class07;

public class Test {

	public static int minPath(int[][] m) {
		return f(m, 0, 0);
	}

	// 递归过程含义：
	// 从row行col列出发，到达右下角，所有可能路径中，最小的路径和返回
	public static int f(int[][] m, int row, int col) {
		if (row == m.length - 1 && col == m[0].length - 1) {
			return m[row][col];
		}
		if (col == m[0].length - 1) {
			int ans = m[row][col] + f(m, row + 1, col);
			return ans;
		}
		if (row == m.length - 1) {
			int ans = m[row][col] + f(m, row, col + 1);
			return ans;
		}
		int p1 = m[row][col] + f(m, row, col + 1);
		int p2 = m[row][col] + f(m, row + 1, col);
		int ans = Math.min(p1, p2);
		return ans;
	}
	
	public static int minPath2(int[][] m) {
		int[][] ansMap = new int[m.length][m[0].length];
		for(int i = 0 ; i < ansMap.length;i++) {
			for(int j = 0 ; j < ansMap[0].length;j++) {
				ansMap[i][j]= -1;
			}
		}
		return f(m, 0, 0, ansMap);
	}

	// 递归过程含义：
	// 从row行col列出发，到达右下角，所有可能路径中，最小的路径和返回
	public static int f(int[][] m, int row, int col, int[][] ansMap) {
		if(ansMap[row][col] != -1) {
			return ansMap[row][col];
		}
		int ans = 0;
		if (row == m.length - 1 && col == m[0].length - 1) {
			ans = m[row][col];
			ansMap[row][col] = ans;
			return ans;
		}
		if (col == m[0].length - 1) {
			 ans = m[row][col] + f(m, row + 1, col);
			 ansMap[row][col] = ans;
			return ans;
		}
		if (row == m.length - 1) {
			 ans = m[row][col] + f(m, row, col + 1);
			 ansMap[row][col] = ans;
			return ans;
		}
		int p1 = m[row][col] + f(m, row, col + 1);
		int p2 = m[row][col] + f(m, row + 1, col);
		 ans = Math.min(p1, p2);
		 ansMap[row][col] = ans;
		return ans;
	}
	
	
	
	

	public static int dpMinPath(int[][] m) {
		int[][] dp = new int[m.length][m[0].length];
		int N = m.length;
		int M = m[0].length;
		dp[N - 1][M - 1] = m[N - 1][M - 1];
		for (int row = N - 2; row >= 0; row--) {
			dp[row][M - 1] = m[row][M - 1] + dp[row + 1][M - 1];
		}
		for (int col = M - 2; col >= 0; col--) {
			dp[N - 1][col] = m[N - 1][col] + dp[N - 1][col + 1];
		}
		for (int row = N - 2; row >= 0; row--) {
			for (int col = M - 2; col >= 0; col--) {
				int p1 = m[row][col] + dp[row][col + 1];
				int p2 = m[row][col] + dp[row + 1][col];
				dp[row][col] = Math.min(p1, p2);
			}
		}
		return dp[0][0];
	}

}
