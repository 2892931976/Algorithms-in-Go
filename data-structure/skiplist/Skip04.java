package com.chj.skiplist;

import java.util.Random;

public class Skip04<T> {
	   private SkipListNode04<T> head,tail; //����ͷβ
	    private int size; //�����С
	    private int listLevel; //�������
	    private Random random; // ����Ͷ��Ӳ��
	    private static final double PROBABILITY=0.5; //��������һ���ĸ���

	    public Skip04() {
	        head = new SkipListNode04<>(SkipListNode04.HEAD_KEY,null);
	        tail = new SkipListNode04<>(SkipListNode04.TAIL_KEY,null);

	        head.next = tail;
	        tail.pre = head;

	        size = 0;
	        listLevel = 0;
	        random = new Random();
	    }

	    public SkipListNode04<T> get(int key){
	    	SkipListNode04<T> p = findNode(key);
	        if (p.key == key){
	            return p;
	        }
	        return null;
	    }

	    /**
	     * ���Ȳ��ҵ�����keyֵ�Ľڵ㣬���ڵ���������Ƴ�����������и���level�Ľڵ㣬��repeat����������ɡ�
	     * @param k
	     * @return
	     */
	    public T remove(int k){
	    	SkipListNode04<T> p = get(k);
	        if (p == null){
	            return null;
	        }

	        T oldV = p.value;
	        SkipListNode04<T> q;

	        while (p != null){
	            q = p.next;
	            q.pre = p.pre;
	            p.pre.next = q;

	            p = p.up;
	        }


	        return oldV;
	    }

	    /**
	     * put������һЩ��Ҫע��Ĳ��裺
	     * 1.���put��keyֵ����Ծ���д��ڣ�������޸Ĳ�����
	     * 2.���put��keyֵ����Ծ���в����ڣ�����Ҫ���������ڵ�Ĳ�����������Ҫ��random����������¼���Ľڵ�ĸ߶ȣ����level����
	     * 3.������ӵĽڵ�߶ȴﵽ��Ծ������level����Ҫ���һ���հײ㣨����-oo��+ooû�б�Ľڵ㣩
	     * @param k
	     * @param v
	     */
	    public void put(int k,T v){
	        //step 1
	        System.out.println("���key��"+k);
	        SkipListNode04<T> p = findNode(k); //���ﲻ��get����Ϊ��������õ�����ڵ�
//	        System.out.println("�ҵ�p:"+p);
	        if (p.key == k){
	            p.value = v;
	            return;
	        }

	        //step 2
	        SkipListNode04<T> q=new SkipListNode04<T>(k, v);
	        insertNode(p, q);

	        int currentLevel = 0;
	        while (random.nextDouble() > PROBABILITY){
	            if (currentLevel>=listLevel){
	                addEmptyLevel();
	                System.out.println("����");
	            }
	            //�ҵ���ߵ�һ�����ϲ�ڵ�����ݣ�
	            while (p.up == null){
//	                System.out.println(p);
	                p = p.pre;
//	                System.out.println("�ҵ���һ�����ϲ�Ľڵ�"+p);
	            }
	            p = p.up;
	            //����q�ľ��������ֻ�洢k�����洢v����Ϊ���ҵ�ʱ����Զ�����ײ����ݣ���
	            SkipListNode04<T> z=new SkipListNode04<T>(k, null);
	            insertNode(p,z);
	            z.down = q;
	            q.up=z;

	            //�����˰�ָ��ת�Ƶ���һ�㣡��
	            q=z;
	            currentLevel++;
	        }
	        size++;
//	        System.out.println("��Ӻ�"+this);
	    }

	    private void addEmptyLevel() {
	    	SkipListNode04<T> p1=new SkipListNode04<T>(SkipListNode04.HEAD_KEY, null);
	    	SkipListNode04<T> p2=new SkipListNode04<T>(SkipListNode04.TAIL_KEY, null);
	        p1.next = p2;
	        p1.down = head;

	        p2.pre = p1;
	        p2.down = tail;

	        head.up = p1;
	        tail.up = p2;

	        head=p1;
	        tail=p2;

	        listLevel++;

	    }

	    private void insertNode(SkipListNode04<T> p, SkipListNode04<T> q) {
	        q.next = p.next;
	        q.pre = p;
	        p.next.pre = q;
	        p.next = q;
	    }

	    /**
	     * ��������keyֵ����Ծ���в����ڣ���findNode������Ծ����keyֵС��key������keyֵ�����С�ĵײ�ڵ�;
	     * ���Բ����ô˷���������get
	     * @param key
	     * @return
	     */
	    private SkipListNode04<T> findNode(int key){
	    	SkipListNode04<T> p = head;
	        while (true){
//	            System.out.println("p.next.key:"+p.next.key);
	            if (p.next!=null && p.next.key<=key){
	                p = p.next;
	            }
//	            System.out.println("�ҵ�node:"+p);
	            //�ҵ���ײ�����
	            if (p.down != null){
//	                System.out.println("node.down:"+p);
	                p = p.down;
	            }else if (p.next!=null && p.next.key > key){
	                break;
	            }
	        }

	        return p;
	    }

	    @Override
	    public String toString() {
	        // TODO Auto-generated method stub
	        if (isEmpty()) {
	            return "��Ծ��Ϊ�գ�";
	        }
	        StringBuilder builder=new StringBuilder();
	        SkipListNode04<T> p=head;
	        while (p.down!=null) {
	            p=p.down;
	        }

	        while (p.pre!=null) {
	            p=p.pre;
	        }
	        if (p.next!=null) {
	            p=p.next;
	        }
	        while (p.next!=null) {
	            builder.append(p);
	            builder.append("\n");
	            p=p.next;
	        }

	        return builder.toString();
	    }

	    public boolean isEmpty() {
	        return size == 0;
	    }

	    public int size() {
	        return size;
	    }

	    public int getLevel(){
	        return listLevel;
	    }
	    
		public void printHorizontal() {
			String s = "";
			int i;

			SkipListNode04<T> p;

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
				p = p.next;
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

		public String getOneRow(SkipListNode04<T> p) {
			String s;
			int a, b, i;

			a = 0;

			s = "" + p.key;
			p = p.next;

			while (p != null) {
				SkipListNode04<T> q;

				q = p;
				while (q.down != null)
					q = q.down;
				b = q.pos;

				s = s + " <-";

				for (i = a + 1; i < b; i++)
					s = s + "--------";

				s = s + "> " + p.key;

				a = b;

				p = p.next;
			}

			return (s);
		}

	    public static void main(String[] args) {
	        // TODO Auto-generated method stub
	    	Skip04<String> list=new Skip04<String>();
	        list.put(2, "yan");
	        list.put(1, "co");
	        list.put(3, "feng");
	        list.put(1, "cao");//����ͬһ��keyֵ
	        list.put(4, "��");
	        list.put(6, "��");
	        list.put(5, "��");
	        list.put(11, "��");
	        list.put(8, "��");
	        list.put(100, "��");
	        list.printHorizontal();
	        System.out.println("�б�Ԫ�أ�\n"+list);
	        System.out.println("ɾ��100��"+list.remove(100));
	        list.printHorizontal();
	        System.out.println("�б�Ԫ�أ�\n"+list);
	        System.out.println("5���ڵ�value��\n"+list.get(5).value);
	        System.out.println("�����С��"+list.size()+",��ȣ�"+list.getLevel());
	    }
}


