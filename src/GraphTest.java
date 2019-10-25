import java.util.NoSuchElementException;

public class GraphTest
{
    public static void main(String[] args)
    {
        DSAGraphT g = new DSAGraphT();

        //Adding Vertices
        g.addVertex("A", 1);
        g.addVertex("B", 2);
        System.out.println("(Has vertex) true = " + g.hasVertex("A"));
        g.addVertex("E", 12);
        g.addVertex("Z", 74);
        System.out.println("(Has vertex) false = " + g.hasVertex("X"));
        try
        {
            g.addVertex("A", 42);
            System.out.println("Already exists = FAIL");
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Already exists = PASS");
        }

        //Adding Edges
        g.addEdge("A", "E");
        g.addEdge("A", "B");
        g.addEdge("Z", "A");
        g.addEdge("Z", "B");
        System.out.println("(Is adjacent) true = " + g.isAdjacent("A", "Z"));
        System.out.println("(Is adjacent) false = " + g.isAdjacent("Z", "B"));
        g.displayAsList();

        //Removing Vertices/Edges
        g.addEdge("A", "Z");
        g.removeEdge("Z", "A");
        g.removeVertex("B");
        try
        {
            g.removeVertex("F");
            System.out.println("\nRemove vertex not in graph = FAIL");
        }
        catch (NoSuchElementException e)
        {
            System.out.println("\nRemove vertex not in graph = PASS");
        }
        System.out.println("Adding edge Z to A, removing edge A from Z and " +
                           "removing vertex B");
        g.displayAsList();
    }
}
