package EPI.LinkedList;

import java.util.Comparator;

public class JumpListNode<T> extends ListNode<T>
{
	public int order = -1;
	public ListNode<T> jumpTo;

	public JumpListNode(Comparator<T> comparator)
	{
		super(comparator);
	}
	public JumpListNode(T theData, Comparator<T> comparator)
	{
		super(theData, comparator);
	}
}
