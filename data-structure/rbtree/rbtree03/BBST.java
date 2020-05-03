package com.chj.tree.rbtree03;

import java.util.Comparator;

public class BBST<E> extends BST<E> {
	public BBST() {
		this(null);
	}
	
	public BBST(Comparator<E> comparator) {
		super(comparator);
	}

	protected void rotateLeft(Node<E> grand) {
		Node<E> parent = grand.right;
		Node<E> child = parent.left;
		grand.right = child;
		parent.left = grand;
		afterRotate(grand, parent, child);
	}
	
	protected void rotateRight(Node<E> grand) {
		Node<E> parent = grand.left;
		Node<E> child = parent.right;
		grand.left = child;
		parent.right = grand;
		afterRotate(grand, parent, child);
	}
	
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		// ��parent��Ϊ�����ĸ��ڵ�
		parent.parent = grand.parent;
		if (grand.isLeftChild()) {
			grand.parent.left = parent;
		} else if (grand.isRightChild()) {
			grand.parent.right = parent;
		} else { // grand��root�ڵ�
			root = parent;
		}
		
		// ����child��parent
		if (child != null) {
			child.parent = grand;
		}
		
		// ����grand��parent
		grand.parent = parent;
	}
	
	protected void rotate(
			Node<E> r, // �����ĸ��ڵ�
			Node<E> b, Node<E> c,
			Node<E> d,
			Node<E> e, Node<E> f) {
		// ��d��Ϊ��������ĸ��ڵ�
		d.parent = r.parent;
		if (r.isLeftChild()) {
			r.parent.left = d;
		} else if (r.isRightChild()) {
			r.parent.right = d;
		} else {
			root = d;
		}
		
		//b-c
		b.right = c;
		if (c != null) {
			c.parent = b;
		}
		
		// e-f
		f.left = e;
		if (e != null) {
			e.parent = f;
		}
		
		// b-d-f
		d.left = b;
		d.right = f;
		b.parent = d;
		f.parent = d;
	}
}
