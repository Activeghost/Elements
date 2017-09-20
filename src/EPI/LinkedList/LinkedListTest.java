package EPI.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Created by clester on 9/18/2017.
 */
class LinkedListTest
{
	private LinkedList<String> list;

	@BeforeEach
	void setup()
	{
		list = new LinkedList<>(String::compareTo);
		list.add("This is a test");
		list.add("sodales neque sodales ut etiam sit amet nisl purus in mollis nunc sed id semper risus in hendrerit gravida rutrum");
		list.add("");
		list.add("");
		list.add("elements of programming interviews");
	}

	@Test
	void add()
	{
		list.add("This is a test");
		assertEquals(6, list.size());
	}

	@Test
	void size()
	{
		assertEquals(5, list.size());
	}

	@Test
	void remove()
	{
		ListNode<String> node = list.search("");
		list.remove(node);

		assertEquals(4, list.size());
	}

	@Test
	void removebyKey()
	{
		list.remove("");
		list.remove("");
		assertEquals(3, list.size());
	}

	@Test
	void insertAfter()
	{
		final ListNode<String> newNode = new ListNode<>(String::compareTo);
		newNode.data = "New Node";

		ListNode<String> node = list.search("");
		list.insertAfter(node, newNode);

		assertEquals(6, list.size());
		assertNotNull(list.search("New Node"));
	}

	@Test
	void insert()
	{
		final ListNode<String> newNode = new ListNode<>(String::compareTo);
		newNode.data = "New Node";
		list.insertHead(newNode);

		assertEquals(6, list.size());
		assertNotNull(list.search("New Node"));
	}

	@Test
	void search()
	{
		assertNotNull(list.search("elements of programming interviews"));
		assertNotNull(list.search(""));

		list.remove("");
		assertNotNull(list.search(""));
		assertNotNull(list.search("sodales neque sodales ut etiam sit amet nisl purus in mollis nunc sed id semper risus in hendrerit gravida rutrum"));

		list.remove("");
		assertNull(list.search(""));
	}
}