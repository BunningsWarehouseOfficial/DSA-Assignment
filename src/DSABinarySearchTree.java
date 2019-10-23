import java.util.*;
import java.io.*;

//TODO Does it need serializable?
public class DSABinarySearchTree implements Serializable, Iterable
{
    private DSATreeNode root;

    private class DSATreeNode implements Serializable
    {
        private String key;
        private Object value;
        private DSATreeNode leftChild;
        private DSATreeNode rightChild;

        public DSATreeNode(String inKey, Object inVal)
        {
            if (inKey == null)
            {
                throw new IllegalArgumentException("Error: Key can not be " +
                                                   "null");
            }
            key = inKey;
            value = inVal.toString();
            rightChild = null;
            leftChild = null;
        }

        //accessors
        public String getKey() { return key; }
        public Object getValue() { return value; }
        public DSATreeNode getLeft() { return leftChild; }
        public DSATreeNode getRight() { return rightChild; }

        //mutators
        public void setLeft(DSATreeNode newLeft)
        {
            leftChild = newLeft;
        }
        public void setRight(DSATreeNode newRight)
        {
            rightChild = newRight;
        }
    }

    private class DSABinarySearchTreeIterator implements Iterator
    {
        private DSATreeNode iterNext;
        private DSAQueue inOrder;

        public DSABinarySearchTreeIterator(DSABinarySearchTree theTree)
        {
            inOrder = export();
            iterNext = (DSATreeNode)inOrder.dequeue();
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
                iterNext = (DSATreeNode)inOrder.dequeue();
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
        return new DSABinarySearchTreeIterator(this);
    }

    // ================================================================== //

    public DSABinarySearchTree()
    {
        root = null;
    }

    //wrapper methods
    public Object find(String key)
    {
        Object value = null;
//        try //TODO remove if not needed (probably won't be)
//        {
            value = findRec(key, root);
//        }
//        catch (NoSuchElementException e)
//        {
//            System.out.println(e.getMessage());
//        }
        return value;
    }
    public void insert(String key, Object value)
    {
        root = insertRec(key, value, root);
    }

    public DSAQueue display()
    {
        Scanner sc = new Scanner(System.in);
        DSAQueue queue = new DSAQueue();
        int input;

        if (root == null)
        {
            System.out.println("The tree is empty");
        }
        else
        { 
            System.out.println("What traversal method would you like to use?" +
                               "\n[1] Pre-Order\n[2] In-Order\n" +
                               "[3] Post-Order");
            input = sc.nextInt();
            switch (input)
            {
                case 1:
                    DSAQueue prefix = new DSAQueue();
                    prefix = preOrderRec(root, prefix);
                    queue = prefix; //copying queue
                    printTree(prefix);
                break;
                case 2:
                    DSAQueue infix = new DSAQueue();
                    infix = inOrderRec(root, infix);
                    queue = infix; //copying queue
                    printTree(infix);
                break;
                case 3:
                    DSAQueue postfix = new DSAQueue();
                    postfix = postOrderRec(root, postfix);
                    queue = postfix; //copying queue
                    printTree(postfix);
                break;
            }
        }
        return queue; //returning copied queue for saving tree
    }
    
    public void delete(String key)
    {
        try
        {
            root = deleteRec(key, root);
        }
        catch (NoSuchElementException e)
        {
            System.out.println(e.getMessage());
        } 
    }

    public DSAQueue export()
    { //Returning the in-order traversal of the tree as a queue
        DSAQueue infix = new DSAQueue();
        infix = inOrderRec(root, infix);
        return infix;
    }

    public int height()
    {
        return heightRec(root);
    }

    public String max()
    {
        return maxRec(root);
    }

    public String min()
    {
        return minRec(root);
    }


    //private methods
    private DSATreeNode deleteRec(String key, DSATreeNode currNode)
    {
        DSATreeNode updateNode;
        updateNode = currNode;
        
        if (currNode == null)
        {
            throw new NoSuchElementException("Error: Key " + key + " not " +
                                             "found");
        }
        else if (key.equals(currNode.getKey())) // ==
        {
            updateNode = deleteNode(currNode);
        }
        else if (key.compareTo(currNode.getKey()) < 0) // < 0
        {
            currNode.setLeft(deleteRec(key, currNode.getLeft()));
        }
        else // > 0
        {
            currNode.setRight(deleteRec(key, currNode.getRight()));
        }
        return updateNode;
    }
 
    private DSATreeNode deleteNode(DSATreeNode delNode)
    {
        DSATreeNode updateNode;

        if (delNode.getLeft() == null && delNode.getRight() == null)
        {
            updateNode = null; //no children
        }
        else if (delNode.getLeft() != null && delNode.getRight() == null)
        {
            updateNode = delNode.getLeft(); //one child: left
        }
        else if (delNode.getLeft() == null && delNode.getRight() != null)
        {
            updateNode = delNode.getRight(); //one child: right
        }
        else //two children
        {
            updateNode = promoteSuccessor(delNode.getRight());
            if (updateNode != delNode.getRight()) //successor isn't only child
            {
                updateNode.setRight(delNode.getRight()); //update right
            }
            updateNode.setLeft(delNode.getLeft()); //update left
        }
        return updateNode;
    }

    private DSATreeNode promoteSuccessor(DSATreeNode currNode)
    { //successor will be left most child of right subtree
        DSATreeNode successor;
        successor = currNode;

        if (currNode.getLeft() != null)
        {
            successor = promoteSuccessor(currNode.getLeft());
            if (successor == currNode.getLeft()) //parent of successor
            {
                currNode.setLeft(successor.getRight()); //adopt right child
            }
        }
        return successor;
    }

    private int heightRec(DSATreeNode currNode)
    {
        int htSoFar, iLeftHt, iRightHt;
        if (currNode == null)
        {
            htSoFar = -1; //base case
        }
        else
        {
            iLeftHt = heightRec(currNode.getLeft()); //left
            iRightHt = heightRec(currNode.getRight()); //right
            
            if (iLeftHt > iRightHt)
            {
                htSoFar = iLeftHt + 1;
            }
            else
            {
                htSoFar = iRightHt + 1;
            }
        }
        return htSoFar;
    }

    private void printTree(DSAQueue queue)
    { //TODO Is this needed?
        boolean empty = false;

        System.out.println("\nKEY | VALUE\n----+------");
        try
        {
            while (true)
            {
                System.out.println(queue.dequeue() + "   | " +
                                   queue.dequeue());
            }
        }
        catch (IllegalArgumentException e) {}
    }

    //prefix
    private DSAQueue preOrderRec(DSATreeNode currNode, DSAQueue prefix)
    { 
        if (currNode != null)
        {
            prefix.enqueue(currNode.getKey());
            prefix.enqueue(currNode.getValue());
            prefix = preOrderRec(currNode.getLeft(), prefix); //left
            prefix = preOrderRec(currNode.getRight(), prefix); //right
        }
        return prefix;
    }

    //infix
    private DSAQueue inOrderRec(DSATreeNode currNode, DSAQueue infix)
    { 
        if (currNode != null)
        {
            infix = inOrderRec(currNode.getLeft(), infix); //left
            infix.enqueue(currNode.getKey());
            infix.enqueue(currNode.getValue());
            infix = inOrderRec(currNode.getRight(), infix); //right
        }
        return infix;
    }

    //postfix
    private DSAQueue postOrderRec(DSATreeNode currNode, DSAQueue postfix)
    { 
        if (currNode != null)
        {
            postfix = postOrderRec(currNode.getLeft(), postfix); //left
            postfix = postOrderRec(currNode.getRight(), postfix); //right
            postfix.enqueue(currNode.getKey());
            postfix.enqueue(currNode.getValue());
        }
        return postfix;
    }

    private Object findRec(String key, DSATreeNode currNode)
    {
        Object value = null;

        if (currNode == null)
        {
            throw new NoSuchElementException("Error: Key " + key + " not " +
                                             "found");
        }
        else if (key.equals(currNode.getKey())) // ==
        {
            value = currNode.getValue();
        }
        else if (key.compareTo(currNode.getKey()) < 0) // < 0
        {
            value = findRec(key, currNode.getLeft());
        }
        else // > 0
        {
            value = findRec(key, currNode.getRight());
        }
        return value;
    }

    private DSATreeNode insertRec(String key, Object value,
                                  DSATreeNode currNode)
    {
        DSATreeNode updateNode;
        updateNode = currNode;
        
        if (currNode == null)
        {
            DSATreeNode newNode = new DSATreeNode(key, value);
            updateNode = newNode;
        }
        else if (key.equals(currNode.getKey())) // ==
        {
            throw new IllegalArgumentException("Error: Key " + key +
                                               " already exists");
        }
        else if (key.compareTo(currNode.getKey()) < 0) // < 0
        {
            currNode.setLeft(insertRec(key, value, currNode.getLeft()));
        }
        else // > 0
        {
            currNode.setRight(insertRec(key, value, currNode.getRight()));
        }
        return updateNode;
    }

    private String maxRec(DSATreeNode currNode)
    {
        String maxKey;
        if (currNode.getRight() != null)
        {
            maxKey = maxRec(currNode.getRight());
        }
        else
        {
            maxKey = currNode.getKey();
        }
        return maxKey;
    }
    
    private String minRec(DSATreeNode currNode)
    {
        String minKey;
        if (currNode.getLeft() != null)
        {
            minKey = minRec(currNode.getLeft());
        }
        else
        {
            minKey = currNode.getKey();
        }
        return minKey;
    }
}
