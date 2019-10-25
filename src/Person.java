public class Person
{
    private String name;
    private int nFollowers;
    private int nFollowing;
    private int nPosts;
    private int nLikes; //TODO may not be necessary

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
    public int getNFollowers() {return nFollowers; }
    public int getNFollowing() {return nFollowing; }
    public int getNPosts() {return nPosts; }
    public int getNLikes() {return nLikes; }

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
}
