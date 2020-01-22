package class10;

import java.util.HashMap;
import java.util.Stack;

public class Code06_LargestComponentSizebyCommonFactor {

	public static int largestComponentSize(int[] arr) {
		UnionFindSet1 set = new UnionFindSet1(arr);
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < i; j++) {
				if (gcd(arr[j], arr[i]) != 1) {
					set.union(arr[j], arr[i]);
				}
			}
		}
		return set.maxSize();
	}

	public static int gcd(int m, int n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	public static class UnionFindSet1 {
		public HashMap<Integer, Integer> fatherMap;
		public HashMap<Integer, Integer> rankMap;

		public UnionFindSet1(int[] arr) {
			fatherMap = new HashMap<>();
			rankMap = new HashMap<>();
			for (int value : arr) {
				fatherMap.put(value, value);
				rankMap.put(value, 1);
			}
		}

		public int maxSize() {
			int ans = 0;
			for (int size : rankMap.values()) {
				ans = Math.max(ans, size);
			}
			return ans;
		}

		private int findHead(int element) {
			Stack<Integer> path = new Stack<>();
			while (element != fatherMap.get(element)) {
				path.push(element);
				element = fatherMap.get(element);
			}
			while (!path.isEmpty()) {
				fatherMap.put(path.pop(), element);
			}
			return element;
		}

		public void union(int a, int b) {
			int aF = findHead(a);
			int bF = findHead(b);
			if (aF != bF) {
				int big = rankMap.get(aF) >= rankMap.get(bF) ? aF : bF;
				int small = big == aF ? bF : aF;
				fatherMap.put(small, big);
				rankMap.put(big, rankMap.get(aF) + rankMap.get(bF));
				rankMap.remove(small);
			}

		}
	}

	public static int largestComponentSize2(int[] arr) {
		UnionFindSet2 unionFind = new UnionFindSet2(arr.length);
		HashMap<Integer, Integer> fatorsMap = new HashMap<>();
		for (int i = 0; i < arr.length; i++) {
			int num = arr[i];
			int limit = (int) Math.sqrt(num);
			for (int j = 1; j <= limit; j++) {
				if (arr[i] % j == 0) {
					if (j != 1) {
						if (!fatorsMap.containsKey(j)) {
							fatorsMap.put(j, i);
						} else {
							unionFind.union(fatorsMap.get(j), i);
						}
					}
					int other = arr[i] / j;
					if (other != 1) {
						if (!fatorsMap.containsKey(other)) {
							fatorsMap.put(other, i);
						} else {
							unionFind.union(fatorsMap.get(other), i);
						}
					}
				}
			}
		}
		return unionFind.maxSize();
	}

	public static class UnionFindSet2 {
		private int[] parents;
		private int[] sizes;

		public UnionFindSet2(int len) {
			parents = new int[len];
			sizes = new int[len];
			for (int i = 0; i < len; i++) {
				parents[i] = i;
				sizes[i] = 1;
			}
		}

		public int maxSize() {
			int ans = 0;
			for (int size : sizes) {
				ans = Math.max(ans, size);
			}
			return ans;
		}

		private int findHead(int element) {
			Stack<Integer> path = new Stack<>();
			while (element != parents[element]) {
				path.push(element);
				element = parents[element];
			}
			while (!path.isEmpty()) {
				parents[path.pop()] = element;
			}
			return element;
		}

		public void union(int a, int b) {
			int aF = findHead(a);
			int bF = findHead(b);
			if (aF != bF) {
				int big = sizes[aF] >= sizes[bF] ? aF : bF;
				int small = big == aF ? bF : aF;
				parents[small] = big;
				sizes[big] = sizes[aF] + sizes[bF];
			}
		}
	}

	public static void main(String[] args) {
		int[] test = { 2, 3, 6, 7, 4, 12, 21, 39 };
		System.out.println(largestComponentSize2(test));
	}

}
