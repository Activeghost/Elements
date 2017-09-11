package EPI;

import org.junit.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * BTee test class
 */
public class BinaryTreeTest {

    private static int sum = 0;

    @Test
    public void insert() throws Exception {
        BinaryTree tree = buildTree("sodales neque sodales ut etiam sit amet nisl purus in mollis nunc sed id semper risus in hendrerit gravida rutrum");
        assertEquals(18, tree.size());
    }

    @Test
    public void inOrderTraversal() throws Exception {
        StringBuilder s = new StringBuilder();
        String paragraph = "sodales neque sodales ut etiam sit amet nisl purus in mollis nunc sed id semper risus in hendrerit gravida rutrum";
        String expected = "amet etiam gravida hendrerit id mollis neque nisl nunc purus risus rutrum sed semper sit ut in sodales ";
        BinaryTree<String, Integer> tree = buildTree(paragraph);

        assertEquals(18, tree.size());

        tree.inOrderTraversal(n -> s.append(n.key).append(" "));
        tree.inOrderTraversal(n -> sum += n.value);
        assertEquals(expected, s.toString());
        assertEquals(20, sum);
    }

    private BinaryTree<String, Integer> buildTree(String paragraph)
    {
        BinaryTree<String, Integer> tree = new BinaryTree<>(Integer::compare);
        Map<String, Integer> dictionary = new TreeMap<>();
        Strings.getWords(paragraph, dictionary);

        for(Map.Entry<String, Integer> entry : dictionary.entrySet())
        {
            tree.insert(entry.getKey(), entry.getValue());
        }

        // add last word
        return tree;
    }
}
