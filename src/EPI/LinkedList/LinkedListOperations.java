package EPI.LinkedList;

import java.util.Comparator;
import java.util.Iterator;

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

	public static <T> void reverseSublist(ListNode<T> head,
			int start,
			int end,
			Comparator<T> comparator)
	{
		ListNode<T> current = new ListNode<T>(comparator);
		current.next = head;

		ListNode<T> prev = null;
		ListNode<T> post = null;

		// find the nodes just prior and post the start and end nodes
		for (int i = 0; i < end; i++)
		{
			if (i + 1 == start)
			{
				prev = current;
			}

			if (i + 1 == end)
			{
				post = current.next;
			}

			current = current.next;
		}

		ListNode<T> next;
		current = prev.next;

		for (int j = start; j < end; j++)
		{
			// save off next
			next = current.next;

			// set next pointer to end and move the end
			current.next = post;
			post = current;

			// move current pointer to next
			current = next;
		}

		prev.next = post;
	}

	public static <T> void reverseList(ListNode<T> head, Comparator<T> comparator)
	{
		ListNode<T> current = new ListNode<T>(comparator);
		current.next = head;

		int i = 0;

		ListNode<T> next;

		while (current != null)
		{
			// save off next
			next = current.next;

			// set the current as the last node.
			current.next = null;

			// move current pointer to next
			current = next;
		}
	}

	/**
	 * Reverse the list in batches of size k, leave any remainder as is
	 * @param head
	 * @param batch
	 * @param comparator
	 * @param <T>
	 */
	public static <T> void reverseList(ListNode<T> head, int batch, Comparator<T> comparator)
	{
		ListNode<T> current = new ListNode<T>(comparator);
		current.next = head;

		/// find the size, remove our dummy node from the count (start from 0 and not 1);
		int size = 0;
		while (current != null)
		{
			current = current.next;
			size++;
		}

		while (size > size % batch)
		{
			reverseSublist(head, size - batch, size, comparator);
			size -= batch;
		}
	}
}
