package class01;

public class Test {

	
	public static void process1() {
		int N = 1000;
		int a = 0;
		for(int i = 0 ; i < N;i++) {
			a = 2+5;
			a = 4*7;
			a = 6*8;
		}
		
		
		
	}
	
	
	public static void process2() {
		int N = 1000;
		int a = 0;
		for(int i = 0 ; i < N;i++) {
			a = 3 | 6;
			a = 3 & 4;
			a = 4 ^ 785;
		}
	}
	
	
	
	
	public static void main(String[] args) {

		int a = 10;
		int b = 10;
		
		a = a^b;
		b = a^b;
		a = a^b;
		
		System.out.println(a);
		System.out.println(b);
		
		
		
	}
	
}
