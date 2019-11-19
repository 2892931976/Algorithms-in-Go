package com.chj.skiplist;

import java.util.Arrays;
import java.util.Random;

public class Skip03 {

	class SkipListEntry {
		Integer key;
		Integer value;
		SkipListEntry right;
		SkipListEntry left;
		SkipListEntry down;
		SkipListEntry up;

		public SkipListEntry(Integer key, Integer value) {
			this.key = key;
			this.value = value;
		}

		public String toString() {
			return "(" + key + "," + value + ")";
		}

		public int pos;// �����ݽṹ�޹أ�ֻΪ�������
	}

	// number of entries in the Skip List
	public int n;
	// height
	public int h;
	// ��ͷ
	private SkipListEntry head;
	// ��β
	private SkipListEntry tail;
	// ����randomLevel�õ��ĸ���ֵ
	private Random r;

	public Skip03() {

		head = new SkipListEntry(Integer.MIN_VALUE, Integer.MIN_VALUE);
		tail = new SkipListEntry(Integer.MAX_VALUE, Integer.MAX_VALUE);
		head.right = tail;
		tail.left = head;
		n = 0;
		h = 0;
		r = new Random();
	}

	/**
	 * ����
	 * 
	 * @param searchKey
	 * @return
	 */
	public SkipListEntry findEntry(Integer key) {
		SkipListEntry p;

		/*
		 * ----------------- Start at "head" -----------------
		 */
		p = head;

		while (true) {
			/*
			 * -------------------------------------------- Search RIGHT until you find a
			 * LARGER entry E.g.: k = 34 10 ---> 20 ---> 30 ---> 40 ^ | p stops here
			 * p.right.key = 40 --------------------------------------------
			 */
			while (p.right.key != Integer.MAX_VALUE && p.right.key < key) {
				p = p.right;
				System.out.println("find >>>> " + p.key);
			}

			/*
			 * --------------------------------- Go down one level if you can...
			 * ---------------------------------
			 */
			if (p.down != null) {
				p = p.down;
				 System.out.println("vvvv " + p.key);
			} else
				break; // We reached the LOWEST level... Exit...
		}

		return (p); // p.key <= k
	}

	public Integer get(int key) {

		SkipListEntry p;

		p = findEntry(key);

		if (p.key == key) {
			return p.value;
		} else {
			return null;
		}

	}

	public Integer insert(int key, int value) {
		SkipListEntry p, q;
		int i = 0;

		// �����ʺϲ����λ��
		p = findEntry(key);

		// �����Ծ���д��ں���keyֵ�Ľڵ㣬�����value���޸Ĳ����������
		if (p.key == key) {
			Integer oldValue = p.value;
			p.value = value;
			return oldValue;
		}

		// �����Ծ���в����ں���keyֵ�Ľڵ㣬�������������
		q = new SkipListEntry(key, value);
		/*
		 * -------------------------------------------------------------- Insert q into
		 * the lowest level after SkipListEntry p: p put q here p q | | | | V V V V V
		 * Lower level: [ ] <------> [ ] ==> [ ] <--> [ ] <--> [ ]
		 * ---------------------------------------------------------------
		 */
		q.left = p;
		q.right = p.right;
		p.right.left = q;
		p.right = q;

		// ���������ϣ������߲����
		// ��Ӳ����������Ƿ��ϲ����
		while (r.nextDouble() < 0.5 /* Coin toss */ ) {
			if (i >= h) // We reached the top level !!!
			{
				// Create a new empty TOP layer
				addEmptyLevel();
			}
			/*
			 * ------------------------------------ Find first element with an UP-link
			 * ------------------------------------
			 */
			while (p.up == null) {
				p = p.left;
			}
			/*
			 * -------------------------------- Make p point to this UP element
			 * --------------------------------
			 */
			p = p.up;

			/*
			 * --------------------------------------------------- Add one more (k,*) to the
			 * column Schema for making the linkage: p <--> e(k,*) <--> p.right ^ | v q
			 * ----------------------------------------------------
			 */
			SkipListEntry e;
			// ������Ҫע����ǳ��ײ�ڵ�֮��Ľڵ�����ǲ���Ҫvalueֵ��
			e = new SkipListEntry(key, null);
			/*
			 * --------------------------------------- Initialize links of e
			 * ---------------------------------------
			 */
			e.left = p;
			e.right = p.right;
			e.down = q;
			/*
			 * --------------------------------------- Change the neighboring links..
			 * ---------------------------------------
			 */
			p.right.left = e;
			p.right = e;
			q.up = e;

			// ��qִ���²���Ľڵ㣺
			q = e;
			// level����
			i = i + 1;

		}
		n = n + 1; // ����������
		return null;
	}

	private void addEmptyLevel() {

		SkipListEntry p1, p2;

		p1 = new SkipListEntry(Integer.MIN_VALUE, null);
		p2 = new SkipListEntry(Integer.MAX_VALUE, null);

		p1.right = p2;
		p1.down = head;

		p2.left = p1;
		p2.down = tail;

		head.up = p1;
		tail.up = p2;

		head = p1;
		tail = p2;

		h = h + 1;
	}

	public Integer remove(int key) {

		SkipListEntry p, q, curret;

		p = findEntry(key);
		System.out.println("remove 0001");
		System.out.println(p.toString());
		System.out.println("remove 0001");
//		if (!p.key.equals(key)) {
//			return null;
//		}
		curret = p.right;

		if (!curret.key.equals(key)) {
			return null;
		}

		System.out.println("remove 0002");
		System.out.println(curret.key);
		System.out.println(curret.toString());
		System.out.println("remove 0002");

		Integer oldValue = curret.value;
		while (curret != null) {
			q = curret.up;
			curret.left.right = curret.right;
			curret.right.left = curret.left;
			curret = q;
		}

		return oldValue;
	}

	public void printHorizontal() {
		String s = "";
		int i;

		SkipListEntry p;

		/*
		 * ---------------------------------- Record the position of each entry
		 * ----------------------------------
		 */
		p = head;

		while (p.down != null) {
			p = p.down;
		}

		i = 0;
		while (p != null) {
			p.pos = i++;
			p = p.right;
		}

		/*
		 * ------------------- Print... -------------------
		 */
		p = head;

		while (p != null) {
			s = getOneRow(p);
			System.out.println(s);

			p = p.down;
		}
		System.out.println();
	}

	public String getOneRow(SkipListEntry p) {
		String s;
		int a, b, i;

		a = 0;

		s = "" + p.key;
		p = p.right;

		while (p != null) {
			SkipListEntry q;

			q = p;
			while (q.down != null)
				q = q.down;
			b = q.pos;

			s = s + " <-";

			for (i = a + 1; i < b; i++)
				s = s + "--------";

			s = s + "> " + p.key;

			a = b;

			p = p.right;
		}

		return (s);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Skip03 l = new Skip03();
//		Random r = new Random();
		int[] values = {4, 8, 16, 10, 14, 12, 18};
		for (int i = 0; i < values.length; i++) {
//			int tmp = r.nextInt(100);
			System.out.println("add:" + values[i]);
			l.insert(values[i], values[i]);
			l.printHorizontal();
		}
//		System.out.println("==================remove==============");
//		for (int i = 0; i < 10; i++) {
//			int tmp = r.nextInt(100);
//			System.out.println("remove:" + tmp);
//			System.out.println("main0001"+ l.remove(tmp));
//			l.printHorizontal();
//		}


		System.out.println("over");

		l.remove(12);
		l.printHorizontal();

		System.out.println(l.findEntry(16));
	}

}
