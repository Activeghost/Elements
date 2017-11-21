package EPI.Trees;

import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import EPI.Strings.StringOperations;

/**
 * BTee test class
 */
public class BinaryTreeTest {
    private static int sum = 0;

    @Test
    public void bottomUpBreadthOrder()
    {
        StringBuilder s = new StringBuilder();
        String expected = "9 12 1 4 5 7 11 15 3 6 10 13 5 11 7";
        BinaryTree<String, Integer> tree = buildSimpleTree();

        tree.bottomUpBreadthFirstTraversal(n -> s.append(n.getValue()).append(" "));
        assertEquals(expected, s.toString().trim());
    }

    @Test
    public void donkeyKongTraversal() throws Exception
    {
        StringBuilder s = new StringBuilder();
        String expected = "7 11 5 3 6 10 13 15 11 7 5 4 1 9 12";
        BinaryTree<String, Integer> tree = buildSimpleTree();

        tree.donkeyKongTraversal(n -> s.append(n.getValue()).append(" "));
        assertEquals(expected, s.toString().trim());
    }

    @Test
    public void size() throws Exception
    {
    }

    @Test
    public void breadthFirstTraversal_AverageKey() throws Exception
    {
        Map<Integer,Double> expectedResult = new HashMap<>();
        expectedResult.put(1, 1.0);
        expectedResult.put(2, 2.5);
        expectedResult.put(3, 5.5);
        expectedResult.put(4, 10.5D);

        KeyAveragingAccumulator accumulator = new KeyAveragingAccumulator();
        BinaryTree<Integer, String> tree = buildIntegerKeyedTree();

        tree.breadthFirstTraversal(accumulator::accumulate);
        assertEquals(expectedResult, accumulator.compute());
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

    private BinaryTree<Integer, String> buildIntegerKeyedTree()
    {
        BinaryTree<Integer, String> tree = new BinaryTree<>(String::compareTo);
        tree.insert(new Node<>(null, 1, "M", String::compareTo));
        tree.insert(new Node<>(null,2, "E", String::compareTo));
        tree.insert(new Node<>(null,3, "T", String::compareTo));
        tree.insert(new Node<>(null,4, "C", String::compareTo));
        tree.insert(new Node<>(null,5, "G", String::compareTo));
        tree.insert(new Node<>(null,6, "P", String::compareTo));
        tree.insert(new Node<>(null,7, "W", String::compareTo));

        tree.insert(new Node<>(null,8,"A", String::compareTo));
        tree.insert(new Node<>(null,9,"D", String::compareTo));
        tree.insert(new Node<>(null,10, "N", String::compareTo));
        tree.insert(new Node<>(null,11,"S", String::compareTo));
        tree.insert(new Node<>(null,12, "U", String::compareTo));
        tree.insert(new Node<>(null,13, "X", String::compareTo));


        // add last word
        return tree;
    }
   
    private BinaryTree<String, Integer> buildSimpleTree()
    {
        BinaryTree<String, Integer> tree = new BinaryTree<>(Integer::compareTo);
        tree.insert(new Node<>(null,"ROOT", 7, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL1_LEFT", 5, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL1_RIGHT", 11, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL2N1_LEFT", 3, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL2N1_RIGHT", 6, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL2N2_LEFT", 10, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL2N2_RIGHT", 13, Integer::compareTo));

        tree.insert(new Node<>(null,"LEVEL3N1_LEFT", 1, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL3N1_RIGHT", 4, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL3N2_RIGHT", 5, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL3N3_LEFT", 7, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL4N1_LEFT", 9, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL3N4_LEFT", 11, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL4N2_RIGHT", 12, Integer::compareTo));
        tree.insert(new Node<>(null,"LEVEL3N4_RIGHT", 15, Integer::compareTo));


        // add last word
        return tree;
    }

    /**
     * Collects the key values at each depth and computes the average
     */
    class KeyAveragingAccumulator
    {
        final Map<Integer, AbstractMap.SimpleEntry<Integer, Integer>> averages =
                new HashMap<>();

        public void accumulate(INode<Integer, String> node, Integer depth)
        {
            Integer count = 1;
            averages.merge(
                    depth,
                    new AbstractMap.SimpleEntry<>(node.getKey(), count),
                    (first, second) ->
                            new AbstractMap.SimpleEntry<>(first.getKey() + second.getKey(), first.getValue() + 1));
        }

        public Map<Integer, Double> compute()
        {
            Map<Integer, Double> results = new HashMap<>();
            for (Map.Entry<Integer, AbstractMap.SimpleEntry<Integer, Integer>> entry : averages.entrySet())
            {
                AbstractMap.SimpleEntry<Integer, Integer> simpleEntry = entry.getValue();
                double i = simpleEntry.getKey() / (double)simpleEntry.getValue();
                results.put(entry.getKey(), i);
            }

            return results;
        }
    }
}
