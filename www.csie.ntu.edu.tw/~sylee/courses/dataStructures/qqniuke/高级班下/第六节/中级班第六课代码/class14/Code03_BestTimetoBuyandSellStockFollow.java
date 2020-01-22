package class14;

public class Code03_BestTimetoBuyandSellStockFollow {

	public static int maxProfit(int K, int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int N = prices.length;
		if (K >= N / 2) {
			return allTrans(prices);
		}
		int[] dp = new int[N];
		int ans = 0;
		for (int tran = 1; tran <= K; tran++) {
			int pre = dp[0];
			int best = pre - prices[0];
			for (int index = 1; index < N; index++) {
				pre = dp[index];
				dp[index] = Math.max(dp[index - 1], prices[index] + best);
				best = Math.max(best, pre - prices[index]);
				ans = Math.max(dp[index], ans);
			}
		}
		return ans;
	}

	public static int allTrans(int[] prices) {
		if (prices == null || prices.length == 0) {
			return 0;
		}
		int ans = 0;
		int min = prices[0];
		int max = prices[0];
		for (int i = 1; i < prices.length; i++) {
			if (prices[i] >= prices[i - 1]) {
				max = prices[i];
			} else {
				ans += max - min;
				min = prices[i];
				max = prices[i];
			}
		}
		return ans + max - min;
	}

	public static int allTrans(int[] prices, int N) {
		int ans = 0;
		for (int i = 1; i < N; i++) {
			if (prices[i] > prices[i - 1]) {
				ans += prices[i] - prices[i - 1];
			}
		}
		return ans;
	}

}
