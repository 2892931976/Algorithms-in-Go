package class02;

import java.util.HashMap;

public class Test {
	
	public static int sumEqualKMaxLen(int[] arr, int k) {
		if(arr == null || arr.length == 0) {
			return 0;
		}
		HashMap<Integer, Integer> map = new HashMap<>();
		map.put(0, -1);
		int sum= 0;
		int ans = 0;
		for(int i = 0 ; i < arr.length;i++) {
			sum += arr[i]; // 0 ~ i 累加和
			if(map.containsKey(sum - k)) {
				ans = Math.max(ans, i - map.get(sum - k));
			}
			if(!map.containsKey(sum)) {
				map.put(sum,i);
			}
		}
		return ans;
	}

}
