package com.chj.skiplist;

public class SkipListNode04<T> {
	    public int key;
	    public T value;
	    public SkipListNode04<T> pre,next,up,down; //上下左右四个节点，pre和up存在的意义在于 "升层"的时候需要查找相邻节点

	    public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
	    public static final int  TAIL_KEY = Integer.MAX_VALUE; // 正无穷
	    public SkipListNode04(int k,T v) {
	        key = k;
	        value = v;
	    }
	    public int getKey() {
	        return key;
	    }
	    public void setKey(int key) {
	        this.key = key;
	    }
	    public T getValue() {
	        return value;
	    }
	    public void setValue(T value) {
	        this.value = value;
	    }
	    public boolean equals(Object o) {
	        if (this==o) {
	            return true;
	        }
	        if (o==null) {
	            return false;
	        }
	        if (!(o instanceof SkipListNode04<?>)) {
	            return false;
	        }
	        SkipListNode04<T> ent;
	        try {
	            ent = (SkipListNode04<T>)  o; // 检测类型
	        } catch (ClassCastException ex) {
	            return false;
	        }
	        return (ent.getKey() == key) && (ent.getValue() == value);
	    }
	    @Override
	    public String toString() {
	        // TODO Auto-generated method stub
	        return "key-value:"+key+"："+value;
	    }
	    
	    public int pos;// 与数据结构无关，只为输出方便
	}
