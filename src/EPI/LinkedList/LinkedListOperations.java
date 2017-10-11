package EPI.LinkedList;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * Linked list operations class
 */
public class LinkedListOperations
{
	/**
	 * Rotate the list by k rotations
	 * @param head the head
	 * @param k the number of rotations to perform
	 * @param <T>
	 */
	public static <T> ListNode<T> rotate(ListNode<T> head,
			Comparator<T> comparator,
			int k)
	{
		if(k <= 0)
		{
			return head;
		}

		ListNode<T> dummy = new ListNode<T>(comparator);
		dummy.next = head;

		ListNode<T> kthLastNode = getKthLastNode(head, k - 1);
		ListNode<T> tail = getTail(head);

		tail.next = head;
		dummy.next = kthLastNode.next;
		kthLastNode.next = null;

		return dummy.next;
	}

	private static <T> ListNode<T> getTail(ListNode<T> node)
	{
		while(node.next != null)
		{
			node = node.next;
		}

		return node;
	}

	public static <T> LinkedList<T> merge(LinkedList<T> a,
			LinkedList<T> b,
			Comparator<T> comparator)
	{
		LinkedList<T> list = new LinkedList<>(comparator);
		Iterator<ListNode<T>> aNodeIterator = a.iterator();
		Iterator<ListNode<T>> bNodeIterator = b.iterator();
		ListNode<T> aNode = aNodeIterator.next();
		ListNode<T> bNode = bNodeIterator.next();

		// insert dummy head node
		final ListNode<T> newNode = new ListNode<>(comparator);
		list.insertHead(newNode);

		while (aNodeIterator.hasNext() && bNodeIterator.hasNext())
		{
			// these are sorted lists, always insert at the tail to maintain sorted order.
			switch (aNode.compareTo(bNode.data))
			{
				case 1:
					bNodeIterator.remove();
					list.insertTail(bNode);
					bNode = bNodeIterator.next();
					break;
				case 0:
					aNodeIterator.remove();
					bNodeIterator.remove();

					list.insertTail(aNode);
					list.insertTail(bNode);

					aNode = aNodeIterator.next();
					bNode = bNodeIterator.next();
					break;
				case -1:
					aNodeIterator.remove();
					list.insertTail(aNode);
					aNode = aNodeIterator.next();
			}
		}

		// append remaining nodes
		if (aNodeIterator.hasNext())
		{
			addLastNodes(list, aNodeIterator, aNode, bNode);
		}
		else
		{
			addLastNodes(list, bNodeIterator, bNode, aNode);
		}

		// remove the dummy at the head
		list.remove(0);
		return list;
	}

	private static <T> void addLastNodes(LinkedList<T> list,
			Iterator<ListNode<T>> nodeIterator,
			ListNode<T> a,
			ListNode<T> b)
	{
		if (a.compareTo(b.data) > 0)
		{
			list.insertTail(b);
			list.insertTail(a);
		}
		else
		{
			nodeIterator.remove();
			list.insertTail(a);
			list.insertTail(b);
			list.insertTail(nodeIterator.next());
		}
	}

	/**
	 * Merge two unwrapped lists
	 *
	 * @param a          Head of list A
	 * @param b          Head of list B
	 * @param comparator
	 * @param <T>
	 * @return
	 */
	public static <T> ListNode<T> mergelists(ListNode<T> a, ListNode<T> b, Comparator<T> comparator)
	{
		ListNode<T> merged = new ListNode<>(comparator);
		ListNode<T> current = merged;

		while (a != null && b != null)
		{
			if (a.compareTo(b.data) < 0)
			{
				current.next = a;
				a = a.next;
			}
			else
			{
				current.next = b;
				b = b.next;
			}

			current = current.next;
		}

		current.next = a != null ? a : b;
		return merged.next;
	}

	public static <T> ListNode<T> reverseSublist(ListNode<T> head,
			int start,
			int end,
			Comparator<T> comparator)
	{
		if(start == end)
		{
			return head;
		}

		ListNode<T> dummy = new ListNode<T>(comparator);
		dummy.next = head;

		ListNode<T> subListHead = dummy;
		int k = 1;

		// find the start of the sublist
		while(k++ < start)
		{
			subListHead = subListHead.next;
		}

		ListNode<T> subListIter = subListHead.next;

		while(start++ < end)
		{
			// save off the next link
			ListNode<T> temp = subListIter.next;

			// save temp's next link into our sublist iterator
			subListIter.next = temp.next;

			// swap the sublist head.next with temp
			temp.next = subListHead.next;
			subListHead.next = temp;
		}

		return dummy.next;
	}

	public static <T> ListNode<T> reverseListInBatches(ListNode<T> head, Comparator<T> comparator)
	{
		ListNode<T> dummy = new ListNode<T>(comparator);
		dummy.next = head;

		ListNode<T> current = dummy.next;
		ListNode<T> prev = null;
		ListNode<T> next;

		while (current != null)
		{
			// save off the next link
			next = current.next;

			// set the prior node as the new next (reversing the link)
			current.next = prev;

			// move the current node to the prior node cursor
			prev = current;

			// move the current cursor to the next node
			current = next;
		}

		return dummy.next;
	}

	/**
	 * Reverse the list in batches of size k, leave any remainder as is
	 * @param <T>
	 * @param head
	 * @param batch
	 * @param comparator
	 */
	public static <T> ListNode<T> reverseListInBatches(ListNode<T> head, int batch, Comparator<T> comparator)
	{
		ListNode<T> current = head;

		/// find the size
		int size = 0;
		while (current != null)
		{
			current = current.next;
			size++;
		}

		while (size > size % batch)
		{
			// start is inclusive, thus a batch of 5 would result in say start: 6 and end: 10
			// (five individual items)
			head = reverseSublist(head, size - batch + 1, size, comparator);
			size -= batch;
		}

		return head;
	}

	public static <T> ListNode<T> getFirstCycle(ListNode<T> head)
	{
		ListNode<T> iter = head;
		Map<ListNode<T>, Integer> hashTable = new Hashtable<>();
		while (iter != null)
		{
			if(hashTable.containsKey(iter))
			{
				return iter;
			}

			hashTable.merge(iter, 1, (a,b) -> a + b);
			iter = iter.next;
		}

		return null;
	}

	/**
	 * Book solution #1, using fast and slow iterators. The cycle is found when the two equal each other.
	 * We then calculate the length of the cycle, and set one pointer to the head and another to the
	 * length, and advance each until they are equal ... where a is the start and b the end of the cycle.
	 * @param head
	 * @param <T>
	 * @return
	 */
	public static <T> ListNode<T> getFirstCycleEx(ListNode<T> head)
	{
		ListNode<T> fastIter = head;
		ListNode<T> slowIter = head;

		// slow iterator advances node by node.
		// fast iterator advances by two nodes each time
		while (fastIter != null && fastIter.next != null)
		{
			slowIter = slowIter.next;
			fastIter = fastIter.next.next;

			// if the slow iterator is equal to the fast iterator
			// we've found a cycle.
			if (slowIter.equals(fastIter))
			{
				// found a cycle, calculate the length
				int cycleLen = 0;
				do
				{
					++cycleLen;
					fastIter = fastIter.next;
				}
				while (slowIter != fastIter);

				// find the start of the cycle
				ListNode<T> cycleLenIter = head;
				while (cycleLen-- > 0)
				{
					cycleLenIter = cycleLenIter.next;
				}

				// advance both pointers until they are equal
				// since one is set to the head, and the other to the cycle length
				// this is guaranteed to find the cycle start.
				ListNode<T> iter = head;
				while (iter != cycleLenIter)
				{
					iter = iter.next;
					cycleLenIter = cycleLenIter.next;
				}

				return iter;
			}

		}

		return null;
	}

	/**
	 * Use http://en.wikipedia.org/wiki/Cycle_detection#Tortoise_and_hare algorithm
	 * without calculating the cycle size, due to the following
	 * Since tortoise travels x + y times before meeting
	 * And the hare travels (x + y + z) + some extra y before meeting
	 *
	 * given that hare travels twice as fast, 2(x + y) = (x + y + z) + y
	 * which reduces to x = z. Where z is the remaining steps in the cycle.
	 *
	 * There fore if we travel z steps from the head, both are guaranteed to meet
	 * at the start of the cycle.
	 * @param head
	 * @param <T>
	 * @return
	 */
	public static <T> ListNode<T> getFirstCycleEx2(ListNode<T> head)
	{
		ListNode<T> fastIter = head;
		ListNode<T> slowIter = head;

		// slow iterator advances node by node.
		// fast iterator advances by two nodes each time
		while (fastIter != null && fastIter.next != null)
		{
			slowIter = slowIter.next;
			fastIter = fastIter.next.next;

			// if the slow iterator is equal to the fast iterator
			// we've found a cycle.
			if (slowIter.equals(fastIter))
			{
				slowIter = head;
				while (slowIter != fastIter)
				{
					slowIter = slowIter.next;
					fastIter = fastIter.next;
				}

				return slowIter;
			}

		}

		return null;
	}

	/**
	 * Find and return the node where a list converges with another list
	 * this assumes there are no cycles in the list
	 * @param <T> The type of content in the nodes
	 * @param a the first list
	 * @param b the second list
	 * @return
	 */
	public static <T> ListNode<T> getConvergence(ListNode<T> a, ListNode<T> b)
	{
		int aCount = 0;
		int bCount = 0;

		ListNode<T> aIterator = a;
		ListNode<T> bIterator = b;

		// find the length of both lists
		aCount = getListSize(aIterator);

		// find the length of both lists
		bCount = getListSize(bIterator);

		int delta;

		// advance the long list a - b nodes.
		if(aCount > bCount)
		{
			aIterator = a;
			delta = aCount - bCount;
			aIterator = getListNodeAtIndex(aIterator, delta + 1);
		}
		else if(bCount > aCount)
		{
			bIterator = b;
			delta = bCount - aCount;
			bIterator = getListNodeAtIndex(bIterator, delta + 1);
		}

		// iterate both at the same frequency
		while(aIterator != bIterator && aIterator != null && bIterator != null)
		{
			aIterator = aIterator.next;
			bIterator = bIterator.next;
		}

		// tail should be the same if these converge.
		if(aIterator == bIterator)
		{
			return aIterator;
		}
		else
		{
			return null;
		}
	}

	public static <T> ListNode<T> getListNodeAtIndex(ListNode<T> listNode, int index)
	{
		ListNode<T> iterator = listNode;
		while(index-- > 1)
		{
			iterator = iterator.next;
		}

		return iterator;
	}

	public static <T> ListNode<T> getConvergenceEx(ListNode<T> a, ListNode<T> b)
	{
		ListNode<T> aCycle = getFirstCycle(a);
		ListNode<T> bCycle = getFirstCycle(b);

		if(aCycle == bCycle
		   && aCycle != null
		   && bCycle != null)
		{
			return aCycle;
		}

		// if one list has a cycle and the other does not, it doesn't converge.
		else if(aCycle != bCycle)
		{
			return null;
		}
		else
		{
			return getConvergence(a, b);
		}
	}

	public static <T> ListNode<T> deleteSuccessorNode(ListNode<T> node)
	{
		ListNode<T> nodeToDelete = node.next;
		if(nodeToDelete == null)
		{
			return null;
		}

		Optional<ListNode<T>> newSuccessorNode = Optional.ofNullable(node.next.next);

		node.data = newSuccessorNode.map(t -> t.data).orElse(null);
		node.next = newSuccessorNode.map(t -> t.next).orElse(null);

		return nodeToDelete;
	}

	public static <T> ListNode<T> deleteNodeAtIndex(ListNode<T> head, int index)
	{
		ListNode<T> nodeToDelete = null;

		// handle deleting the head
		if(index == 1)
		{
			nodeToDelete = head;
			head = head.next;
			return nodeToDelete;
		}

		nodeToDelete = getListNodeAtIndex(head, index - 1);
		return deleteSuccessorNode(nodeToDelete);
	}

	/**
	 * Uses a window to calculate the kth last node and delete it.
	 * @param head
	 * @param k
	 * @param <T>
	 * @return
	 */
	public static <T> ListNode<T> deleteKthLastNode(ListNode<T> head, int k)
	{
		ListNode<T> second = getKthLastNode(head, k -1);
		return deleteSuccessorNode(second);
	}

	public static <T> ListNode<T> getKthLastNode(ListNode<T> head, int k)
	{
		if(k <= 0)
		{
			return head;
		}

		ListNode<T> second = head;
		ListNode<T> first = getListNodeAtIndex(head, k);
		while(first != null)
		{
			second = second.next;
			first = first.next;
		}

		return second.next;
	}

	public static <T> void removeDuplicates(ListNode<T> head)
	{
		ListNode<T> iterator = head;
		while(iterator.next != null)
		{
			if(iterator.compareTo(iterator.next.data) == 0)
			{
				deleteSuccessorNode(iterator);
			}

			iterator = iterator.next;
		}
	}


	public static <T> int getListSize(ListNode<T> bIterator)
	{
		int count = 0;
		while(bIterator != null)
		{
			bIterator = bIterator.next;
			count++;
		}

		return count;
	}
}
