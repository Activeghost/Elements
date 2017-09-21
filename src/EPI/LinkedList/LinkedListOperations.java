package EPI.LinkedList;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by clester on 9/15/2017.
 */
public class LinkedListOperations
{
	public static <T> LinkedList<T> merge(
			LinkedList<T> a,
			LinkedList<T> b,
			Comparator<T> comparator)
	{
		LinkedList<T> list = new LinkedList<>(comparator);
		Iterator<ListNode<T>> aNodeIterator = a.iterator();
		Iterator<ListNode<T>> bNodeIterator = b.iterator();
		ListNode<T> aNode = aNodeIterator.next();
		ListNode<T> bNode = bNodeIterator.next();

		// insert dummy head node
		list.insertHead(new ListNode<T>(comparator));

		while(aNodeIterator.hasNext() && bNodeIterator.hasNext())
		{
			// these are sorted lists, always insert at the tail to maintain sorted order.
			switch(aNode.compareTo(bNode.data))
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
		list.insertTail(aNode != null ? aNode : bNode);

		return list;
	}

	public static <T> ListNode<T> merge(
			ListNode<T> a,
			ListNode<T> b,
			Comparator<T> comparator)
	{
		ListNode<T> merged = new ListNode<>(comparator);
		ListNode<T> current  = merged;

		while(a != null && b != null)
		{
			if(a.compareTo(b.data) < 0)
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
		return merged;
	}

}
