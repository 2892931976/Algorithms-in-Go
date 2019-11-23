package class05;

public class Problem04_ConvertStringToInteger {

	public static int convert(String s) {
		if (s == null || s.equals("")) {
			return 0; // can not convert
		}
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			throw new RuntimeException("can not convert");
		}
		boolean neg = str[0] == '-' ? true : false;
		int minq = Integer.MIN_VALUE / 10;
		int minr = Integer.MIN_VALUE % 10;
		int res = 0;
		int cur = 0;
		for (int i = neg ? 1 : 0; i < str.length; i++) {
			// str[i] = '0' cur -> 0
			// str[i] = '1' cur -> -1
			// str[i] = '4' cur -> -4
			cur = '0' - str[i];
			// 中途转化过程中，溢出的时候
			if ((res < minq) || (res == minq && cur < minr)) {
				throw new RuntimeException("can not convert");
			}
			res = res * 10 + cur;
		}
		// res是，转化数字绝对值的负数表达
		if (!neg && res == Integer.MIN_VALUE) {
			throw new RuntimeException("can not convert");
		}
		return neg ? res : -res;
	}

	// 检查某一个字符串str，是否符合日常书写标准
	public static boolean isValid(char[] str) {
		if (str[0] != '-' && (str[0] < '0' || str[0] > '9')) {
			return false;
		}
		// 1) str[0] == '-' && str.length == 1
		// 2) str[0] == '-' && str.length != 1 && str[1] == '0')
		if (str[0] == '-' && (str.length == 1 || str[1] == '0')) {
			return false;
		}
		if (str[0] == '0' && str.length > 1) {
			return false;
		}
		for (int i = 1; i < str.length; i++) {
			if (str[i] < '0' || str[i] > '9') {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String test1 = "2147483647"; // max in java
		System.out.println(convert(test1));

		String test2 = "-2147483648"; // min in java
		System.out.println(convert(test2));

//		String test3 = "2147483648"; // overflow
//		System.out.println(convert(test3));
//
//		String test4 = "-2147483649"; // overflow
//		System.out.println(convert(test4));

		String test5 = "-123";
		System.out.println(convert(test5));

		System.out.println(Integer.MIN_VALUE);
		// 2147483647   "2147483648"
		System.out.println(Integer.MAX_VALUE);

		int a = Integer.MIN_VALUE;
		for(int i = 31; i > -1;i--) {
			if  ( (int)(  (a >>> i) & 1) != 0) {
				System.out.print(1);
			}else {
				System.out.print(0);
			}
		}
		
		System.out.println();
		
		a = Integer.MIN_VALUE;
		a = -a;
		System.out.println(a);
	}

}
