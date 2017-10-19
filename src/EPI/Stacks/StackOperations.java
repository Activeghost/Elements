package EPI.Stacks;

import java.util.Deque;
import java.util.LinkedList;

import static EPI.Stacks.StackOperations.Operands.*;

/**
 * Stack operations
 */
public class StackOperations
{

	private static Deque<Double> _stack;

	enum Operands
	{
		ADD,
		DIVIDE,
		MINUS,
		MULTIPLY,
		UNKNOWN
	}

	public static Double evalRpnExpression(String rpn)
	{
		_stack = new LinkedList<>();
		StringBuilder rpnElement = new StringBuilder();

		for(Character c : rpn.toCharArray())
		{
			if(isRpnElementDelimiter(c))
			{
				// end of rpn element
				processElement(rpnElement.toString());
				rpnElement = new StringBuilder();
			}
			else
			{
				rpnElement.append(c);
			}
		}

		// check for case of only one element and no delimiter
		if(rpnElement.length() > 0)
		{
			processElement(rpnElement.toString());
		}

		return _stack.pop();
	}

	private static void processElement(String s)
	{
		Double result;
		if(isOperand(s))
		{
			// pop last two off queue in reverse order (b was just added)
			Double b = _stack.pop();
			Double a = _stack.pop();

			// get our operand
			Operands operand = getOperand(s);

			// apply operand and push the result
			result = calculate(a, b, operand);
		}
		else
		{
			result = Double.parseDouble(s);
		}

		_stack.push(result);
	}

	private static Double calculate(double a, double b, Operands operand)
	{
		Double result = 0d;
		switch(operand)
		{
			case MINUS:
				result = a - b;
				break;
			case ADD:
				result = a + b;
				break;
			case DIVIDE:
				result = a / b;
				break;
			case MULTIPLY:
				result = a * b;
				break;
			case UNKNOWN:
				throw new IllegalArgumentException("Unknown operand encountered in RPN string");
		}

		return result;
	}

	private static Operands getOperand(String s)
	{
		Operands operand;
		switch(s)
		{
			case "-":
				operand = MINUS;
				break;
			case "+":
				operand = ADD;
				break;
			case "/":
				operand = DIVIDE;
				break;
			case "x":
				operand = MULTIPLY;
				break;
			default:
				throw new IllegalArgumentException("Unknown operand encountered in RPN string");
		}

		return operand;
	}

	private static boolean isRpnElementDelimiter(Character c)
	{
		return c == ',';
	}

	private static boolean isOperand(String maybeOperand)
	{
		// cannot be an operand if this is larger than 1
		if(maybeOperand.length() > 1)
		{
			return false;
		}

		switch(maybeOperand)
		{
			case "-":
			case "+":
			case "/":
			case "x":
				return true;
		}

		return false;
	}
}
