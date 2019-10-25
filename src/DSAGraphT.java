import java.util.NoSuchElementException;

//TODO self-cite here (note that it is directed and simple (ie. not multigraph))

//TODO getDegree if needed
//TODO getHighestDegree if needed
public class DSAGraphT
{
    private class DSAGraphVertex
    {
        private String label;
        private Object value;
        private DSABinarySearchTree links;
        private boolean visited;

        //CONSTRUCTOR
        public DSAGraphVertex(String inLabel, Object inValue)
        {
            links = new DSABinarySearchTree();
            label = inLabel;
            value = inValue;
        }

        //ACCESSORS
        public String getLabel() { return label; }
        public Object getValue() { return value; }
        public DSABinarySearchTree getAdjacent() { return links; }
        public boolean getVisited() { return visited; }
        public boolean hasEdge(String label)
        {
            boolean hasEdge;
            try
            {
                links.find(label);
                hasEdge = true;
            }
            catch (NoSuchElementException e)
            {
                hasEdge = false;
            }
            return hasEdge;
        }

        public String toString()
        {
            String s, output;
            output = "";

            //Iterate through list of edge vertices
            for (Object o : links)
            {
                s = ((DSAGraphVertex)o).getLabel();
                output += " " + s;
            }
            return output;
        }

        //MUTATORS
        public void addEdge(DSAGraphVertex vertex)
        {
            links.insert(vertex.getLabel(), vertex);
        }
        public void removeEdge(String label)
        {
            links.delete(label);
        }
        public void setVisited()
        {
            visited = true;
        }
        public void clearVisited()
        {
            visited = false;
        }
    }

    // ================================================================= //

    private DSABinarySearchTree vertices;
    private int vertexCount;
    private int edgeCount;

    //CONSTRUCTOR
    public DSAGraphT()
    {
        vertices = new DSABinarySearchTree();
        vertexCount = 0;
        edgeCount = 0;
    }

    //MUTATORS
    public void addVertex(String label, Object value)
    {
        if (!hasVertex(label)) //Checking if label exists already
        {
            DSAGraphVertex vertex = new DSAGraphVertex(label, value);
            vertices.insert(label, vertex);
            vertexCount++;
        }
        else
        {
            throw new IllegalArgumentException("Error: Label already exists");
        }
    }
    public void addEdge(String source, String sink)
    { //Adding a directed edge
        if (!source.equals(sink) && hasVertex(source) && hasVertex(sink))
        {
            DSAGraphVertex src, sk;
            src = getVertex(source);
            sk = getVertex(sink);

            src.addEdge(sk);
            edgeCount++;
        }
        else if (source.equals(sink))
        {
            throw new IllegalArgumentException("Error: Can not add an edge " +
                                               "between the same vertex");
        }
        else
        {
            throw new IllegalArgumentException("Error: One or both of the " +
                                               "vertices do not exist");
        }
    }
    public void removeVertex(String label)
    { //Remove vertex AND all occurrences of vertex in any edges
        DSAGraphVertex v, findValue;
        DSABinarySearchTree links;

        vertices.delete(label);
        for (Object o : vertices)
        {
            v = (DSAGraphVertex)o;
            //links = v.getAdjacent();
            //Checking if vertex to be removed is in v's links/edges tree
              //hasEdge = v.hasEdge(label);
            //findValue = (DSAGraphVertex)links.find(label);
            if (v.hasEdge(label))
            {
                v.removeEdge(label);
            }
        }
        vertexCount--;
    }
    public void removeEdge(String source, String sink)
    { //Removing a directed edge
        if (!source.equals(sink) && hasVertex(source) && hasVertex(sink))
        {
            DSAGraphVertex src, sk;
            src = getVertex(source);
            sk = getVertex(sink);

            src.removeEdge(sink);
            edgeCount--;
        }
        else if (source.equals(sink))
        {
            throw new IllegalArgumentException("Error: Can not add an edge " +
                                               "between the same vertex");
        }
        else
        {
            throw new IllegalArgumentException("Error: One or both of the " +
                                               "vertices do not exist");
        }
    }

    //ACCESSORS
    public int getVertexCount() { return vertexCount; }
    public int getEdgeCount() { return edgeCount; }
    
    public boolean hasVertex(String label)
    {
        boolean hasVertex;
        try
        {
            vertices.find(label);
            hasVertex = true;
        }
        catch (NoSuchElementException e)
        {
            hasVertex = false;
        }
        return hasVertex;
    }
    public DSAGraphVertex getVertex(String label)
    {
        DSAGraphVertex retVertex;
        retVertex = null;        

        try
        {
            retVertex = (DSAGraphVertex)vertices.find(label);
        }
        catch (NoSuchElementException e)
        {
            throw new IllegalArgumentException("Error: Could not find vertex");
        }
        return retVertex;
    }
    public DSABinarySearchTree getAdjacent(String label)
    {
        return getVertex(label).getAdjacent();
    }
    public boolean isAdjacent(String label1, String label2)
    {
        boolean isAdjacent = false;
        DSABinarySearchTree links;
        DSAGraphVertex v;

        //Graph is directional, thus need to check both lists for opposing label
        links = getAdjacent(label1); //Retrieve the links from label 1 vertex
        for (Object o : links)
        {
            v = (DSAGraphVertex)o;
            if (v.getLabel().equals(label2))
            {
                isAdjacent = true;
            }
        }
        links = getAdjacent(label2); //Retrive the links from label 2 vertex
        for (Object o : links)
        {
            v = (DSAGraphVertex)o;
            if (v.getLabel().equals(label1))
            {
                isAdjacent = true;
            }
        }
        return isAdjacent;
    }

    //DISPLAY
    public void displayAsList()
    {
        String s;
        DSAGraphVertex v;

        for (Object o : vertices)
        {
            v = (DSAGraphVertex)o;
            System.out.print(v.getLabel() + ":"); //Print label
            System.out.println(v.toString()); //Print list of edges
        }
    }
}
