/**
 * copyright Chris Lester (9/9/2017)
 */
package EPI.Trees;

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
        INode<K, V> node = new Node<K, V>(null, key, value, _comparator);
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
    }

    /**
     * Perform a breadth first traversal
     * @param consumer
     */
    public void breadthFirstTraversal(Consumer<INode<K, V>> consumer)
    {
        Deque<INode<K, V>> current = new LinkedList<>();
        current.add(_root);
        traverseBreadthFirst(current, consumer);
    }

    /**
     * Perform a breadth first traversal
     * @param consumer
     */
    public void depthFirstTraversal(Consumer<INode<K, V>> consumer)
    {
        Deque<INode<K, V>> current = new LinkedList<>();
        current.add(_root);
        traverseDepthFirst(current, consumer);
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
        Deque<INode<K, V>> current = new LinkedList<>();
        current.add(_root);
        traverseDonkeyKong(current, consumer, false);
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
            final INode<K, V> parent = currentLevel.removeFirst();
            consumer.accept(parent);
            addChildLeftToRight(children, parent);
        }

        traverseBreadthFirst(children, consumer);
    }

    /**
     * traverse the tree in a depth first, right to left fashion.
     * @param currentLevel
     * @param consumer
     */
    private void traverseDepthFirst(Deque<INode<K, V>> currentLevel, Consumer<INode<K, V>> consumer)
    {
        // parse children first
        if(currentLevel.isEmpty())
        {
            return;
        }

        // For all current nodes, add their children to child queue
        // in a left to right ordering
        Deque<INode<K, V>> children = new LinkedList<>();

        while(!currentLevel.isEmpty())
        {
            final INode<K, V> parent = currentLevel.removeFirst();
            addChildLeftToRight(children, parent);

            consumer.accept(parent);
        }

        traverseBreadthFirst(children, consumer);
    }

    /**
     * Traverse in a left to right, then right to left fashion breadth first.
     * @param currentLevel the current level
     * @param consumer consumer function
     * @param leftToRight vector of movement
     */
    private void traverseDonkeyKong(Deque<INode<K, V>> currentLevel,
            Consumer<INode<K, V>> consumer,
            boolean leftToRight)
    {
        if(currentLevel.isEmpty())
        {
            return;
        }

        Iterator<INode<K, V>> iter = currentLevel.iterator();

        // For all current nodes, add their children to child queue
        // in a left to right ordering
        Deque<INode<K, V>> children = new LinkedList<>();

        // process parents in list order
        while(iter.hasNext())
        {
            consumer.accept(iter.next());
        }

        // process children in reverse order
        iter = currentLevel.descendingIterator();
        while(iter.hasNext())
        {
            // if processing a child, they will be in reverse order of the parent
            final INode<K, V> next = iter.next();
            if(leftToRight)
            {
                addLeftChild(children, next);
                addRightChild(children, next);
            }
            else
            {
                addRightChild(children, next);
                addLeftChild(children, next);
            }
        }

        traverseDonkeyKong(children, consumer, !leftToRight);
    }

    private void addLeftChild(Deque<INode<K, V>> children, INode<K, V> parent)
    {
        if (parent.hasLeftChild())
		{
			children.addLast(parent.getLeft());
		}
    }

    private void addChildLeftToRight(Deque<INode<K, V>> children,
            INode<K, V> parent)
    {
        addLeftChild(children, parent);
        addRightChild(children, parent);
    }

    private void addRightChild(Deque<INode<K, V>> children, INode<K, V> parent)
    {
        if(parent.hasRightChild())
		{
			children.addLast(parent.getRight());
		}
    }

    public int size()
    {
        return _nodeCount;
    }
}

