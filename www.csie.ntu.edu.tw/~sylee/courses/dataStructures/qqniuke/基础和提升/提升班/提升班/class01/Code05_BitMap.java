package class01;

public class Code05_BitMap {

	public static void main(String[] args) {

		
		int a = 0;
		
		
		// a  32 bit
		
		
		int[] arr = new int[10]; // 32bit * 10 -> 320 bits
		
		// arr[0]  int  0  ~ 31
		// arr[1]  int  32 ~ 63
		// arr[2]  int  64 ~ 95
		
		

		int i = 178; // 想取得178个bit的状态
		
		
		
		
		
		int numIndex = i / 32;
		int bitIndex = i % 32;
		
		// 拿到178位的状态
		int s = (      (arr[numIndex] >> (bitIndex))        & 1);
		
		
		
		
        // 请把178位的状态改成1

		arr[numIndex] = arr[numIndex] | (1 << (bitIndex));

		
		
		
		
		i = 178; // 请把178位的状态改成0

		arr[numIndex] = arr[numIndex] & (~   (1 << bitIndex)  );

		
		
		
		
		i = 178; // 请把178位的状态拿出来

		// bit 0 1
		int bit = (arr[i / 32] >> (i % 32)) & 1;

	}

}
