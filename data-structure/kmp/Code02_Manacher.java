package com.chj.kmp;

public class Code02_Manacher {

	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	public static int maxLcpsLength(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = manacherString(s); // 1221 ->  #1#2#2#1#
		int[] pArr = new int[str.length]; // 回文半径数组
		int C = -1; // 中心
		int R = -1; // 回文右边界的再往右一个位置    最右的有效区是R-1位置
		int max = Integer.MIN_VALUE; // 扩出来的最大值
		for (int i = 0; i != str.length; i++) { // 每一个位置都求回文半径
			// i至少的回文区域，先给pArr[i]
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			while (i + pArr[i] < str.length && i - pArr[i] > -1) {
				if (str[i + pArr[i]] == str[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			max = Math.max(max, pArr[i]);
		}
		return max - 1;
	}

	public static void main(String[] args) {
		String str1 = "abc1234321ab";
		System.out.println(maxLcpsLength(str1));
	}

}
