/**
 * copyright Chris Lester (9/9/2017)
 */
package EPI.Trees;

import org.omg.CORBA.TRANSACTION_REQUIRED;

import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * A binary search tree implementation
 */
public class BinaryTree<K, V> {
    private INode<K, V> _root;
    private final Comparator<V> _comparator;
    private int _nodeCount;

    public BinaryTree(Comparator<V> comparator) {
        _comparator = comparator;
        _nodeCount = 0;
    }

    /**
     * Add a new node to the tree
     *
     * @param node   the node to add
     */
    public void insert(INode<K, V> node) {
        if (_root == null)
        {
            _root = node;
        }
        else {
            insert(_root, node);
        }

        _nodeCount++;
    }

    /**
     * Add a new node to the tree
     *
     * @param key   the key to add
     * @param value the value to add
     */
    public void insert(K key, V value) {
        INode<K, V> node = new Node<K, V>(key, value, _comparator);
        insert(node);
    }

    private INode<K, V> insert(INode<K, V> root, INode<K, V> node)
    {
        if(root == null)
        {
            root = node;
        }
        else if (node.compareTo(root) < 0)
        {
            root.setLeft(insert(root.getLeft(), node));
        }
        else
        {
            root.setRight(insert(root.getRight(), node));
        }

        return root;
    }

    /**
     * Perform an in order traversal of the tree, and run the consumer on each node.
     * @param consumer
     */
    public void inOrderTraversal(Consumer<INode<K, V>> consumer)
    {
        traverseInOrder(_root, consumer);
    }

    /**
     * Perform a breadth first traversal
     * @param consumer
     */
    public void breadthFirstTraversal(BiConsumer<INode<K, V>, Integer> consumer)
    {
        Deque<INode<K, V>> queue = new LinkedList<>();
        queue.push(_root);

        traverseBreadthFirst(queue, consumer, 1);
    }

    /**
     * Perform a breadth first traversal
     * @param consumer
     */
    public void breadthFirstTraversal(Consumer<INode<K, V>> consumer)
    {
        Deque<INode<K, V>> children = new LinkedList<>();
        processNodeForBreathFirstTraversal(children, _root, consumer);

        traverseBreadthFirst(children, consumer);
    }

    /**
     * Perform a breadth first traversal
     * @param consumer
     */
    public void bottomUpBreadthFirstTraversal(Consumer<INode<K, V>> consumer)
    {
        Deque<INode<K, V>> treeOrder = new LinkedList<>();
        Deque<INode<K, V>> queue = new LinkedList<>();

        queue.push(_root);
        while(queue.size() > 0)
        {
            // add them R -> L as we'll be reading them off backwards
            INode<K, V> node = queue.pop();
            treeOrder.push(node);
            addRightChild(queue, node);
            addLeftChild(queue, node);
        }

        while(treeOrder.size() > 0)
        {
            consumer.accept(treeOrder.pop());
        }
    }

    /**
     * Perform a top-down breadth first traversal that traverses left - right, then right to left.
     * @param consumer
     */
    public void donkeyKongTraversal(Consumer<INode<K, V>> consumer)
    {
        Deque<INode<K, V>> children = new LinkedList<>();
        processNodeForDonkeyKongTraversal(children, _root, consumer, true);
        traverseDonkeyKong(children, consumer, false);
    }

    private void traverseInOrder(INode<K, V> node, Consumer<INode<K, V>> consumer)
    {
        if(node != null) {
            traverseInOrder(node.getLeft(), consumer);
            consumer.accept(node);
            traverseInOrder(node.getRight(), consumer);
        }
    }

    private void traverseBreadthFirst(Deque<INode<K, V>> currentLevel, Consumer<INode<K, V>> consumer)
    {
        if(currentLevel.isEmpty())
        {
            return;
        }

        // For all current nodes, add their children to child queue
        // in a left to right ordering
        Deque<INode<K, V>> children = new LinkedList<>();

        while(!currentLevel.isEmpty())
        {
            processNodeForBreathFirstTraversal(children, currentLevel.removeFirst(), consumer);
        }

        traverseBreadthFirst(children, consumer);
    }

    private void traverseBreadthFirst(
            Deque<INode<K, V>> currentLevel,
            BiConsumer<INode<K, V>, Integer> consumer,
            int depth)
    {
        if(currentLevel.isEmpty())
        {
            return;
        }

        // For all current nodes, add their children to child queue
        // in a left to right ordering
        Deque<INode<K, V>> children = new LinkedList<>();

        while(!currentLevel.isEmpty())
        {
            INode<K, V> parent = currentLevel.removeFirst();
            consumer.accept(parent, depth);
            processNodeForBreathFirstTraversal(children, parent);
        }

        traverseBreadthFirst(children, consumer, depth + 1);
    }

    private void traverseDonkeyKong(Deque<INode<K, V>> currentLevel,
            Consumer<INode<K, V>> consumer,
            boolean leftToRight)
    {
        if(currentLevel.isEmpty())
        {
            return;
        }

        Iterator<INode<K, V>> iter;
        if(!leftToRight)
        {
            iter = currentLevel.descendingIterator();
        }
        else
        {
            iter = currentLevel.iterator();
        }

        // For all current nodes, add their children to child queue
        // in a left to right ordering
        Deque<INode<K, V>> children = new LinkedList<>();

        while(iter.hasNext())
        {
            processNodeForDonkeyKongTraversal(
                    children,
                    iter.next(),
                    consumer,
                    leftToRight);
        }

        traverseDonkeyKong(children, consumer, !leftToRight);
    }

    private void processNodeForDonkeyKongTraversal(Deque<INode<K, V>> children,
            INode<K, V> parent,
            Consumer<INode<K, V>> consumer,
            boolean moveLeft)
    {
        consumer.accept(parent);

        if(moveLeft)
        {
            addLeftChild(children, parent);
            addRightChild(children, parent);
        }
        else
        {
            addRightChild(children, parent);
            addLeftChild(children, parent);
        }
    }

    private void addLeftChild(Deque<INode<K, V>> children, INode<K, V> parent)
    {
        if (parent.hasLeftChild())
		{
			children.addLast(parent.getLeft());
		}
    }

    private void addRightChild(Deque<INode<K, V>> children, INode<K, V> parent)
    {
        if(parent.hasRightChild())
		{
			children.addLast(parent.getRight());
		}
    }

    private void processNodeForBreathFirstTraversal(Deque<INode<K, V>> children,
                                                    INode<K, V> parent,
                                                    Consumer<INode<K, V>> consumer)
    {
        consumer.accept(parent);

        addLeftChild(children, parent);
        addRightChild(children, parent);
    }

    private void processNodeForBreathFirstTraversal(Deque<INode<K, V>> children,
                                                    INode<K, V> parent)
    {
        addLeftChild(children, parent);
        addRightChild(children, parent);
    }

    public int size()
    {
        return _nodeCount;
    }
}

class Node<K, V> implements INode<K, V>
{
    private K key;
    private V value;
    private INode<K, V> left;
    private INode<K, V> right;

    private final Comparator<V> _comparator;

    public Node(K k, V v, Comparator<V> comparator)
    {
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
}
