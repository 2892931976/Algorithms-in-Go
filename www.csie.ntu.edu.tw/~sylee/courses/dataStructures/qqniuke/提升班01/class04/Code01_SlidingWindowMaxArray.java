package class04;

import java.util.LinkedList;

public class Code01_SlidingWindowMaxArray {

	
	
	
	public static class WindowMax{
		private int L;
		private int R;
		private int[] arr; // arr[   [L..R)     ]
		private LinkedList<Integer> qmax;
		
		public WindowMax(int[] a) {
			arr = a;
			L = -1;
			R = 0;
			qmax = new LinkedList<>();
		}
		
		//L = -1
		// R = 0
		// arr   [-1,1)
		//[ 3,2,1,4,5   ]
		// 0
		// [0,1)  arr[0]
		// 1
		// [-1,2)  arr[0,1]
		public void addNumFromRight() {
			if(R == arr.length) {
				return;
			}
			while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[R]) {
				qmax.pollLast();
			}
			qmax.addLast(R);
			R++;
		}
		
		// arr  [L,R)
		public void removeNumFromLeft() {
			if(L >= R-1) {
				return;
			}
			L++;
			if(qmax.peekFirst() == L) {
				qmax.pollFirst();
			}
		}
		
		public Integer getMax() {
			if(!qmax.isEmpty()) {
				return arr[qmax.peekFirst()];
			}
			return null;
		}
		
		
	}
	
	
	public static int[] getMaxWindow(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		// 下标      值   大 -> 小
		LinkedList<Integer> qmax = new LinkedList<Integer>();
		int[] res = new int[arr.length - w + 1];
		int index = 0;
		for (int i = 0; i < arr.length; i++) { // 窗口（刚才讲的）的R
			// i -> arr[i]
			while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]) {
				qmax.pollLast();
			}
			qmax.addLast(i);
			if (qmax.peekFirst() == i - w) { // i - w  过期的下标
				qmax.pollFirst();
			}
			if (i >= w - 1) { // 窗口形成了
				res[index++] = arr[qmax.peekFirst()];
			}
		}
		return res;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr = { 4, 3, 5, 4, 3, 3, 6, 7 };
		int w = 3;
		printArray(getMaxWindow(arr, w));

	}

}
