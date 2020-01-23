package class08;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Coffee {

	public static class Machine {
		public int timePoint;
		public int workTime;

		public Machine(int t, int w) {
			timePoint = t;
			workTime = w;
		}
	}

	public static class MachineComparator implements Comparator<Machine> {

		@Override
		public int compare(Machine o1, Machine o2) {
			return (o1.timePoint + o1.workTime) - (o2.timePoint + o2.workTime);
		}

	}

	public static int minTime(int[] arr, int n, int a, int b) {
		PriorityQueue<Machine> heap = new PriorityQueue<Machine>(new MachineComparator());
		for (int i = 0; i < arr.length; i++) {
			heap.add(new Machine(0, arr[i]));
		}
		int[] drinks = new int[arr.length];
		for (int i = 0; i < n; i++) {
			Machine cur = heap.poll();
			cur.timePoint += cur.workTime;
			drinks[i] = cur.timePoint;
			heap.add(cur);
		}
		return process(drinks, a, b, 0, 0);
	}

	// a是洗咖啡机器洗净时长     固定
	// b是挥发干净的时长            固定
	// arr每个人喝完的时间        固定
	// 目前来到arr[index]这个时间点喝完的咖啡杯
	// washLine是洗咖啡机器到什么时候可以洗新的咖啡杯
	// 返回洗完arr[index...]所有咖啡杯，所需要的最短时长
	public static int process(int[] arr, int a, int b, int index, int washLine) {
		if (index == arr.length - 1) { // 最后的咖啡杯
			int p1 = Math.max(arr[index], washLine) + a;
			int p2 = arr[index] + b;
			return Math.min(p1, p2);
		}
		// 当前的咖啡杯决定洗
		int wash = Math.max(arr[index], washLine) + a;
		int next1 = process(arr, a, b, index + 1, wash);
		int p1 = Math.max(wash, next1);
		int dry = arr[index] + b;
		int next2 = process(arr, a, b, index + 1, washLine);
		int p2 = Math.max(dry, next2);
		return Math.min(p1, p2);
	}

	public static void main(String[] args) {
		System.out.println("hello");
	}

}
