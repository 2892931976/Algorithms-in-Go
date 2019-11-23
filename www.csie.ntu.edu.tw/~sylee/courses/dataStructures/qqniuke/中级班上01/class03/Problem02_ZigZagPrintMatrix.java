package class03;

public class Problem02_ZigZagPrintMatrix {

	public static void printMatrixZigZag(int[][] matrix) {
		int ar = 0;
		int ac = 0;
		int br = 0;
		int bc = 0;
		int endR = matrix.length - 1;
		int endC = matrix[0].length - 1;
		boolean fromUp = false;
		while (ar != endR + 1) {
			printLevel(matrix, ar, ac, br, bc, fromUp);
			ar = ac == endC ? ar + 1 : ar;
			ac = ac == endC ? ac : ac + 1;
			bc = br == endR ? bc + 1 : bc;
			br = br == endR ? br : br + 1;
			fromUp = !fromUp;
		}
		System.out.println();
	}

	public static void printLevel(int[][] m, int tR, int tC, int dR, int dC,
			boolean f) {
		if (f) {
			while (tR != dR + 1) {
				System.out.print(m[tR++][tC--] + " ");
			}
		} else {
			while (dR != tR - 1) {
				System.out.print(m[dR--][dC++] + " ");
			}
		}
	}

	public static void main(String[] args) {
		int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		printMatrixZigZag(matrix);

	}

}
