/* Author: Kristian Rados (19764285)
   Created: 23/10/2019
   Last Modified: 28/10/2019                                                  */

public class Person implements Comparable<Person>
{
    private String name;
    private int nFollowers;
    private int nFollowing;
    private int nPosts;
    private int nLikes;
    private DSAQueue justShared; //For posts that have just been posted or liked

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
            justShared = new DSAQueue();
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
    public DSAQueue getJustShared() { return justShared; }
    public String toString()
    {
        String s;
        s = "\n-= " + name + " =-\nFollowers: " + String.valueOf(nFollowers) +
            "\nFollowing: " + String.valueOf(nFollowing) + "\nPosts: " +
            String.valueOf(nPosts) + "\nLikes: " + String.valueOf(nLikes);
        return s;
    }

    @Override
    public int compareTo(Person p)
    { //Used for sorts
        int value;
        value = nFollowers - p.getNFollowers();
        return value;
    }

    //MUTATORS
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
    public void addJustShared(Post inPost)
    {
        justShared.enqueue(inPost);
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

 /* Viewing someone's post that has been shared to this person and determining
    whether it will be liked and consequently if that sharer will be followed */
    public void viewPost(Post viewedPost, Person poster, Network network)
    {
        boolean hasSeen = viewedPost.hasSeen(name);
        if (!hasSeen) //Check they haven't seen the post already
        {
            double clickbait;
            viewedPost.addSeen(name);
            clickbait = viewedPost.getClickbait();
            if (Math.random() <= network.getProbLike() * clickbait)
            { //Like the post (hence 'share' it also)
                poster.addLike();
                viewedPost.addLike();
                network.enqueueTimeStepText("\n- LIKE: '" + name + "' liked a" +
                    " post by '" + poster.getName() + "'");
                justShared.enqueue(viewedPost);

                if (!network.hasEdge(poster.getName(), name))
                {
                    if (Math.random() <= network.getProbFollow())
                    { //If not already following, follow the poster
                        network.addEdge(poster.getName(), name);
                        network.enqueueTimeStepText("\n- FOLLOW: '" + name +
                            "' followed '" + poster.getName() + "'");
                    }
                }
            }
        }
    }
}