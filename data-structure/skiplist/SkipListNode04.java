package com.chj.skiplist;

public class SkipListNode04<T> {
	    public int key;
	    public T value;
	    public SkipListNode04<T> pre,next,up,down; //���������ĸ��ڵ㣬pre��up���ڵ��������� "����"��ʱ����Ҫ�������ڽڵ�

	    public static final int HEAD_KEY = Integer.MIN_VALUE; // ������
	    public static final int  TAIL_KEY = Integer.MAX_VALUE; // ������
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
	            ent = (SkipListNode04<T>)  o; // �������
	        } catch (ClassCastException ex) {
	            return false;
	        }
	        return (ent.getKey() == key) && (ent.getValue() == value);
	    }
	    @Override
	    public String toString() {
	        // TODO Auto-generated method stub
	        return "key-value:"+key+"��"+value;
	    }
	    
	    public int pos;// �����ݽṹ�޹أ�ֻΪ�������
	}
