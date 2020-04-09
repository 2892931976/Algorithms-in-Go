package class16;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Code02_WordBreak {

	public static int ways1(String str, String[] arr) {
		HashSet<String> set = new HashSet<>();
		for (String word : arr) {
			set.add(word);
		}
		return f1(str, 0, set);
	}

	// set是单词表
	// str[index...]被set单词表分解的方法数返回
	public static int f1(String str, int index, HashSet<String> set) {
		if (index == str.length()) {
			return 1;
		}
		int ways = 0;
		// 第一部分是str[index.....end]
		for (int end = index; end < str.length(); end++) {
			String first = str.substring(index, end + 1);
			if (set.contains(first)) {
				ways += f1(str, end + 1, set);
			}
		}
		return ways;
	}

	//  str[L..R]  是不是set里面的字符串
	
	
	
	public static class Node{
		public boolean end;
		public Node[] nexts;
		
		public Node() {
			end = false;
			nexts = new Node[26];
		}
	}
	
	public static int ways2(String str, String[] arr) {
		Node head = new Node();
		// 遍历arr所有的字符，根据前缀树的方式，把字符挂在总的头部head以下
		return f2(str.toCharArray(), 0, head);
	}

	// set是单词表
	// str[index...]被set单词表分解的方法数返回
	public static int f2(char[] str, int index, Node head) {
		if (index == str.length) {
			return 1;
		}
		int ways = 0;
		Node cur = head;
		// 第一部分是str[index.....end]
		for (int end = index; end < str.length; end++) {
			char cha = str[end];
			if(cur.nexts[cha-'0']==null) {
				break;
			}
			cur = cur.nexts[cha-'0'];
			if(cur.end) {
				ways += f2(str, end +1, head);
			}
		}
		return ways;
	}
	
	
	
	
	
	public static List<String> wordBreak1(String s, List<String> wordDict) {
		HashSet<String> set = new HashSet<>(wordDict);
		LinkedList<String> path = new LinkedList<String>();
		ArrayList<String> res = new ArrayList<String>();
		process(s, 0, set, path, res);
		return res;
	}

	// 从s的i位置出发，s[i...]这一段字符串，被字典所有组成的方案返回
	// 沿途做的决定放在path里
	// 如果找到某个组成方式，把它加入到结果序列中
	public static void process(String s, int i, HashSet<String> set, LinkedList<String> path, ArrayList<String> res) {
		if (i == s.length()) {
			res.add(getString(path));
		} else {
			// s[i..k]
			for (int k = i; k < s.length(); k++) {
				if (set.contains(s.substring(i, k + 1))) { // [i,k+1)
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

	// s 剩余的字符串
	// set 单词表
	// map 有 s的记录，
	// 剩余的字符串有多少种拼接的方案，记录在map中
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

	public static void main(String[] args) {

		String s = "ababbcc";
		List<String> wordDict = Arrays.asList("a", "b", "c", "ab");
		List<String> res = wordBreak1(s, wordDict);

		for (String obj : res) {
			System.out.println(obj);
		}

	}

}
