package class08;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Code03_PrintAllPermutations {

	public static ArrayList<String> Permutation(String str) {
		ArrayList<String> res = new ArrayList<>();
		if (str == null || str.length() == 0) {
			return res;
		}
		char[] chs = str.toCharArray();
		process(chs, 0, res);
		return res;
	}

	// str[i..]范围上，所有的字符，都可以在i位置上，后续都去尝试
	// str[0..i-1]范围上，是之前做的选择
	// 请把所有的字符串形成的全排列，加入到res里去
	public static void process(char[] str, int i, ArrayList<String> res) {
		if (i == str.length) {
			res.add(String.valueOf(str));
		}
		boolean[] visit = new boolean[26]; // visit[0 1 .. 25]
		for (int j = i; j < str.length; j++) {
			if (!visit[str[j] - 'a']) {
				visit[str[j] - 'a'] = true;
				swap(str, i, j);
				process(str, i + 1, res);
				swap(str, i, j);
			}
		}
	}

	public static void swap(char[] chs, int i, int j) {
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}

	public static List<String> getAllC(String s) {
		List<String> ans = new ArrayList<>();
		ArrayList<Character> set = new ArrayList<>();
		for (char cha : s.toCharArray()) {
			set.add(cha);
		}
		process(set, "", ans);
		return ans;
	}

	public static void process(ArrayList<Character> list, String path, List<String> ans) {
		if (list.isEmpty()) {
			ans.add(path);
			return;
		}
		HashSet<Character> picks = new HashSet<>();
		for (int index = 0; index < list.size(); index++) {
			if (!picks.contains(list.get(index))) {
				picks.add(list.get(index));
				String pick = path + list.get(index);
				ArrayList<Character> next = new ArrayList<>(list);
				next.remove(index);
				process(next, pick, ans);
			}
		}
	}

	public static void main(String[] args) {
		String s = "aac";
		List<String> ans = getAllC(s);
		for (String str : ans) {
			System.out.println(str);
		}
	}

}
