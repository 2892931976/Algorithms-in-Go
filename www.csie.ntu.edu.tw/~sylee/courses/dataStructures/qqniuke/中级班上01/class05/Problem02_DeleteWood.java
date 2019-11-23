package class05;

public class Problem02_DeleteWood {

	public static int minDelete(int n) {
		if (n < 4) {
			return 0;
		}
		int k_2 = 2;
		int k_1 = 3;
		int num = 3;
		while (k_2 + k_1 <= n) {
			num++;
			k_1 += k_2;
			k_2 = k_1 - k_2;
		}
		return n - num;
	}

	public static void main(String[] args) {
		int test = 8;
		System.out.println(minDelete(test));
	}
}
