import java.io.FileReader;
import java.util.Stack;

import com.google.gson.Gson;

public class Evaluate {

	static final int ADD = 0;
	static final int SUB = 1;
	static final int MUL = 2;
	static final int DIV = 3;
	static final int LT = 4;
	static final int RT = 5;

	static final int[][] priority = new int[][] {
			// + - * /
			{ 1, 1, 0, 0 }, // +
			{ 1, 1, 0, 0 }, // -
			{ 1, 1, 1, 1 }, // *
			{ 1, 1, 1, 1 }, // /
	};

	static boolean isHigher(int op1, int op2) {
		return priority[op1][op2] > 0;
	}

	static int token2op(String token) {
		if ("+".equals(token)) {
			return ADD;
		}
		if ("-".equals(token)) {
			return SUB;
		}
		if ("*".equals(token)) {
			return MUL;
		}
		if ("/".equals(token)) {
			return DIV;
		}
		if ("(".equals(token)) {
			return LT;
		}
		if (")".equals(token)) {
			return RT;
		}
		return -1;
	}

	static int calculate(int op, int n1, int n2) {
		if (op == ADD) {
			return n1 + n2;
		}
		if (op == SUB) {
			return n1 - n2;
		}
		if (op == MUL) {
			return n1 * n2;
		}
		if (op == DIV) {
			return n1 / n2;
		}
		throw new IllegalArgumentException("Invalid operator: " + op);
	}

	public static int evaluate(String[] tokens) {
		Stack<Integer> ops = new Stack<Integer>();
		Stack<Integer> vals = new Stack<Integer>();
		for (String token : tokens) {
			int op = token2op(token);
			if (op != (-1)) {
				if (op == LT) {
					// always push '(':
					ops.push(op);
				} else if (op == RT) {
					// pop until find '(':
					int topOp;
					while ((topOp = ops.pop()) != LT) {
						int n2 = vals.pop();
						int n1 = vals.pop();
						vals.push(calculate(topOp, n1, n2));
					}
				} else {
					// operator:
					// pop if top op > current op:
					while (!ops.isEmpty() && ops.peek() != LT && isHigher(ops.peek(), op)) {
						int topOp = ops.pop();
						int n2 = vals.pop();
						int n1 = vals.pop();
						vals.push(calculate(topOp, n1, n2));
					}
					ops.push(op);
				}
			} else {
				// number:
				vals.push(Integer.parseInt(token));
			}
		}
		while (!ops.isEmpty()) {
			int n2 = vals.pop();
			int n1 = vals.pop();
			vals.push(calculate(ops.pop(), n1, n2));
		}
		return vals.pop();
	}

	public static void main(String[] args) throws Exception {
		EvaluateInput[] inputs = new Gson().fromJson(new FileReader("evaluate/input.json"), EvaluateInput[].class);
		for (EvaluateInput input : inputs) {
			int r = evaluate(input.expression.split("\\s+"));
			System.out.println(input.expression + " => " + r);
			if (input.result != r) {
				throw new Exception("failed. expected = " + input.result + ", actual = " + r);
			}
		}
	}
}

class EvaluateInput {
	String expression;
	int result;
}
