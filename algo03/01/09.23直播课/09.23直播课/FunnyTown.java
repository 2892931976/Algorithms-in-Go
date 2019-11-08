package class1;

public class FunnyTown {

	public static int town(int num, String[] strs) {
		if (num <= 0 || strs == null || strs.length == 0) {
			return num == 1 ? 1 : -1;
		}
		int[] map = new int[num];
		String[] tmp = null;
		for (int i = 0; i < strs.length; i++) {
			tmp = strs[i].split(" ");
			if (!tmp[0].equals(tmp[1])) {
				map[Integer.valueOf(tmp[0]) - 1]--;
				map[Integer.valueOf(tmp[1]) - 1]++;
			}
		}
		for (int i = 0; i < map.length; i++) {
			if (map[i] == num - 1) {
				return i + 1;
			}
		}
		return -1;
	}

	public static void printStrings(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.println(arr[i] + " ");
		}
	}

	public static void main(String[] args) {
		int num1 = 2;
		String[] strs1 = {};
		System.out.println(town(num1, strs1));

		int num2 = 3;
		String[] strs2 = { "1 2", "3 2" };
		System.out.println(town(num2, strs2));

		int num3 = 4;
		String[] strs3 = { "1 1", "2 1", "3 1", "4 1", "3 3" };
		System.out.println(town(num3, strs3));

		int num4 = 1;
		String[] strs4 = { "1 1" };
		System.out.println(town(num4, strs4));

		int num5 = 1;
		String[] strs5 = {};
		System.out.println(town(num5, strs5));

	}
}
