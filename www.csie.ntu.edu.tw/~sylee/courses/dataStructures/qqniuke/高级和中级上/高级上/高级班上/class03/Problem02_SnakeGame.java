package class03;

public class Problem02_SnakeGame {

	public static int walk1(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				int[] ans = process(matrix, i, j);
				res = Math.max(res, Math.max(ans[0], ans[1]));
			}
		}
		return res;
	}

	// 从左侧到达(i,j)的旅程中
	// 0) 在没有使用过能力的情况下，返回路径最大和
	// 1) 在使用过能力的情况下，返回路径最大和
	public static int[] process(int[][] m, int i, int j) {
		if (j == 0) { // (i,j)就是最左侧的位置
			return new int[] { m[i][j], -m[i][j] };
		}
		// (i,j)的左侧有之前的路
		// 第一条路
		int[] preAns = process(m, i, j - 1);
		int preUnuse = preAns[0];
		int preUse = preAns[1];
		if (i - 1 >= 0) {
			preAns = process(m, i - 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		if (i + 1 < m.length) {
			preAns = process(m, i + 1, j - 1);
			preUnuse = Math.max(preUnuse, preAns[0]);
			preUse = Math.max(preUse, preAns[1]);
		}
		// preUnuse  之前旅程，没用过能力
		// preUse    之前旅程，已经使用过能力了
		int no = -1; // 之前没使用过能力，当前位置也不使用能力，的最优解
		int yes = -1; // 不管是之前使用能力，还是当前使用了能力，请保证能力只使用一次，最优解
		if (preUnuse >= 0) {
			no = m[i][j] + preUnuse;
			yes = -m[i][j] + preUnuse;
		}
		if (preUse >= 0) {
			yes = Math.max(yes, m[i][j] + preUse);
		}
		return new int[] { no, yes };
	}

	public static int walk2(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int[][][] dp = new int[matrix.length][matrix[0].length][2];
		for (int i = 0; i < dp.length; i++) {
			dp[i][0][0] = matrix[i][0];
			dp[i][0][1] = -matrix[i][0];
			max = Math.max(dp[i][0][0], dp[i][0][1]);
		}
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 1; j < matrix[0].length; j++) {
				int preUnuse = dp[i][j - 1][0];
				int preUse = dp[i][j - 1][1];
				if (i - 1 >= 0) {
					preUnuse = Math.max(preUnuse, dp[i - 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i - 1][j - 1][1]);
				}
				if (i + 1 < matrix.length) {
					preUnuse = Math.max(preUnuse, dp[i + 1][j - 1][0]);
					preUse = Math.max(preUse, dp[i + 1][j - 1][1]);
				}
				dp[i][j][0] = -1;
				dp[i][j][1] = -1;
				if (preUnuse >= 0) {
					dp[i][j][0] = matrix[i][j] + preUnuse;
					dp[i][j][1] = -matrix[i][j] + preUnuse;
				}
				if (preUse >= 0) {
					dp[i][j][1] = Math.max(dp[i][j][1], matrix[i][j] + preUse);
				}
				max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
			}
		}
		return max;
	}

	public static String getString(int num) {
		String str = String.valueOf(num);
		if (str.length() == 1) {
			return " " + str;
		}
		return str;
	}

	public static void main(String[] args) {
		int[][] matrix = { 
				{ -100, -4000, -10000 }, 
				{ -200, -2000, -100000 }, 
				{ -300, -1000, -60000 }, 
				{ -2000, -5000, -20000000 } };
		System.out.println(walk1(matrix));
		System.out.println(walk2(matrix));
	}

}
