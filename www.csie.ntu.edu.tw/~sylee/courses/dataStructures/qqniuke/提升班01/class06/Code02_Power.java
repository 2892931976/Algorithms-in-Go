package class06;

public class Code02_Power {

	public static boolean is2Power(int n) {
		return (n & (n - 1)) == 0;
	}

	public static boolean is4Power(int n) {
		                                  //010101..010101
		return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
	}

}
