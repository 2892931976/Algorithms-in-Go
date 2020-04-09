package class1;

public class LongTimeNoSee {

	public static int getNYearsAge(double y, double x, int n) {
		double a = 1 - x;
		double b = 1 + 20 * x;
		double base = y + b / (a - 1);
		double res = Math.pow(a, n) * base;
		return (int) Math.ceil(res - b / (a - 1));
	}

	// Math.pow(a, n)方法也可以替换成如下的方法，时间复杂度O(logN)
	public static double getPower(double a, int n) {
		double res = 1;
		for (; n != 0; n >>= 1) {
			if ((n & 1) != 0) {
				res *= a;
			}
			a *= a;
		}
		return res;
	}

	public static void main(String[] args) {
		double y = 30;
		double x = 0.5;
		int n = 1;
		System.out.println(getNYearsAge(y, x, n));

	}

}
