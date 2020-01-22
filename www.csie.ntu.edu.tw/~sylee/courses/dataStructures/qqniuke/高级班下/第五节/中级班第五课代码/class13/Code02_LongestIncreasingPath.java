package class13;

public class Code02_LongestIncreasingPath {

	public static int longestIncreasingPath(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int[][] h = new int[m.length][m[0].length];
		int max = 0;
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[0].length; j++) {
				max = Math.max(max, maxIncrease(m, h, i + 1, j, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, h, i, j + 1, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, h, i - 1, j, m[i][j]) + 1);
				max = Math.max(max, maxIncrease(m, h, i, j - 1, m[i][j]) + 1);
			}
		}
		return max;
	}

	public static int maxIncrease(int[][] m, int[][] h, int i, int j, int p) {
		if (i < 0 || i >= m.length || j < 0 || j >= m[0].length || m[i][j] >= p) {
			return 0;
		}
		if (h[i][j] == 0) {
			h[i][j] = maxIncrease(m, h, i + 1, j, m[i][j]) + 1;
			h[i][j] = Math.max(h[i][j], maxIncrease(m, h, i, j + 1, m[i][j]) + 1);
			h[i][j] = Math.max(h[i][j], maxIncrease(m, h, i - 1, j, m[i][j]) + 1);
			h[i][j] = Math.max(h[i][j], maxIncrease(m, h, i, j - 1, m[i][j]) + 1);
		}
		return h[i][j];
	}
}
