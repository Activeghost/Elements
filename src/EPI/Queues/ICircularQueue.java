package EPI.Queues;

/**
 * Interface for a circular queue structure
 */
public interface ICircularQueue<TValueType>{

    /**
     * Gets the size
     * @return the size
     */
    int getSize();

    /**
     * Returns whether this queue is empty or not
     * @return true if empty, false otherwise
     */
    boolean isEmpty();

    /**
     * enqueue a  value
     * @param value
     */
    void enqueue(TValueType value);

    /**
     * dequeue a value
     * @return TValueType
     */
    TValueType dequeue();
}
