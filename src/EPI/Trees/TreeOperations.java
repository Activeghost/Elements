package EPI.Trees;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import sun.plugin.dom.exception.InvalidStateException;

/**
 * Perform tree operations on nodes
 */
public class TreeOperations<KeyType, ValueType>
{
    void traverse(INode<KeyType, ValueType> root,
            Consumer<ValueType> preOrderFn,
            Consumer<ValueType> inOrderFn,
            Consumer<ValueType> postOrderFn)
    {
        if (root == null)
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
        if (root == null)
        {
            return new BalancedStatusWithHeight(true, -1);
        }

        BalancedStatusWithHeight leftResult = checkBalancedStatus(root.getLeft());
        if (!leftResult.balanced)
        {
            return leftResult;
        }

        BalancedStatusWithHeight rightResult = checkBalancedStatus(root.getRight());
        if (!rightResult.balanced)
        {
            return rightResult;
        }

        boolean isBalanced = Math.abs(leftResult.height - rightResult.height) <= 1;
        int height = Math.max(leftResult.height, rightResult.height);

        return new BalancedStatusWithHeight(isBalanced, height);
    }

    /**
     * Caclulates the height of the tree
     *
     * @param root     the root node
     * @param consumer the consumer function to record heights
     */
    public void caclulateHeight(INode<KeyType, ValueType> root, Consumer<Integer> consumer)
    {
        if (root == null)
        {
            return;
        }

        int depth = 0;
        INode<KeyType, ValueType> parent = root.getParent();
        if (parent != null)
        {
            depth = parent.getDepth() + 1;
        }

        root.setDepth(depth);

        // only process the leaf nodes
        if (isLeaf(root))
        {
            consumer.accept(root.getDepth());
        }
        else
        {
            // traverse left
            caclulateHeight(root.getLeft(), consumer);

            // traverse right
            caclulateHeight(root.getRight(), consumer);
        }
    }

    /**
     * Checks whether a node is a leaf or not.
     * @param root
     * @return
     */
    private boolean isLeaf(INode<KeyType, ValueType> root) {
        return !(root.hasLeftChild() || root.hasRightChild());
    }

    /**
     * Checks for tree symmetry
     * @param root the tree root
     * @return true if the tree is symmetric.
     */
    public boolean isSymmetric(INode<KeyType, ValueType> root)
    {
        return checkSymmetry(root.getLeft(), root.getRight());
    }

    private boolean checkSymmetry(INode<KeyType, ValueType> left, INode<KeyType, ValueType> right)
    {
        if(left == null && right == null)
        {
            return true;
        }
        else if(left == null || right == null)
        {
            return false;
        }

        boolean isSymmetric = left.compareTo(right) != 0;
        isSymmetric &= checkSymmetry(left.getLeft(), right.getRight());
        isSymmetric &= checkSymmetry(left.getRight(), right.getLeft());

        return isSymmetric;
    }

    public INode<KeyType, ValueType> computeLowestCommonAncestor(
            INode<KeyType, ValueType> a,
            INode<KeyType, ValueType> b)
    {
        if (a == null || b == null)
        {
            throw new IllegalArgumentException("The root, and both target nodes, must exist");
        }

        // walk a to root, store the level
        int aDepth = getDepth(a);
        int bDepth = getDepth(b);

        if(aDepth == bDepth)
        {
            return findRoot(a);
        }
        else if(aDepth > bDepth)
        {
            return walkUp(a, aDepth - bDepth);
        }
        else if(aDepth < bDepth)
        {
            return walkUp(b, bDepth - aDepth);
        }
    }

    private INode<KeyType, ValueType> findRoot(INode<KeyType, ValueType> node)
    {
        if(node == null)
        {
            return null;
        }

        while(node.getParent() != null)
        {
            node = node.getParent();
        }

        return node;
    }

    private INode<KeyType, ValueType> walkUp(INode<KeyType, ValueType> node, int i)
    {
        while(i > 0)
        {
            node = node.getParent();
            --i;
        }

        return node;
    }

    private int getDepth(INode<KeyType, ValueType> a)
    {
        int depth = 0;
        INode<KeyType, ValueType> aParent = a.getParent();

        while(a.getParent() != null)
        {
            aParent = aParent.getParent();
            depth++;
        }

        return depth;
    }

    public INode<KeyType, ValueType> getLowestCommonAncestor(INode<KeyType, ValueType> root,
            INode<KeyType, ValueType> a,
            INode<KeyType, ValueType> b)
    {
        if (a == null || b == null)
        {
            throw new IllegalArgumentException("The root, and both target nodes, must exist");
        }

        return computeLowestCommonAncestor(root, a, b).lcaNode;
    }

    private LCAInfo<KeyType, ValueType> computeLowestCommonAncestor(INode<KeyType, ValueType> root,
            INode<KeyType, ValueType> a,
            INode<KeyType, ValueType> b)
    {
        /*
            base cases

            1. root is null
            2. root is a target
            3. root has no children, is not a target
            4. root has only left children, is not the target
            5. root has only right children, is not the target
            6. root has children, is not the target
        */

        LCAInfo<KeyType, ValueType> lcaInfo = new LCAInfo<>();

        if(root == null)
        {
            return lcaInfo;
        }
        else if (root.compareTo(a) == 0)
        {
            lcaInfo.foundA = true;
            return lcaInfo;
        }
        else if (root.compareTo(b) == 0)
        {
            lcaInfo.foundB = true;
            return lcaInfo;
        }

        LCAInfo<KeyType, ValueType> leftInfo = computeLowestCommonAncestor(root.getLeft(), a, b);
        LCAInfo<KeyType, ValueType> rightInfo = computeLowestCommonAncestor(root.getRight(), a, b);

        lcaInfo.lcaNode = leftInfo.lcaNode != null?  leftInfo.lcaNode  : rightInfo.lcaNode;
        lcaInfo.foundA = leftInfo.foundA || rightInfo.foundA;
        lcaInfo.foundB = leftInfo.foundB || rightInfo.foundB;

        // set lca node only once
        if(lcaInfo.foundA && lcaInfo.foundB && lcaInfo.lcaNode == null)
        {
            lcaInfo.lcaNode = root;
        }

        return lcaInfo;
    }

}
