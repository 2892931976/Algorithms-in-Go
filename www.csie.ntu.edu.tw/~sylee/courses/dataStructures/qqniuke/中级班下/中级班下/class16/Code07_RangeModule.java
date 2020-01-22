package class16;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Code07_RangeModule {

	public static class RangeModule {
		TreeMap<Integer, Integer> map;

		public RangeModule() {
			map = new TreeMap<>();
		}

		// [left, right)
		public void addRange(int left, int right) {
			if (right <= left) {
				return;
			}
			
			Integer start1 = map.floorKey(left);
			Integer start2 = map.floorKey(right);
			
			
			if (start1 == null && start2 == null) { // 新加的区间谁也不影响，并且最小
				map.put(left, right);
			} else if (start1 != null && map.get(start1) >= left) { // start1, start2不都为空
				map.put(start1, Math.max(map.get(start2), right));
			} else {
				// 0) start1 == null & start2 == null  X
				// 1) start1 == null & start2 != null 
				// 3) start1 != null & start2 == null  X
				// 4) start1 != null & start2 != null & map.get(start1) >= left  X
				// 5) start1 != null & start2 != null & map.get(start1) < left
				map.put(left, Math.max(map.get(start2), right));
			}
			// clean up intermediate intervals
			// 得到所有start在(left, right]
			Map<Integer, Integer> subMap = map.subMap(left, false, right, true);
			Set<Integer> set = new HashSet<>(subMap.keySet());
			map.keySet().removeAll(set);
		}

		//  [5,6)
		// <=5 s   e
		public boolean queryRange(int left, int right) {
			Integer start = map.floorKey(left);
			if (start == null)
				return false;
			return map.get(start) >= right;
		}

		public void removeRange(int left, int right) {
			if (right <= left)
				return;
			Integer start1 = map.floorKey(left);
			Integer start2 = map.floorKey(right);
			if (start2 != null && map.get(start2) > right) {
				map.put(right, map.get(start2));
			}
			if (start1 != null && map.get(start1) > left) {
				map.put(start1, left);
			}
			// clean up intermediate intervals
			Map<Integer, Integer> subMap = map.subMap(left, true, right, false);
			Set<Integer> set = new HashSet<>(subMap.keySet());
			map.keySet().removeAll(set);
		}
	}

	public static void main(String[] args) {
		RangeModule r = new RangeModule();
		r.addRange(1, 9);
		r.removeRange(2, 7);
		System.out.println(r.queryRange(3, 5));
	}

}
