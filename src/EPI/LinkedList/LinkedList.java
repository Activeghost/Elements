package EPI.LinkedList;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

/**
 * Created by clester on 9/15/2017.
 */
public class LinkedList<T> implements Iterable<ListNode<T>>
{
	ListNode<T> _head;
	private final Comparator<T> _comparator;
	private int _count;
	private ListNode<T> _tail;

	public LinkedList(Comparator<T> comparator)
	{
		_head = null;
		_tail = null;
		_comparator = comparator;
		_count = 0;
	}

	public void add(T data)
	{
		ListNode<T> node = new ListNode<>(_comparator);
		node.data = data;
		insertHead(node);
	}

	public ListNode<T> get(int index)
	{
		if(index > size() - 1)
		{
			throw new IndexOutOfBoundsException("Index is greater than the size of the list");
		}

		ListNode<T> node = _head;
		for(int i = 0; i < index; i++)
		{
			node = node.next;
		}

		return node;
	}

	public ListNode<T> remove(int index)
	{
		if(index > size() - 1)
		{
			throw new IndexOutOfBoundsException("Index is greater than the size of the list");
		}

		ListNode<T> node = _head;
		for(int i = 0; i < index; i++)
		{
			node = node.next;
		}

		return remove(node);
	}

	public ListNode<T> remove(T key)
	{
		ListNode<T> node = new ListNode<>(_comparator);
		node.data = key;
		return remove(node);
	}

	public ListNode<T> remove(ListNode<T> node)
	{
		ListNode<T> temp = null;

		// find the node just before the node we want to remove, and remove it from the list.
		ListNode<T> prevNode = search(node, (a, b) ->
		{
			if(a == null |
			   a.next == null |
			   b == null)
			{
				return -1;
			}

			return _comparator.compare(a.next.data, b.data);
		});

		if(prevNode != null)
		{
			temp = prevNode.next;
			prevNode.next = Optional.ofNullable(prevNode.next.next)
									.orElse(null);
			_count--;

			if(temp.equals(_head))
			{
				_head = prevNode;
			}

			if(temp.equals(_tail))
			{
				_tail = prevNode;
			}
		}
		else if(_head.equals(node))
		{
			temp = _head;
			_head = node.next;
			_count--;
		}


		return temp;
	}

	/**
	 * Inserts after a node
	 * @param node the node to insertHead after
	 * @param newNode the new node
	 */
	public void insertAfter(ListNode<T> node, ListNode<T> newNode)
	{
		if(node.equals(_tail))
		{
			_tail = newNode;
		}

		node.next = newNode;
		_count++;
	}

	/**
	 * Inserts a new node at the head of the list.
	 * @param newNode
	 */
	public void insertHead(ListNode<T> newNode)
	{
		// if this is the first node, set the tail
		if(_head == null)
		{
			mergeToTail(newNode);
			_head = newNode;
		}
		else
		{
			mergeToHead(newNode);
		}

	}

	/**
	 * Merge the new node to the head of the list, setting the
	 * last node in the chain's .next to the head.
	 * @param newNode
	 */
	private void mergeToHead(ListNode<T> newNode)
	{	ListNode<T> node = newNode;
		ListNode<T> temp = _head;

		Iterator<ListNode<T>> it = iterator();
		_head = node;

		while(it.hasNext())
		{
			node = it.next();
			_count++;
		}

		node.next = temp;
	}

	/**
	 * Inserts a new node at the end of the list.
	 * @param newNode
	 */
	public void insertTail(ListNode<T> newNode)
	{
		mergeToTail(newNode);

		if(_head == null)
		{
			_head = newNode;
		}
	}

	private void mergeToTail(ListNode<T> newNode)
	{
		ListNode<T> node = newNode;

		if(_tail != null)
		{
			_tail.next = newNode;
		}

		while(node != null)
		{
			_tail = node;
			_count++;

			node = node.next;
		}
	}

	/**
	 * Search the list for a matching key
	 * @param key
	 * @return
	 */
	public ListNode<T> search(T key)
	{
		if(key == null)
		{
			return null;
		}

		ListNode<T> node = _head;
		while(node != null)
		{
			if(node.compareTo(key) == 0)
			{
				break;
			}

			node = node.next;
		}

		return node;
	}

	/**
	 * Search the list using a custom comparator
	 * @param targetNode
	 * @param comparator
	 * @return
	 */
	public ListNode<T> search(ListNode<T> targetNode, Comparator<ListNode<T>> comparator)
	{
		ListNode<T> node = _head;
		while(node != null)
		{
			if(comparator.compare(node, targetNode) == 0)
			{
				return node;
			}

			node = node.next;
		}

		return null;
	}

	public int size()
	{
		return _count;
	}

	@Override
	public Iterator<ListNode<T>> iterator()
	{
		Iterator<ListNode<T>> it = new Iterator<ListNode<T>>()
		{
			private int _cursor = 0;

			@Override
			public boolean hasNext()
			{
				return _cursor < _count && get(_cursor) != null;
			}

			@Override
			public ListNode<T> next()
			{
				return get(_cursor++);
			}
		};

		return it;
	}
}
