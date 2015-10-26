import java.io.FileReader;
import java.util.Objects;

import com.google.gson.Gson;

public class QuickSort {

	static void sort(int[] array, int lo, int hi) {
		if (lo >= hi) {
			return;
		}
		int i = lo;
		int j = hi + 1;
		int base = array[lo];
		while (true) {
			while (array[++i] < base && i < hi);
			while (base < array[--j] && lo < j);
			if (i >= j) {
				break;
			}
			int tmp = array[i];
			array[i] = array[j];
			array[j] = tmp;
		}
		array[lo] = array[j];
		array[j] = base;
		// sort left part:
		sort(array, lo, j - 1);
		// sort right part:
		sort(array, j + 1, hi);
	}

	public static void sort(int[] array) {
		sort(array, 0, array.length - 1);
	}

	public static void main(String[] args) throws Exception {
		QuickSortInput[] inputs = new Gson().fromJson(new FileReader("quick-sort/input.json"), QuickSortInput[].class);
		for (QuickSortInput input : inputs) {
			int[] array = new int[input.array.length];
			System.arraycopy(input.array, 0, array, 0, array.length);
			System.out.println("original => " + new Gson().toJson(array));
			sort(array);
			System.out.println("sorted => " + new Gson().toJson(array));
			if (!Objects.deepEquals(array, input.result)) {
				throw new Exception("failed. expected = " + new Gson().toJson(input.result) + ", actual = "
						+ new Gson().toJson(array));
			}
		}
	}
}

class QuickSortInput {
	int[] array;
	int[] result;
}