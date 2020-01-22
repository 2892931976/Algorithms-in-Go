package class11;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code07_LineMaxCovers {

	public static class Line {
		public int start;
		public int end;

		public Line(int s, int e) {
			start = s;
			end = e;
		}
	}

	public static class StartComparator implements Comparator<Line> {

		@Override
		public int compare(Line o1, Line o2) {
			return o1.start - o2.start;
		}

	}

	public static class EndComparator implements Comparator<Line> {

		@Override
		public int compare(Line o1, Line o2) {
			return o1.end - o2.end;
		}

	}

	// 求最大重合部分上盖了多少条线段
	// 要求重合部分长度要比0大
	public static int maxConvers(int[] start, int[] end) {
		if (start == null || end == null || start.length != end.length) {
			return 0;
		}
		PriorityQueue<Line> startMinHeap = new PriorityQueue<>(new StartComparator());
		for (int i = 0; i < start.length; i++) {
			if (start[i] != end[i]) {
				startMinHeap.add(new Line(start[i], end[i]));
			}
		}
		PriorityQueue<Line> endMinHeap = new PriorityQueue<>(new EndComparator());
		int max = 0;
		while (!startMinHeap.isEmpty()) {
			Line cur = startMinHeap.poll();
			while (!endMinHeap.isEmpty() && endMinHeap.peek().end <= cur.start) {
				endMinHeap.poll();
			}
			endMinHeap.add(cur);
			max = Math.max(max, endMinHeap.size());
		}
		return max;
	}

	// for test
	public static int right(int[] start, int[] end) {
		if (start == null || end == null || start.length != end.length) {
			return 0;
		}
		int min = start[0];
		int max = end[0];
		for (int i = 1; i < start.length; i++) {
			min = Math.min(min, start[i]);
			max = Math.max(max, end[i]);
		}
		int maxCover = 0;
		for (double p = min + 0.5; p < max; p += 1) {
			int cover = 0;
			for (int i = 0; i < start.length; i++) {
				if (start[i] < p && end[i] > p) {
					cover++;
				}
			}
			maxCover = Math.max(maxCover, cover);
		}
		return maxCover;
	}

	// for test
	public static int[][] random(int len, int base) {
		int size = (int) (Math.random() * len) + 1;
		int[] start = new int[size];
		int[] end = new int[size];
		for (int i = 0; i < size; i++) {
			start[i] = (int) (Math.random() * base);
			end[i] = start[i] + (int) (Math.random() * base);
		}
		return new int[][] { start, end };
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int len = 1000;
		int base = 1000;
		for (int i = 0; i < 1000; i++) {
			int[][] test = random(len, base);
			int[] start = test[0];
			int[] end = test[1];
			int cover1 = maxConvers(start, end);
			int cover2 = right(start, end);
			if (cover1 != cover2) {
				printArray(start);
				printArray(end);
				break;
			}
		}
	}

}
