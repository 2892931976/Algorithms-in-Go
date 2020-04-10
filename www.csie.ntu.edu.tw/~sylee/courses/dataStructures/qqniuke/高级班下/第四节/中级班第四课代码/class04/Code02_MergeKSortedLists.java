package class12;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Code02_MergeKSortedLists {

	public static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public static class ListNodeComparator implements Comparator<ListNode> {

		@Override
		public int compare(ListNode o1, ListNode o2) {
			return o1.val - o2.val;
		}

	}

	public static ListNode mergeKLists(ListNode[] lists) {
		PriorityQueue<ListNode> heap = new PriorityQueue<>(new ListNodeComparator());
		for (int i = 0; i < lists.length; i++) {
			if (lists[i] != null) {
				heap.add(lists[i]);
			}
		}
		ListNode head = null;
		ListNode pre = null;
		while (!heap.isEmpty()) {
			ListNode cur = heap.poll();
			head = head == null ? cur : head;
			if (pre != null) {
				pre.next = cur;
			}
			pre = cur;
			if (cur.next != null) {
				heap.add(cur.next);
			}
		}
		return head;
	}

}
