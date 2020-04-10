package class13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class Code04_CloneGraph {

	public static class UndirectedGraphNode {
		int label;
		List<UndirectedGraphNode> neighbors;

		UndirectedGraphNode(int x) {
			label = x;
			neighbors = new ArrayList<UndirectedGraphNode>();
		}
	}

	// bfs
	public static UndirectedGraphNode cloneGraph(UndirectedGraphNode node) {
		if (node == null) {
			return null;
		}
		UndirectedGraphNode head = new UndirectedGraphNode(node.label);
		LinkedList<UndirectedGraphNode> queue = new LinkedList<>();
		HashMap<UndirectedGraphNode, UndirectedGraphNode> map = new HashMap<>();
		queue.add(node);
		map.put(node, head);
		while (!queue.isEmpty()) {
			UndirectedGraphNode cur = queue.poll();
			for (UndirectedGraphNode next : cur.neighbors) {
				if (!map.containsKey(next)) {
					UndirectedGraphNode copy = new UndirectedGraphNode(next.label);
					map.put(next, copy);
					queue.add(next);
				}
			}
		}
		for (Entry<UndirectedGraphNode, UndirectedGraphNode> entry : map.entrySet()) {
			UndirectedGraphNode cur = entry.getKey();
			UndirectedGraphNode copy = entry.getValue();
			for (UndirectedGraphNode next : cur.neighbors) {
				copy.neighbors.add(map.get(next));
			}
		}
		return head;
	}

	// dfs
	public UndirectedGraphNode cloneGraph2(UndirectedGraphNode node) {
		HashMap<Integer, UndirectedGraphNode> map = new HashMap<>();
		return clone(node, map);
	}

	private UndirectedGraphNode clone(UndirectedGraphNode node, HashMap<Integer, UndirectedGraphNode> map) {
		if (node == null)
			return null;

		if (map.containsKey(node.label)) {
			return map.get(node.label);
		}
		UndirectedGraphNode clone = new UndirectedGraphNode(node.label);
		map.put(clone.label, clone);
		for (UndirectedGraphNode neighbor : node.neighbors) {
			clone.neighbors.add(clone(neighbor, map));
		}
		return clone;
	}

}
