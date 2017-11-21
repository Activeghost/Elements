package EPI.Trees;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Perform tree operations on nodes
 */
public class TreeOperations<KeyType, ValueType>  {
    void traverse(INode<KeyType, ValueType> root,
                                       Consumer<ValueType> preOrderFn,
                                       Consumer<ValueType> inOrderFn,
                                       Consumer<ValueType> postOrderFn){
        if(root == null)
        {
            return;
        }

        // pre-order
        preOrderFn.accept(root.getValue());

        // traverse left
        traverse(root.getLeft(), preOrderFn, inOrderFn, postOrderFn);

        // in order
        inOrderFn.accept(root.getValue());

        // traverse right
        traverse(root.getRight(), preOrderFn, inOrderFn, postOrderFn);
        postOrderFn.accept(root.getValue());
    }

    public BalancedStatusWithHeight checkBalancedStatus(INode<KeyType, ValueType> root)
    {
        if(root == null)
        {
            return new BalancedStatusWithHeight(true, -1);
        }

        BalancedStatusWithHeight leftResult = checkBalancedStatus(root.getLeft());
        if(!leftResult.balanced)
        {
            return leftResult;
        }

        BalancedStatusWithHeight rightResult = checkBalancedStatus(root.getRight());
        if(!rightResult.balanced)
        {
            return rightResult;
        }

        boolean isBalanced = Math.abs(leftResult.height - rightResult.height) <= 1;
        int height = Math.max(leftResult.height, rightResult.height);

        return new BalancedStatusWithHeight(isBalanced, height);
    }

    /**
     * Caclulates the height of the tree
     * @param root the root node
     * @param consumer the consumer function to record heights
     */
    public void caclulateHeight(INode<KeyType, ValueType> root, Consumer<Integer> consumer)
    {
        if(root == null)
        {
            return;
        }

        int depth = 0;
        INode<KeyType, ValueType> parent = root.getParent();
        if(parent != null)
        {
            depth = parent.getDepth() + 1;
        }

        root.setDepth(depth);

        // only process the leaf nodes
        if(isLeaf(root)) {
            consumer.accept(root.getDepth());
        }
        else {
            // traverse left
            caclulateHeight(root.getLeft(), consumer);

            // traverse right
            caclulateHeight(root.getRight(), consumer);
        }
    }

    private boolean isLeaf(INode<KeyType, ValueType> root) {
        return !(root.hasLeftChild() || root.hasRightChild());
    }
}
