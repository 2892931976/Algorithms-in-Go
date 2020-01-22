package class10;

import java.util.Arrays;

public class Code04_DistinctSubseq {

	public static int distinctSubseq1(String s) {
		char[] str = s.toCharArray();
		int result = 0;
		int[] dp = new int[str.length];
		Arrays.fill(dp, 1);
		for (int i = 0; i < str.length; i++) {
			for (int j = 0; j < i; j++) {
				if (str[j] != str[i]) {
					dp[i] += dp[j];
				}
			}
			result += dp[i];
		}
		return result;
	}

	public static int distinctSubseq2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int[] dp = new int[str.length];
		Arrays.fill(dp, 1);
		int[] count = new int[26];
		int result = 0;
		for (int i = 0; i < str.length; i++) {
			int index = str[i] - 'a';
			dp[i] += result - count[index];
			result += dp[i];
			count[index] = count[index] + dp[i];
		}
		return result;
	}

	public static int distinctSubseq3(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		int[] count = new int[26];
		int result = 0;
		for (int i = 0; i < str.length; i++) {
			int index = str[i] - 'a';
			int pre = result - count[index] + 1;
			count[index] += pre;
			result += pre;
		}
		return result;
	}

	public static String random(int len, int varible) {
		int size = (int) (Math.random() * len) + 1;
		char[] str = new char[size];
		for (int i = 0; i < size; i++) {
			str[i] = (char) ((int) (Math.random() * varible) + 'a');
		}
		return String.valueOf(str);
	}

	public static void main(String[] args) {
		int len = 10;
		int varible = 5;
		for (int i = 0; i < 1000000; i++) {
			String test = random(len, varible);
			if (distinctSubseq1(test) != distinctSubseq2(test) || distinctSubseq2(test) != distinctSubseq3(test)) {
				System.out.println("fuck");
			}
		}

	}

}
