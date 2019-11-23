package class03;

public class Add_MaxLength {

	public static class Node{
		public int value;
		public Node left;
		public Node right;
	}
	
	public static class ReturnType{
		public int maxDistance;
		public int height;
		public ReturnType(int maxDis, int hei) {
			maxDistance = maxDis;
			height = hei;
		}
	}
	
	public static int maxDistance(Node head) {
		return process(head).maxDistance;
	}
	
	public static ReturnType process(Node x) {
		if(x == null ) {
			return new ReturnType(0,0);
		}
		ReturnType leftData = process(x.left);
		ReturnType rightData = process(x.right);
		int height = Math.max(leftData.height, rightData.height) + 1;
		int maxDistance = Math.max(leftData.maxDistance, rightData.maxDistance);
		maxDistance = Math.max(maxDistance, leftData.height + rightData.height + 1);
		return new ReturnType(maxDistance, height);
	}
	
	
}
