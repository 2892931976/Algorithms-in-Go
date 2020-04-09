package class1;

public class FirstOnce {

	public static char firstOnce(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		char[] charArr = str.toCharArray();
		int[] map = new int[256];
		for (int i = 0; i < charArr.length; i++) {
			map[charArr[i]] = map[charArr[i]] == 0 ? 1 : -1;
		}
		for (int i = 0; i < charArr.length; i++) {
			if (map[charArr[i]] == 1) {
				return charArr[i];
			}
		}
		return 0;
	}

	public static void main(String[] args) {
		System.out.println(firstOnce("aabbccd"));

	}

}
