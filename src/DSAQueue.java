//Obtained from Kristian Rados DSA Practical 3 Work

import java.util.*;
public class DSAQueue
{
    private DSALinkedList queue;
    private int start;
    private int end;
    private int count;

    //default
    public DSAQueue()
    {
        queue = new DSALinkedList();
        start = 0;
        end = 0;
        count = 0;
    }

    //accessors
    public int getCount() { return count; }
    public DSALinkedList getQueue() { return queue; }
    public boolean isEmpty() { return (count == 0); }
    public Iterator iterator() { return queue.iterator(); }

    public Object peek()
    {
        Object next;
        if (isEmpty())
        {
            throw new IllegalArgumentException("Error: The queue is empty");
        }
        else
        {
            next = queue.peekFirst();
        }
        return next;
    }

    //mutators
    public void setCount(int inCount)
    {
        count = inCount;
    }

    public void enqueue(Object value)
    {
        queue.insertLast(value);
        count++;
    }

    public Object dequeue()
    {
        Object next;
        next = queue.peekFirst();
        queue.removeFirst();
        count--;
        return next;
    }

}