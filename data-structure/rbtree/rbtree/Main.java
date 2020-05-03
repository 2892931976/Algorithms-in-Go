package com.chj.tree.rbtree;

import java.util.ArrayList;
import java.util.List;

import com.chj.tree.rbtree.printer.BinaryTreeInfo;
import com.chj.tree.rbtree.printer.BinaryTrees;
import com.chj.tree.rbtree.BST;
import com.chj.tree.rbtree.BinaryTree;
import com.chj.tree.rbtree.RBTree;
import com.chj.tree.rbtree.BinaryTree.Visitor;

@SuppressWarnings("unused")
public class Main {

	static void test3() {
		Integer data[] = new Integer[] {
				55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
		};
		
		RBTree<Integer> rb = new RBTree<>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
			System.out.println("¡¾" + data[i] + "¡¿");
			BinaryTrees.println(rb);
			System.out.println("---------------------------------------");
		}
		
		BinaryTrees.println(rb);
	}

	static void test4() {
		Integer data[] = new Integer[] {
				55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
		};
		
		RBTree<Integer> rb = new RBTree<>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
		}

		BinaryTrees.println(rb);

		for (int i = 0; i < data.length; i++) {
			rb.remove(data[i]);
			System.out.println("---------------------------------------");
			System.out.println("¡¾" + data[i] + "¡¿");
			BinaryTrees.println(rb);
		}
	}
	
	public static void main(String[] args) {
//		test3();
		test4();
	}
}
