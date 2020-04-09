package class13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Code04_CloneGraph {

	public static class Node {
		int label;
		List<Node> neighbors;

		Node(int x) {
			label = x;
			neighbors = new ArrayList<Node>();
		}
	}

	// bfs
	public static Node cloneGraph(Node node) {
		if (node == null) {
			return null;
		}
		Node head = new Node(node.label);
		LinkedList<Node> queue = new LinkedList<>();
		// key 克隆节点所对应的原节点  value 克隆节点
		HashMap<Node, Node> map = new HashMap<>();
		queue.add(node);
		map.put(node, head);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			for (Node next : cur.neighbors) {
				if (!map.containsKey(next)) {
					Node copy = new Node(next.label);
					map.put(next, copy);
					queue.add(next);
				}
			}
		}
		for (Entry<Node, Node> entry : map.entrySet()) {
			Node cur = entry.getKey();
			Node copy = entry.getValue();
			for (Node next : cur.neighbors) {
				copy.neighbors.add(map.get(next));
			}
		}
		return head;
	}

	// dfs
	public Node cloneGraph2(Node node) {
		// key 老节点  value 克隆节点
		HashMap<Node, Node> map = new HashMap<>();
		return clone(node, map);
	}

	// 来到当前节点是node，
	// 生成node的克隆节点并返回
	// 返回之前，把node克隆节点的neighbors设置好
	private Node clone(Node node, HashMap<Node, Node> map) {
		if (node == null) {
			return null;
		}
		if (map.containsKey(node)) {
			return map.get(node);
		}
		Node clone = new Node(node.label);
		map.put(node, clone);
		for (Node neighbor : node.neighbors) {
			clone.neighbors.add(clone(neighbor, map));
		}
		return clone;
	}

}
