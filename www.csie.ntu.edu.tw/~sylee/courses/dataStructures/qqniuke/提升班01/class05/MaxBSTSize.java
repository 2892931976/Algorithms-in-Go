package class05;

public class MaxBSTSize {

	public static class Node {
		public int value;
		Node left;
		Node right;
	}

	public static int maxBST(Node head) {
		return process(head).maxBSTSize;
	}
	
	
	public static class Info {
		public int maxBSTSize;
		public boolean isBST;
		public int min;
		public int max;

		public Info(int maxBST, boolean is, int mi, int ma) {
			maxBSTSize = maxBST;
			isBST = is;
			min = mi;
			max = ma;
		}
	}

	public static Info  process(Node x){
		if(x == null) { // base case
			return new Info(0, true, Integer.MAX_VALUE, Integer.MIN_VALUE);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		boolean isBST = false;
		int maxBSTSize = Math.max(leftInfo.maxBSTSize, rightInfo.maxBSTSize); // p1, p2
		if(leftInfo.isBST 
				&& rightInfo.isBST 
				&& leftInfo.max < x.value 
				&& rightInfo.min > x.value) {
			//p3
			maxBSTSize = leftInfo.maxBSTSize + 1 + rightInfo.maxBSTSize;
			isBST = true;
		}
		int min = Math.min(x.value, Math.min(leftInfo.min, rightInfo.min));
		int max = Math.max(x.value, Math.max(leftInfo.max, rightInfo.max));
		return new Info(maxBSTSize, isBST, min, max);
	}

}
