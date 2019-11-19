package com.chj.skiplist;

public class SkipListNode06<T> {
	public int key;
	public T value;
	public SkipListNode06<T> up, down, left, right; // 上下左右 四个指针

	public static final int HEAD_KEY = Integer.MIN_VALUE; // 负无穷
	public static final int TAIL_KEY = Integer.MAX_VALUE; // 正无穷

	public SkipListNode06(int k, T v) {
		// TODO Auto-generated constructor stub
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
		if (this == o) {
			return true;
		}
		if (o == null) {
			return false;
		}
		if (!(o instanceof SkipListNode06<?>)) {
			return false;
		}
		SkipListNode06<T> ent;
		try {
			ent = (SkipListNode06<T>) o; // 检测类型
		} catch (ClassCastException ex) {
			return false;
		}
		return (ent.getKey() == key) && (ent.getValue() == value);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "key-value:" + key + "-" + value;
	}
}
