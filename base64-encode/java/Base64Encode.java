import java.io.FileReader;

import com.google.gson.Gson;

public class Base64Encode {

	static final char[] BASE64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".toCharArray();

	public static String base64Encode(byte[] input) {
		// DO NOT use JDK Base64.encode:
		int srcLength = input.length;
		int count = srcLength / 3;
		int paddings = srcLength % 3;
		int encLength = (count + (paddings == 0 ? 0 : 1)) * 4;
		char[] output = new char[encLength];
		for (int i = 0; i < count; i++) {
			// encode every 3 bytes to 4 bytes:
			fill(input[i * 3], input[i * 3 + 1], input[i * 3 + 2], output, i * 4);
		}
		// padding:
		if (paddings == 1) {
			fill(input[input.length - 1], (byte) 0, (byte) 0, output, count * 4);
			output[count * 4 + 2] = '=';
			output[count * 4 + 3] = '=';
		} else if (paddings == 2) {
			fill(input[input.length - 2], input[input.length - 1], (byte) 0, output, count * 4);
			output[count * 4 + 3] = '=';
		}
		return new String(output);
	}

	static void fill(byte src1, byte src2, byte src3, char[] output, int offset) {
		int e1 = (src1 & 0xfc) >> 2; // 11111100
		int e2 = ((src1 & 0x03) << 4) | ((src2 & 0xf0) >> 4); // 11..11110000
		int e3 = ((src2 & 0x0f) << 2) | ((src3 & 0xc0) >> 6); // 1111..11000000
		int e4 = src3 & 0x3f; // 00111111
		output[offset] = BASE64_CHARS[e1];
		output[offset + 1] = BASE64_CHARS[e2];
		output[offset + 2] = BASE64_CHARS[e3];
		output[offset + 3] = BASE64_CHARS[e4];
	}

	public static void main(String[] args) throws Exception {
		Base64Input[] inputs = new Gson().fromJson(new FileReader("base64-encode/input.json"), Base64Input[].class);
		for (Base64Input input : inputs) {
			String r = base64Encode(input.source.getBytes("UTF-8"));
			System.out.println("base64Encode(\"" + input.source + "\") => " + r);
			if (!input.result.equals(r)) {
				throw new Exception("failed. expected = " + input.result + ", actual = " + r);
			}
		}
	}
}

class Base64Input {
	String source;
	String result;
}
