package com.chj.skiplist;

import java.util.Random;

public class Skip08 {

    public SkipListEntry08 head;    // First element of the top level
    public SkipListEntry08 tail;    // Last element of the top level

    public int n;        // number of entries in the Skip List
    public int h;        // Height

    public Random r;    // Coin toss

    // constructor
    public Skip08() {
    	SkipListEntry08 p1, p2;

        // ����һ�� -oo ��һ�� +oo ����
        p1 = new SkipListEntry08(SkipListEntry08.negInf, null);
        p2 = new SkipListEntry08(SkipListEntry08.posInf, null);

        // �� -oo �� +oo �໥����
        p1.right = p2;
        p2.left = p1;

        // �� head �� tail ��ʼ��
        head = p1;
        tail = p2;

        n = 0;
        h = 0;
        r = new Random();
    }

    private SkipListEntry08 findEntry(String key) {

    	SkipListEntry08 p;

        // ��headͷ�ڵ㿪ʼ����
        p = head;

        while (true) {
            // �������Ҳ��ң�ֱ���ҽڵ��keyֵ����Ҫ���ҵ�keyֵ
            while (p.right.key != SkipListEntry08.posInf
                    && p.right.key.compareTo(key) <= 0) {
                p = p.right;
            }

            // ����и��Ͳ�Ľڵ㣬����Ͳ��ƶ�
            if (p.down != null) {
                p = p.down;
            } else {
                break;
            }
        }

        // ����p����ע������p��keyֵ��С�ڵ��ڴ���key��ֵ�ģ�p.key <= key��
        return p;
    }

    public Integer get(String key) {

    	SkipListEntry08 p;

        p = findEntry(key);

        if (p.key.equals(key)) {
            return p.value;
        } else {
            return null;
        }
    }

    public Integer put(String key, Integer value) {

    	SkipListEntry08 p, q;
        int i = 0;

        // �����ʺϲ����λ��
        p = findEntry(key);

        // �����Ծ���д��ں���keyֵ�Ľڵ㣬�����value���޸Ĳ����������
        if (p.key.equals(key)) {
            Integer oldValue = p.value;
            p.value = value;
            return oldValue;
        }

        // �����Ծ���в����ں���keyֵ�Ľڵ㣬�������������
        q = new SkipListEntry08(key, value);
        q.left = p;
        q.right = p.right;
        p.right.left = q;
        p.right = q;

        // ��ʹ������������Ƿ�Ҫ�����level����
        while (r.nextDouble() < 0.5) {

            // �����Ԫ�صļ����Ѿ��ﵽ��Ծ������߶ȣ����½��հײ�
            if (i >= h) {
                addEmptyLevel();
            }

            // ��p����ɨ�躬�и߲�ڵ�Ľڵ�
            while (p.up == null) {
                p = p.left;
            }
            p = p.up;

            // ������qָ��ָ��Ľڵ㺬����ͬkeyֵ�Ľڵ����
            // ������Ҫע����ǳ��ײ�ڵ�֮��Ľڵ�����ǲ���Ҫvalueֵ��
            SkipListEntry08 z = new SkipListEntry08(key, null);

            z.left = p;
            z.right = p.right;
            p.right.left = z;
            p.right = z;

            z.down = q;
            q.up = z;

            q = z;
            i = i + 1;
        }

        n = n + 1;

        // ����null��û�оɽڵ��valueֵ
        return null;
    }

    private void addEmptyLevel() {

    	SkipListEntry08 p1, p2;

        p1 = new SkipListEntry08(SkipListEntry08.negInf, null);
        p2 = new SkipListEntry08(SkipListEntry08.posInf, null);

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

    public Integer remove(String key) {

    	SkipListEntry08 p, q;

        p = findEntry(key);

        if (!p.key.equals(key)) {
            return null;
        }

        Integer oldValue = p.value;
        while (p != null) {
            q = p.up;
            p.left.right = p.right;
            p.right.left = p.left;
            p = q;
        }

        return oldValue;
    }
    
	public void printHorizontal() {
		String s = "";
		int i;

		SkipListEntry08 p;

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

	public String getOneRow(SkipListEntry08 p) {
		String s;
		int a, b, i;

		a = 0;

		s = "" + p.key;
		p = p.right;

		while (p != null) {
			SkipListEntry08 q;

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
    	Skip08 list=new Skip08();
        list.put("yan", 2);
        list.put("cao", 1);
        list.put("feng", 3);
        list.put("cao", 111);//����ͬһ��keyֵ
        list.put("��", 4);
        list.put("��", 6);
        list.put("��", 5);
        list.put("��", 11);
        list.put("��", 8);
        list.put("��", 100);
        list.printHorizontal();
        System.out.println("�б�Ԫ�أ�\n"+list);
        System.out.println("ɾ��100��"+list.remove("��"));
        list.printHorizontal();
        System.out.println("�б�Ԫ�أ�\n"+list);
        System.out.println("5���ڵ�value��\n"+list.get("��"));
//        System.out.println("�����С��"+list.size()+",��ȣ�"+list.getLevel());
    }
}
