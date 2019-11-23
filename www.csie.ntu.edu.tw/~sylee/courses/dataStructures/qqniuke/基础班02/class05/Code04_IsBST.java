package class05;

import java.util.LinkedList;
import java.util.Stack;

import class05.Code01_PreInPosTraversal.Node;

public class Code04_IsBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static class ReturnData {
		public boolean isBST;
		public int min;
		public int max;

		public ReturnData(boolean is, int mi, int ma) {
			isBST = is;
			min = mi;
			max = ma;
		}
	}
	
	public static ReturnData process(Node x) {
		if(x == null) {
			return null;
		}
		ReturnData leftData = process(x.left);
		ReturnData rightData = process(x.right);
		int min = x.value;
		int max = x.value;
		if(leftData!=null) {
			min = Math.min(min, leftData.min);
			max = Math.max(max, leftData.max);
		}
		if(rightData!=null) {
			min = Math.min(min, rightData.min);
			max = Math.max(max, rightData.max);
		}
//		boolean isBST = true;
//		if(leftData!=null && (!leftData.isBST   ||  leftData.max >= x.value  )) {
//			isBST= false;
//		}
//		if(rightData!=null && ( !rightData.isBST || x.value >= rightData.min   )) {
//			isBST= false;
//		}
		
		boolean isBST = false;
		if(
				(leftData != null ? (leftData.isBST  &&  leftData.max < x.value) : true)
				  &&
				(rightData !=null ? (rightData.isBST  && rightData.min > x.value) : true)	  	
				) {
			
			isBST = true;
			
		}
		
		
		
		return new ReturnData(isBST, min, max);
	}
	
	
	
	public static boolean isF(Node head) {
		if(head == null) {
			return true;
		}
		Info data = f(head);
		return data.nodes == (1 << data.height - 1);
	}
	public static class Info{
		public int height;
		public int nodes;
		
		public Info(int h, int n) {
			height = h;
			nodes = n;
		}
		
	}
	public static Info f(Node x) {
		if(x == null) {
			return new Info(0,0);
		}
		Info leftData = f(x.left);
		Info rightData = f(x.right);
		int height  = Math.max(leftData.height,rightData.height)+1;
		int nodes = leftData.nodes + rightData.nodes + 1;
		return new Info(height, nodes);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static boolean inOrderUnRecur(Node head) {
		if (head == null) {
			return true;
		}
		int pre = Integer.MIN_VALUE;
		Stack<Node> stack = new Stack<Node>();
		while (!stack.isEmpty() || head != null) {
			if (head != null) {
				stack.push(head);
				head = head.left;
			} else {
				head = stack.pop();
				if (head.value <= pre) {
					return false;
				}
				pre = head.value;
				head = head.right;
			}
		}
		return true;
	}

	public static boolean isBST(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> inOrderList = new LinkedList<>();
		process(head, inOrderList);
		int pre = Integer.MIN_VALUE;
		for (Node cur : inOrderList) {
			if (pre >= cur.value) {
				return false;
			}
			pre = cur.value;
		}
		return true;
	}

	public static void process(Node node, LinkedList<Node> inOrderList) {
		if (node == null) {
			return;
		}
		process(node.left, inOrderList);
		inOrderList.add(node);
		process(node.right, inOrderList);
	}

}
