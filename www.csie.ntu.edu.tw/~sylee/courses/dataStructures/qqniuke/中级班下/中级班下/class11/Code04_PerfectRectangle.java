package class11;

import java.util.HashSet;

public class Code04_PerfectRectangle {

	public boolean isRectangleCover(int[][] rectangles) {
		if (rectangles.length == 0 || rectangles[0].length == 0) {
			return false;
		}
		int xL = Integer.MAX_VALUE;
		int xR = Integer.MIN_VALUE;
		int yD = Integer.MAX_VALUE;
		int yU = Integer.MIN_VALUE;
		HashSet<String> set = new HashSet<String>();
		int area = 0; // 每一个小矩形累加的面积和
		for (int[] rect : rectangles) {
			// rect[0] 左下角点x坐标
			// rect[1] 左下角点y坐标
			// rect[2] 右上角点x坐标
			// rect[3] 右上角点y坐标
			xL = Math.min(rect[0], xL);
			yD = Math.min(rect[1], yD);
			xR = Math.max(rect[2], xR);
			yU = Math.max(rect[3], yU);
			area += (rect[2] - rect[0]) * (rect[3] - rect[1]); // 所有小矩形的面积累加和
			String s1 = rect[0] + "_" + rect[1]; // 左下角点坐标
			String s2 = rect[0] + "_" + rect[3]; // 左上角点坐标
			String s3 = rect[2] + "_" + rect[3]; // 右上角点坐标
			String s4 = rect[2] + "_" + rect[1]; // 右下角点坐标
			// 加入了奇数次，保留
			// 加入了偶数次，删掉
			if (!set.add(s1)) set.remove(s1);
			if (!set.add(s2)) set.remove(s2);
			if (!set.add(s3)) set.remove(s3);
			if (!set.add(s4)) set.remove(s4);
		}
		if (!set.contains(xL + "_" + yD) || !set.contains(xL + "_" + yU)
				|| !set.contains(xR + "_" + yD) || !set.contains(xR + "_" + yU)
				|| set.size() != 4) {
			return false;
		}
		return area == (xR - xL) * (yU - yD);
	}

}
