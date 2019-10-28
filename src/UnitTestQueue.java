import java.util.*;

public class UnitTestQueue
{
    public static void main(String[] args)
    {
        DSAQueue queue = new DSAQueue();
        test(queue);
    }

    private static void test(DSAQueue queue)
    {
        Scanner sc = new Scanner(System.in);
        String cmd;
        cmd = "";
        do
        {
            try
            {
                System.out.println("Enter a string to add to the queue. " +
                                   "Enter [-] to dequeue a term or enter " +
                                   "[end] to quit.");
                cmd = sc.nextLine();
                if (cmd.equals("-"))
                {
                    System.out.println("Dequeued Term: " + queue.dequeue());
                }
                else
                {
                    queue.enqueue(cmd);
                }
            }
            catch (IllegalArgumentException e)
            {
                System.out.println(e.getMessage());
            }
        } while (!cmd.equals("end")); 
    }
}
