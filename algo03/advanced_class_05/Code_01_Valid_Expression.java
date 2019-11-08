package advanced_class_05;

import java.util.Stack;

public class Code_01_Valid_Expression {

	public static boolean isValid(String expression) {
		char[] chs = expression.toCharArray();
		Stack<Character> stack = new Stack<>();
		for (int i = 0; i < chs.length; i++) {
			if (chs[i] == '{' || chs[i] == '[' || chs[i] == '(') {
				stack.add(chs[i]);
			}
			if (chs[i] == '}' || chs[i] == ']' || chs[i] == ')') {
				char match = chs[i] == '}' ? '{' : (chs[i] == ']' ? '[' : '(');
				if (stack.isEmpty() || !stack.pop().equals(match)) {
					return false;
				}
			}
		}
		return stack.isEmpty();
	}

	public static void main(String[] args) {
		String test = "{1+(2+3)+[(1+3)+(4*5)]}";
		System.out.println(isValid(test));
	}

}
