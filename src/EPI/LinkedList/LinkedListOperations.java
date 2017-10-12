package EPI.LinkedList;

import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by clester on 9/15/2017.
 */
public class LinkedListOperations
{
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

	public static <T> List<ListNode<T>> getFirstCycle(ListNode<T> head)
	{
		ListNode<T> iter = head;
		Map<ListNode<T>, Integer> hashTable = new Hashtable<>();
		while (iter != null)
		{
			hashTable.merge(iter, 1, (a,b) -> a + b);
			iter = iter.next;
		}

		return hashTable
				.entrySet()
				.stream()
				.filter(entry -> entry.getValue() > 1)
				.map(entry -> entry.getKey())
				.collect(Collectors.toList());
	}

	public static <T> ListNode<T> getConvergence(ListNode<T> listA, ListNode<T> listB)
	{
		// count both lists
		ListNode<T> aIterator = listA;
		ListNode<T> bIterator = listB;

		int aSize = getListSize(listA);
		int bSize = getListSize(listB);
		int delta = 0;

		if(aSize > bSize)
		{
			delta = aSize - bSize;
			aIterator = getListNodeAtIndex(
					delta,
					listA);

		}
		else
		{
			delta = bSize - aSize;
			bIterator = getListNodeAtIndex(
					delta,
					listB);
		}

		while(aIterator != bIterator
				&& aIterator != null
				&& bIterator != null)
		{
			aIterator = aIterator.next;
			bIterator = bIterator.next;
		}

		return aIterator;
	}

	public static <T> void evenOddMerge(ListNode<T> head)
	{
		ListNode<T> p1 = head;
		ListNode<T> p2 = head.next;
		ListNode<T> temp = null;

		int nodeIndex = 0;

		while(p2 != null)
		{
			if(nodeIndex % 2 == 0)
			{
				// increment P2 marker by 1
				p2 = p2.next;
			}
			else
			{
				// swap the odd node (p1.next) with the even node (p2).
				temp = p1.next;
				p1.next = p2;

				temp.next = p2.next.next;
				p2.next = temp;

				// increment P1 by 1 and P2 by 1
				p1 = p2;
				p2 = p2.next;
			}

			nodeIndex++;
		}
	}

	private static <T> ListNode<T> getListNodeAtIndex(
			int index,
			ListNode<T> head)
	{
		while(index-- > 0)
		{
			head = head.next;
		}
		return head;
	}

	private static <T> int getListSize(ListNode<T> aIterator)
	{
		int aSize = 0;
		while(aIterator != null)
		{
			aIterator = aIterator.next;
			aSize++;
		}
		return aSize;
	}
}
