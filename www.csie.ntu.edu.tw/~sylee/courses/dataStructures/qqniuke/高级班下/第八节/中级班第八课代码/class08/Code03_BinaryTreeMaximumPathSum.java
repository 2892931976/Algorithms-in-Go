package class16;

public class Code03_BinaryTreeMaximumPathSum {

	public class Node {
		int value;
		public Node left;
		public Node right;
	}

	public static int maxPathSum(Node head) {
		if (head == null) {
			return 0;
		}
		ReturnType allData = process(head);
		return allData.maxValue < 0 ? allData.maxValue : allData.maxSumAll;
	}

	public static class ReturnType {
		public int maxSumAll;
		public int maxSumHead;
		public int maxValue;

		public ReturnType(int all, int fromHead, int max) {
			maxSumAll = all;
			maxSumHead = fromHead;
			maxValue = max;
		}
	}

	public static ReturnType process(Node x) {
		if (x == null) {
			return new ReturnType(0, 0, Integer.MIN_VALUE);
		}
		ReturnType leftData = process(x.left);
		ReturnType rightData = process(x.right);
		int maxValue = Math.max(x.value, Math.max(leftData.maxValue, rightData.maxValue));
		int maxSumHead = Math.max(0, Math.max(leftData.maxSumHead, rightData.maxSumHead)) + x.value;
		int maxSumAll = Math.max(leftData.maxSumAll, rightData.maxSumAll);
		maxSumAll = Math.max(maxSumAll, Math.max(0, leftData.maxSumHead) + Math.max(0, rightData.maxSumHead) + x.value);
		return new ReturnType(maxSumAll, maxSumHead, maxValue);
	}

}
