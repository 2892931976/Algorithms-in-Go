package advanced_class_01;

public class Code_02_Tow_Sum {

	public int[] twoSum(int[] nums, int target) {
		int[] indices = new int[nums.length];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = i;
		}
		sort(nums, indices);
		int l = 0;
		int r = nums.length - 1;
		int sum = 0;
		while (l < r) {
			sum = nums[l] + nums[r];
			if (sum > target) {
				r--;
			} else if (sum < target) {
				l++;
			} else {
				return new int[] { indices[l], indices[r] };
			}
		}
		return new int[] { -1, -1 };
	}

	public void sort(int[] nums, int[] indices) {
		for (int i = 0; i < nums.length; i++) {
			heapInsert(nums, indices, i);
		}
		for (int i = nums.length - 1; i >= 0; i--) {
			swap(nums, indices, 0, i);
			heapify(nums, indices, i);
		}
	}

	public void heapInsert(int[] nums, int[] indices, int i) {
		while (i > 0) {
			int p = (i - 1) / 2;
			if (nums[i] <= nums[p]) {
				break;
			}
			swap(nums, indices, i, p);
			i = p;
		}
	}

	public void heapify(int[] nums, int[] indices, int size) {
		int i = 0;
		int left = 1;
		int right = 2;
		int largest;
		while (left < size) {
			largest = nums[left] > nums[i] ? left : i;
			largest = right < size && nums[right] > nums[largest] ? right : largest;
			if (largest == i) {
				break;
			}
			swap(nums, indices, largest, i);
			i = largest;
			left = i * 2 + 1;
			right = i * 2 + 2;
		}
	}

	public void swap(int[] nums, int[] indices, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
		tmp = indices[i];
		indices[i] = indices[j];
		indices[j] = tmp;
	}

}
