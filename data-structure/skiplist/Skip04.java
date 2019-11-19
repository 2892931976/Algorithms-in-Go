package com.chj.skiplist;

import java.util.Random;

public class Skip04<T> {
	   private SkipListNode04<T> head,tail; //链表头尾
	    private int size; //链表大小
	    private int listLevel; //链表层数
	    private Random random; // 用于投掷硬币
	    private static final double PROBABILITY=0.5; //向上提升一个的概率

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
	     * 首先查找到包含key值的节点，将节点从链表中移除，接着如果有更高level的节点，则repeat这个操作即可。
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
	     * put方法有一些需要注意的步骤：
	     * 1.如果put的key值在跳跃表中存在，则进行修改操作；
	     * 2.如果put的key值在跳跃表中不存在，则需要进行新增节点的操作，并且需要由random随机数决定新加入的节点的高度（最大level）；
	     * 3.当新添加的节点高度达到跳跃表的最大level，需要添加一个空白层（除了-oo和+oo没有别的节点）
	     * @param k
	     * @param v
	     */
	    public void put(int k,T v){
	        //step 1
	        System.out.println("添加key："+k);
	        SkipListNode04<T> p = findNode(k); //这里不用get是因为下面可能用到这个节点
//	        System.out.println("找到p:"+p);
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
	                System.out.println("升层");
	            }
	            //找到左边第一个有上层节点的数据；
	            while (p.up == null){
//	                System.out.println(p);
	                p = p.pre;
//	                System.out.println("找到第一个有上层的节点"+p);
	            }
	            p = p.up;
	            //创建q的镜像变量（只存储k，不存储v，因为查找的时候会自动找最底层数据），
	            SkipListNode04<T> z=new SkipListNode04<T>(k, null);
	            insertNode(p,z);
	            z.down = q;
	            q.up=z;

	            //别忘了把指针转移到上一层！！
	            q=z;
	            currentLevel++;
	        }
	        size++;
//	        System.out.println("添加后："+this);
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
	     * 如果传入的key值在跳跃表中不存在，则findNode返回跳跃表中key值小于key，并且key值相差最小的底层节点;
	     * 所以不能用此方法来代替get
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
//	            System.out.println("找到node:"+p);
	            //找到最底层数据
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
	            return "跳跃表为空！";
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
	        list.put(1, "cao");//测试同一个key值
	        list.put(4, "曹");
	        list.put(6, "丰");
	        list.put(5, "艳");
	        list.put(11, "陈");
	        list.put(8, "李");
	        list.put(100, "边");
	        list.printHorizontal();
	        System.out.println("列表元素：\n"+list);
	        System.out.println("删除100："+list.remove(100));
	        list.printHorizontal();
	        System.out.println("列表元素：\n"+list);
	        System.out.println("5对于的value：\n"+list.get(5).value);
	        System.out.println("链表大小："+list.size()+",深度："+list.getLevel());
	    }
}


