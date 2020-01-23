package class03;

public class Problem01_MaxPathSumInBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int val) {
			value = val;
		}
	}
	
	
	public static int maxPathS(Node head) {
		if(head == null) {
			return 0;
		}
		return func(head).maxPathSum;
	}
	
	public static class Info{
		public int maxPathSum;
		public int headMaxPathSum;
		public Info(int maxPath, int headMaxPath) {
			maxPathSum = maxPath;
			headMaxPathSum = headMaxPath;
		}
	}
	
	
	public static Info func(Node x) {
		if(x == null) {
			return null;
		}
		Info leftInfo = func(x.left);
		Info rightInfo = func(x.right);
		int p1 = Integer.MIN_VALUE;
		if(leftInfo!=null) {
			p1 = leftInfo.maxPathSum;
		}
		int p2 = Integer.MIN_VALUE;
		if(rightInfo!=null) {
			p2 = rightInfo.maxPathSum;
		}
		int p3 = x.value;
		int p4 = Integer.MIN_VALUE;
		if(leftInfo!=null) {
			p4 = x.value + leftInfo.headMaxPathSum;
		}
		int p5 = Integer.MIN_VALUE;
		if(rightInfo!=null) {
			p5 = x.value + rightInfo.headMaxPathSum;
		}
		int maxPathSum = Math.max(Math.max(Math.max(p1, p2), p3), Math.max(p4, p5));
		int headMaxPathSum = Math.max(Math.max(p3, p4), p5);
		return new Info(maxPathSum, headMaxPathSum);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static int maxPathSum(Node head) {
		if (head == null) {
			return 0;
		}
		ReturnType allData = process(head);
		return allData.maxValue < 0 ? allData.maxValue : allData.maxPathSumAll;
	}

	// 每棵子树，执行完递归之后，需要返回的内容
	public static class ReturnType {
		public int maxPathSumAll; // 路径最大和
		public int maxPathSumHead;// 含头路径最大和
		public int maxValue; // 先忽略 

		public ReturnType(int all, int fromHead, int max) {
			maxPathSumAll = all;
			maxPathSumHead = fromHead;
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
		int maxPathSumHead = Math.max(leftData.maxPathSumHead, 
				rightData.maxPathSumHead) 
				+ x.value;
		maxPathSumHead = Math.max(x.value, maxPathSumHead);
		int maxPathSumAll = Math.max(Math.max(leftData.maxPathSumAll,
				rightData.maxPathSumAll),
				maxPathSumHead);
		return new ReturnType(maxPathSumAll, maxPathSumHead, maxValue);
	}

	public static void main(String[] args) {
		Node head1 = new Node(-7);
		head1.left = new Node(-7);
		head1.right = new Node(-7);
		head1.left.left = new Node(3);
		head1.right.left = new Node(-7);
		head1.left.left.left = new Node(2);
		System.out.println(maxPathSum(head1));

		Node head2 = new Node(-7);
		head2.left = new Node(-6);
		head2.right = new Node(-5);
		head2.left.left = new Node(-3);
		head2.right.left = new Node(-4);
		head2.left.left.left = new Node(-2);
		System.out.println(maxPathSum(head2));

	}

}
