package class05;

public class Code06_IsBalancedTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isFull(Node head) {
		ReturnData allInfo = p(head);
		return  (  1 << allInfo.height - 1) == allInfo.nums;
	}

	public static class ReturnData {
		public int height;
		public int nums;

		public ReturnData(int h, int n) {
			height = h;
			nums = n;
		}
	}


	public static ReturnData p(Node x) {
		if(x == null) {
			return new ReturnData(0,0);
		}
		ReturnData leftData = p(x.left);
		ReturnData rightData = p(x.right);
		
		int height= Math.max(leftData.height, rightData.height) + 1;
		
		int nums = leftData.nums + rightData.nums + 1;
		return new ReturnData(height,nums);
	}
	
	
	
	
	
	
	

	public static boolean isBalanced(Node head) {
		return process(head).isBalanced;
	}

	public static class ReturnType {
		public boolean isBalanced;
		public int height;

		public ReturnType(boolean isB, int hei) {
			isBalanced = isB;
			height = hei;
		}
	}

	public static ReturnType process(Node x) {
		if (x == null) {
			return new ReturnType(true, 0);
		}
		ReturnType leftData = process(x.left);
		ReturnType rightData = process(x.right);
		int height = Math.max(leftData.height, rightData.height) + 1;
		boolean isBalanced = leftData.isBalanced && rightData.isBalanced
				&& Math.abs(leftData.height - rightData.height) < 2;
		return new ReturnType(isBalanced, height);
	}
	
	
	
	
	
	
	public static class Info{
		public boolean isBST;
		public int max;
		public int min;
		
		public Info(boolean isb, int ma, int mi) {
			isBST= isb;
			max = ma;
			min = mi;
		}
	}
	
	
	
	
	public static Info func(Node x) {
		if(x == null) {
			return null;
		}
		Info leftInfo = func(x.left);
		Info rightInfo = func(x.right);
		// x   isBST  max min
		boolean isBST = true;
		if(leftInfo !=null &&  ( !leftInfo.isBST    ||   leftInfo.max >= x.value  )  ) {
			isBST = false;
		}
		if(rightInfo !=null && ( !rightInfo.isBST   || rightInfo.min <= x.value   )  ) {
			isBST = false;
		}
		int min = Integer.MAX_VALUE;
		int max = Integer.MIN_VALUE;
		min = x.value;
		max = x.value;
		if(leftInfo !=null) {
			min = Math.min(min, leftInfo.min);
			max = Math.max(max, leftInfo.max);
		}
		if(rightInfo !=null) {
			min = Math.min(min, rightInfo.min);
			max = Math.max(max, rightInfo.max);
		}
		return new Info(isBST, max, min);
	}
	
	
	
	
	
	
	
	
	
	

}
