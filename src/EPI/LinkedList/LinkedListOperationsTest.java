package EPI.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by clester on 9/18/2017.
 */
class LinkedListOperationsTest
{
	private LinkedList<Integer> list;

	@BeforeEach
	void setUp()
	{
		list = new LinkedList<>(Integer::compareTo);
		list.add(9);
		list.add(7);
		list.add(5);
		list.add(3);
		list.add(1);
	}

	@Test
	void merge()
	{
		LinkedList<Integer> b = new LinkedList<>(Integer::compareTo);
		b.add(10);
		b.add(8);
		b.add(6);
		b.add(4);
		b.add(2);

		LinkedList<Integer> mergedList = LinkedListOperations.merge(list, b, Integer::compareTo);
		assertEquals(10, mergedList.size());

		for(ListNode<Integer> node : mergedList)
		{
			if (node.next != null)
			{
				assertTrue(node.data < node.next.data);
			}
		}
	}

}