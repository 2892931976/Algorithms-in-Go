package class16;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Code02_WordBreak {

	public static List<String> wordBreak1(String s, List<String> wordDict) {
		HashSet<String> set = new HashSet<>(wordDict);
		LinkedList<String> path = new LinkedList<String>();
		ArrayList<String> res = new ArrayList<String>();
		process(s, 0, set, path, res);
		return res;
	}

	public static void process(String s, int i, HashSet<String> set, LinkedList<String> path, ArrayList<String> res) {
		if (i == s.length()) {
			res.add(getString(path));
		} else {
			for (int k = i; k < s.length(); k++) {
				if (set.contains(s.substring(i, k + 1))) {
					path.add(s.substring(i, k + 1));
					process(s, k + 1, set, path, res);
					path.pollLast();
				}
			}
		}
	}

	public static String getString(LinkedList<String> path) {
		StringBuilder res = new StringBuilder();
		for (String str : path) {
			res.append(str + " ");
		}
		return res.substring(0, res.length() - 1);
	}

	public static List<String> wordBreak2(String s, List<String> words) {
		HashSet<String> set = new HashSet<>(words);
		return process(s, set, new HashMap<String, LinkedList<String>>());
	}

	public static List<String> process(String s, Set<String> set, HashMap<String, LinkedList<String>> map) {
		if (map.containsKey(s)) {
			return map.get(s);
		}
		LinkedList<String> res = new LinkedList<String>();
		if (s.length() == 0) {
			res.add("");
		} else {
			for (String word : set) {
				if (s.startsWith(word)) {
					List<String> sublist = process(s.substring(word.length()), set, map);
					for (String sub : sublist) {
						res.add(word + (sub.isEmpty() ? "" : " ") + sub);
					}
				}
			}
			map.put(s, res);
		}
		return res;
	}

}
