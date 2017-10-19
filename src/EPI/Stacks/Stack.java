package EPI.Stacks;

import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

class ElementWithCachedMax<T>
{
	T element;
	T max;
	T min;
}

/**
 * Stack class
 */
public class Stack<T>
{
	private final Deque<ElementWithCachedMax<T>> _deque;
	private Comparator<T> _comparator;
	private T _max;
	private T _min;

	public Stack(Comparator<T> comparator)
	{
		_comparator = comparator;
		_deque = new LinkedList<>();
	}

	public void push(T item)
	{
		ElementWithCachedMax<T> wrappedItem = new ElementWithCachedMax<>();
		if(_comparator.compare(_max, item) == 1)
		{
			_max = item;
		}

		if(_comparator.compare(_min, item) == -1)
		{
			_min = item;
		}

		wrappedItem.max = _max;
		wrappedItem.min = _min;
		_deque.push(wrappedItem);
	}

	public T pop()
	{
		ElementWithCachedMax<T> item = _deque.pop();
		_max = item.max;
		_min = item.min;

		return item.element;
	}

	public int size()
	{
		return _deque.size();
	}

	public T min()
	{
		return _min;
	}

	public T max()
	{
		return _max;
	}

	public boolean isEmpty()
	{
		return _deque.isEmpty();
	}
}
