package EPI.Trees;

import java.util.Comparator;

public class Node<K, V> implements INode<K, V>
{
    private K key;
    private V value;
    private INode<K, V> left;
    private INode<K, V> right;
    private INode<K, V> parent;
    private int depth;

    private final Comparator<V> _comparator;

    public Node(INode<K, V> parent, K k, V v, Comparator<V> comparator)
    {
        this.parent = parent;
        key = k;
        value = v;
        _comparator = comparator;
    }

    @Override
    public int compareTo(INode<K, V> o) {
        return _comparator.compare(this.value, o.getValue());
    }

    @Override
    public boolean hasRightChild()
    {
        return right != null;
    }

    @Override
    public boolean hasLeftChild()
    {
        return left != null;
    }

    @Override
    public K getKey()
    {
        return key;
    }

    @Override
    public void setKey(K theKey)
    {
        key = theKey;
    }

    @Override
    public V getValue()
    {
        return value;
    }

    @Override
    public void setValue(V theValue)
    {
        value = theValue;
    }

    @Override
    public INode<K, V> getLeft()
    {
        return left;
    }

    @Override
    public INode<K, V> getRight()
    {
        return right;
    }

    @Override
    public void setLeft(INode<K, V> node)
    {
        left = node;
    }

    @Override
    public void setRight(INode<K, V> node)
    {
        right = node;
    }

    public INode<K, V> getParent() {
        return parent;
    }

    public void setParent(INode<K, V> parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
