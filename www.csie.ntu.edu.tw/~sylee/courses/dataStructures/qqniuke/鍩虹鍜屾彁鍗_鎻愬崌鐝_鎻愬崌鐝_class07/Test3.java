package class07;

public class Test3 {

	public static int maxScore(int[] a) {
		int[] arr = new int[a.length + 2];
		arr[0] = 1;
		arr[arr.length -1] = 1;
		for(int i = 0 ; i < a.length; i++) {
			arr[i+1] = a[i];
		}
		return process(arr, 1, arr.length -2);
	}
	
	
	// 潜台词 ： arr[L-1]和arr[R+1]的气球一定没有爆
	// 每一次调用都要满足潜台词
	// L..R范围的气球，如果都打爆，获得的最大分数
	// 尝试的方法：试每一个位置都最后打爆的情况
	public static int process(int[] arr, int L, int R) {
		if(L==R) {
			return arr[L-1] * arr[L] * arr[R+1];	
		}
		// 最后打爆L位置上的气球，先去打爆L+1..R范围上的气球
		int p1 = process(arr, L+1, R) + arr[L-1] * arr[L] * arr[R+1];
		// 最后打爆R位置上的气球，先去打爆L..R-1范围上的气球
		int p2 = process(arr, L, R-1) +  arr[L-1] * arr[R] * arr[R+1];
		int ans = Math.max(p1, p2);
		for(int test = L+1; test < R; test++) {
			// 每一个位置都尝试最后打爆，先去打爆L..test -1，再打爆test+1.. R，最后打爆test
			int p = process(arr, L, test-1) 
					+ process(arr, test+1, R) 
					+ arr[L-1] * arr[test] * arr[R+1];
			 ans = Math.max(ans, p);
		}
		return ans;
	}
	
}
