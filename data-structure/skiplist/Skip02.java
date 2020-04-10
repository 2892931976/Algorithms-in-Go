package com.chj.skiplist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Arrays;

// �����д洢���������������Ҵ洢�������ǲ��ظ���
public class Skip02 {

	private static final int MAX_LEVEL = 16; // ���ĸ���

	private int levelCount = 1; // �����Ĳ㼶��

	private Node head = new Node(); // ͷ���

	private Random random = new Random();

	// ���Ҳ���
	public Node find(int value) {
		Node p = head;
		for (int i = levelCount - 1; i >= 0; --i) {
			while (p.next[i] != null && p.next[i].data < value) {
				p = p.next[i];
			}
		}

		System.out.println("find 0001" + p.toString());
		if (p.next[0] != null && p.next[0].data == value) {
			return p.next[0]; // �ҵ����򷵻�ԭʼ�����еĽ��
		} else {
			return null;
		}
	}

	// �������
	public void insert(int value) {
		int level = randomLevel();
		Node newNode = new Node();
		newNode.data = value;
		newNode.maxLevel = level; // ͨ����������ı�������Ľ�㲼��
		Node update[] = new Node[level];
		for (int i = 0; i < level; ++i) {
			update[i] = head;
		}

		Node p = head;
//		System.out.println("show(lookUp())-");
//		System.out.println(level);
//		System.out.println(Arrays.toString(update));
//		show(lookUp());
//		System.out.println("show(lookUp())-");
		for (int i = level - 1; i >= 0; --i) {
			while (p.next[i] != null && p.next[i].data < value) {
//				System.out.println("leavel-" + i);
//				System.out.println(level);
//				System.out.println(p.toString());
//				System.out.println(p.next[i].toString());
//				System.out.println("leavel-" + i);
				p = p.next[i];
			}
			update[i] = p;
		}

//		System.out.println("show(lookUp())-33");
//		System.out.println(Arrays.toString(update));
//		show(lookUp());
//		System.out.println("show(lookUp())-33");

		for (int i = 0; i < level; ++i) {
//			System.out.println(update[i].next[i]);
			newNode.next[i] = update[i].next[i];
			update[i].next[i] = newNode;
		}

//		System.out.println("show(lookUp())-22");
//		show(lookUp());
//		System.out.println("show(lookUp())-22");
		if (levelCount < level) {
			levelCount = level;
		}
	}

	// ɾ������
	public void delete(int value) {
		Node[] update = new Node[levelCount];
		Node p = head;
		for (int i = levelCount - 1; i >= 0; --i) {
			while (p.next[i] != null && p.next[i].data < value) {
				p = p.next[i];
			}
			update[i] = p;
		}

		if (p.next[0] != null && p.next[0].data == value) {
			for (int i = levelCount - 1; i >= 0; --i) {
				if (update[i].next[i] != null && update[i].next[i].data == value) {
					update[i].next[i] = update[i].next[i].next[i];
				}
			}
		}
	}

	// �������
	private int randomLevel() {
		int level = 1;
		for (int i = 1; i < MAX_LEVEL; ++i) {
			if (random.nextInt() % 2 == 1) {
				level++;
			}
		}

		return level;
	}

	// Node�ڲ���
	public class Node {
		private int data = -1;
		private Node next[] = new Node[MAX_LEVEL];
		private int maxLevel = 0;

		// ��дtoString����
//		@Override
//		public String toString() {
//			StringBuilder builder = new StringBuilder();
//			builder.append("{data:");
//			builder.append(data);
//			builder.append("; leves: ");
//			builder.append(maxLevel);
//			builder.append(" }");
//			return builder.toString();
//		}

		public String toString() {
			return "[data=" + data + ", maxLevel=" + maxLevel + ", next=" + next.length + " ] -->";
		}
	}

	// ��ʾ�����еĽ��
	public void display() {
		Node p = head;
		while (p.next[0] != null) {
			System.out.println(p.next[0] + " ||");
			p = p.next[0];
		}
		System.out.println();
	}

	public Map<Integer, List<Node>> lookUp() {
		Map<Integer, List<Node>> map = new HashMap<Integer, List<Node>>();
		List<Node> nodes;
		for (int i = 0; i < head.next.length; i++) {
			nodes = new ArrayList<Node>();
			for (Node current = head; current != null; current = current.next[i]) {
				nodes.add(current);
			}
			map.put(i, nodes);
		}
		return map;
	}

	public void show(Map<Integer, List<Node>> map) {
		for (int i = map.size() - 1; i >= 0; i--) {
			List<Node> list = map.get(i);
			StringBuffer sb = new StringBuffer("��" + i + "��:");
			for (Iterator<Node> it = list.iterator(); it.hasNext();) {
				sb.append(it.next().toString());
			}
			System.out.println(sb.substring(0, sb.toString().lastIndexOf("-->")));
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Skip02 list = new Skip02();
		System.out.println("============" + list.levelCount);
//		list.show(list.lookUp());
		list.insert(4);
		list.insert(8);
		list.insert(16);
		list.insert(10);
		list.insert(14);
		list.insert(12);
		list.insert(18);
		System.out.println("============" + list.levelCount);
		System.out.println("find: " + list.find(15));
		System.out.println("find: " + list.find(12));
		list.display();
		list.show(list.lookUp());
		
		list.delete(12);
		System.out.println("ɾ���ڵ�ֵΪ12�������Ϊ��");
		list.show(list.lookUp());
	}
}
