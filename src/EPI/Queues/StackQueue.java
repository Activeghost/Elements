package EPI.Queues;

import java.util.Comparator;
import java.util.function.Consumer;

import EPI.Stacks.Stack;

/**
 * Created by clester on 11/16/2017.
 */
public class StackQueue<T>
{
	private final Stack<T> _enqueue;
	private final Stack<T> _dequeue;

	public StackQueue(Comparator<T> compareTo)
	{
		_enqueue = new Stack<>(compareTo);
		_dequeue = new Stack<>(compareTo);
	}

	public void enqueue(T value)
	{

	}

	public T dequeue()
	{
		// if we have nothing in the _dequeue stack, move _enqueue over.
		if(_dequeue.isEmpty())
		{

		}

		return _dequeue.pop();
	}
}
