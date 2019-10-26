public class Person
{
    private String name;
    private int nFollowers;
    private int nFollowing;
    private int nPosts;
    private int nLikes;

    //CONSTRUCTOR
    public Person(String newName)
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
    public void setNPosts(int newN)
    {
        if (newN >= 0)
        {
            nPosts = newN;
        }
        else
        {
            throw new IllegalArgumentException("Error: Value must be positive");
        }
    }
    public void setNLikes(int newN)
    {
        if (newN >= 0)
        {
            nLikes = newN;
        }
        else
        {
            throw new IllegalArgumentException("Error: Value must be positive");
        }
    }

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
}
