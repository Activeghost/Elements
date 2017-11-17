package EPI.Queues;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Circular tests
 */
public class CircularQueueTest {
    CircularQueue<String> queue;

    @Before
    public void before(){
        queue = new CircularQueue<>(100);
    }

    @Test
    public void getSize() throws Exception {
        for(int i = 1; i <= 200; i++) {
            CircularQueue<String> queue = getQueue(i, "Test" + i);
            Assert.assertEquals(i, queue.getSize());
        }

        // Test removing an item and checking size.
        for(int i = 1; i <= 200; i++) {
            CircularQueue<String> queue = getQueue(i, "Test" + i);
            Assert.assertEquals(i, queue.getSize());

            String sample = queue.dequeue();
            Assert.assertEquals("Test" + i, sample);
            Assert.assertEquals(i - 1, queue.getSize());
        }
    }

    @Test
    public void isEmpty() throws Exception {

    }

    @Test
    public void enqueue() throws Exception {
        for(int i = 1; i <= 1200; i++) {
            CircularQueue<String> queue = getQueue(i, "Test");
            Assert.assertEquals(i, queue.getSize());
        }

        // Test removing an item and checking size.
        for(int i = 0; i < 200; i++) {
            CircularQueue<String> queue = getQueue(i, "Test");
            Assert.assertEquals(i, queue.getSize());
        }

    }

    @Test
    public void dequeue() throws Exception {
        // Test removing an item and checking size.
        CircularQueue<String> queue = getQueue(200, "Test");
        for(int i = 0; i < 200; i++) {
            Assert.assertEquals(200 - i, queue.getSize());
            String sample = queue.dequeue();
        }
    }

    @Test
    public void enqueue_dequeue() throws Exception {
        // Test removing an item and checking size.
        CircularQueue<String> queue = getQueue(10, "Test");

        // dequeue all but one... should be {"Test"} now.
        for(int i = 0; i < 9; i++) {
            queue.dequeue();
        }

        // start the next set of FOR loops from 1 as I'm using their values in naming.
        // force a wrap by enqueueing until we get to 11 elements
        for(int i = 1; i <= 9; i++) {
            queue.enqueue("Test" + i);
            Assert.assertEquals(1 + i, queue.getSize());
        }

        // force a reallocation
        for(int i = 1; i < 10; i++) {
            queue.enqueue("TEST");
            Assert.assertEquals(10 + i, queue.getSize());
        }

        // check the first value (should be from the first enqueue's)
        Assert.assertEquals("Test", queue.dequeue());

        // dequeue and check the second set of queued values
        for(int i = 1; i < 10; i++) {
            Assert.assertEquals("Test" + i, queue.dequeue());
        }

        // now dequeue and check the last set.
        for(int i = 1; i < 10; i++) {
            Assert.assertEquals("TEST", queue.dequeue());
        }
    }

    private <TValueType>  CircularQueue<TValueType> getQueue(int entries, TValueType sampleValue)
    {
        CircularQueue<TValueType> queue = new CircularQueue<TValueType>(entries);
        for(int i = 0; i < entries; i++)
        {
            queue.enqueue(sampleValue);
        }

        return queue;
    }
}
