package class06;

public class Code01_GetMax {

	// 请保证参数n，不是1就是0的情况下
	// 1 -> 0
	// 0 -> 1
	public static int flip(int n) {
		return n ^ 1;
	}

	// n是非负数，返回1
	// n是负数，返回0
	public static int sign(int n) {
		return flip(     (n >> 31) & 1     );
	}

	public static int getMax1(int a, int b) {
		int c = a - b;
		int scA = sign(c); // a-b为非负，scA为1；a-b为负，scA为0
		int scB = flip(scA); // scA为0，scB为1；scA为1，scB为0
		// scA为0，scb必为1；scA为1，scB必为0
		return a * scA + b * scB;
	}

	public static int getMax2(int a, int b) {
		int c = a - b;
		int sa = sign(a);
		int sb = sign(b);
		int sc = sign(c);
		int difSab = sa ^ sb;// a和b的符号不一样，为1；一样，为0
		int sameSab = flip(difSab); // a和b的符号一样，为1；不一样，为0
		int returnA = difSab * sa + sameSab * sc;
		int returnB = flip(returnA);
		return a * returnA + b * returnB;
	}

	public static void main(String[] args) {
		int a = -16;
		int b = 1;
		System.out.println(getMax1(a, b));
		System.out.println(getMax2(a, b));
		a = 2147483647;
		b = -2147480000;
		System.out.println(getMax1(a, b)); // wrong answer because of overflow
		System.out.println(getMax2(a, b));

	}

}
