package EPI.LinkedList;

import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by clester on 9/18/2017.
 */
class LinkedListOperationsTest
{
	private LinkedList<Integer> list;
	private ListNode<Integer> _aHead;
	private ListNode<Integer> _bHead;

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
	void reverseListInBatches()
	{
		_aHead = getSkipList(1, 1);
		LinkedListOperations.reverseList(_aHead, 10, Integer::compareTo);

		ListNode<Integer> node = _aHead;
		while(node != null)
		{
			if (node.next != null)
			{
				assertTrue(node.data > node.next.data);
			}

			node = node.next;
		}
	}

	@Test
	void reverseList()
	{
		_aHead = getSkipList(1, 1);
		LinkedListOperations.reverseList(_aHead, Integer::compareTo);

		ListNode<Integer> node = _aHead;
		while(node != null)
		{
			if (node.next != null)
			{
				assertTrue(node.data > node.next.data);
			}

			node = node.next;
		}
	}

	@Test
	void reverseSublist()
	{
		_aHead = getSkipList(1, 1);
		LinkedListOperations.reverseSublist(_aHead, 1, 20, Integer::compareTo);

		ListNode<Integer> node = _aHead;
		while(node != null)
		{
			if (node.next != null)
			{
				assertTrue(node.data > node.next.data);
			}

			node = node.next;
		}
	}

	@Test
	void mergelists()
	{
		// create sample lists
		_aHead = getSkipList(2, 2);
		_bHead = getSkipList(1, 2);

		ListNode<Integer> mergedList = LinkedListOperations.mergelists(
				_aHead,
				_bHead,
				Comparator.naturalOrder());

		// ASSERT
		assertMergeResults(mergedList);
	}

	private ListNode<Integer> getOddList()
	{
		return null;
	}

	@Test
	void merge()
	{
		// ARRANGE
		LinkedList<Integer> b = getEvenIntegerList();

		// ACT
		LinkedList<Integer> mergedList = LinkedListOperations.merge(list, b, Integer::compareTo);

		// ASSERT
		assertMergeResults(mergedList);
	}

	private LinkedList<Integer> getEvenIntegerList()
	{
		LinkedList<Integer> b = new LinkedList<>(Integer::compareTo);
		b.add(10);
		b.add(8);
		b.add(6);
		b.add(4);
		b.add(2);
		return b;
	}

	private void assertMergeResults(LinkedList<Integer> mergedList)
	{
		assertEquals(10, mergedList.size());

		for(ListNode<Integer> node : mergedList)
		{
			if (node.next != null)
			{
				assertTrue(node.data < node.next.data);
			}
		}
	}

	private void assertMergeResults(ListNode<Integer> mergedList)
	{
		ListNode<Integer> node = mergedList;
		while(node != null)
		{
			if (node.next != null)
			{
				assertTrue(node.data < node.next.data);
			}

			node = node.next;
		}
	}

	private ListNode<Integer> getSkipList(int startValue, int skipValue)
	{
		ListNode<Integer> head = new ListNode<>(Integer::compareTo);
		ListNode<Integer> temp = head;
		for(int i = startValue; i <= 20; i += skipValue)
		{
			temp.next = new ListNode<>(i, Integer::compareTo);
			temp = temp.next;
		}

		return head.next;
	}
}