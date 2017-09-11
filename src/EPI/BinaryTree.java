/**
 * copyright Chris Lester (9/9/2017)
 */
package EPI;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * A binary search tree implementation
 */
public class BinaryTree<K, V> {
    private Node<K, V> _root;
    private final Comparator<V> _comparator;
    private int _nodeCount;

    public BinaryTree(Comparator<V> comparator) {
        _comparator = comparator;
        _nodeCount = 0;
    }

    /**
     * Add a new node to the tree
     *
     * @param key   the key to add
     * @param value the value to add
     */
    public void insert(K key, V value) {
        Node<K, V> node = new Node<K, V>(key, value, _comparator);
        if (_root == null)
        {
            _root = node;
        }
        else {
            insert(_root, node);
        }

        _nodeCount++;
    }

    private Node<K, V> insert(Node<K, V> root, Node<K, V> node)
    {
        if(root == null)
        {
            root = node;
        }
        else if (node.compareTo(root) < 0)
        {
            root.left = insert(root.left, node);
        }
        else
        {
            root.right = insert(root.right, node);
        }

        return root;
    }

    /**
     * Perform an in order traversal of the tree, and run the consumer on each node.
     * @param consumer
     */
    public void inOrderTraversal(Consumer<Node<K, V>> consumer)
    {
        traverse(_root, consumer);
    }

    private void traverse(Node<K, V> node, Consumer<Node<K, V>> consumer)
    {
        if(node != null) {
            traverse(node.left, consumer);
            consumer.accept(node);
            traverse(node.right, consumer);
        }
    }

    public int size()
    {
        return _nodeCount;
    }
}

class Node<K, V> implements Comparable<Node<K,V>>
{
    K key;
    V value;
    Node<K, V> left;
    Node<K, V> right;

    private final Comparator<V> _comparator;

    public Node(K k, V v, Comparator<V> comparator)
    {
        key = k;
        value = v;
        _comparator = comparator;
    }

    @Override
    public int compareTo(Node<K, V> o) {
        return _comparator.compare(this.value, o.value);
    }
}
