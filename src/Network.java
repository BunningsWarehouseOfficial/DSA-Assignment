public class Network extends DSAGraphT
{
    private double probLike;
    private double probFollow;
    private DSAQueue events;

    //CONSTRUCTOR
    public Network()
    {
        super();
        probLike = 0.0;
        probFollow = 0.0;
        events = new DSAQueue();
    }

    //ACCESSORS
    public double getProbLike() { return probLike; }
    public double getProbFollow() { return probFollow; }
    public void findNode(String name)
    {
        Person node, p;
        node = (Person)getVertexValue(name);
        if (node != null)
        {
            DSABinarySearchTree links, vertices;
            DSALinkedList listOfLinkTrees;
            links = getAdjacent(name);
            vertices = super.getVertices();

            node.updateFollowers(links); //In case a follower was removed
            System.out.println(node.toString());
        }
    }

    //MUTATORS
    public void setProbLike(double newProb)
    {
        if (newProb >= 0 && newProb <= 1)
        {
            probLike = newProb;
        }
        else
        {
            throw new IllegalArgumentException("Error: Invalid probability");
        }
    }
    public void setProbFollow(double newProb)
    {
        if (newProb >= 0 && newProb <= 1)
        {
            probFollow = newProb;
        }
        else
        {
            throw new IllegalArgumentException("Error: Invalid probability");
        }
    }
    public void addEdge(String source, String sink)
    {
        Person followee, follower;
        super.addEdge(source, sink);
        followee = (Person)getVertexValue(source);
        follower = (Person)getVertexValue(sink);

        followee.setNFollowers(followee.getNFollowers() + 1);
        follower.setNFollowing(follower.getNFollowing() + 1);
    }
    /*public void removeEdge(String source, String sink)
    {

    }*/
    public void removeVertex(String label)
    {
        int count;
        Person p;
        DSAQueue adjacentValues;
        adjacentValues = getAdjacentValues(label);
        super.removeVertex(label);

     /* Decreasing the following count of any person who followed the now
        removed person */
        count = adjacentValues.getCount();
        for (int ii = 0; ii < count; ii++)
        {
            p = (Person)adjacentValues.dequeue();
            p.setNFollowing(p.getNFollowing() - 1);
        }
    }
}