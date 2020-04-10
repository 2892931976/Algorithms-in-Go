package class00;

import java.util.HashMap;
import java.util.TreeMap;

public class MkssKeyValue {

	public static class ValueCollector<V> {
		private TreeMap<Integer, V> ssValueMap; // 快照时间和值的对应关系
		private V value; // 最新的值

		public ValueCollector() {
			ssValueMap = new TreeMap<Integer, V>();
			value = null;
		}

		private boolean isEqual(V o1, V o2) {
			return (o1 == null && o2 == null)
					|| (o1 != null && o2 != null && o1.equals(o2));
		}

		// 更新value
		public void put(V nowValue, Integer nearestSsTime) {
			V oldValue = value;
			value = nowValue;
			if (nearestSsTime != null && !isEqual(oldValue, nowValue)) {
				if (ssValueMap.isEmpty()) {
					ssValueMap.put(nearestSsTime, oldValue);
				} else { // 之前建立过快照信息
					// 上次建立的、最晚的，拿出来
					Integer lastSsTime = ssValueMap.lastKey();
					V lastSsValue = ssValueMap.get(lastSsTime);
					if (!lastSsTime.equals(nearestSsTime)) { // 新出现的快照，才记录
						if (isEqual(lastSsValue, oldValue)) {
							ssValueMap.pollLastEntry();
						}
						ssValueMap.put(nearestSsTime, oldValue);
					}
				}
			}
		}

		public V get() {
			return value;
		}

		public V get(Integer ssTime) {
			if (!ssValueMap.isEmpty()
					&& ssValueMap.ceilingEntry(ssTime) != null) {
				// (key, value)
				return ssValueMap.ceilingEntry(ssTime).getValue();
			}
			return value;
		}

		public void delete(Integer nearestSsTime) {
			put(null, nearestSsTime);
		}

	}

	public static class MyDB<K, V> {
		private HashMap<K, ValueCollector<V>> keyValueCollectorMap;
		private HashMap<String, Integer> ssIdSsTimeMap;
		private Integer nearestSsTime;

		public MyDB() {
			keyValueCollectorMap = new HashMap<K, ValueCollector<V>>();
			ssIdSsTimeMap = new HashMap<String, Integer>();
			nearestSsTime = null;
		}

		public V get(K key) {
			if (keyValueCollectorMap.containsKey(key)) {
				keyValueCollectorMap.get(key).get();
			}
			return null;
		}

		public V get(K key, String ssid) {
			if (keyValueCollectorMap.containsKey(key)) {
				keyValueCollectorMap.get(key).get(ssIdSsTimeMap.get(ssid));
			}
			return null;
		}

		public void mkss(String ssid) {
			if (nearestSsTime == null) {
				nearestSsTime = 0;
			}
			ssIdSsTimeMap.put(ssid, ++nearestSsTime);
		}

		public void put(K key, V value) {
			if (!keyValueCollectorMap.containsKey(key)) {
				keyValueCollectorMap.put(key, new ValueCollector<V>());
			}
			keyValueCollectorMap.get(key).put(value, nearestSsTime);
		}

		public void delete(K key) {
			if (keyValueCollectorMap.containsKey(key)) {
				keyValueCollectorMap.get(key).delete(nearestSsTime);
			}
		}

	}

	public static void main(String[] args) {
		// 红黑树     orderedMap
		TreeMap<Integer, String> treeMap = new TreeMap<>();
		treeMap.put(1, "A");
		treeMap.put(5, "B");
		treeMap.put(7, "C");
		treeMap.put(10, "D");
		treeMap.put(23, "E");
		treeMap.put(50, "F");
		treeMap.put(89, "G");
		// O(logN)
		System.out.println(treeMap.get(23));
		System.out.println(treeMap.firstKey());
		System.out.println(treeMap.lastKey());
		System.out.println(treeMap.ceilingKey(15));
		System.out.println(treeMap.floorKey(15));
		System.out.println(treeMap.ceilingKey(50));
		System.out.println(treeMap.floorKey(50));
		System.out.println(treeMap.remove(23));
		System.out.println(treeMap.get(23));
	}

}
