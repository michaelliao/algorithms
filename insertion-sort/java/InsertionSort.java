import java.io.FileReader;
import java.util.Objects;

import com.google.gson.Gson;

public class InsertionSort {

	public static void sort(int[] array) {
		for (int i = 1; i < array.length; i++) {
			for (int j = i; j > 0 && array[j] < array[j - 1]; j--) {
				int tmp = array[j];
				array[j] = array[j - 1];
				array[j - 1] = tmp;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		InsertionSortInput[] inputs = new Gson().fromJson(new FileReader("insertion-sort/input.json"),
				InsertionSortInput[].class);
		for (InsertionSortInput input : inputs) {
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

class InsertionSortInput {
	int[] array;
	int[] result;
}