package com.chj.tree.rbtree03.printer;

public abstract class Printer {	
	/**
	 * �������Ļ�����Ϣ
	 */
	protected BinaryTreeInfo tree;
	
	public Printer(BinaryTreeInfo tree) {
		this.tree = tree;
	}
	
	/**
	 * ���ɴ�ӡ���ַ���
	 */
	public abstract String printString();
	
	/**
	 * ��ӡ����
	 */
	public void println() {
		print();
		System.out.println();
	}
	
	/**
	 * ��ӡ
	 */
	public void print() {
		System.out.print(printString());
	}
}