package class11;

import java.util.ArrayList;
import java.util.List;

public class Code02_T1SubtreeEqualsT2 {

	public static class Node {
		public int val;
		public Node left;
		public Node right;

		public Node(int data) {
			this.val = data;
		}
	}

	public static boolean isSubtree(Node t1, Node t2) {
		List<String> res1 = new ArrayList<>();
		List<String> res2 = new ArrayList<>();
		serialByPre(t1, res1);
		serialByPre(t2, res2);
		return getIndexOf(res1, res2) != -1;
	}

	public static void serialByPre(Node head, List<String> res) {
		if (head == null) {
			res.add("#");
		} else {
			res.add(String.valueOf(head.val));
			serialByPre(head.left, res);
			serialByPre(head.right, res);
		}
	}

	// KMP
	public static int getIndexOf(List<String> str1, List<String> str2) {
		if (str1 == null || str2 == null || str2.size() < 1 || str1.size() < str2.size()) {
			return -1;
		}
		int[] nextArr = getNextArray(str2);
		int index = 0;
		int mi = 0;
		while (index < str1.size() && mi < str2.size()) {
			if (str1.get(index).equals(str2.get(mi))) {
				index++;
				mi++;
			} else if (nextArr[mi] == -1) {
				index++;
			} else {
				mi = nextArr[mi];
			}
		}
		return mi == str2.size() ? index - mi : -1;
	}

	public static int[] getNextArray(List<String> str2) {
		if (str2.size() == 1) {
			return new int[] { -1 };
		}
		int[] nextArr = new int[str2.size()];
		nextArr[0] = -1;
		nextArr[1] = 0;
		int pos = 2;
		int cn = 0;
		while (pos < nextArr.length) {
			if (str2.get(pos - 1).equals(str2.get(cn))) {
				nextArr[pos++] = ++cn;
			} else if (cn > 0) {
				cn = nextArr[cn];
			} else {
				nextArr[pos++] = 0;
			}
		}
		return nextArr;
	}

	public static void main(String[] args) {
		Node t1 = new Node(1);
		t1.left = new Node(2);
		t1.right = new Node(3);
		t1.left.left = new Node(4);
		t1.left.right = new Node(5);
		t1.right.left = new Node(6);
		t1.right.right = new Node(7);
		t1.left.left.right = new Node(8);
		t1.left.right.left = new Node(9);

		Node t2 = new Node(2);
		t2.left = new Node(4);
		t2.left.right = new Node(8);
		t2.right = new Node(5);
		t2.right.left = new Node(9);

		System.out.println(isSubtree(t1, t2));

	}

}
