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
		
		
		
		int[] arr = {4,5,3};
		int i = 0;
		int j = 0;
		
		arr[i]  = arr[i] ^ arr[j];
		arr[j]  = arr[i] ^ arr[j];
		arr[i]  = arr[i] ^ arr[j];
		
		
		for(int c = 0 ; c < arr.length;c++) {
			System.out.println(arr[c]);
		}
		
		
		
		
	}
	
}
