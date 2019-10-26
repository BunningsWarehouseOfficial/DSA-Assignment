public class Event
{
    private Object data;
    private char tag;

    //CONSTRUCTOR
    public Event(Object inData, char inTag)
    {
        data = inData;
        tag = inTag;
    }

    //ACCESSORS
    public Object getData() { return data; }
    public char getTag() { return tag; }
}
