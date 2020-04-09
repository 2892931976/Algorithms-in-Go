package com.chj.windowstack;

//import java.util.LinkedList;

public class Code01_SlidingWindowMaxArray {

	
	
	
	public static class WindowMax{
		private int L;
		private int R;
		private int[] arr; // arr[   [L..R)     ]
		private CircleDeque<Integer> qmax;
		
		public WindowMax(int[] a) {
			arr = a;
			L = -1;
			R = 0;
			qmax = new CircleDeque<>();
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
			while (!qmax.isEmpty() && arr[qmax.rear()] <= arr[R]) {
				qmax.deQueueRear();
			}
			qmax.enQueueRear(R);
			R++;
		}
		
		// arr  [L,R)
		public void removeNumFromLeft() {
			if(L >= R-1) {
				return;
			}
			L++;
			if(qmax.front() == L) {
				qmax.deQueueFront();
			}
		}
		
		public Integer getMax() {
			if(!qmax.isEmpty()) {
				return arr[qmax.front()];
			}
			return null;
		}
		
		
	}
	
	
	public static int[] getMaxWindow(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		// 下标      值   大 -> 小
		CircleDeque<Integer> qmax = new CircleDeque<Integer>();
		int[] res = new int[arr.length - w + 1];
		int index = 0;
		for (int i = 0; i < arr.length; i++) { // 窗口（刚才讲的）的R
			// i -> arr[i]
			while (!qmax.isEmpty() && arr[qmax.rear()] <= arr[i]) {
				qmax.deQueueRear();
			}
			qmax.enQueueRear(i);
			if (qmax.front() == i - w) { // i - w  过期的下标
				qmax.deQueueFront();
			}
			if (i >= w - 1) { // 窗口形成了
				res[index++] = arr[qmax.front()];
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