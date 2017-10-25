package EPI.LinkedList;

import java.util.Comparator;

/**
 * Created by clester on 9/15/2017.
 */
public class ListNode<T> implements Comparable<T>
{
	public T data;
	public ListNode<T> next;

	private final Comparator<T> _comparator;

	public ListNode(Comparator<T> comparator)
	{
		_comparator = comparator;
	}

	public ListNode(T theData, Comparator<T> comparator)
	{
		data = theData;
		_comparator = comparator;
	}

	@Override
	public int compareTo(T data)
	{
		return _comparator.compare(this.data, data);
	}
}

