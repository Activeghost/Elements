package EPI.Stacks;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Created by clester on 10/18/2017.
 */
class StackTest
{
	@Test
	void max()
	{
		Stack<Integer> stack = new Stack<>(Integer::compareTo);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);

		assertTrue(stack.max() == 5);
	}

	@Test
	void max_afterpopping_returnsCorrectValue()
	{
		Stack<Integer> stack = new Stack<>(Integer::compareTo);
		stack.push(1);
		stack.push(2);
		stack.push(3);
		stack.push(4);
		stack.push(5);
		stack.pop();

		assertEquals((int)stack.max(), 4);
	}

}