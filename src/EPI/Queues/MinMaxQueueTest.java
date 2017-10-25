package EPI.Queues;

import java.util.Deque;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Test class for MinMaxQueue
 */
class MinMaxQueueTest
{

	private MinMaxQueue<Integer> getNewQueue()
	{
		MinMaxQueue<Integer> q = new MinMaxQueue<>(Integer::compareTo);
		q.enqueue(1);
		q.enqueue(2);
		q.enqueue(3);
		q.enqueue(4);
		q.enqueue(5);

		return q;
	}

	@Test
	void enqueue()
	{
		MinMaxQueue<Integer> q = new MinMaxQueue<>(Integer::compareTo);
		q.enqueue(1);
		assertEquals(1, (int) q.dequeue());
	}

	@Test
	void dequeue()
	{
		MinMaxQueue<Integer> q = getNewQueue();
		assertEquals(1, (int) q.dequeue());

	}

	@Test
	void max()
	{
		MinMaxQueue<Integer> q = getNewQueue();
		assertEquals(5, (int) q.max());
	}

	@Test
	void min()
	{
		MinMaxQueue<Integer> q = getNewQueue();
		assertEquals(1, (int) q.min());
	}

}