package com.chj.tree.rbtree;

import java.util.Comparator;

public class RBTree<E> extends BBST<E> {
	private static final boolean RED = false;
	private static final boolean BLACK = true;

	public RBTree() {
		this(null);
	}

	public RBTree(Comparator<E> comparator) {
		super(comparator);
	}

	// 添加的节点默认是红色
	@Override
	protected void afterAdd(Node<E> node) {
		Node<E> parent = node.parent;

		// 上溢LL,RR,LR,RL
		// 添加的是根节点 或者 上溢到达了根节点
		if (parent == null) {
			black(node);
			return;
		}

		// 第一种情况
		// 如果父节点是黑色，直接返回
		if (isBlack(parent))
			return;

		// 叔父节点
		Node<E> uncle = parent.sibling();
		// 祖父节点
		Node<E> grand = parent.parent;
		// 上溢LL,RR,LR,RL
		if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
			black(parent);
			black(uncle);

			// 祖父节点向上合并
			// 把祖父节点变成红色,当做是新添加的节点
			// 把祖父节点当做是新添加的节点
			afterAdd(red(grand));
			return;
		}

		// LL,RR,LR,RL
		// 叔父节点不是红色
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				black(parent);
				red(grand);
				rotateRight(grand);
			} else { // LR
				black(node);
				red(grand);
				rotateLeft(parent);
				rotateRight(grand);
			}

		} else { // R
			if (node.isLeftChild()) { // RL
				black(node);
				red(grand);
				rotateRight(parent);
				rotateLeft(grand);
			} else { // RR
				black(parent);
				red(grand);
				rotateLeft(grand);
			}
		}
	}

	@Override
	protected void afterRemove(Node<E> node, Node<E> replacement) {
		// 第一种情况
		// 如果删除的节点是红色
		if (isRed(node))
			return;

		// 第二种情况
		// 删除的是黑色节点,包括单节点和双节点
		// 用以取代node的子节点是红色
		if (isRed(replacement)) {
			black(replacement);
			return;
		}

		// 第三种情况
		// 删除的是黑色叶子节点【下溢】
		Node<E> parent = node.parent;

		// 第3.1种情况
		// 删除的是根节点
		if (parent == null)
			return;

		// 删除的是黑色叶子节点【下溢】
		// 判断被删除的node是左还是右
		// 不能用 Node<E> sibling = parent.sibling()
		// node.isLeftChild()对应afterRemove(parent, null)
		boolean left = parent.left == null || node.isLeftChild();
		Node<E> sibling = left ? parent.right : parent.left;
		if (left) { // 被删除的节点在左边，兄弟节点在右边
			// 第3.3种情况
			// RR
			// 兄弟节点是红色
			if (isRed(sibling)) { // 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateLeft(parent);
				// 更换兄弟
				sibling = parent.right;
				// 复用第3.2种大情况
			}

			// 兄弟节点是黑色
			// 第3.2.2种情况
			// 兄弟节点是黑色
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				// black(parent);
				// red(sibling);

				// 父节点是黑色
				if (parentBlack) {
					black(parent);
					red(sibling);
					// 父节点下溢,递归调用
					// 父节点是叶子节点,不能借节点替换
					afterRemove(parent, null);
				} else {
					black(parent);
					red(sibling);
				}

				// 第3.2.1种情况
			} else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// 兄弟节点的右边是黑色，兄弟要先旋转
				// RL
				if (isBlack(sibling.right)) {
					rotateRight(sibling);
					// 更换兄弟
					sibling = parent.right;
					// RR
					color(sibling, colorOf(parent));
					black(sibling.right);
					black(parent);
					rotateLeft(parent);
					
				// 兄弟节点的右边是红色,包括双红结点情况
				} else {
					// RR
					color(sibling, colorOf(parent));
					black(sibling.right);
					black(parent);
					rotateLeft(parent);
				}

				// RR
				// color(sibling, colorOf(parent));
				// black(sibling.right);
				// black(parent);
				// rotateLeft(parent);
			}

		} else { // 被删除的节点在右边，兄弟节点在左边
			// 第3.3种情况
			// LL
			// 兄弟节点是红色
			if (isRed(sibling)) { // 兄弟节点是红色
				black(sibling);
				red(parent);
				rotateRight(parent);
				// 更换兄弟
				sibling = parent.left;
				// 复用第3.2种大情况
			}

			// 兄弟节点是黑色
			// 第3.2.2种情况
			// 兄弟节点必然是黑色
			if (isBlack(sibling.left) && isBlack(sibling.right)) {
				// 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
				boolean parentBlack = isBlack(parent);
				// black(parent);
				// red(sibling);

				// 父节点是黑色
				if (parentBlack) {
					black(parent);
					red(sibling);
					// 父节点下溢,递归调用
					// 父节点是叶子节点,不能借节点替换
					afterRemove(parent, null);

				} else {// 父节点是红色
					black(parent);
					red(sibling);
				}

				// 第3.2.1种情况
			} else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
				// LR
				// 兄弟节点的左边是黑色，兄弟要先旋转
				if (isBlack(sibling.left)) {
					rotateLeft(sibling);
					// 更换兄弟
					sibling = parent.left;
					// LL
					color(sibling, colorOf(parent));
					black(sibling.left);
					black(parent);
					rotateRight(parent);

			    // 兄弟节点的左边是红色,包括双红结点情况
				} else {// LL
					color(sibling, colorOf(parent));
					black(sibling.left);
					black(parent);
					rotateRight(parent);
				}

				// LL
				// color(sibling, colorOf(parent));
				// black(sibling.left);
				// black(parent);
				// rotateRight(parent);
			}
		}
	}

	private Node<E> color(Node<E> node, boolean color) {
		if (node == null)
			return node;
		((RBNode<E>) node).color = color;
		return node;
	}

	private Node<E> red(Node<E> node) {
		return color(node, RED);
	}

	private Node<E> black(Node<E> node) {
		return color(node, BLACK);
	}

	private boolean colorOf(Node<E> node) {
		return node == null ? BLACK : ((RBNode<E>) node).color;
	}

	private boolean isBlack(Node<E> node) {
		return colorOf(node) == BLACK;
	}

	private boolean isRed(Node<E> node) {
		return colorOf(node) == RED;
	}

	@Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new RBNode<>(element, parent);
	}

	private static class RBNode<E> extends Node<E> {
		boolean color = RED;

		public RBNode(E element, Node<E> parent) {
			super(element, parent);
		}

		@Override
		public String toString() {
			String str = "";
			if (color == RED) {
				str = "R_";
			}
			return str + element.toString();
		}
	}
}
