package EPI.Trees;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import EPI.Strings.StringOperations;

/**
 * BTee test class
 */
public class BinaryTreeTest {
    private static int sum = 0;

    @Test
    public void donkeyKongTraversal() throws Exception
    {
        StringBuilder s = new StringBuilder();
        String expected = "7 5 11 13 10 6 3 1 4 5 7 9 11 12 15";
        BinaryTree<String, Integer> tree = buildSimpleTree();

        tree.donkeyKongTraversal(n -> s.append(n.getValue()).append(" "));
        assertEquals(expected, s.toString());
    }

    @Test
    public void size() throws Exception
    {
    }

    @Test
    public void breadthFirstTraversal() throws Exception
    {
        StringBuilder s = new StringBuilder();
        String expected = "0 1 0 1 0 1 0 1 0 1 ";
        BinaryTree<Integer, Integer> tree = buildIntegerTree();

        tree.breadthFirstTraversal(n -> s.append(n.getValue()).append(" "));
        assertEquals(expected, s.toString());
    }

    @Test
    public void insert() throws Exception {
        BinaryTree tree = buildIntegerTree("sodales neque sodales ut etiam sit amet nisl purus in mollis nunc sed id semper risus in hendrerit gravida rutrum");
        assertEquals(18, tree.size());
    }

    @Test
    public void inOrderTraversal() throws Exception {
        StringBuilder s = new StringBuilder();
        String paragraph = "sodales neque sodales ut etiam sit amet nisl purus in mollis nunc sed id semper risus in hendrerit gravida rutrum";
        String expected = "amet etiam gravida hendrerit id mollis neque nisl nunc purus risus rutrum sed semper sit ut in sodales ";
        BinaryTree<String, Integer> tree = buildIntegerTree(paragraph);

        assertEquals(18, tree.size());

        tree.inOrderTraversal(n -> s.append(n.getKey()).append(" "));
        tree.inOrderTraversal(n -> sum += n.getValue());
        assertEquals(expected, s.toString());
        assertEquals(20, sum);
    }

    private BinaryTree<String, Integer> buildIntegerTree(String paragraph)
    {
        BinaryTree<String, Integer> tree = new BinaryTree<>(Integer::compare);
        Map<String, Integer> dictionary = new TreeMap<>();
        StringOperations.getWords(paragraph, dictionary);

        for(Map.Entry<String, Integer> entry : dictionary.entrySet())
        {
            tree.insert(entry.getKey(), entry.getValue());
        }

        // add last word
        return tree;
    }

    private BinaryTree<Integer, Integer> buildIntegerTree()
    {
        BinaryTree<Integer, Integer> tree = new BinaryTree<>(Integer::compareTo);
        for(int i = 0; i < 10; i++)
        {
            tree.insert(i, i % 2);
        }

        // add last word
        return tree;
    }

    private BinaryTree<String, Integer> buildSimpleTree()
    {
        BinaryTree<String, Integer> tree = new BinaryTree<>(Integer::compareTo);
        tree.insert(new Node<>("ROOT", 7, Integer::compareTo));
        tree.insert(new Node<>("LEVEL1_LEFT", 5, Integer::compareTo));
        tree.insert(new Node<>("LEVEL1_RIGHT", 11, Integer::compareTo));
        tree.insert(new Node<>("LEVEL2N1_LEFT", 3, Integer::compareTo));
        tree.insert(new Node<>("LEVEL2N1_RIGHT", 6, Integer::compareTo));
        tree.insert(new Node<>("LEVEL2N2_LEFT", 10, Integer::compareTo));
        tree.insert(new Node<>("LEVEL2N2_RIGHT", 13, Integer::compareTo));

        tree.insert(new Node<>("LEVEL3N1_LEFT", 1, Integer::compareTo));
        tree.insert(new Node<>("LEVEL3N1_RIGHT", 4, Integer::compareTo));
        tree.insert(new Node<>("LEVEL3N2_LEFT", 5, Integer::compareTo));
        tree.insert(new Node<>("LEVEL3N2_RIGHT", 7, Integer::compareTo));
        tree.insert(new Node<>("LEVEL3N3_LEFT", 9, Integer::compareTo));
        tree.insert(new Node<>("LEVEL3N3_RIGHT", 11, Integer::compareTo));
        tree.insert(new Node<>("LEVEL3N4_LEFT", 12, Integer::compareTo));
        tree.insert(new Node<>("LEVEL3N4_RIGHT", 15, Integer::compareTo));


        // add last word
        return tree;
    }
}
