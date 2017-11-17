package EPI.Queues;

import java.lang.reflect.Array;
import java.util.*;

/**
 * A circular queue class
 */
public class CircularQueue<TValueType> implements ICircularQueue<TValueType>
{
    private int head_;
    private int end_;
    private int size_;
    private TValueType[] queue_;
    private final int SCALE_FACTOR;

    CircularQueue(int initialSize)
    {
        queue_ = (TValueType[]) new Object[initialSize];
        head_ =0;
        end_ = 0;
        SCALE_FACTOR = 2;
    }

    @Override
    public int getSize()
    {
        return size_;
    }

    @Override
    public boolean isEmpty() {
        return size_ == 0;
    }

    @Override
    public void enqueue(TValueType value) {
        if(getSize() == queue_.length)
        {
            resizeQueue();
        }

        queue_[end_] = value;

        // if we've reached the end of the array, this will wrap the tail to 0
        end_ = (end_ + 1) % queue_.length;
        ++size_;
    }

    private void resizeQueue() {
        // resize in chunks, using the size as our chunk size...
        // if this becomes a problem, track the size over time using a poisson distribution
        // and predict the needed chunk size.
        Collections.rotate(Arrays.asList(queue_), -head_);
        int length = queue_.length;
        queue_ = Arrays.copyOf(queue_, SCALE_FACTOR * length);

        head_ = 0;
        end_ = length;
    }

    @Override
    public TValueType dequeue() {
        if(isEmpty())
        {
            throw new ArrayIndexOutOfBoundsException("Queue is empty");
        }

        // increment the head.
        TValueType value = queue_[head_];

        // if we've reached the end of the array, this will wrap the head to 0
        head_ = (head_ + 1) % queue_.length;
        --size_;

        return value;
    }
}
