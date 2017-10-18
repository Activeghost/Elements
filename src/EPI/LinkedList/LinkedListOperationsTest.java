package EPI.LinkedList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Linked list operations test class
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
	void add() {
		// arrange
		ListNode<Integer> a = new ListNode<>(Integer::compareTo);
		list.add(9);
		list.add(9);
		list.add(9);
		list.add(9);

		ListNode<Integer> b = new ListNode<>(Integer::compareTo);
		list.add(9);
		list.add(9);
		list.add(9);
		list.add(9);

		ListNode<Integer> expectedResult = new ListNode<>(Integer::compareTo);
		list.add(1);
		list.add(9);
		list.add(9);
		list.add(9);
		list.add(8);

		// act
		ListNode<Integer> result = LinkedListOperations.add(a, b);

		// assert
		assertEquals(LinkedListOperations.getListSize(expectedResult), LinkedListOperations.getListSize(result));
		while(expectedResult != null)
		{
			assertEquals(expectedResult.data, result.data);
			expectedResult = expectedResult.next;
			result = result.next;
		}
	}

	@Test
	void findFirst() {
	}

	@Test
	void findFirstPredecessor() {
	}

	@Test
	void getPredecessor() {
	}

	@Test
	void getKthLastNode()
	{
	}

	@Test
	void pivotList()
	{
		// arrange
		ListNode<Integer> head = new ListNode<>(1, Integer::compareTo);
		ListNode<Integer> list = head;
		list.next = new ListNode<>(1, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(3, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(1, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(5, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(3, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(4, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(6, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(4, Integer::compareTo);
		list = list.next;

		list.next = new ListNode<>(6, Integer::compareTo);
		list = list.next;
		list.next = null;

		int originalSize = LinkedListOperations.getListSize(head);

		// act
		ListNode<Integer> theList = LinkedListOperations.pivotList(head, 4);

		// assert
		ListNode<Integer> pivot = LinkedListOperations.findFirst(theList, 4);

		assertEquals(originalSize, LinkedListOperations.getListSize(theList));

		boolean beforePivot = true;
		boolean inPivot = false;
		boolean afterPivot = false;

		while(theList != null)
		{
			int compareResult = pivot.data.compareTo(theList.data);

			if(compareResult == 0)
			{
				assertFalse(afterPivot);
				beforePivot = false;
				inPivot = true;
			}
			else if(compareResult > 0)
			{
				assertTrue(beforePivot);
				assertFalse(inPivot);
			}
			else if(compareResult < 0)
			{
				assertFalse(beforePivot);
				inPivot = false;
				afterPivot = true;
			}

			theList = theList.next;
		}
	}

	@Test
	void evenOddMerge()
	{
		ListNode<Integer> a = getSkipList(0, 1);
		ListNode<Integer> oddHead = LinkedListOperations.getListNodeAtIndex(a, 2);
		ListNode<Integer> evenOddMergedList = LinkedListOperations.evenOddMerge(a);

		assertEquals(a, evenOddMergedList);
		assertEquals(oddHead, LinkedListOperations.getListNodeAtIndex(evenOddMergedList, 10));
	}

	@Test
	void rotate()
	{
		for(int i = 0; i < 100; i++)
		{
			ListNode<Integer> a = getSkipList(1, 1);
			ListNode<Integer> rotatedList = LinkedListOperations.rotate(a, Integer::compareTo, i);
			int size = LinkedListOperations.getListSize(rotatedList);
			int index = size > i ? size - i: i % size;

			assertEquals(a.data, LinkedListOperations.getKthLastNode(rotatedList, index).data);
		}
	}

	@Test
	void getListSize()
	{
		ListNode<Integer> a = getSkipList(1, 1);
		assertEquals(20, LinkedListOperations.getListSize(a));
	}

	@Test
	void removeDuplicates()
	{
		ListNode<Integer> head = getSortedListWithDuplicates(1, 2);
		LinkedListOperations.removeDuplicates(head);
		assertEquals(10, 10 );
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

	private ListNode<Integer> getSortedListWithDuplicates(int startValue, int duplicateSkipIndex)
	{
		ListNode<Integer> head = new ListNode<>(Integer::compareTo);
		ListNode<Integer> temp = head;
		int data = 0;

		for(int i = startValue; i <= 20; i++)
		{
			data = duplicateSkipIndex == i ? i - 1: i;
			temp.next = new ListNode<>(data, Integer::compareTo);
			temp = temp.next;
		}

		return head.next;
	}
}