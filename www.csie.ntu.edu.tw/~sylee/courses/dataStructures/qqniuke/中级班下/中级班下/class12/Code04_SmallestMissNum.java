package class12;

public class Code04_SmallestMissNum {

	public static int missNum(int[] arr) {
		int L = 0; // 0~L-1  范围上，做到了任何一个i，上面放的是i+1
		int R = arr.length;
		// R 假设待定区域是最好的情况下，我可以收集到1~R的所有数字
		// R 表示无效区
		while (L < R) {
			// arr[L]  当前数字，  之前的区域都是有效区域
			if (arr[L] == L + 1) { // 已经做到了
				L++;
			} else if (arr[L] <= L || arr[L] > R || arr[arr[L] - 1] == arr[L]) {
				swap(arr, L, --R);
			} else {
				swap(arr, L, arr[L] - 1);
			}
		}
		return L + 1;
	}

	public static void swap(int[] arr, int index1, int index2) {
		int tmp = arr[index1];
		arr[index1] = arr[index2];
		arr[index2] = tmp;
	}

	public static void main(String[] args) {
		int[] arr = { -1, 0, 2, 1, 3, 5 };
		System.out.println(missNum(arr));

	}
}
