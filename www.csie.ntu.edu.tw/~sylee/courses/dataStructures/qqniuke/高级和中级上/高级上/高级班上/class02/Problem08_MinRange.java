package class02;

import java.util.Comparator;
import java.util.TreeSet;

public class Problem08_MinRange {

	public static class Node {
		public int value;
		public int index;
		public int arri;

		public Node(int val, int i, int kth) {
			value = val;
			index = i;
			arri = kth;
		}
	}

	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node arg0, Node arg1) {
			return arg0.value - arg1.value;
		}

	}

	public static int[] minRange(int[][] arrs) {
		TreeSet<Node> tree = new TreeSet<>(new NodeComparator());
		int range = Integer.MAX_VALUE;
		int[] res = new int[2];
		for (int i = 0; i < arrs.length; i++) {
			tree.add(new Node(arrs[i][0], 0, i));
		}
		while (true) {
			Node first = tree.pollFirst();
			Node last = tree.last();
			if (range > last.value - first.value) {
				range = last.value - first.value;
				res[0] = first.value;
				res[1] = last.value;
			}
			int next = first.index + 1;
			int arri = first.arri;
			if (next < arrs[arri].length) {
				tree.add(new Node(arrs[arri][next], next, arri));
			} else {
				break;
			}
		}
		return res;
	}

	public static void main(String[] args) {
		int[][] arrs = { { 1, 3, 5 }, { 4, 8 }, { 2, 5 } };
		int[] res = minRange(arrs);
		System.out.println("[" + res[0] + "," + res[1] + "]");
	}

}
