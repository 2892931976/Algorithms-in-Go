package class02;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Test {

	public static class Node {
		public int value;

		public Node(int v) {
			value = v;
		}
	}

	public static class Comp implements Comparator<Node> {

		@Override
		public int compare(Node arg0, Node arg1) {
			return arg0.value - arg1.value;
		}
	}

	public static void main(String[] args) {
		// UnOrderedMap UnsortedMap
		HashSet<Integer> set = new HashSet<>();

		set.add(4);
		set.add(5);
		set.add(6);

		set.remove(6);

		set.contains(5);

		HashMap<Integer, String> m = new HashMap<>();
		m.put(3, "我是3");
		m.put(3, "我还是3");
		m.remove(3);
		m.containsKey(3);

//		TreeMap<Node, String> map1 = new TreeMap<>(new Comp());
//		Node node1 = new Node(3);
//		map1.put(node1, "我是node1");

		// OrderedMap SortedMap
		TreeMap<Integer, String> map = new TreeMap<>();

		map.put(4, "我是4");
		map.put(3, "我是3");
		map.put(7, "我是7");
		map.put(1, "我是1");
		map.put(9, "我是9");
		map.put(6, "我是6~");
		
		map.remove(7);
		
		System.out.println(map.get(6));
		System.out.println(map.containsKey(2));

		System.out.println(map.firstKey());
		System.out.println(map.lastKey());
		System.out.println(map.floorKey(5)); // <=5
		System.out.println(map.ceilingKey(5)); // >=5
		System.out.println(map.floorKey(6)); // <=6
		System.out.println(map.ceilingKey(6)); // >=6
		
		
		for(Entry<Integer, String> entry : map.entrySet()){
			Integer key = entry.getKey();
			String value = entry.getValue();
			System.out.println(key +" , " + value);
		}

		// 哈希表 所有的增删改查都是O(1)
		// 有序表 所有的增删改查都是O(logN)

	}

}
