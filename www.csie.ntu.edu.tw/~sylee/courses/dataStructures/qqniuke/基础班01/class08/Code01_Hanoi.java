package class08;

public class Code01_Hanoi {

	public static void hanoi(int n) {
		if (n > 0) {
			func(n, "左", "右", "中");
		}
	}

	// 1~i 圆盘 目标是from -> to， other是另外一个
	public static void func(int N, String from, String to, String other) {
		if (N == 1) { // base
			System.out.println("Move 1 from " + from + " to " + to);
		} else {
			func(N - 1, from, other, to);
			System.out.println("Move " + N + " from " + from + " to " + to);
			func(N - 1, other, to, from);
		}
	}
	
	
	
	
	public static void printAllWays(int n) {
		leftToRight(n);
	}
	
	public static void leftToRight(int n) {
		if(n== 1) {
			System.out.println("Move 1 from left to right");
			return ;
		}
		leftToMid(n-1);
		System.out.println("Move " +n + " from left to right");
		midToRight(n-1);
	}
	
	public static void leftToMid(int n) {
		if(n== 1) {
			System.out.println("Move 1 from left to mid");
			return ;
		}
		leftToRight(n-1);
		System.out.println("Move " +n + " from left to mid");
		rightToMid(n-1);
	}
	
	public static void midToRight(int n) {
		
	}
	
	public static void rightToMid(int n) {
		
	}
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) {
		int n = 3;
		hanoi(n);
	}

}
