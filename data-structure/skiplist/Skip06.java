package com.chj.skiplist;

import java.util.Random;

public class Skip06<T> {
	private SkipListNode06<T> head, tail;
	private int nodes;// �ڵ�����
	private int listLevel;// ����
	private Random random;// ����Ͷ��Ӳ��
	private static final double PROBABILITY = 0.5;// ��������һ���ĸ���

	public Skip06() {
		// TODO Auto-generated constructor stub
		random = new Random();
		clear();
	}

	/**
	 * �����Ծ��
	 */
	public void clear() {
		head = new SkipListNode06<T>(SkipListNode06.HEAD_KEY, null);
		tail = new SkipListNode06<T>(SkipListNode06.TAIL_KEY, null);
		horizontalLink(head, tail);
		listLevel = 0;
		nodes = 0;
	}

	public boolean isEmpty() {
		return nodes == 0;
	}

	public int size() {
		return nodes;
	}

	/**
	 * ��������һ�㣬�ҵ�Ҫ�����λ��ǰ����Ǹ�key
	 */
	private SkipListNode06<T> findNode(int key) {
		SkipListNode06<T> p = head;
		while (true) {
			while (p.right.key != SkipListNode06.TAIL_KEY && p.right.key <= key) {
				p = p.right;
			}
			if (p.down != null) {
				p = p.down;
			} else {
				break;
			}

		}
		return p;
	}

	/**
	 * �����Ƿ�洢key�������򷵻ظýڵ㣬���򷵻�null
	 */
	public SkipListNode06<T> search(int key) {
		SkipListNode06<T> p = findNode(key);
		if (key == p.getKey()) {
			return p;
		} else {
			return null;
		}
	}

	/**
	 * ����Ծ�������key-value
	 * 
	 */
	public void put(int k, T v) {
		SkipListNode06<T> p = findNode(k);
		// ���keyֵ��ͬ���滻ԭ����vaule���ɽ���
		if (k == p.getKey()) {
			p.value = v;
			return;
		}
		SkipListNode06<T> q = new SkipListNode06<T>(k, v);
		backLink(p, q);
		int currentLevel = 0;// ��ǰ���ڵĲ㼶��0
		// ��Ӳ��
		while (random.nextDouble() < PROBABILITY) {
			// ��������˸߶ȣ���Ҫ���½�һ������
			if (currentLevel >= listLevel) {
				listLevel++;
				SkipListNode06<T> p1 = new SkipListNode06<T>(SkipListNode06.HEAD_KEY, null);
				SkipListNode06<T> p2 = new SkipListNode06<T>(SkipListNode06.TAIL_KEY, null);
				horizontalLink(p1, p2);
				vertiacallLink(p1, head);
				vertiacallLink(p2, tail);
				head = p1;
				tail = p2;
			}
			// ��p�ƶ�����һ��
			while (p.up == null) {
				p = p.left;
			}
			p = p.up;

			SkipListNode06<T> e = new SkipListNode06<T>(k, null);// ֻ����key��ok
			backLink(p, e);// ��e���뵽p�ĺ���
			vertiacallLink(e, q);// ��e��q��������
			q = e;
			currentLevel++;
		}
		nodes++;// ��������
	}

	// node1�������node2
	private void backLink(SkipListNode06<T> node1, SkipListNode06<T> node2) {
		node2.left = node1;
		node2.right = node1.right;
		node1.right.left = node2;
		node1.right = node2;
	}

	/**
	 * ˮƽ˫������
	 */
	private void horizontalLink(SkipListNode06<T> node1, SkipListNode06<T> node2) {
		node1.right = node2;
		node2.left = node1;
	}

	/**
	 * ��ֱ˫������
	 */
	private void vertiacallLink(SkipListNode06<T> node1, SkipListNode06<T> node2) {
		node1.down = node2;
		node2.up = node1;
	}

	/**
	 * ��ӡ��ԭʼ����
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			return "��Ծ��Ϊ�գ�";
		}
		StringBuilder builder = new StringBuilder();
		SkipListNode06<T> p = head;
		while (p.down != null) {
			p = p.down;
		}

		while (p.left != null) {
			p = p.left;
		}
		if (p.right != null) {
			p = p.right;
		}
		while (p.right != null) {
			builder.append(p);
			builder.append("\n");
			p = p.right;
		}

		return builder.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Skip06<String> list = new Skip06<String>();
		System.out.println(list);
		list.put(2, "yan");
		list.put(1, "co");
		list.put(3, "feng");
		list.put(1, "cao");// ����ͬһ��keyֵ
		list.put(4, "��");
		list.put(6, "��");
		list.put(5, "��");
		System.out.println(list);
		System.out.println(list.size());
	}
}
