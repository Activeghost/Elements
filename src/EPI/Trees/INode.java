package EPI.Trees;

/**
 * Created by clester on 10/30/2017.
 */
public interface INode<K, V> extends Comparable<INode<K,V>>
{
	@Override
	int compareTo(INode<K,V> o);

	boolean hasRightChild();

	boolean hasLeftChild();

	K getKey();

	void setKey(K key);

	V getValue();

	void setValue(V value);

	INode<K, V> getLeft();

	INode<K, V> getRight();

	void setLeft(INode<K, V> node);

	void setRight(INode<K, V> node);
}
