package com.chj.skiplist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//public class Skip05 {
//
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}

import java.util.Random;


/**
 * ����
 * 
 * @author ��·������
 *
 */
public class Skip05<E extends Comparable<? super E>> {

    /*
     * �������32�㣺 �����32�������϶���2^32-1��Ԫ�صĲ�ѯ���š�
     */
    private final int MAX_LEVEL = 32;
    /*
     * ��ǰ�������Ч��
     */
    private int level = 0;
    /*
     * �����ͷ���ڵ�
     */
    private final SkipNode<E> Header = new SkipNode<E>(MAX_LEVEL, null);
    /*
     * �����������
     */
    private final Random random = new Random();
    /*
     * ��Ȼ��e
     */
    private final double E = Math.E;

    /**
     * ����������Ƿ����val�ڵ�
     * 
     * @param node
     * @return
     */
    public boolean contains(E val) {
        /*
         * curָ������ͷ���
         */
        SkipNode<E> cur = Header;
        /*
         * �Ӷ��㿪ʼ���ҵ�ǰ����������Ƿ�����ڵ�node���������node�ڵ㣬ֱ�ӷ���true����������һ���в����Ƿ����node�ڵ㡣
         * �����ײ������Ҳ������node�ڵ㣬��Ż�false��
         */
        for (int i = level; i >= 0; i--) {
            while (cur.next != null && cur.next[i].val.compareTo(val) < 0) {
                cur = cur.next[i];
            }
            if (cur.next[i].val.equals(val)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void insert(E val) {
        SkipNode<E> cur = Header;
        SkipNode<E>[] predecessors = new SkipNode[MAX_LEVEL];
        /*
         * �ҳ�ÿ���д�����ڵ��ǰ�̽ڵ�
         */
        for (int i = level; i >= 0; i--) {
            cur = Header;
            while (cur.next[i] != null && cur.next[i].val.compareTo(val) < 0) {
                cur = cur.next[i];
            }
            predecessors[i] = cur;
        }
        cur = cur.next[0];
        int nextLevel = randomLevel();
        System.out.println("over"+nextLevel);
        /*
         * ���������ڵ�λ���ǿյĻ�����node�ڵ�ֵ��ͬ ���½ڵ���뵽������
         */
        if (cur == null || !cur.val.equals(val)) {
            /*
             * ������һ������
             */
            if (nextLevel > level) {
                predecessors[nextLevel] = Header;
                level = nextLevel;
            }
            SkipNode<E> node = new SkipNode(MAX_LEVEL, val);
            for (int i = level; i >= 0; i--) {
                node.next[i] = predecessors[i].next[i];
                predecessors[i].next[i] = node;
            }
        }
    }

    @SuppressWarnings({ "unchecked" })
    public void delete(E val) {
        SkipNode<E> cur = Header;
        SkipNode<E>[] predecessors = new SkipNode[MAX_LEVEL];
        /*
         * Ѱ�Ҵ�ɾ��Ԫ���ڲ�ͬ���ϵ�ǰ�̽ڵ�
         */
        for (int i = level; i >= 0; i--) {
            while (cur.next != null && cur.next[i].val.compareTo(val) < 0) {
                cur = cur.next[i];
            }
            predecessors[i] = cur;
        }
        cur = cur.next[0];
        /*
         * �����в����˽ڵ�
         */
        if (!cur.val.equals(val)) {
            return;
        }
        for (int i = level; i >= 0; i--) {
            if (!predecessors[i].next[i].val.equals(val)) {
                continue;
            }
            predecessors[i].next[i] = cur.next[i];
        }
        /*
         * ���ɾ��Ԫ��val��level��Ԫ����ĿΪ0����������һ��
         */
        while (level > 0 && Header.next[level] == null) {
            level--;
        }

    }

    /**
     * ��������е�Ԫ��
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        SkipNode<E> cur = Header.next[0];
        sb.append("{");
        while (cur.next[0] != null) {
            sb.append(cur.val);
            sb.append(",");
            cur = cur.next[0];
        }
        sb.append(cur.val);
        sb.append("}");
        return sb.toString();
    }

    /**
     * ����������������������Ƿ�����һ��
     * 
     * @return
     */
    private int randomLevel() {
        double ins = random.nextDouble();
        int nextLevel = level;
        System.out.println("rand"+ ins*E*2);
        System.out.println("rand"+ E);
        if (ins*E*2 > E && level < MAX_LEVEL) {
            nextLevel++;
        }
        return nextLevel;
    }
    
	public Map<Integer, List<SkipNode<E>>> lookUp(){
		Map<Integer, List<SkipNode<E>>> map = new HashMap<Integer, List<SkipNode<E>>>();
		List<SkipNode<E>> nodes;
		for(int i = 0; i < Header.next.length; i ++){
			nodes = new ArrayList<SkipNode<E>>();
			for(SkipNode<E> current = Header; current != null; current = current.next[i]){
//				System.out.println("lookUp");
//				System.out.println(i);
//				System.out.println(current);
//				System.out.println("lookUp");
				nodes.add(current);
			}
			map.put(i,nodes);
//			System.out.println("lookUp");
//			show(map);
//			System.out.println("lookUp");
		}
		return map;
	}
 
	public void show(Map<Integer, List<SkipNode<E>>> map){
		for(int i = map.size() - 1; i >= 0; i --){
			List<SkipNode<E>> list = map.get(i);
			StringBuffer sb = new StringBuffer("��"+i+"��:");
			for(Iterator<SkipNode<E>> it = list.iterator(); it.hasNext();){
				sb.append(it.next().toString());
			}
			System.out.println(sb.substring(0,sb.toString().lastIndexOf("-->")));
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Skip05<Integer> l = new Skip05<Integer>();
//		l.insert("yan");
//		l.insert("feng");
//		l.insert("cao");
//		l.insert("��");
		Random r = new Random();
//		int[] values = {4, 8, 16, 10, 14, 12, 18};
		for (int i = 0; i < 10; i++) {
			int tmp = r.nextInt(100);
//			System.out.println("add:" + values[i]);
			l.insert(tmp);
////			l.printHorizontal();
			l.show(l.lookUp());
////			System.out.println(l.toString());
		}
//		l.show(l.lookUp());
		
//		System.out.println("==================remove==============");
//		for (int i = 0; i < 10; i++) {
//			int tmp = r.nextInt(100);
//			System.out.println("remove:" + tmp);
//			System.out.println("main0001"+ l.remove(tmp));
//			l.printHorizontal();
//		}

	}
}

class SkipNode<E extends Comparable<? super E>> {
    /*
     * �ڵ�洢��ֵVal
     */
    public E val;
    /*
     * �ڵ�ָ���i��Ľڵ�next[i]
     */
    public SkipNode<E>[] next;

    @SuppressWarnings("unchecked")
    public SkipNode(int MAX_LEVEL, E val) {
        this.next = new SkipNode[MAX_LEVEL];
        this.val = val;
    }
    
	public String toString(){
		return "[data="+val+", height="+next.length+"] -->";
	}
}
