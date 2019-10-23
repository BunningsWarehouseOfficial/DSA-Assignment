import java.util.*;
public class GraphTest
{
    public static void main(String[] args)
    {
        DSAGraph g = new DSAGraph();

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
        g.addEdge("A","B");
        //g.getAdjacent
    }
}
