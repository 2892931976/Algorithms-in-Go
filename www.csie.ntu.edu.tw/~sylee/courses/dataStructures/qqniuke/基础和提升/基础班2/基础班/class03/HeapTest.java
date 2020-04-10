package class03;

import java.util.Comparator;
import java.util.PriorityQueue;

public class HeapTest {
	
	
	public static class AComp implements Comparator<Integer>{

		// 如果返回负数，认为第一个参数应该放在上面
		// 如果返回正数，认为第二个参数应该放在上面
		// 如果返回0，认为谁放上面都行
		@Override
		public int compare(Integer arg0, Integer arg1) {
			
			return arg1 - arg0;
			
//			return 0;
		}
		
	}
	
	
	public static void main(String[] args) {
		PriorityQueue<Integer> heap = new PriorityQueue<>(new AComp());
		
		heap.add(6);
		heap.add(9);
		heap.add(3);
		heap.add(2);
		heap.add(10);
		
		while(!heap.isEmpty()) {
			System.out.println(heap.poll());
			
		}
		
		
		
	}

}
