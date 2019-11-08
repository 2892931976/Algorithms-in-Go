package advanced_class_07;

public class Code_01_Scramble_String {

	public static boolean isScramble1(String s1, String s2) {
		if (s1.length() != s2.length()) {
			return false;
		}
		if (s1.equals(s2)) {
			return true;
		}
		int N = s1.length();
		// return func1(s1.toCharArray(), s2.toCharArray(), 0, N - 1, 0, N - 1);
		return func2(s1.toCharArray(), s2.toCharArray(), 0, 0, N);
	}

	public static boolean func1(char[] chs1, char[] chs2, int L1, int R1,
			int L2, int R2) {
		if (L1 == R1) {
			return chs1[L1] == chs2[L2];
		}
		for (int i = 0; i < (R1 - L1); i++) {
			if ((func1(chs1, chs2, L1, L1 + i, L2, L2 + i) && func1(chs1, chs2,
					L1 + i + 1, R1, L2 + i + 1, R2))
					|| (func1(chs1, chs2, L1, L1 + i, R2 - i, R2) && func1(
							chs1, chs2, L1 + i + 1, R1, L2, R2 - i - 1))) {
				return true;
			}
		}
		return false;
	}

	public static boolean func2(char[] chs1, char[] chs2, int L1, int L2,
			int size) {
		if (size == 1) {
			return chs1[L1] == chs2[L2];
		}
		for (int part = 1; part < size; part++) {
			if ((func2(chs1, chs2, L1, L2, part) && func2(chs1, chs2,
					L1 + part, L2 + part, size - part))
					|| (func2(chs1, chs2, L1, L2 + size - part, part) && func2(
							chs1, chs2, L1 + part, L2, size - part))) {
				return true;
			}
		}
		return false;
	}

	public static boolean isScramble2(String s1, String s2) {
		if (s1.length() != s2.length()) {
			return false;
		}
		if (s1.equals(s2)) {
			return true;
		}
		int N = s1.length();
		char[] chs1 = s1.toCharArray();
		char[] chs2 = s2.toCharArray();
		boolean[][][] dp = new boolean[N][N][N + 1];
		for (int L1 = 0; L1 < N; L1++) {
			for (int L2 = 0; L2 < N; L2++) {
				dp[L1][L2][1] = chs1[L1] == chs2[L2];
			}
		}
		for (int size = 2; size <= N; size++) {
			for (int L1 = 0; L1 <= N - size; L1++) {
				for (int L2 = 0; L2 <= N - size; L2++) {
					for (int p = 1; p < size; p++) {
						if ((dp[L1][L2][p] && dp[L1 + p][L2 + p][size - p])
								|| (dp[L1][L2 + size - p][p] && dp[L1 + p][L2][size
										- p])) {
							dp[L1][L2][size] = true;
							break;
						}
					}
				}
			}
		}
		return dp[0][0][N];
	}

	public static void main(String[] args) {
		String test1 = "bcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcdebcde";
		String test2 = "cebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebdcebd";
		// System.out.println(isScramble1(test1, test2));
		System.out.println(isScramble2(test1, test2));
	}

}
