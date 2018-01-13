package EPI.Trees;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

/**
 * Tree operation
 */
public class TreeOperationsTest {

    @Rule
    public ExpectedException thrown= ExpectedException.none();


    @Test
    public void computeLowestCommonAncestor()
    {
    }

    @Test
    public void computeTreeFromTraversal()
    {
        List<String> inorder = new LinkedList<String>(
                Arrays.asList("F","B", "A", "E", "H", "C", "D", "I", "G"));

        Deque<String>  preorder = new LinkedList<String>(
                Arrays.asList("H","B", "F", "E", "A", "C", "D", "G", "I"));

        TreeOperations<String, String> operations = new TreeOperations<>();
        INode<String, String> root = operations.computeTreeFromTraversal(inorder,
                                            preorder,
                                            "Test",
                                            String::compareTo);

        Assert.assertNotNull(root);
        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> integers = map.keySet();
        operations.caclulateHeight(
                root,
                (depth) -> map.merge(depth, 1, (key, value) -> value + 1) );
        Integer max = integers.stream().max(Integer::compareTo).orElse(0);

        Assert.assertEquals(4, (long)max);
    }

    @Test
    public void getLowestCommonAncestorUsingParent()
    {
        INode<Integer, String> root = createTree();
        TreeOperations<Integer, String> operations = new TreeOperations<>();
        Assert.assertEquals(operations.getLowestCommonAncestor(root,
                                                               root.getLeft(),
                                                               root.getRight()),
                            root);

        Assert.assertEquals(operations.getLowestCommonAncestor(root,
                                                               root.getLeft().getLeft().getLeft(),
                                                               root.getRight()),
                            root);

        Assert.assertEquals(operations.getLowestCommonAncestor(root,
                                                               root.getLeft().getLeft().getLeft(),
                                                               root.getLeft().getRight()),
                            root.getLeft());
    }

    @Test
    public void getLowestCommonAncestor_withNullArgs_ThrowsException()
    {
        TreeOperations<Integer, String> operations = new TreeOperations<>();
        INode<Integer, String> root = createTree();

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("The root, and both target nodes, must exist");
        operations.getLowestCommonAncestor(root,null,null);
    }

    @Test
    public void getLowestCommonAncestor()
    {
        INode<Integer, String> root = createTree();
        TreeOperations<Integer, String> operations = new TreeOperations<>();
        Assert.assertEquals(operations.getLowestCommonAncestor(root,
                                                               root.getLeft(),
                                                               root.getRight()),
                            root);

        Assert.assertEquals(operations.getLowestCommonAncestor(root,
                                                               root.getLeft().getLeft().getLeft(),
                                                               root.getRight()),
                            root);

        Assert.assertEquals(operations.getLowestCommonAncestor(root,
                                                               root.getLeft().getLeft().getLeft(),
                                                               root.getLeft().getRight()),
                            root.getLeft());
    }

    @Test
    public void checkBalancedStatus() throws Exception {
        INode<Integer, String> root = createTree();
        TreeOperations<Integer, String> operations = new TreeOperations<>();
        Assert.assertTrue(operations.checkBalancedStatus(root).balanced);
    }

    @Test
    public void caclulateHeight() throws Exception {
        INode<Integer, String> root = createTree();
        TreeOperations<Integer, String> operations = new TreeOperations<>();
        Map<Integer, Integer> map = new HashMap<>();

        operations.caclulateHeight(
                root,
                (depth) -> map.merge(depth, 1, (key, value) -> value + 1) );
        Set<Integer> integers = map.keySet();
        Integer max = integers.stream().max(Integer::compareTo).orElse(0);
        Integer min = integers.stream().min(Integer::compareTo).orElse(0);
        Assert.assertTrue(max - min <= 1  );
    }

    @Test
    public void traverse() throws Exception {
        INode<Integer, String> root = createTree();
        TreeOperations operations = new TreeOperations();
        operations.traverse(
                root,
                getStringConsumer("PreOrder: %s"),
                getStringConsumer("InOrder: %s"),
                getStringConsumer("PostOrder: %s"));
    }

    @Test
    public void isSymmetric()
    {
        TreeOperations<Integer, String> operations = new TreeOperations<>();

        // case 1, both children are null .. symmetric
        INode<Integer, String> root = new Node<>(null, 1, "A", String::compareTo);
        Assert.assertTrue(operations.isSymmetric(root));

        // case 2, one child is null .. not symmetric
        root.setLeft(new Node<>(root, 2, "B", String::compareTo));
        Assert.assertFalse(operations.isSymmetric(root));

        // case 3, both childred are set and equal.. symmetric
        root.setLeft(new Node<>(root, 2, "B", String::compareTo));
        Assert.assertFalse(operations.isSymmetric(root));
    }

    @Test
    public void getMaxTree()
    {
        TreeOperations<Integer, Integer> operations = new TreeOperations<>();
        Integer[] values = {1, 8, 3, 5, 6, 10};
        INode<Integer, Integer> tree = operations.getMaxTree(values);
        final INode<Integer, Integer> left = tree.getLeft();
        final INode<Integer, Integer> right = tree.getRight();

        Assert.assertEquals(10, (int)tree.getValue());
        Assert.assertEquals(8, (int)left.getValue());
        Assert.assertEquals(null, right);
        Assert.assertEquals(1, (int)left.getLeft().getValue());
        Assert.assertEquals(6, (int)left.getRight().getValue());
    }

    private Consumer<String> getStringConsumer(String format) {
        return (String value) -> System.out.printf(format, value);
    }

    private INode<Integer, String> createTree()
    {
        INode<Integer, String> root = new Node<Integer, String>(null, 1, "Root", String::compareTo);
        INode<Integer, String> left = new Node<Integer, String>(root, 2, "Root:Lvl1Left", String::compareTo);
        INode<Integer, String> right = new Node<Integer, String>(root, 3, "Root:Lvl1Right", String::compareTo);

        root.setLeft(left);
        root.setRight(right);

        Node<Integer, String> leftLeft = new Node<Integer, String>(left,4, "Root:Lvl1Left:Lvl2Left", String::compareTo);
        Node<Integer, String> leftRight = new Node<Integer, String>(left,5, "Root:Lvl1Left:Lvl2Right", String::compareTo);
        Node<Integer, String> rightLeft = new Node<Integer, String>(right,6, "Root:Lvl1Left:Lvl2Left", String::compareTo);
        Node<Integer, String> rightRight = new Node<Integer, String>(right,7, "Root:Lvl1Right:Lvl2Right", String::compareTo);

        left.setLeft(leftLeft);
        left.setRight(leftRight);
        right.setRight(rightRight);
        right.setLeft(rightLeft);

        leftLeft.setLeft(new Node<>(leftLeft,8, "Root:Lvl1Left:Lvl2Left:Level3Left", String::compareTo));
        leftLeft.setRight(new Node<>(leftLeft,9, "Root:Lvl1Left:Lvl2Left:Level3Right", String::compareTo));
        leftRight.setLeft(new Node<>(leftRight,10, "Root:Lvl1Left:Lvl2Right:Level3Left", String::compareTo));
        leftRight.setRight(new Node<>(leftRight,11,"Root:Lvl1Left:Lvl2Right:Level3Right", String::compareTo));
        rightLeft.setLeft(new Node<>(rightLeft,12, "Root:Lvl1Right:Lvl2Left:Level3Left", String::compareTo));
        rightLeft.setRight(new Node<>(rightLeft, 13, "Root:Lvl1Right:Lvl2Left:Level3Right", String::compareTo));
        rightRight.setLeft(new Node<>(rightRight, 14, "Root:Lvl1Right:Lvl2Right:Level3Left", String::compareTo));
        rightRight.setRight(new Node<>(rightRight,15, "Root:Lvl1Right:Lvl2Right:Level3Right", String::compareTo));

        return root;
    }
}
