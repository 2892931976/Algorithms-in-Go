package class10;

public class Code01_UglyNumber {

	public static int uglyNumber1(int index) {
		int number = 0;
		int count = 0;
		while (count < index) {
			++number;
			if (isUgly(number))
				count++;
		}
		return number;
	}

	public static boolean isUgly(int number) {
		while (number % 2 == 0)
			number = number / 2;
		while (number % 3 == 0)
			number = number / 3;
		while (number % 5 == 0)
			number = number / 5;
		return (number == 1) ? true : false;
	}

	public static int uglyNumber2(int n) {
		int[] help = new int[n];
		help[0] = 1;
		int i2 = 0;
		int i3 = 0;
		int i5 = 0;
		int index = 1;
		while (index < n) {
			help[index] = Math.min(2 * help[i2], Math.min(3 * help[i3], 5 * help[i5]));
			if (help[index] == 2 * help[i2])
				i2++;
			if (help[index] == 3 * help[i3])
				i3++;
			if (help[index] == 5 * help[i5])
				i5++;
			index++;
		}
		return help[index - 1];
	}

	public static void main(String[] args) {
		int test = 8;
		System.out.println(uglyNumber1(test));
		System.out.println(uglyNumber2(test));
	}
}
