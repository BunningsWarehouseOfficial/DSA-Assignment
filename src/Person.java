public class Person
{
    private String name;
    private int nFollowers;
    private int nFollowing;
    private int nPosts;
    private int nLikes;
    private Post justShared; //For posts that have just been posted or liked

    //CONSTRUCTOR
    public Person(String newName)
    {
        if (newName != null)
        {
            name = newName;
            nFollowers = 0;
            nFollowing = 0;
            nPosts = 0;
            nLikes = 0;
            justShared = null;
        }
        else
        {
            throw new IllegalArgumentException("Error: Name can't be empty");
        }
    }

    //ACCESSORS
    public String getName() { return name; }
    public int getNFollowers() { return nFollowers; }
    public int getNFollowing() { return nFollowing; }
    public int getNPosts() { return nPosts; }
    public int getNLikes() { return nLikes; }
    public String toString()
    {
        String s;
        s = "\n-= " + name + " =-\nFollowers: " + String.valueOf(nFollowers) +
            "\nFollowing: " + String.valueOf(nFollowing) + "\nPosts: " +
            String.valueOf(nPosts) + "\nLikes: " + String.valueOf(nLikes);
        return s;
    }

    //MUTATORS
    public void setName(String newName)
    {
        if (newName != null)
        {
            name = newName;
        }
        else
        {
            throw new IllegalArgumentException("Error: Name can't be empty");
        }
    }
    public void setNFollowers(int newN)
    {
        if (newN >= 0)
        {
            nFollowers = newN;
        }
        else
        {
            throw new IllegalArgumentException("Error: Value must be positive");
        }
    }
    public void setNFollowing(int newN)
    {
        if (newN >= 0)
        {
            nFollowing = newN;
        }
        else
        {
            throw new IllegalArgumentException("Error: Value must be positive");
        }
    }
    public void setJustShared(Post inPost)
    {
        justShared = inPost;
    }
    public void addPost() { nPosts++; }
    public void addLike() { nLikes++; }

 /* Update the number of followers, in case any followers were removed from
    the network */
    public void updateFollowers(DSABinarySearchTree links)
    {
        int followers = 0;
        for (Object o : links)
        { //Count the number of nodes in the person's edges tree
            if (o != null)
            {
                followers++;
            }
        }
        setNFollowers(followers);
    }

    public void viewPost(Post viewedPost, Person poster, Network network)
    {
        double clickbait = viewedPost.getClickbait();
        if (Math.random() <= network.getProbLike() * clickbait)
        { //Like the post (hence 'share' it also)
            poster.addLike();
            viewedPost.addLike();
            justShared = viewedPost;

            if (!network.hasEdge(poster.getName(), name))
            {
                if (Math.random() <= network.getProbFollow())
                { //If not already following, follow the poster
                    network.addEdge(poster.getName(), name);
                }
            }
        }
        viewedPost.addSeen(name);
    }
}
