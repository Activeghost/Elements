package EPI.Queues;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import EPI.Trees.BinaryTree;

/**
 * Utility class for queue operations
 */
public class QueueOperations
{
	public <T, V> List<T> computeDepthOrder(BinaryTree<T, V> tree)
	{
		List<T> computedOrder = new ArrayList<>();
		Deque<T> childOrder = new LinkedList<>();
		Deque<T> currentLevel = new LinkedList<>();

		return computedOrder;
	}
}
