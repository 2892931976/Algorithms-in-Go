package class07;

public class Test2 {

	// arr中都是正数
	// 无重复值
	public static int moneyWays(int[] arr, int aim) {
		return ways(arr, 0, aim);
	}

	// 可以自由使用arr[index....]组成aim的方法数，返回
	public static int ways(int[] arr, int index, int aim) {
		if (index == arr.length) {
			return aim == 0 ? 1 : 0;
		}
		if(aim == 0) {
			return 1;
		}
		// arr[index]
		int way = 0;
		for (int zhang = 0; zhang * arr[index] <= aim; zhang++) {

			way += ways(arr, index + 1, aim  -  zhang * arr[index]);
			
		}
		return way;
	}
	
	
	public static int ways1(int[] arr, int aim) {
		int N = arr.length;
		int[][] dp = new int[N + 1][aim+1];
		dp[N][0] =1; 
		for(int row = 0; row < N;row++) {
			dp[row][0] = 1;
		}
		for(int row = N -1; row >=0; row--) {
			for(int col = 0 ; col <= aim; col++) {
				int way = 0;
				for (int zhang = 0; zhang * arr[row] <= col; zhang++) {
					way += dp[row + 1][ col  -  zhang * arr[row]];
				}
				dp[row][col] = way;
			}
		}
		return dp[0][aim];
	}
	
	
	public static int ways2(int[] arr, int aim) {
		int N = arr.length;
		int[][] dp = new int[N + 1][aim+1];
		dp[N][0] =1; 
		for(int row = 0; row < N;row++) {
			dp[row][0] = 1;
		}
		for(int row = N -1; row >=0; row--) {
			for(int col = 0 ; col <= aim; col++) {
				dp[row][col] = dp[row + 1][col];
				if(col - arr[row] >= 0) {
					dp[row][col] += dp[row][col - arr[row]];
				}
			}
		}
		return dp[0][aim];
	}
	
	public static  void main(String[] args) {
		int[] arr = { 5, 10, 2, 3};
		int aim = 100;
		System.out.println(moneyWays(arr, aim));
		System.out.println(ways1(arr, aim));
		System.out.println(ways2(arr, aim));
	}
	
	

}
