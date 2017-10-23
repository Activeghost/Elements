package EPI.Stacks;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

import static EPI.Stacks.StackOperations.Operands.*;

import EPI.LinkedList.JumpListNode;
import EPI.LinkedList.ListNode;

/**
 * Stack operations
 */
public class StackOperations
{

	private Deque<Double> _stack;
	private Operands _operand = Operands.UNKNOWN;

	enum Operands
	{
		ADD,
		DIVIDE,
		MINUS,
		MULTIPLY,
		UNKNOWN
	}

	public <T> void computeJumpOrderRecursive(JumpListNode<T> postings)
	{
		computeJumpOrderHelper(postings, 0);
	}

	public <T> Deque<T> computeJumpOrder(JumpListNode<T> postings)
	{
		Deque<T> jumpOrdering = new LinkedList<>();
		Deque<T> processedNodes = new LinkedList<>();

		JumpListNode<T> iterator = postings;
		while(iterator != null)
		{

		}

		return jumpOrdering;
	}

	private <T> int computeJumpOrderHelper(JumpListNode<T> node, int order)
	{
		if(node != null && node.order == -1)
		{
			node.order = order++;
			order = computeJumpOrderHelper((JumpListNode<T>)node.jumpTo, order);
			order = computeJumpOrderHelper((JumpListNode<T>)node.next, order);
		}

		return order;
	}

	public String normalizePath(String path)
	{
		StringBuilder thePath = new StringBuilder();
		StringBuilder element = new StringBuilder();
		Deque<String> stack = new LinkedList<>();

		for(int i = 0; i < path.length(); i++)
		{
			char c = path.charAt(i);
			if(c == '/')
			{
				boolean stackEmpty = stack.isEmpty();
				int length = element.length();

				if(stackEmpty && length == 0)
				{
					stack.push(Character.toString(c));
					continue;
				}

				// collapse multiple /// into /
				else if(!stackEmpty
                        && stack.peek().equals("/")
                        && length == 0)
				{
					continue;
				}

				parsePathElement(element, stack, c);
				element = new StringBuilder();
			}
			else
			{
				element.append(c);
			}
		}

		// check for unparsed last element.
		if(element.length() > 0) {
            parsePathElement(element, stack, null);
        }

		// after which we do not need a trailing slash
		if(stack.peek().equals("/"))
		{
		    stack.pop();
		}


		stack
				.descendingIterator()
				.forEachRemaining(thePath::append);
		return thePath.toString();
	}

	private void parsePathElement(StringBuilder element, Deque<String> stack, Character c) {
		// close previous path
		String s = element.toString();
		switch (s) {
            case ".":
                // collapse these, they refer to the current directory
				break;
            case "..":
                // remove previous element set (name, separator).
                if (!stack.isEmpty()) {
                    stack.pop();
                    stack.pop();
                }
                break;
            default:
                // record the path element set (name and separator, eg. dir1/)
                stack.push(element.toString());

                if(c != null) {
					stack.push(c.toString());
				}
				break;
        }
	}

	public boolean isWellFormed(String param)
	{
		Deque<Character> stack = new LinkedList<>();

		// push element
		for(Character c : param.toCharArray())
		{
			// if matching pair, pop
			if(isPair(stack.peek(), c))
			{
				stack.pop();
			}
			else {
				// otherwise push
				stack.push(c);
			}
		}

		// return true if stack is empty.
		return stack.isEmpty();
	}

	private boolean isPair(Character start, Character maybeEnd) {
		if(start == null || maybeEnd == null)
		{
			return false;
		}

		boolean isPair = false;
		switch(start)
		{
			case '(':
				isPair = maybeEnd == ')';
				break;
			case '[':
				isPair = maybeEnd == ']';
				break;
			case '{':
				isPair = maybeEnd == '}';
				break;
		}

		return isPair;
	}

	public Double evalPolishNotation(String pn)
	{
		resetState();
		processElement(pn, this::processPolishNotationElement);
		if(_stack.size() > 1)
		{
			Double b = _stack.pop();
			Double a = _stack.pop();
			return calculate(a, b, _operand);
		}

		return _stack.pop();
	}

	public Double evalRpnExpression(String rpn)
	{
		processElement(rpn, this::processRpnElement);
		resetState();

		return _stack.pop();
	}

	private void resetState() {
		_operand = Operands.UNKNOWN;
	}

	private void processElement(String pn, Consumer<String> consumer) {
		_stack = new LinkedList<>();
		StringBuilder element = new StringBuilder();

		for(Character c : pn.toCharArray()) {
			if(isElementDelimiter(c))
			{
				// end of rpn element
				consumer.accept(element.toString());
				element = new StringBuilder();
			}
			else
			{
				element.append(c);
			}
		}

		// check for case of only one element and no delimiter
		if(element.length() > 0)
		{
			consumer.accept(element.toString());
		}
	}

	private void processPolishNotationElement(String s)
	{
		Double result = 0.0d;
		if(s.isEmpty())
		{
			return;
		}

		if(isOperand(s))
		{
			// get our operand
			Operands op = getOperand(s);

			// first time through we haven't set an operand
			if(_operand != Operands.UNKNOWN)
			{
				// pop last two off queue in reverse order (b was just added)
				Double b = _stack.pop();
				Double a = _stack.pop();

				// apply operand and push the result
				result = calculate(a, b, _operand);
				_stack.push(result);
			}

			_operand = op;
		}
		else
		{
			result = Double.parseDouble(s);
			_stack.push(result);
		}
	}

	private void processRpnElement(String s)
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

	private Double calculate(double a, double b, Operands operand)
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

	private Operands getOperand(String s)
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

	private boolean isElementDelimiter(Character c)
	{
		return c == ',';
	}

	private boolean isOperand(String maybeOperand)
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
