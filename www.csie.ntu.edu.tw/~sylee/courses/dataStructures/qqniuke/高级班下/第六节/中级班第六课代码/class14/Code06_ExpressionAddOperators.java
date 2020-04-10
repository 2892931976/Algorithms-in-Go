package class14;

import java.util.LinkedList;
import java.util.List;

public class Code06_ExpressionAddOperators {

	public static List<String> addOperators(String num, int target) {
		List<String> ret = new LinkedList<>();
		if (num.length() == 0) {
			return ret;
		}
		char[] path = new char[num.length() * 2 - 1];
		char[] digits = num.toCharArray();
		long n = 0;
		for (int i = 0; i < digits.length; i++) {
			n = n * 10 + digits[i] - '0';
			path[i] = digits[i];
			dfs(ret, path, i + 1, 0, n, digits, i + 1, target);
			if (n == 0) {
				break;
			}
		}
		return ret;
	}

	public static void dfs(List<String> ret, char[] path, int len, long left, long cur, char[] digits, int pos,
			int target) {
		if (pos == digits.length) {
			if (left + cur == target) {
				ret.add(new String(path, 0, len));
			}
			return;
		}
		long n = 0;
		int j = len + 1;
		for (int i = pos; i < digits.length; i++) {
			n = n * 10 + digits[i] - '0';
			path[j++] = digits[i];
			path[len] = '+';
			dfs(ret, path, j, left + cur, n, digits, i + 1, target);
			path[len] = '-';
			dfs(ret, path, j, left + cur, -n, digits, i + 1, target);
			path[len] = '*';
			dfs(ret, path, j, left, cur * n, digits, i + 1, target);
			if (digits[pos] == '0') {
				break;
			}
		}
	}

}
