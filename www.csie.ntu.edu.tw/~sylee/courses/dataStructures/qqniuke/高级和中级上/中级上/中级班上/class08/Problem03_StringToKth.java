package class08;

public class Problem03_StringToKth {

	// 必须以i号字符开头、总长度为len的子序列有多少个
	public static int g(int i, int len) {
		int sum = 0;
		if (len == 1) {
			return 1;
		}
		for (int j = i + 1; j <= 26; j++) {
			sum += g(j, len - 1);
		}
		return sum;
	}

	// 长度为len的字符串有多少个
	public static int f(int len) {
		int sum = 0;
		for (int i = 1; i <= 26; i++) {
			sum += g(i, len);
		}
		return sum;
	}

	public static int kth(String s) {
		char[] str = s.toCharArray();
		int sum = 0;
		int len = str.length;
		for (int i = 1; i < len; i++) {
			sum += f(i);
		}
		int first = str[0] - 'a' + 1; // 第一个字符
		for (int i = 1; i < first; i++) {
			sum += g(i, len);
		}
		int pre = first;
		for (int i = 1; i < len; i++) {
			int cur = str[i] - 'a' + 1;
			for (int j = pre + 1; j < cur; j++) {
				sum += g(j, len - i);
			}
			pre = cur;
		}
		return sum + 1;
	}

	public static void main(String[] args) {
		String test = "bc";
		System.out.println(kth(test));
	}

}
