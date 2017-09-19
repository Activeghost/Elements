package EPI.LinkedList;

import java.util.Comparator;

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
		int cursorA = 0;
		int cursorB = 0;

		while(cursorA + cursorB < a.size() + b.size() - 2)
		{
			ListNode<T> aNode = a.get(cursorA++);
			ListNode<T> bNode = b.get(cursorB++);

			switch(aNode.compareTo(bNode.data))
			{
				case 1:
					while(aNode.compareTo(bNode.data) > 0)
					{
						list.insert(bNode);
						bNode = b.get(cursorB++);
					}

					list.insert(aNode);

					break;
				case 0:
					list.insert(aNode);
					list.insert(bNode);
				case -1:
					while(aNode.compareTo(bNode.data) == -1)
					{
						list.add(aNode.data);
						aNode = a.get(cursorA++);
					}
			}
		}

		return list;
	}
}
