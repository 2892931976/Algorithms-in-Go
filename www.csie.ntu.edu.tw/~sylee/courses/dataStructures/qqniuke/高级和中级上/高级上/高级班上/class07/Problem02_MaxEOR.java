package class07;

public class Problem02_MaxEOR {

	public static class Node {
		public Node[] nexts = new Node[2];
	}

	// 把所有前缀异或和，加入到NumTrie，并按照前缀树组织
	public static class NumTrie {
		public Node head = new Node();

		public void add(int num) {
			Node cur = head;
			for (int move = 31; move >= 0; move--) { // move:向左位移多少位
				int path = ((num >> move) & 1);
				cur.nexts[path] = cur.nexts[path] == null 
						? new Node() : cur.nexts[path];
				cur = cur.nexts[path];
			}
		}

		// sum最希望遇到的路径，最大的异或结果返回   O(32)
		public int maxXor(int sum) {
			Node cur = head;
			int res = 0; // 最后的结果 ( num ^ 最优选择) 所得到的值 
			for (int move = 31; move >= 0; move--) {
				// 当前位如果是0，path就是整数0；
				// 当前位如果是1，path就是整数1；
				int path = (sum >> move) & 1; // num第move位置上的状态提取出来
				// sum该位的状态，最期待的路
				int best = move == 31 ? path : (path ^ 1);
				// best : 最期待的路  -> 实际走的路
				best = cur.nexts[best] != null ? best : (best ^ 1);
				// path num第move位的状态， best是根据path实际走的路
				res |= (path ^ best) << move;	
				cur = cur.nexts[best];
			}
			return res;
		}

	}

	public static int maxXorSubarray(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int sum = 0; // 一个数都没有的时候，异或和为0
		NumTrie numTrie = new NumTrie();
		numTrie.add(0);
		for (int i = 0; i < arr.length; i++) {
			sum ^= arr[i]; // eor -> 0~i 异或和
			max = Math.max(max, numTrie.maxXor(sum));
			numTrie.add(sum);
		}
		return max;
	}

	// for test
	public static int comparator(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			int eor = 0;
			for (int j = i; j < arr.length; j++) {
				eor ^= arr[j];
				max = Math.max(max, eor);
			}
		}
		return max;
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 30;
		int maxValue = 50;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr = generateRandomArray(maxSize, maxValue);
			int res = maxXorSubarray(arr);
			int comp = comparator(arr);
			if (res != comp) {
				succeed = false;
				printArray(arr);
				System.out.println(res);
				System.out.println(comp);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");
	}
}
