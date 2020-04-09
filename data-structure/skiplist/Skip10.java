package com.chj.skiplist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class Skip10 {

	/**
	 * 1�������һ��ʵ�ַ�����������ϰ�������д洢���������������Ҵ洢���ǲ��ظ��ġ�
	 * 2�������ǲο�����zheng ���Լ�ѧϰ���Ż�����ӷ���
	 * 3������������Ҿ����ٿ�ConcurrentSkipListMap Դ�룬���кܴ��ջ�
	 * Author��ldb
	 */
//	public class SkipList2 {

	    private static final int MAX_LEVEL = 16;
	    private int levelCount = 1;

	    /**
	     * ��ͷ����
	     */
	    private Node head = new Node(MAX_LEVEL);
	    private Random r = new Random();

	    public Node find(int value) {
	        Node p = head;
	        // �����㿪ʼ���ң��ҵ�ǰһ�ڵ㣬ͨ��--i���ƶ����²��ٿ�ʼ����
	        for (int i = levelCount - 1; i >= 0; --i) {
	            while (p.forwards[i] != null && p.forwards[i].data < value) {
	                // �ҵ�ǰһ�ڵ�
	                p = p.forwards[i];
	            }
	        }

	        if (p.forwards[0] != null && p.forwards[0].data == value) {
	            return p.forwards[0];
	        } else {
	            return null;
	        }
	    }

	    /**
	     * �Ż�������zheng�Ĳ��뷽��
	     *
	     * @param value ֵ
	     */
	    public void insert(int value) {
	        int level = head.forwards[0] == null ? 1 : randomLevel();
	        // ÿ��ֻ����һ�㣬�����������
	        if (level > levelCount) {
	            level = ++levelCount;
	        }
	        Node newNode = new Node(level);
	        newNode.data = value;
	        Node update[] = new Node[level];
	        for (int i = 0; i < level; ++i) {
	            update[i] = head;
	        }

	        Node p = head;
	        // �����㿪ʼ���ң��ҵ�ǰһ�ڵ㣬ͨ��--i���ƶ����²��ٿ�ʼ����
	        for (int i = levelCount - 1; i >= 0; --i) {
	            while (p.forwards[i] != null && p.forwards[i].data < value) {
	                // �ҵ�ǰһ�ڵ�
	                p = p.forwards[i];
	            }
	            // levelCount �� > level�����Լ����ж�
	            if (level > i) {
	                update[i] = p;
	            }

	        }
	        for (int i = 0; i < level; ++i) {
	            newNode.forwards[i] = update[i].forwards[i];
	            update[i].forwards[i] = newNode;
	        }

	    }

	    /**
	     * �Ż�������zheng�Ĳ��뷽��2
	     *
	     * @param value ֵ
	     */
	    public void insert2(int value) {
//	    	System.out.println("====null====");
//	    	System.out.println(head.forwards[0] == null ? 1 : head.forwards[0].toString());
//	    	System.out.println(head.forwards[0] == null);
//	    	System.out.println("====null====");
	        int level = head.forwards[0] == null ? 1 : randomLevel();
//	        System.out.println("====level===="+head.forwards[0] == null);
//	        System.out.println("====level===="+level);
//	        System.out.println("====levelCount===="+levelCount);
	        // ÿ��ֻ����һ�㣬�����������
//	        �� a = ++i���൱�� i=i+1; a = i;
//	        �� a = i++���൱�� a = i; i=i+1;
//	        ++ i ���ȼӺ�ֵ��
//	        i ++ ���ȸ�ֵ��ӣ�
	        if (level > levelCount) {
	            level = ++levelCount;
	        }
	        
	        System.out.println("==222==level===="+level);
	        System.out.println("==222==levelCount===="+levelCount);
	        
	        Node newNode = new Node(level);
	        newNode.data = value;
	        Node p = head;
	        // �����㿪ʼ���ң��ҵ�ǰһ�ڵ㣬ͨ��--i���ƶ����²��ٿ�ʼ����
	        for (int i = levelCount - 1; i >= 0; --i) {
	            while (p.forwards[i] != null && p.forwards[i].data < value) {
	                // �ҵ�ǰһ�ڵ�
	                p = p.forwards[i];
	            }
	            // levelCount �� > level�����Լ����ж�
	            if (level > i) {
	                if (p.forwards[i] == null) {
	                    p.forwards[i] = newNode;
	                } else {
	                    Node next = p.forwards[i];
	                    p.forwards[i] = newNode;
	                    newNode.forwards[i] = next;
	                }
	            }

	        }

	    }

	    /**
	     * ����zheng�Ĳ��뷽����δ�Ż�ǰ���Ż���μ�����insert()
	     *
	     * @param value
	     * @param level 0 ��ʾ�����������Ϊ0����ʾָ��������ָ������
	     *              ������ÿ�δ�ӡ������䶯��������Ϊ�˱���ѧϰ���
	     */
	    public void insert(int value, int level) {
	    	
//	    	System.out.println("===head===="+head.toString());
	        // ���һ������
	        if (level == 0) {
	            level = randomLevel();
	        }
	        // �����½ڵ�
	        Node newNode = new Node(level);
	        newNode.data = value;
	        // ��ʾ�����㵽�Ͳ㣬��Ҫ�нڵ�����
	        newNode.maxLevel = level;
	        // ��¼Ҫ���µĲ�������ʾ�½ڵ�Ҫ���µ��ļ���
	        Node update[] = new Node[level];
	        for (int i = 0; i < level; ++i) {
	            update[i] = head;
	        }

	        /**
	         *
	         * 1��˵�������Ǵ��µ��ϵģ��������²�����0�����ϲ�����15
	         * 2������û�д������������㣨�����󣩿�ʼ�ң����������������㣩������Щ���⡣
	         *    ���������Ϊ1�ڣ����level=1 ����ô����ʱ�临�Ӷ�ΪO��n��
	         */
	        Node p = head;
	        for (int i = level - 1; i >= 0; --i) {
	            while (p.forwards[i] != null && p.forwards[i].data < value) {
	                p = p.forwards[i];
	            }
	            // ����update[i]��ʾ��ǰ��ڵ��ǰһ�ڵ㣬��ΪҪ�ҵ�ǰһ�ڵ㣬�źò�������
	            update[i] = p;
	        }

	        // ��ÿһ��ڵ�ͺ���ڵ����
	        for (int i = 0; i < level; ++i) {
	            // ��¼��ǰ��ڵ����ڵ�ָ��
	            newNode.forwards[i] = update[i].forwards[i];
	            // ǰһ���ڵ��ָ�룬ָ��ǰ�ڵ�
	            update[i].forwards[i] = newNode;
	        }

	        // ���²��
	        if (levelCount < level) levelCount = level;
	    }

	    public void delete(int value) {
	        Node[] update = new Node[levelCount];
	        Node p = head;
	     // �����㿪ʼ���ң��ҵ�ǰһ�ڵ㣬ͨ��--i���ƶ����²��ٿ�ʼ����
	        for (int i = levelCount - 1; i >= 0; --i) {
	            while (p.forwards[i] != null && p.forwards[i].data < value) {
	                p = p.forwards[i];
	            }
	            update[i] = p;
	        }

	        if (p.forwards[0] != null && p.forwards[0].data == value) {
	            for (int i = levelCount - 1; i >= 0; --i) {
	                if (update[i].forwards[i] != null && update[i].forwards[i].data == value) {
	                    update[i].forwards[i] = update[i].forwards[i].forwards[i];
	                }
	            }
	        }
	    }

	    /**
	     * ��� level �Σ�������������� +1����ֹα���
	     *
	     * @return
	     */
	    private int randomLevel() {
	        int level = 1;
	        for (int i = 1; i < MAX_LEVEL; ++i) {
	            if (r.nextInt() % 2 == 1) {
	                level++;
	            }
	        }
	        return level;
	    }

	    /**
	     * ��ӡÿ���ڵ����ݺ�������
	     */
	    public void printAll() {
	        Node p = head;
	        int i = 0;
	        while (p.forwards[0] != null) {
	            System.out.print(p.forwards[0] + " ");
	            p = p.forwards[0];
	            i++;
	        }
	        System.out.println(i);
	    }

	    /**
	     * ��ӡ��������
	     */
	    public void printAll_beautiful() {
	        Node p = head;
	        Node[] c = p.forwards;
	        Node[] d = c;
	        int maxLevel = c.length;
	        for (int i = maxLevel - 1; i >= 0; i--) {
	            do {
	                System.out.print((d[i] != null ? d[i].data : null) + ":" + i + "-------");
	            } while (d[i] != null && (d = d[i].forwards)[i] != null);
	            System.out.println();
	            d = c;
	        }
	    }

	    /**
	     * ����Ľڵ㣬ÿ���ڵ��¼�˵�ǰ�ڵ����ݺ����ڲ�������
	     */
	    public class Node {
	        private int data = -1;
	        /**
	         * ��ʾ��ǰ�ڵ�λ�õ���һ���ڵ����в�����ݣ����ϲ��л����²㣬���������±�-1��
	         * forwards[3]��ʾ��ǰ�ڵ��ڵ��������һ���ڵ㡣
	         */
	        private Node forwards[];

	        /**
	         * ���ֵ��ʵ���Բ��ã����Ż�insert()
	         */
	        private int maxLevel = 0;

	        public Node(int level) {
	            forwards = new Node[level];
	        }

	        @Override
//	        public String toString() {
//	            StringBuilder builder = new StringBuilder();
//	            builder.append("{ data: ");
//	            builder.append(data);
//	            builder.append("; levels: ");
//	            builder.append(maxLevel);
//	            builder.append(" }");
//	            return builder.toString();
//	        }
//	        
			public String toString() {
				return "[data=" + data + ", maxLevel=" + maxLevel + ", next=" + forwards.length + " ] -->";
			}
	    }
	    
		//��������  �޵�һ��
		public Map<Integer, List<Node>> lookUp(){
			Map<Integer, List<Node>> map = new HashMap<Integer, List<Node>>();
			List<Node> nodes;
			for(int i = 0; i < head.forwards.length; i ++){
				nodes = new ArrayList<Node>();
				for(Node current = head; current != null; current = current.forwards[i]){
					nodes.add(current);
				}
				map.put(i,nodes);
			}
			return map;
		}
	 
		public void show(Map<Integer, List<Node>> map){
			for(int i = map.size() - 1; i >= 0; i --){
				List<Node> list = map.get(i);
				StringBuffer sb = new StringBuffer("��"+i+"��:");
				
				for(Iterator<Node> it = list.iterator(); it.hasNext();){
					sb.append(it.next().toString());
				}
				System.out.println(sb.substring(0,sb.toString().lastIndexOf("-->")));
			}
		}

	    public static void main(String[] args) {
	    	Skip10 list = new Skip10();
	        list.insert(1, 3);
	        list.insert(2, 3);
	        list.insert(3, 2);
	        list.insert(4, 4);
	        list.insert(5, 10);
	        list.insert(6, 4);
	        list.insert(8, 5);
	        list.insert(7, 4);
	        list.show(list.lookUp());
//	        list.printAll_beautiful();
//	        list.printAll();
	        /**
	         * ������£�
	         * 									    null:15-------
	         * 									    null:14-------
	         * 									    null:13-------
	         * 									    null:12-------
	         * 									    null:11-------
	         * 									    null:10-------
	         * 										   5:9-------
	         * 										   5:8-------
	         * 										   5:7-------
	         * 										   5:6-------
	         * 										   5:5-------
	         * 										   5:4-------					 8:4-------
	         * 							     4:3-------5:3-------6:3-------7:3-------8:3-------
	         * 1:2-------2:2-------		     4:2-------5:2-------6:2-------7:2-------8:2-------
	         * 1:1-------2:1-------3:1-------4:1-------5:1-------6:1-------7:1-------8:1-------
	         * 1:0-------2:0-------3:0-------4:0-------5:0-------6:0-------7:0-------8:0-------
	         * { data: 1; levels: 3 } { data: 2; levels: 3 } { data: 3; levels: 2 } { data: 4; levels: 4 }
	         * { data: 5; levels: 10 } { data: 6; levels: 4 } { data: 7; levels: 4 } { data: 8; levels: 5 }
	         */
	        
	        System.out.println("�Ż���");
	        // �Ż���insert()

	        Skip10 list2 = new Skip10();
	        list2.insert2(1);
	        list2.insert2(2);
	        list2.insert2(6);
	        list2.insert2(7);
	        list2.insert2(8);
	        list2.insert2(3);
	        list2.insert2(4);
	        list2.insert2(5);
	        System.out.println();
//	        list2.printAll_beautiful();
	        list2.show(list2.lookUp());
	        System.out.println(list2.find(8));
	        System.out.println(list2.find(9));
	    }
	}

//}
