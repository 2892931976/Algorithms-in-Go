package class05;

import java.util.List;

public class Code03_MaxHappy {

	public static int maxHappy(int[][] matrix) {
		int[][] dp = new int[matrix.length][2];
		boolean[] visited = new boolean[matrix.length];
		int root = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (i == matrix[i][0]) {
				root = i;
			}
		}
		process(matrix, dp, visited, root);
		return Math.max(dp[root][0], dp[root][1]);
	}

	public static void process(int[][] matrix, int[][] dp, boolean[] visited, int root) {
		visited[root] = true;
		dp[root][1] = matrix[root][1];
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][0] == root && !visited[i]) {
				process(matrix, dp, visited, i);
				dp[root][1] += dp[i][0];
				dp[root][0] += Math.max(dp[i][1], dp[i][0]);
			}
		}
	}
	
	public static class Employee { 
		public int happy; // 这名员工可以带来的快乐值 
	    public List<Employee> nexts; // 这名员工有哪些直接下级 
	}
	
	
	public static int maxHappy(Employee boss) {
		Info headInfo = process(boss);
		return Math.max(headInfo.laiMaxHappy, headInfo.buMaxHappy);
	}
	
	public static class Info{
		public int laiMaxHappy;
		public int buMaxHappy;
		public Info(int lai, int bu) {
			laiMaxHappy = lai;
			buMaxHappy = bu;
		}
	}
	
	public static Info process(Employee x) {
		if(x.nexts.isEmpty()) { // x是基层员工的时候
			return new Info(x.happy,0);
		}
		int lai = x.happy; // x来的情况下，整棵树最大收益
		int bu = 0; // x不来的情况下，整棵树最大收益
		for(Employee next : x.nexts) {
			Info nextInfo = process(next);
			lai += nextInfo.buMaxHappy;
			bu += Math.max(nextInfo.laiMaxHappy, nextInfo.buMaxHappy);
		}
		return new Info(lai,bu);
	}
	
	
	
	
	
	
	

	public static void main(String[] args) {
		int[][] matrix = { { 1, 8 }, { 1, 9 }, { 1, 10 } };
		System.out.println(maxHappy(matrix));
	}
}
