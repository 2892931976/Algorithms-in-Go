package com.chj.skiplist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Skip11 {

	public static class SkipListNode {
		public Integer value;
		public ArrayList<SkipListNode> nextNodes;

		public SkipListNode(Integer value) {
			this.value = value;
			nextNodes = new ArrayList<SkipListNode>();
		}
		
		public String toString(){
			return "[data="+value+", height="+nextNodes.size()+"] -->";
		}
	}

	public static class SkipListIterator implements Iterator<Integer> {
		SkipList list;
		SkipListNode current;

		public SkipListIterator(SkipList list) {
			this.list = list;
			this.current = list.getHead();
		}

		public boolean hasNext() {
			return current.nextNodes.get(0) != null;
		}

		public Integer next() {
			current = current.nextNodes.get(0);
			return current.value;
		}
	}

	public static class SkipList {
		private SkipListNode head;
		private int maxLevel;
		private int size;
		private static final double PROBABILITY = 0.5;

		public SkipList() {
			size = 0;
			maxLevel = 0;
			head = new SkipListNode(null);
			head.nextNodes.add(null);
		}

		public SkipListNode getHead() {
			return head;
		}

		public void add(Integer newValue) {
			if (!contains(newValue)) {
				size++;
				int level = 0;
				while (Math.random() < PROBABILITY) {
					level++;
				}
				while (level > maxLevel) {
					head.nextNodes.add(null);
					maxLevel++;
				}
				SkipListNode newNode = new SkipListNode(newValue);
				SkipListNode current = head;
				do {
					current = findNext(newValue, current, level);
					newNode.nextNodes.add(0, current.nextNodes.get(level));
					current.nextNodes.set(level, newNode);
				} while (level-- > 0);
			}
		}

		public void delete(Integer deleteValue) {
			if (contains(deleteValue)) {
				SkipListNode deleteNode = find(deleteValue);
				size--;
				int level = maxLevel;
				SkipListNode current = head;
				do {
					current = findNext(deleteNode.value, current, level);
					if (deleteNode.nextNodes.size() > level) {
						current.nextNodes.set(level, deleteNode.nextNodes.get(level));
					}
				} while (level-- > 0);
			}
		}

		// Returns the skiplist node with greatest value <= e
		private SkipListNode find(Integer e) {
			return find(e, head, maxLevel);
		}

		// Returns the skiplist node with greatest value <= e
		// Starts at node start and level
		private SkipListNode find(Integer e, SkipListNode current, int level) {
			do {
				current = findNext(e, current, level);
			} while (level-- > 0);
			return current;
		}

		// Returns the node at a given level with highest value less than e
		private SkipListNode findNext(Integer e, SkipListNode current, int level) {
			SkipListNode next = current.nextNodes.get(level);
			while (next != null) {
				Integer value = next.value;
				if (lessThan(e, value)) { // e < value
					break;
				}
				current = next;
				next = current.nextNodes.get(level);
			}
			return current;
		}

		public int size() {
			return size;
		}

		public boolean contains(Integer value) {
			SkipListNode node = find(value);
			return node != null && node.value != null && equalTo(node.value, value);
		}

		public Iterator<Integer> iterator() {
			return new SkipListIterator(this);
		}

		/******************************************************************************
		 * Utility Functions *
		 ******************************************************************************/

		private boolean lessThan(Integer a, Integer b) {
			return a.compareTo(b) < 0;
		}

		private boolean equalTo(Integer a, Integer b) {
			return a.compareTo(b) == 0;
		}
		
		// 遍历跳表 限第一层
		public Map<Integer, List<SkipListNode>> lookUp() {
			Map<Integer, List<SkipListNode>> map = new HashMap<Integer, List<SkipListNode>>();
			List<SkipListNode> nodes;
			for (int i = 0; i < head.nextNodes.size(); i++) {
				nodes = new ArrayList<SkipListNode>();
				for (SkipListNode current = head; current != null; current = current.nextNodes.get(i)) {
					nodes.add(current);
				}
				map.put(i, nodes);
			}
			return map;
		}

		public void show(Map<Integer, List<SkipListNode>> map) {
			for (int i = map.size() - 1; i >= 0; i--) {
				List<SkipListNode> list = map.get(i);
				StringBuffer sb = new StringBuffer("第" + i + "层:");
				for (Iterator<SkipListNode> it = list.iterator(); it.hasNext();) {
					sb.append(it.next().toString());
				}
				System.out.println(sb.substring(0, sb.toString().lastIndexOf("-->")));
			}
		}

	}
	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SkipList list = new SkipList();
		list.show(list.lookUp());
		int[] data = { 4, 8, 16, 10, 14 };
		System.out.println(Arrays.toString(data));
//		list.addArrayToSkipList(data);
		list.add(4);
		list.add(8);
		list.add(16);
		list.add(10);
		list.add(14);
		list.add(22);
		list.show(list.lookUp());
		System.out.println("在本次跳表中查找15的节点或前驱节点为：" + list.find(15));
		System.out.println("在本次跳表中查找12的节点或前驱节点为：" + list.find(12) + "\n");
		list.add(12);
		list.add(12);
		list.add(18);
		list.show(list.lookUp());
		System.out.println("在本次跳表中查找15的节点或前驱节点为：" + list.find(15));
		System.out.println("在本次跳表中查找12的节点或前驱节点为：" + list.find(12) + "\n");
		list.delete(12);
		System.out.println("删除节点值为12后的跳表为：");
		list.show(list.lookUp());
	}

}
