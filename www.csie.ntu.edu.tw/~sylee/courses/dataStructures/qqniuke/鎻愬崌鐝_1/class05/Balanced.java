package class05;

public class Balanced {
	
	public static class Node{
		public int value;
		public Node left;
		public Node right;
	}
	
	public static boolean isBalanced(Node head) {
		return process(head).isBalanced;
	}
	
	
	public static class Info{
		public boolean isBalanced;
		public int height;
		public Info(boolean is, int h) {
			isBalanced = is;
			height = h;
		}
	}
	
	public static Info process(Node x) {
		if(x == null) {
			return new Info(true, 0);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		int height = Math.max(leftInfo.height, rightInfo.height) + 1;
		boolean isBalanced = leftInfo.isBalanced 
				&& rightInfo.isBalanced 
				&& Math.abs(leftInfo.height - rightInfo.height) <= 1;
		return new Info(isBalanced, height);
	}
	
	
	
	

}
