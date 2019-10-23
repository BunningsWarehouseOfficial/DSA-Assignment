/* Name:               Kristian Rados (19764285)
 * Date Created:       23/09/2019
 * Date Last Modified: 12/10/2019                                            */

import java.util.*;
public class DSAGraph
{
    private class DSAGraphVertex
    {
        private String label;
        private Object value;
        private DSALinkedList links;
        private boolean visited;

        //CONSTRUCTOR
        public DSAGraphVertex(String inLabel, Object inValue)
        {
            links = new DSALinkedList();
            label = inLabel;
            value = inValue;
        }

        //ACCESSORS
        public String getLabel() { return label; }
        public Object getValue() { return value; }
        public DSALinkedList getAdjacent() { return links; }
        public boolean getVisited() { return visited; }
        
        public String toString()
        {
            DSAGraphVertex v;
            String s = "";

            //Iterate through vertices list
            for (Object o : links)
            {
                v = (DSAGraphVertex)o;
                s += v.getLabel() + ",";
            }
            return "";
        }

        //MUTATORS
        public void addEdge(DSAGraphVertex vertex)
        {
            links.insertLast(vertex); //Adding vertex to list of links
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

    private DSALinkedList vertices;
    private int vertexCount;
    private int edgeCount;

    //CONSTRUCTOR
    public DSAGraph()
    {
        vertices = new DSALinkedList();
        vertexCount = 0;
        edgeCount = 0;
    }

    //MUTATORS
    public void addVertex(String label, Object value)
    {
        if (hasVertex(label) == false) //Checking if label exists already
        {
            DSAGraphVertex newVertex = new DSAGraphVertex(label, value);
            vertices.insertLast(newVertex);
            vertexCount++;
        }
        else
        {
            throw new IllegalArgumentException("Error: Label already exists");
        }
    }
    public void addEdge(String label1, String label2)
    {
        if (hasVertex(label1) == true && hasVertex(label2) == true)
        {
            DSAGraphVertex i, v1, v2;
            v1 = getVertex(label1);
            v2 = getVertex(label2);

            //Iterate through vertices list
            for (Object o : vertices)
            {
                i = (DSAGraphVertex)o;
                
                //Check for label 1
                if (i.getLabel().equals(label1)) 
                {
                    i.addEdge(v2); //Add opposing label as a link
                }
                //Check for label 2
                else if (i.getLabel().equals(label2)) 
                { 
                    i.addEdge(v1); //Add opposing label as a link
                }
            }
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
        boolean hasVertex = false;
        DSAGraphVertex v;
        
        //Iterate through vertices list
        for (Object o : vertices)
        {
            v = (DSAGraphVertex)o;
            if (v.getLabel().equals(label)) //Check for label
            {
                hasVertex = true;
            }
        }
        return hasVertex;
    }
    public DSAGraphVertex getVertex(String label)
    {
        DSAGraphVertex v, retVertex;
        retVertex = null;        

        //Iterate through vertices list
        for (Object o : vertices)
        {
            v = (DSAGraphVertex)o;
            if (v.getLabel().equals(label)) //Check for label
            {
                retVertex = v;
            }
        }

        if (retVertex == null)
        {
            throw new IllegalArgumentException("Error: Could not find vertex");
        }
        return retVertex;
    }
    /*public DSALinkedList getAdjacent(String label)
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
