package class03;

public class Test1 {

	public static void printTimes(int[] arr) {
		int cand = 0;
		int HP= 0;
		for(int  i = 0;i<arr.length;i++) {
			if(HP == 0 ) {
				cand = arr[i];
				HP = 1;
			}else if(arr[i] == cand) {
				HP++;
			}else {
				HP--;
			}
		}
		// cand -> 唯一的可能性
		if(HP == 0) {
			return;
		}
		HP =0;
		for(int i = 0 ; i< arr.length;i++) {
			if(arr[i] == cand) {
				HP++;
			}
		}

		if(HP > arr.length / 2) {
			System.out.println(cand);
		}
		
		
	}
	
	
}
