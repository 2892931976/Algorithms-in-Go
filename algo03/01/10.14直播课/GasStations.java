package class2;

import java.util.LinkedList;

public class GasStations {

	public static boolean[] stations(int[] a, int[] b) {
		if (a == null || b == null || a.length == 0 || a.length != b.length) {
			return null;
		}
		int n = a.length;
		int[] h = new int[n * 2 - 1];
		h[0] = b[0] - a[0];
		LinkedList<Integer> qmin = new LinkedList<Integer>();
		qmin.addLast(0);
		for (int i = 1; i < n; i++) {
			h[i] = h[i - 1] + b[i] - a[i];
			while (!qmin.isEmpty() && h[qmin.peekLast()] >= h[i]) {
				qmin.pollLast();
			}
			qmin.addLast(i);
		}
		boolean[] res = new boolean[n];
		res[0] = h[qmin.peekFirst()] < 0 ? false : true;
		for (int i = n; i < h.length; i++) {
			if (qmin.peekFirst() <= i - n) {
				qmin.pollFirst();
			}
			h[i] = h[i - 1] + b[i - n] - a[i - n];
			while (!qmin.isEmpty() && h[qmin.peekLast()] >= h[i]) {
				qmin.pollLast();
			}
			qmin.addLast(i);
			res[i - n + 1] = h[qmin.peekFirst()] - h[i - n] < 0 ? false : true;
		}
		return res;
	}

	public static void printArray(boolean[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] dis = { 5, 5, 5, 5, 5 };
		int[] gas = { 8, 2, 3, 7, 6 };
		boolean[] res = stations(dis, gas);
		printArray(res);
	}

}
