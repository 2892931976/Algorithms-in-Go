package class07;

public class Code03_CardsInLine {

	public static int win1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return Math.max(
				f(arr, 0, arr.length - 1),
				s(arr, 0, arr.length - 1)
				);
	}

	// 先手函数
	// 当前该你拿，arr[i..j]
	// 返回你的最好分数
	public static int f(int[] arr, int i, int j) {
		if (i == j) {
			return arr[i];
		}
		// i..j
		return Math.max(
				arr[i] + s(arr, i + 1, j), 
				arr[j] + s(arr, i, j - 1)
				);
	}

	// 后手函数
	// 当前不该你拿，是对方在arr[i..j]范围上拿
	// 返回你的最好分数
	public static int s(int[] arr, int i, int j) {
		if (i == j) {
			return 0;
		}
		// i..j
		return Math.min(  
				f(arr, i + 1, j)   , 
				f(arr, i, j - 1)  
				);
	}
	
	
	public static int dp(int[] arr) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		int[][] f = new int[arr.length][arr.length];
		int[][] s = new int[arr.length][arr.length];
		for(int i = 0 ; i < arr.length;i++) {
			f[i][i] = arr[i];
		}
		int row = 0;
		int col = 1;
		// 对角线开始位置 row 行  col 列
		while(col < arr.length) {
			int i = row;
			int j = col;
			while(i < arr.length && j < arr.length) {
				f[i][j] = Math.max(arr[i] + s[i + 1][ j], arr[j] + s[i][j - 1]);
				s[i][j] = Math.min(f[i + 1][ j], f[i][j - 1]);
				i++;
				j++;
			}
			col++;
		}
		return Math.max(f[0][arr.length-1], s[0][arr.length-1]);
	}
	
	
	

	public static int win2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] f = new int[arr.length][arr.length];
		int[][] s = new int[arr.length][arr.length];
		for (int j = 0; j < arr.length; j++) {
			f[j][j] = arr[j];
			for (int i = j - 1; i >= 0; i--) {
				f[i][j] = Math.max(arr[i] + s[i + 1][j], arr[j] + s[i][j - 1]);
				s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
			}
		}
		return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
	}

	public static void main(String[] args) {
		int[] arr = { 1, 9, 1 };
		System.out.println(dp(arr));
		System.out.println(win1(arr));
		System.out.println(win2(arr));

	}

}
