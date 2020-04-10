package class2;

public class TwoStringsDistance {

	public static long gapNum(String str1, String str2) {
		if (str1 == null || str2 == null || str1.equals(str2)) {
			return 0;
		}
		char[] s1 = str1.toCharArray();
		char[] s2 = str2.toCharArray();
		int len = Math.max(s1.length, s2.length);
		return pos(s2, len) - pos(s1, len) - 1;
	}

	public static long pos(char[] s, int len) {
		long res = 0;
		long pre = 0;
		for (int i = 0; i < s.length; i++) {
			pre = pre * 26 + s[i] - 'a';
			res += pre + 1;
		}
		for (int i = s.length; i < len; i++) {
			pre *= 26;
			res += pre;
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println(gapNum("aa", "c"));
	}

}
