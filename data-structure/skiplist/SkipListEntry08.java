package com.chj.skiplist;

public class SkipListEntry08 {

	// data
	public String key;
	public Integer value;

	// links
	public SkipListEntry08 up;
	public SkipListEntry08 down;
	public SkipListEntry08 left;
	public SkipListEntry08 right;

	// special
	public static final String negInf = "-oo";
	public static final String posInf = "+oo";

	// constructor
	public SkipListEntry08(String key, Integer value) {
		this.key = key;
		this.value = value;
	}
	
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "key-value:"+key+"："+value;
    }

	public int pos;// 与数据结构无关，只为输出方便
	// methods...
}
