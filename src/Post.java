/* Author: Kristian Rados (19764285)
   Created: 23/10/2019
   Last Modified: 28/10/2019                                                  */

public class Post implements Comparable<Post>
{
    private String poster;
    private String text;
    private double clickbait;
    private int likes;
    private DSALinkedList haveSeen;
    private int numSeen;
    private int numSeenPrev;
    private boolean stale;

    //CONSTRUCTOR
    public Post(String inPoster, String inText, double inClickbait)
    {
        if (inPoster != null && !inPoster.equals(""))
        {
            if (inText != null && !inText.equals(""))
            {
                //1.0 <= clickbait <= 5.0
                if (inClickbait < 1.0)
                {
                    throw new IllegalArgumentException("Error: " +
                            "Clickbait factor must be >= 1.0");
                }
                else if (inClickbait > 5.0)
                {
                    throw new IllegalArgumentException("Error: " +
                            "Clickbait factor must be <= 5.0");
                }
                else
                {
                    poster = inPoster;
                    text = inText;
                    clickbait = inClickbait;
                    likes = 0;
                    haveSeen = new DSALinkedList();
                    //The poster can't view/like their own post
                    haveSeen.insertLast(inPoster);
                    numSeen = 1;
                    numSeenPrev = 0;
                    stale = false;
                }
            }
            else
            {
                throw new IllegalArgumentException("Error: Post text is " +
                    "invalid");
            }
        }
        else
        {
            throw new IllegalArgumentException("Error: Poster name is invalid");
        }
    }

    //ACCESSORS
    public String getPoster() { return poster; }
    public String getText() { return text; }
    public double getClickbait() { return clickbait; }
    public int getLikes() { return likes; }
    public boolean isStale() { return stale; }
    public int getNumSeen() { return numSeen; }
    public int getNumSeenPrev() { return numSeenPrev; }
    public boolean hasSeen(String name)
    { //Check if viewer is already in haveSeen list
        String hasViewed;
        boolean hasSeen = false;
        for (Object o : haveSeen)
        {
            hasViewed = (String)o;
            if (hasViewed.equals(name))
            {
                hasSeen = true;
            }
        }
        return hasSeen;
    }

    @Override
    public int compareTo(Post p)
    { //Used for sorts
        int value;
        value = likes - p.getLikes();
        return value;
    }

    //MUTATORS
    public void addLike() { likes++; }
    public void addSeen(String name)
    {
        haveSeen.insertLast(name);
        numSeen++;
    }
    public void updateNumSeenPrev()
    {
        numSeenPrev = numSeen;
    }
    public void setStale(boolean b)
    {
        stale = b;
    }
}