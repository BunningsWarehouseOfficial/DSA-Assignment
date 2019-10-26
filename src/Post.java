public class Post
{
    private String poster;
    private String text;
    private double clickbait;
    private int likes;
    private DSALinkedList hasSeen;

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
                    hasSeen = new DSALinkedList();
                    //The poster can't view/like their own post
                    hasSeen.insertLast(inPoster);
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

    //MUTATORS
    public void addLike() { likes++; }
    public void addSeen(String name)
    {
        hasSeen.insertLast(name);
    }
}
