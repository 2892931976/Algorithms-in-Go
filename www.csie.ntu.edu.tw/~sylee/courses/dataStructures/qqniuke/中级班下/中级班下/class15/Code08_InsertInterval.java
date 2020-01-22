package class15;

import java.util.ArrayList;
import java.util.List;

public class Code08_InsertInterval {

	public static class Interval {
		int start;
		int end;

		Interval() {
			start = 0;
			end = 0;
		}

		Interval(int s, int e) {
			start = s;
			end = e;
		}
	}

	// items中，区间从小到大出现的，无重复区域
	// 
	public List<Interval> insert(List<Interval> items, Interval newItems) {
		List<Interval> result = new ArrayList<>();
		int i = 0;
		// 左侧不会被影响到的区间，原封不动放入结果序列中
		while (i < items.size() && items.get(i).end < newItems.start) {
			result.add(items.get(i++));
		}
		
		// i 结尾了;要么，items.get(i).end >= newItems.start
		while (i < items.size() && items.get(i).start <= newItems.end) {
			newItems.start = Math.min(newItems.start, items.get(i).start);
			newItems.end = Math.max(newItems.end, items.get(i).end);
			i++;
		}
		result.add(newItems);
		while (i < items.size()) {
			result.add(items.get(i++));
		}
		return result;
	}

}
