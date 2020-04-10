package class02;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProblem01 {
	
	// 请保证所有字符串都是小写字母组成
	public static List<List<String>> getGroups(String[] strArr){
		Map<Integer, List<String>> map = new HashMap<>();
		// map
		for(int i = 0 ;i< strArr.length;i++) {
			if(strArr[i] != null) {
			   char[] str = strArr[i].toCharArray();
			   int counts = 0;
			   for(int j = 0 ; j < str.length;j++) {
				   counts |=  1 << (str[j] - 'a');
			   }
			   if(!map.containsKey(counts)) {
				   map.put(counts, new ArrayList<>());
			   }
			   map.get(counts).add(strArr[i]);
			}
		}
		return (List<List<String>>) map.values();
		
	}

}
