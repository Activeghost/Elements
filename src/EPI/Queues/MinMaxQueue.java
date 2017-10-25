package EPI.Queues;

import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;

/**
 * A facade providing queue services with min and max functions.
 */
public class MinMaxQueue<T>
{
	private final Deque<T> dequeue = new LinkedList<>();
	private final Comparator<T> _comparator;

	/**
	 * Instantiate the min max queue class.
	 * @param comparator a comparator
	 */
	public MinMaxQueue(Comparator<T> comparator)
	{
		_comparator = comparator;
	}

	/**
	 * Add a new element to the end of the queue.
	 * @param item the item to add
	 */
	public void enqueue(T item)
	{
		dequeue.addLast(item);
	}

	/**
	 * remove the first element from the queue.
	 * @return item of type T
	 */
	public T dequeue()
	{
		return dequeue.removeFirst();
	}

	/**
	 * Max function
	 * @return the maximum element
	 */
	public T max()
	{
		return max(_comparator);
	}

	/**
	 * Return the maximum element in the queue
	 * @param comparator the comparison function
	 * @return the maximum element as given by the comparator
	 */
	public T max(Comparator<T> comparator)
	{
		if(!dequeue.isEmpty())
		{
			return Collections.max(dequeue,comparator);
		}

		throw new IllegalStateException("Queue has no items, cannot perform max() operation.");
	}

	/**
	 * Min function
	 * @return the minimum element
	 */
	public T min()
	{
		return min(_comparator);
	}

	/**
	 * Return the minimum element in the queue
	 * @param comparator the comparison function
	 * @return the minimum element as given by the comparator
	 */
	public T min(Comparator<T> comparator)
	{
		if(!dequeue.isEmpty())
		{
			return Collections.min(dequeue,comparator);
		}

		throw new IllegalStateException("Queue has no items, cannot perform min() operation.");
	}
}
