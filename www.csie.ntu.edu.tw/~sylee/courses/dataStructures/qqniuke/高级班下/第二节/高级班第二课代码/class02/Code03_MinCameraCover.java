package class09;

public class Code03_MinCameraCover {

	public static class Node {
		public int value;
		public Node left;
		public Node right;
	}

	public static int minCameraCover1(Node root) {
		RetrunType1 data = process1(root);
		return (int) Math.min(data.uncovered + 1, Math.min(data.coveredNoCamera, data.coveredHasCamera));
	}

	public static class RetrunType1 {
		public long uncovered;
		public long coveredNoCamera;
		public long coveredHasCamera;

		public RetrunType1(long un, long no, long has) {
			uncovered = un;
			coveredNoCamera = no;
			coveredHasCamera = has;
		}
	}

	public static RetrunType1 process1(Node root) {
		if (root == null) {
			return new RetrunType1(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
		}
		RetrunType1 left = process1(root.left);
		RetrunType1 right = process1(root.right);
		long uncovered = left.coveredNoCamera + right.coveredNoCamera;
		long coveredNoCamera = Math.min(left.coveredHasCamera + right.coveredHasCamera,
				Math.min(left.coveredHasCamera + right.coveredNoCamera, left.coveredNoCamera + right.coveredHasCamera));
		long coveredHasCamera = Math.min(left.uncovered, Math.min(left.coveredNoCamera, left.coveredHasCamera))
				+ Math.min(right.uncovered, Math.min(right.coveredNoCamera, right.coveredHasCamera)) + 1;
		return new RetrunType1(uncovered, coveredNoCamera, coveredHasCamera);
	}

	public static int minCameraCover2(Node root) {
		ReturnType2 data = process2(root);
		return data.cameras + (data.status == Status.UNCOVERED ? 1 : 0);
	}

	public static enum Status {
		UNCOVERED, COVERED_NO_CAMERA, COVERED_HAS_CAMERA
	}

	public static class ReturnType2 {
		public Status status;
		public int cameras;

		public ReturnType2(Status status, int cameras) {
			this.status = status;
			this.cameras = cameras;
		}
	}

	public static ReturnType2 process2(Node root) {
		if (root == null) {
			return new ReturnType2(Status.COVERED_NO_CAMERA, 0);
		}
		ReturnType2 left = process2(root.left);
		ReturnType2 right = process2(root.right);
		int cameras = left.cameras + right.cameras;
		if (left.status == Status.UNCOVERED || right.status == Status.UNCOVERED) {
			return new ReturnType2(Status.COVERED_HAS_CAMERA, cameras + 1);
		}
		if (left.status == Status.COVERED_HAS_CAMERA || right.status == Status.COVERED_HAS_CAMERA) {
			return new ReturnType2(Status.COVERED_NO_CAMERA, cameras);
		}
		return new ReturnType2(Status.UNCOVERED, cameras);
	}

}
