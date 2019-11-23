package class07;

public class Problem04_Kiki {

	public static int minCcoins1(int add, int times, int del, int start, int end) {
		if (start > end) {
			return -1;
		}
		return process(0, end, add, times, del, start, end * 2, ((end - start) / 2) * add);
	}

	// preMoney 之前花了多少币     可变
	// end 目标     可变
	// int add, int times, int del,     固定
	// start当前来到的人气
	// limitAim人气大到什么程度不需要再尝试了   固定
	// limitCoin已经使用的币大到什么程度不需要再尝试了   固定
	// 返回最小币数
	public static int process(int preMoney, int end, 
			int add, int times, int del,
			int start, 
			int limitAim, int limitCoin) {
		if (preMoney > limitCoin) {
			return Integer.MAX_VALUE;
		}
		if (start < 0) {
			return Integer.MAX_VALUE;
		}
		if (start > limitAim) {
			return Integer.MAX_VALUE;
		}
		if (end == start) {
			return preMoney;
		}
		int min = Integer.MAX_VALUE;
		// 让人气+2的方式
		int p1 = process(preMoney + add, end, add, times, del, start+2, limitAim, limitCoin);
		if (p1 != Integer.MAX_VALUE) {
			min = p1;
		}
		// 让人气-2的方式
		int p2 = process(preMoney + del, end, add, times, del, start-2, limitAim, limitCoin);
		if (p2 != Integer.MAX_VALUE) {
			min = Math.min(min, p2);
		}
		int p3 = process(preMoney + times, end, add, times, del, start*2, limitAim, limitCoin);
		if (p3 != Integer.MAX_VALUE) {
			min = Math.min(min, p3);
		}
		return min;
	}

	public static int minCcoins2(int add, int times, int del, int start, int end) {
		if (start > end) {
			return -1;
		}
		int limitCoin = ((end - start) / 2) * add;
		int limitAim = end * 2;
		int[][] dp = new int[limitCoin + 1][limitAim + 1];
		for (int pre = 0; pre <= limitCoin; pre++) {
			for (int aim = 0; aim <= limitAim; aim++) {
				if (aim == start) {
					dp[pre][aim] = pre;
				} else {
					dp[pre][aim] = Integer.MAX_VALUE;
				}
			}
		}
		for (int pre = limitCoin; pre >= 0; pre--) {
			for (int aim = 0; aim <= limitAim; aim++) {
				if (aim - 2 >= 0 && pre + add <= limitCoin) {
					dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + add][aim - 2]);
				}
				if (aim + 2 <= limitAim && pre + del <= limitCoin) {
					dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + del][aim + 2]);
				}
				if ((aim & 1) == 0) {
					if (aim / 2 >= 0 && pre + times <= limitCoin) {
						dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + times][aim / 2]);
					}
				}
			}
		}
		return dp[0][end];
	}
	
	
	
	// 初始的尝试版本
	// 点赞，人气+2
	// 送礼，人气*2
	// 私聊，人气-2
	public static int process(int start, int end, int dianC, int songC, int siC) {
		if(start == end) {
			return 0;
		}
		int p1= process(start + 2, end, dianC, songC, siC) + dianC;
		int p2 = process(start * 2, end, dianC, songC, siC) + songC;
		int p3 = process(start - 2, end, dianC, songC, siC) + siC;
		return Math.min(p1, Math.min(p2, p3));
	}
	
	
	
	
	
	public static int coins(int start, int end, int x, int y, int z) {
		return func(x, y, x, end, start);
	}
	
	
	// 想要达到的目标是end
	// 目前达到的分数是cur
	// 三种决策：
	// 1 ) cur + 2
	// 2 ) cur * 2
	// 3 ) cur - 2
	public static int func(int x, int y, int z, int end, int cur) {
		if(cur == end) {
			return 0;
		}
		int p1 = process(x, y, z, end, cur + 2) + x;
		int p2 = process(x, y, z, end, cur * 2) + y;
		int p3 = process(x, y, z, end, cur - 2 ) + z;
		return Math.min(p1, Math.min(p2, p3));
	}
	
	
	
	
	
	public static int func2(int x, int y, int z, int end, int cur, 
			int renqiMin, int renqiMax, int commonCoins, int already) {
		if(cur == end) {
			return already;
		}
		if(cur < 0) {
			return Integer.MAX_VALUE;
		}
		if(cur > end * 2) {
			return Integer.MAX_VALUE;
		}
		if(already > commonCoins) {
			return Integer.MAX_VALUE;
		}
		
		int p1 = func2(x, y, z, end, cur + 2, renqiMin,renqiMax,commonCoins, already + x  );
		int p2 = func2(x, y, z, end, cur * 2, renqiMin,renqiMax,commonCoins, already + y  );
		int p3 = func2(x, y, z, end, cur - 2, renqiMin,renqiMax,commonCoins, already + z  );
		
		return Math.min(p1, Math.min(p2, p3));
	}
	
	
	
	
	
	

	public static void main(String[] args) {
		int add = 6;
		int times = 5;
		int del = 1;
		int start = 10;
		int end = 30;
		System.out.println(minCcoins1(add, times, del, start, end));
		System.out.println(minCcoins2(add, times, del, start, end));
	}

}
