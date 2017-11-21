package EPI.Trees;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Tree operation
 */
public class TreeOperationsTest {
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