package lovecoding;

public class TwoSubarrayMaxSum {

	// ���Ž�
	public static int maxSum(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int[] help = new int[N];
		help[0] = arr[0];
		int tmp = arr[0] < 0 ? 0 : arr[0];
		for (int i = 1; i < N - 1; i++) {
			tmp += arr[i];
			help[i] = Math.max(help[i - 1], tmp);
			tmp = tmp < 0 ? 0 : tmp;
		}
		help[N - 1] = arr[N - 1];
		tmp = arr[N - 1] < 0 ? 0 : arr[N - 1];
		int res = help[N - 2] + help[N - 1];
		for (int i = N - 2; i >= 1; i--) {
			tmp += arr[i];
			help[i] = Math.max(help[i + 1], tmp);
			tmp = tmp < 0 ? 0 : tmp;
			res = Math.max(res, help[i - 1] + help[i]);
		}
		return res;
	}

	// �Ƚϱ����ķ���������Ϊ�˲��Բ�д��
	public static int test1(int[] arr) {
		if (arr == null || arr.length < 2) {
			return 0;
		}
		int N = arr.length;
		int[][] data = new int[N][N];
		for (int i = 0; i < N; i++) {
			data[i][i] = arr[i];
			for (int j = i + 1; j < N; j++) {
				data[i][j] = data[i][j - 1] + arr[j];
			}
		}
		int res = Integer.MIN_VALUE;
		for (int i = 0; i < N - 1; i++) {
			res = Math.max(res, test2(data, 0, i) + test2(data, i + 1, N - 1));
		}
		return res;
	}

	// �Ƚϱ����ķ���������Ϊ�˲��Բ�д��
	public static int test2(int[][] data, int left, int right) {
		int res = Integer.MIN_VALUE;
		for (int i = left; i < right + 1; i++) {
			for (int j = i; j < right + 1; j++) {
				res = Math.max(res, data[i][j]);
			}
		}
		return res;
	}

	// Ϊ�˲��Բ�д��
	public static int[] getRandomArray(int len) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = (int) (Math.random() * 100) - (int) (Math.random() * 100);
		}
		return arr;
	}

	// Ϊ�˲��Բ�д��
	public static void printArray(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int testTime = 50000; // ���Ե�����
		int len = 20; // �����������ĳ���
		for (int i = 0; i < testTime; i++) {
			int[] arr = getRandomArray(len);
			if (maxSum(arr) != test1(arr)) {
				System.out.println("what a fuck!!!");
				break;
			}
		}

	}
}
