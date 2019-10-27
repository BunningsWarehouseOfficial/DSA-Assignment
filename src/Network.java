public class Network extends DSAGraphT
{
    private double probLike;
    private double probFollow;
    private DSAQueue events;
    private DSALinkedList posts; //All posts
    private int nPosts;
    private int nPostsStale;

    //CONSTRUCTOR
    public Network()
    {
        super();
        probLike = 0.0;
        probFollow = 0.0;
        events = new DSAQueue();
        posts = new DSALinkedList();
        nPosts = 0;
    }

    //ACCESSORS
    public double getProbLike() { return probLike; }
    public double getProbFollow() { return probFollow; }
    public int getNPosts() { return nPosts; }
    public int getNPostsStale() { return nPostsStale; }
    public DSALinkedList getPosts() { return posts; }
    public int getEventsCount() { return events.getCount(); }
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
    public char peekQueueTag()
    {
        char tag = '0';
        if (!events.isEmpty())
        {
            tag = ((Event)events.peek()).getTag();
        }
        return tag;
    }
    public DSAQueue findSharers()
    {
        String name;
        Person person;
        DSALinkedList labels;
        DSAQueue sharers, justShared;
        sharers = new DSAQueue();

        labels = getLabels(); //Retrieving all names in network
        for (Object o : labels)
        {
            name = (String)o;
            person = (Person)getVertexValue(name);
            justShared = person.getJustShared();
            if (!justShared.isEmpty()) //Checking they've just 'shared' a post
            { //If someone is about to share > 1 posts, add them multiple times
                for (int ii = 0; ii < justShared.getCount(); ii++)
                {
                    sharers.enqueue(person);
                }
            }
        }

        return sharers;
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
    public void addEvent(Object eventData, char tag)
    { //Adding a standard event
        if (eventData != null)
        {
            Event newEvent = new Event(eventData, tag);
            events.enqueue(newEvent);
        }
        else
        {
            throw new IllegalArgumentException("Error: Event is null");
        }
    }
    public void addEvent(Object eventData1, Object eventData2, char tag)
    { //Adding an edge event; timestep must remove two events from the queue
        if (eventData1 != null && eventData2 != null)
        {
            Event newEvent1, newEvent2;
            newEvent1 = new Event(eventData1, tag);
            newEvent2 = new Event(eventData2, tag);
            events.enqueue(newEvent1);
            events.enqueue(newEvent2);
        }
        else
        {
            throw new IllegalArgumentException("Error: An event is null");
        }
    }
    public Object dequeueEvent()
    {
        Event event = (Event)events.dequeue();
        return event.getData();
    }
    public void addPost(Post inPost)
    {
        posts.insertLast(inPost);
        nPosts++;
    }
    public void addPostStale()
    {
        nPostsStale++;
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