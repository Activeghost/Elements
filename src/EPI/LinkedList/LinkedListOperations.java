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

		while(aNodeIterator.hasNext() && bNodeIterator.hasNext())
		{
			// these are sorted lists, always insert at the tail to maintain sorted order.
			switch(aNode.compareTo(bNode.data))
			{
				case 1:
					list.insertTail(bNode);
					bNode = bNodeIterator.next();
					break;
				case 0:
					list.insertTail(aNode);
					list.insertTail(bNode);

					aNode = aNodeIterator.next();
					bNode = bNodeIterator.next();
					break;
				case -1:
   				    list.insertTail(aNode);
					aNode = aNodeIterator.next();
			}
		}

		// append remaining nodes
		list.insertTail(aNode != null ? aNode : bNode);

		return list;
	}
}
