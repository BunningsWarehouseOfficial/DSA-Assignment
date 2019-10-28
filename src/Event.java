/* Author: Kristian Rados (19764285)
   Created: 23/10/2019
   Last Modified: 28/10/2019                                                  */

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
