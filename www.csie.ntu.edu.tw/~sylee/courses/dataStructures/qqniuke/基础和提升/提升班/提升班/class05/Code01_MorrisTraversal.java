package class05;

public class Code01_MorrisTraversal {
	
	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}
	
	public static void process(Node head) {
		if(head == null) {
			return;
		}
		// 1
		process(head.left);
		// 2
		process(head.right);
		// 3
	}
	
	
	public static void morris(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
		while (cur != null) { // 过流程
			mostRight = cur.left; // mostRight是cur左孩子
			if (mostRight != null) { // 有左子树
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				// mostRight变成了cur左子树上，最右的节点
				if (mostRight.right == null) { // 这是第一次来到cur
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else { // mostRight.right == cur
					mostRight.right = null;
				}
			}
			cur = cur.right;
		}
	}
	
	
	
	public static void morrisPre(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
		while (cur != null) { // 过流程
			mostRight = cur.left; // mostRight是cur左孩子
			if (mostRight != null) { // 有左子树
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				// mostRight变成了cur左子树上，最右的节点
				if (mostRight.right == null) { // 这是第一次来到cur
					System.out.println(cur.value);
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else { // mostRight.right == cur
					mostRight.right = null;
				}
			} else { // 没有左子树的情况
				System.out.println(cur.value);
			}
			cur = cur.right;
		}
	}
	
	
	
	public static void morrisIn(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
		while (cur != null) { // 过流程
			mostRight = cur.left; // mostRight是cur左孩子
			if (mostRight != null) { // 有左子树
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				// mostRight变成了cur左子树上，最右的节点
				if (mostRight.right == null) { // 这是第一次来到cur
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else { // mostRight.right == cur
					mostRight.right = null;
				}
			}
			System.out.println(cur.value);
			cur = cur.right;
		}
	}
	



	public static void morrisPos(Node head) {
		if (head == null) {
			return;
		}
		Node cur = head;
		Node mostRight = null;
		while (cur != null) {
			mostRight = cur.left;
			if (mostRight != null) {
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				if (mostRight.right == null) {
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else {
					mostRight.right = null;
					printEdge(cur.left);
				}
			}
			cur = cur.right;
		}
		printEdge(head);
		System.out.println();
	}

	// 以X为头的树，逆序打印这棵树的右边界
	public static void printEdge(Node X) {
		Node tail = reverseEdge(X);
		Node cur = tail;
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		reverseEdge(tail);
	}

	public static Node reverseEdge(Node from) {
		Node pre = null;
		Node next = null;
		while (from != null) {
			next = from.right;
			from.right = pre;
			pre = from;
			from = next;
		}
		return pre;
	}

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}
	
	
	
	public static boolean isBST(Node head) {
		if (head == null) {
			return true;
		}
		Node cur = head;
		Node mostRight = null;
		int preValue = Integer.MIN_VALUE;
		while (cur != null) { // 过流程
			mostRight = cur.left; // mostRight是cur左孩子
			if (mostRight != null) { // 有左子树
				while (mostRight.right != null && mostRight.right != cur) {
					mostRight = mostRight.right;
				}
				// mostRight变成了cur左子树上，最右的节点
				if (mostRight.right == null) { // 这是第一次来到cur
					mostRight.right = cur;
					cur = cur.left;
					continue;
				} else { // mostRight.right == cur
					mostRight.right = null;
				}
			}
			if(cur.value <= preValue) {
				return false;
			}
			preValue = cur.value;
			cur = cur.right;
		}
		return true;
	}
	
	
	
	

	public static void main(String[] args) {
		Node head = new Node(4);
		head.left = new Node(2);
		head.right = new Node(6);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.right.left = new Node(5);
		head.right.right = new Node(7);
		printTree(head);
		morrisIn(head);
		morrisPre(head);
		morrisPos(head);
		printTree(head);

	}

}
