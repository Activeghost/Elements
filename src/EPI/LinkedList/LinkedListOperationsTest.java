package EPI.LinkedList;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
	void deleteSuccessorNode()
	{
		for(int i = 1; i < 20; i++)
		{
			ListNode<Integer> a = getSkipList(1, 1);
			ListNode<Integer> listNodeAtIndex = LinkedListOperations.getListNodeAtIndex(a, i + 1);
			ListNode<Integer> predecessor =  LinkedListOperations.getListNodeAtIndex(a, i);
			ListNode<Integer> deletedNode = LinkedListOperations.deleteSuccessorNode(predecessor);

			assertEquals(listNodeAtIndex, deletedNode);
		}
	}

	@Test
	void deleteNodeAtIndex()
	{
		for(int i = 1; i < 20; i++)
		{
			ListNode<Integer> a = getSkipList(1, 1);
			ListNode<Integer> listNodeAtIndex = LinkedListOperations.getListNodeAtIndex(a, i);
			ListNode<Integer> deletedNode = LinkedListOperations.deleteNodeAtIndex(a, i);

			assertEquals(listNodeAtIndex, deletedNode);
		}
	}

	@Test
	void deleteKthLastNode()
	{
		for(int i = 1; i < 20; i++)
		{
			ListNode<Integer> a = getSkipList(1, 1);
			ListNode<Integer> listNodeAtIndex = LinkedListOperations.getListNodeAtIndex(a, 20 - i);
			ListNode<Integer> deletedNode = LinkedListOperations.deleteKthLastNode(a, i);

			assertEquals(listNodeAtIndex, deletedNode);
		}
	}

	@Test
	void getConvergenceEx()
	{
		for(int i = 0; i < 20; i++)
		{
			ListNode<Integer> a = getSkipList(1, 1);
			ListNode<Integer> b = getSkipList(1, 1);

			// insert a cycle every other time
			boolean hasCycle = i % 2 == 0;
			ListNode<Integer> cycleNode = null;

			if(hasCycle)
			{
				cycleNode = introduceCycle(b);
			}

			ListNode<Integer> listNodeAtIndex = LinkedListOperations.getListNodeAtIndex(a, i);
			listNodeAtIndex.next = b;

			ListNode<Integer> convergenceNode = LinkedListOperations.getConvergenceEx(a, b);

			if(hasCycle)
			{
				assertEquals(cycleNode.next, convergenceNode);
			}
			else
			{
				assertEquals(b, convergenceNode);
			}
		}
	}

	@Test
	void getListNodeAtIndex()
	{
		ListNode<Integer> a = getSkipList(1, 1);
		assertEquals((int)20,
					 (int)LinkedListOperations.getListNodeAtIndex(a, 20).data);
	}

	@Test
	void getConvergence()
	{
		for(int i = 0; i < 20; i++)
		{
			ListNode<Integer> a = getSkipList(1, 1);
			ListNode<Integer> b = getSkipList(1, 1);

			ListNode<Integer> listNodeAtIndex = LinkedListOperations.getListNodeAtIndex(a, i);
			listNodeAtIndex.next = b;

			ListNode<Integer> convergenceNode = LinkedListOperations.getConvergence(a, b);
			assertEquals(b, convergenceNode);
		}
	}

	@Test
	void getFirstCycleEx()
	{
		_aHead = getSkipList(1, 1);
		ListNode<Integer> cycleIndex = introduceCycle(_aHead);
		ListNode<Integer> firstCycle = LinkedListOperations.getFirstCycleEx(_aHead);
		assertEquals(cycleIndex, firstCycle);
	}

	@Test
	void getFirstCycleEx2()
	{
		for(int i = 0; i < 100; i++)
		{
			ListNode<Integer> head = getSkipList(1, 1);
			ListNode<Integer> cycleIndex = introduceCycle(head);
			ListNode<Integer> firstCycle = LinkedListOperations.getFirstCycleEx2(head);
			assertEquals(cycleIndex, firstCycle);
		}
	}

	@Test
	void reverseSublist()
	{
	}

	@Test
	void getFirstCycle()
	{
		_aHead = getSkipList(1, 1);
		ListNode<Integer> cycleIndex = introduceCycle(_aHead);
		ListNode<Integer> firstCycle = LinkedListOperations.getFirstCycle(_aHead);

		assertEquals(cycleIndex, firstCycle);
	}

	private ListNode<Integer> introduceCycle(ListNode<Integer> listIter)
	{
		Random rand = new Random();
		int first = rand.nextInt(20);
		int second = rand.nextInt(20);

		int cycleFrom = first < second ? second: first;
		int cycleTo = first > second ? second: first;
		ListNode<Integer> cycleFromNode = LinkedListOperations.getListNodeAtIndex(listIter, cycleFrom);
		ListNode<Integer> cycleToNode = LinkedListOperations.getListNodeAtIndex(listIter, cycleTo);

		cycleFromNode.next = cycleToNode;
		return cycleFromNode;
	}

	@Test
	void reverseListInBatches()
	{
		_aHead = getSkipList(1, 1);
		_aHead = LinkedListOperations.reverseListInBatches(_aHead, 10, Integer::compareTo);

		assertBatchReversal(10, 20, _aHead);
	}

	@Test
	void reverseList()
	{
		_aHead = getSkipList(1, 1);
		_aHead = LinkedListOperations.reverseListInBatches(_aHead, Integer::compareTo);

		assertListReversal(_aHead);
	}

	@Test
	void reverseSublist_reverses_entire_list()
	{
		// Arrange
		_aHead = getSkipList(1, 1);

		final int start = 1;
		final int end = 20;

		// ACT
		ListNode<Integer> head = _aHead;
		LinkedListOperations.reverseSublist(head, start, end, Integer::compareTo);

		// ASSERT
		assertListReversal(head);
	}

	@Test
	void reverseSublist_reverses_partial_list()
	{
		reverseSubListTest(1, 2);
		reverseSubListTest(1, 20);
		reverseSubListTest(10, 20);
		reverseSubListTest(19, 20);
	}

	private void reverseSubListTest(int start, int end)
	{
		_aHead = getSkipList(1, 1);

		// ACT
		ListNode<Integer> head = _aHead;
		LinkedListOperations.reverseSublist(head, start, end, Integer::compareTo);

		// ASSERT
		assertSubListReversal(start, end, head);
	}

	private void assertBatchReversal(int batchSize, int listSize, ListNode<Integer> node)
	{
		int size = 0;
		int i = batchSize - 1;

		while(node != null)
		{
			if (i == 0)
			{
				assertTrue(node.data < Optional
						.ofNullable(node.next)
						.map(theNode -> theNode.data)
						.orElse(Integer.MAX_VALUE));
				i = batchSize;
			}
			else
			{
				assertTrue(node.data > node.next.data);
			}

			node = node.next;
			i--;
			size++;
		}

		assertEquals(listSize, size);
	}

	private void assertSubListReversal(int start, int end, ListNode<Integer> node)
	{
		int i = 1;

		while(node != null && i <= end)
		{
			if(node.next != null)
			{
				if (i < start)
				{
					assertTrue(node.data < node.next.data);
				}
				else if (i >= start && i <= end)
				{
					assertTrue(node.data > node.next.data);
				}
			}

			node = node.next;
			i++;
		}
	}

	private void assertListReversal(ListNode<Integer> head)
	{
		ListNode<Integer> node = head;
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