package test;

public class Joseph {

	// 课上的方法
	public static int getLive1(int N, int M) {
		int num = 1;
		int live = 1;
		while (num < N) {
			live = (live + M - 1) % (++num) + 1;
		}
		return live;
	}

	// 学员方法
	public static int getLive2(int N, int M) {
		int pre = 1;
		if (N > 1) {
			for (int i = 2; i <= N; i++) {
				pre += M;
				if (pre > i) {
					pre = pre % i == 0 ? i : (pre / i * i);
				}
			}
		}
		return pre;
	}

	public static void main(String[] args) {
		int N = 5;
		int M = 3;
		System.out.println(getLive1(N, M));
		System.out.println(getLive2(N, M));
	}

}
