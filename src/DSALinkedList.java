import java.util.*;
import java.io.*;

public class DSALinkedList implements Iterable, Serializable
{
    private DSAListNode head;
    private DSAListNode tail;

    private class DSAListNode implements Serializable
    {
        private Object value;
        private DSAListNode next;
        private DSAListNode prev;

        public DSAListNode(Object inValue)
        {
            value = inValue;
            next = null;
            prev = null;
        }

        //accessors
        public Object getValue()
        {
            return value;
        }

        public DSAListNode getNext()
        {
            return next;
        }

        public DSAListNode getPrev()
        {
            return prev;
        }

        //mutators
        public void setValue(Object inValue)
        {
            value = inValue;
        }

        public void setNext(DSAListNode newNext)
        {
            next = newNext;
        }

        public void setPrev(DSAListNode newPrev)
        {
            prev = newPrev;
        }
    }

    //iterator implementation
    private class DSALinkedListIterator implements Iterator
    {
        private DSAListNode iterNext;

        public DSALinkedListIterator(DSALinkedList theList)
        {
            iterNext = theList.head;
        }

        public boolean hasNext() { return (iterNext != null); }

        public Object next()
        {
            Object value;
            if (iterNext == null)
            {
                value = null;
            }
            else
            {
                value = iterNext.getValue();
                iterNext = iterNext.getNext();
            }
            return value;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException("Error: Not supported");
        }
    }
    public Iterator iterator()
    {
        return new DSALinkedListIterator(this);
    }

    // ================================================================== //

    public DSALinkedList()
    {
        head = null;
        tail = null;
    }

    //accessors
    public boolean isEmpty()
    {
        boolean empty;
        empty = (head == null);
        return empty;
    }

    public Object peekFirst()
    {
        Object nodeValue;
        if (isEmpty())
        {
            throw new IllegalArgumentException("Error: The list is empty");
        }
        else
        {
            nodeValue = head.getValue();
        }
        return nodeValue;
    }

    public Object peekLast()
    {
        DSAListNode currNd;
        Object nodeValue;
        if (isEmpty())
        {
            throw new IllegalArgumentException("Error: The list is empty");
        }
        else
        {
            nodeValue = tail.getValue();
        }
        return nodeValue;
    }

    //mutators
    public void insertFirst(Object newValue)
    {
        DSAListNode newNd = new DSAListNode(newValue);
        if (isEmpty())
        {
            head = newNd;
            tail = newNd;
        }
        else
        {
            head.setPrev(newNd);
            newNd.setNext(head);
            head = newNd;
        }
    }

    public void insertLast(Object newValue)
    {
        DSAListNode currNd, newNd;
        newNd = new DSAListNode(newValue);
        if (isEmpty())
        {
            head = newNd;
            tail = newNd;
        }
        else
        {
            tail.setNext(newNd);
            newNd.setPrev(tail);
            tail = newNd;
        }
    }

    public Object removeFirst()
    {
        Object nodeValue;
        if (isEmpty())
        {
            throw new IllegalArgumentException("Error: The list is empty");
        }
        else if (head.getNext() == null)
        {
            nodeValue = head.getValue();
            head = null;
            tail = null;
        }
        else
        {
            nodeValue = head.getValue();
            head = head.getNext();
            head.setPrev(null);
        }
        return nodeValue;
    }

    public Object removeLast()
    {
        DSAListNode prevNd;
        Object nodeValue;
        if (isEmpty())
        {
            throw new IllegalArgumentException("Error: The list is empty");
        }
        else if (head.getNext() == null)
        {
            nodeValue = head.getValue();
            head = null;
        }
        else
        {
            prevNd = tail.getPrev();
            nodeValue = tail.getValue();
            prevNd.setNext(null);
            tail = prevNd;
        }
        return nodeValue;
    }
}
