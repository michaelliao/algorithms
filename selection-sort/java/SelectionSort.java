import java.io.FileReader;
import java.util.Objects;

import com.google.gson.Gson;

public class SelectionSort {

	public static void sort(int[] array) {
		for (int i = 0; i < array.length; i++) {
			// swap the array[i] with min(array[i+1:])
			int min = i;
			for (int j = i + 1; j < array.length; j++) {
				if (array[min] > array[j]) {
					min = j;
				}
			}
			// swap a[i] and a[min]:
			int tmp = array[i];
			array[i] = array[min];
			array[min] = tmp;
		}
	}

	public static void main(String[] args) throws Exception {
		SelectionSortInput[] inputs = new Gson().fromJson(new FileReader("selection-sort/input.json"),
				SelectionSortInput[].class);
		for (SelectionSortInput input : inputs) {
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

class SelectionSortInput {
	int[] array;
	int[] result;
}
