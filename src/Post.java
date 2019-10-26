public class Post
{
    private String poster;
    private String text;
    private int likes;

    //CONSTRUCTOR
    public Post(String inPoster, String inText)
    {
        if (inPoster != null && !inPoster.equals(""))
        {
            if (inText != null && !inText.equals(""))
            {
                poster = inPoster;
                text = inText;
                likes = 0;
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
    public int getLikes() { return likes; }

    //MUTATORS
    public void addLike() { likes++; }
}
