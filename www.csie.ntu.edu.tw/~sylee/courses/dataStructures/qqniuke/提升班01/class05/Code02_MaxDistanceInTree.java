package class05;

public class Code02_MaxDistanceInTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}
	
	
	public static int maxDistance(Node head) {
		return process(head).maxDistance;
	}
	
	public static class Info{
		public int maxDistance;
		public int height;
		public Info(int dis, int h) {
			maxDistance = dis;
			height  = h;
		}
	}
	
	// 返回以x为头的整棵树，两个信息
	public static Info process(Node x) {
		if(x == null) {
			return new Info(0,0);
		}
		Info leftInfo = process(x.left);
		Info rightInfo = process(x.right);
		// info
		int p1 = leftInfo.maxDistance;
		int p2 = rightInfo.maxDistance;
		int p3 = leftInfo.height + 1 + rightInfo.height;
		int maxDistance = Math.max(p3, Math.max(p1, p2));
		int height = Math.max(leftInfo.height, rightInfo.height) + 1 ;
		return new Info(maxDistance, height);
	}


	public static void main(String[] args) {
		Node head1 = new Node(1);
		head1.left = new Node(2);
		head1.right = new Node(3);
		head1.left.left = new Node(4);
		head1.left.right = new Node(5);
		head1.right.left = new Node(6);
		head1.right.right = new Node(7);
		head1.left.left.left = new Node(8);
		head1.right.left.right = new Node(9);
		System.out.println(maxDistance(head1));

		Node head2 = new Node(1);
		head2.left = new Node(2);
		head2.right = new Node(3);
		head2.right.left = new Node(4);
		head2.right.right = new Node(5);
		head2.right.left.left = new Node(6);
		head2.right.right.right = new Node(7);
		head2.right.left.left.left = new Node(8);
		head2.right.right.right.right = new Node(9);
		System.out.println(maxDistance(head2));

	}

}
