import java.util.NoSuchElementException;

//TODO self-cite here
//TODO make the graph directional

//TODO Remove vertex (removes all edges) if needed
//TODO Remove edge (called by remove vertex) if needed
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

        public String toString()
        {
            DSAGraphVertex v;
            String s = ""; //TODO fix for BST

            //Iterate through vertices list
            for (Object o : links)
            {
                v = (DSAGraphVertex)o;
                s += v.getLabel() + " ";
            }
            return "";
        }

        //MUTATORS
        public void addEdge(DSAGraphVertex vertex)
        { //Adding vertex to list of links
            links.insert(vertex.getLabel(), vertex.getValue());
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
            vertices.insert(label, value);
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
            src = (DSAGraphVertex)vertices.find(source);
            sk = (DSAGraphVertex)vertices.find(sink);

            src.addEdge(sk);

//            DSAGraphVertex i, v1, v2;
//            v1 = getVertex(label1); //TODO remove if unnecessary
//            v2 = getVertex(label2);
//
//            //Iterate through vertices list
//            for (Object o : vertices)
//            {
//                i = (DSAGraphVertex)o;
//
//                //Check for label 1
//                if (i.getLabel().equals(label1))
//                {
//                    i.addEdge(v2); //Add opposing label as a link
//                }
//                //Check for label 2
//                else if (i.getLabel().equals(label2))
//                {
//                    i.addEdge(v1); //Add opposing label as a link
//                }
//            }
            edgeCount++;
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
//        DSAGraphVertex v;
        try
        {
            vertices.find(label);
            hasVertex = true;
        }
        catch (NoSuchElementException e)
        {
            hasVertex = false;
        }
        //Iterate through vertices list
//        for (Object o : vertices) //TODO remove if unnecessary
//        {
//            v = (DSAGraphVertex)o;
//            if (v.getLabel().equals(label)) //Check for label
//            {
//                hasVertex = true;
//            }
//        }
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

        //Iterate through vertices list
//        for (Object o : vertices) //TODO remove if unnecessary
//        {
//            v = (DSAGraphVertex)o;
//            if (v.getLabel().equals(label)) //Check for label
//            {
//                retVertex = v;
//            }
//        }
//
//        if (retVertex == null)
//        {
//            throw new IllegalArgumentException("Error: Could not find vertex");
//        }
        return retVertex;
    }
    /*public DSABinarySearchTree getAdjacent(String label)
    {
        // //
    }*/
    public boolean isAdjacent(String label1, String label2)
    {
        // //
        return true;
    }

    //DISPLAY
    public void displayAsList()
    {
        // //
    }
    public void displayAsMatrix()
    {
        // //
    }
}
