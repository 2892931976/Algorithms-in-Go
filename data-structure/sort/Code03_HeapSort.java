package com.chj.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Code03_HeapSort {

	public static void heapSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		// O(N*logN)
		for (int i = 0; i < arr.length; i++) { // O(N)
			heapInsert(arr, i); // O(logN)
		}
//		 for(int i = arr.length -1; i >=0; i--) {
//		    heapify(arr, i, arr.length);
//		 }

		printArray(arr);
		// O(N*logN)
		int heapSize = arr.length;
		swap(arr, 0, --heapSize);
		while (heapSize > 0) { // O(N)
			heapify(arr, 0, heapSize); // O(logN)
			swap(arr, 0, --heapSize); // O(1)
		}
	}

	// arr[0...index-1]宸茬粡鏄爢浜嗭紝鏌愪釜鏁扮幇鍦ㄥ鍦╥ndex浣嶇疆锛屽線涓婄户缁Щ鍔�
	public static void heapInsert(int[] arr, int index) {
		System.out.println("start: " + index + " end: " + (index - 1) / 2);
		while (arr[index] > arr[(index - 1) / 2]) {
			swap(arr, index, (index - 1) / 2);
			index = (index - 1) / 2;
		}
	}

	// 鏌愪釜鏁板湪index浣嶇疆锛岀湅鐪嬭兘鍚﹀線涓嬫矇锛岃兘鍚﹀線涓嬬Щ鍔�
	public static void heapify(int[] arr, int index, int heapSize) {
		int left = index * 2 + 1; // 宸﹀瀛愮殑涓嬫爣
		while (left < heapSize) { // 涓嬫柟杩樻湁瀛╁瓙鐨勬椂鍊�
			// 涓や釜瀛╁瓙涓紝璋佺殑鍊煎ぇ锛屾妸涓嬫爣缁檒argest
			int largest = left + 1 < heapSize && arr[left + 1] > arr[left] ? left + 1 : left;
			// 鐖跺拰杈冨ぇ鐨勫瀛愪箣闂达紝璋佺殑鍊煎ぇ锛屾妸涓嬫爣缁檒argest
			largest = arr[largest] > arr[index] ? largest : index;
			if (largest == index) {
				break;
			}
			swap(arr, largest, index);
			index = largest;
			left = index * 2 + 1;
		}
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {

		int index = -1;

		System.out.println(index / 2);

		int testTime = 500;
		int maxSize = 10;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			heapSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		heapSort(arr);
		printArray(arr);
	}

}
