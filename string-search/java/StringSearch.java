import java.io.FileReader;

import com.google.gson.Gson;

public class StringSearch {

	public static int search(String source, String target) {
		// DO NOT use JDK String.indexOf():
		char[] src = source.toCharArray();
		char[] sub = target.toCharArray();
		return source.indexOf(target);
	}

	public static void main(String[] args) throws Exception {
		StringSearchInput[] inputs = new Gson().fromJson(new FileReader("string-search/input.json"),
				StringSearchInput[].class);
		for (StringSearchInput input : inputs) {
			int r = search(input.source, input.target);
			System.out.println("search(\"" + input.source + "\", \"" + input.target + "\") => " + r);
			if (input.result != r) {
				throw new Exception("failed. expected = " + input.result + ", actual = " + r);
			}
		}
	}
}

class StringSearchInput {
	String source;
	String target;
	int result;
}
