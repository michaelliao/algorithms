import java.io.FileReader;
import java.util.Objects;

import com.google.gson.Gson;

public class MapReduce {

	public static int[] map(Mapper mapper, int[] array) {
		int[] results = new int[array.length];
		for (int i = 0; i < array.length; i++) {
			results[i] = mapper.apply(array[i]);
		}
		return results;
	}

	public static int reduce(Reducer reducer, int[] array) {
		int r = array[0];
		for (int i = 1; i < array.length; i++) {
			r = reducer.apply(r, array[i]);
		}
		return r;
	}

	public static void main(String[] args) throws Exception {
		// test map:
		MapInput[] mapInputs = new Gson().fromJson(new FileReader("map-reduce/map-input.json"), MapInput[].class);
		for (MapInput mapInput : mapInputs) {
			int[] r = map(x -> {
				return x * x;
			} , mapInput.array);
			System.out.println("map(fn, " + new Gson().toJson(mapInput.array) + ") => " + new Gson().toJson(r));
			if (!Objects.deepEquals(r, mapInput.result)) {
				throw new Exception("failed. expected = " + new Gson().toJson(mapInput.result) + ", actual = "
						+ new Gson().toJson(r));
			}
		}
		// test reduce:
		ReduceInput[] reduceInputs = new Gson().fromJson(new FileReader("map-reduce/reduce-input.json"),
				ReduceInput[].class);
		for (ReduceInput reduceInput : reduceInputs) {
			int result = reduce((r, x) -> {
				return r + x * x;
			} , reduceInput.array);
			System.out.println("reduce(fn, " + new Gson().toJson(reduceInput.array) + ") => " + result);
			if (result != reduceInput.result) {
				throw new Exception("failed. expected = " + reduceInput.result + ", actual = " + result);
			}
		}
	}
}

@FunctionalInterface
interface Mapper {
	int apply(int x);
}

@FunctionalInterface
interface Reducer {
	int apply(int r, int x);
}

class MapInput {
	int[] array;
	int[] result;
}

class ReduceInput {
	int[] array;
	int result;
}
