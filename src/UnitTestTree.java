//Obtained from Kristian Rados DSA Practical 4 Work

public class UnitTestTree
{
    public static void main(String[] args)
    {
        DSABinarySearchTree tree = new DSABinarySearchTree();

        //empty tree
        System.out.println("EMPTY TREE");
        tree.display();
        System.out.println();

        //creating tree
        tree.insert("D", "1");
        tree.insert("B", 2);
        tree.insert("F", 3);
        tree.insert("A", 4);
        tree.insert("C", 5);

        //find
        System.out.println("FIND");
        System.out.println("5 = " + tree.find("C"));
        System.out.println("3 = " + tree.find("F") + "\n");

        //min/max
        System.out.println("MIN / MAX");
        System.out.println("A = " + tree.min());
        System.out.println("F = " + tree.max() + "\n");

        //height
        System.out.println("HEIGHT");
        System.out.println("2 = " + tree.height() + "\n");

        //traversal
        tree.display();

        //delete
        System.out.println("\nDELETE\nNo children (deleting A):");
        tree.delete("A");
        tree.display();
        System.out.println("\nOne child (deleting B):");
        tree.delete("B");
        tree.display();
        System.out.println("\nTwo children (deleting D):");
        tree.delete("D");
        tree.display();
    }
}
