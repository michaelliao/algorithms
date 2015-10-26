import java.io.FileReader;

import com.google.gson.Gson;

public class BinarySearch {

	public static int binarySearch(int key, int[] arr) {
		int lo = 0;
		int hi = arr.length - 1;
		while (lo <= hi) {
			int mid = lo + (hi - lo) / 2;
			if (key < arr[mid]) {
				hi = mid - 1;
			} else if (key > arr[mid]) {
				lo = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;
	}

	public static void main(String[] args) throws Exception {
		BinarySearchInput[] inputs = new Gson().fromJson(new FileReader("binary-search/input.json"), BinarySearchInput[].class);
		for (BinarySearchInput input : inputs) {
			int r = binarySearch(input.key, input.array);
			if (input.result != r) {
				throw new Exception("binary search failed for: " + new Gson().toJson(input) + ", actual: " + r);
			}
		}
		System.out.println("ok.");
	}

}

class BinarySearchInput {
	int key;
	int[] array;
	int result;
}
