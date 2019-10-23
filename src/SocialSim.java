import java.util.*;

public class SocialSim
{
    public static void main(String[] args)
    {
        try
        {
            if (args.length == 5)
            { //Checking for simulation mode
                //TODO check for correct -s CLI format
            }
            else if (args.length == 1)
            { //Checking for interactive mode
                if (args[0].equals("-i"))
                {
                    menu();
                }
                else
                { //Checking for an incorrect command line argument
                    throw new IllegalArgumentException("Command line argument" +
                        "must either be the -i flag for the interactive" +
                        "testing environment or the -s flag for simulation " +
                        "mode");
                }
            }
            else if (args.length == 0)
            { //No command line flags
                usageInfo();
            }
            else
            { //Checking for > 1 command line arguments
                throw new IllegalArgumentException("Invalid number of command" +
                    "line arguments, run program with the -i flag, the -s " +
                    "flag or no flags");
            }
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }

  /*Main menu for the interactive testing environment*/
    private static void menu()
    {
        Scanner sc = new Scanner(System.in);
        Network network = new Network();
        String filename;
        int cmd = -1;

        do
        {
            try
            {
                System.out.println("\n[1] Load Network\n[2] Set Probabilities" +
                    "\n[3] Node Operations\n[4] Edge Operations\n[5] New Post" +
                    "\n[6] Display Network\n[7] Display Statistics" +
                    "\n[8] Update (Run Timestep)\n[9] Save Network\n[0] Exit");
                cmd = sc.nextInt(); //TODO check this doesn't break program
                switch (cmd)
                {
                    case 1:
                        System.out.print("Filename: ");
                        sc.nextLine();
                        filename = sc.next();
                        /*"""tree""" = loadNetwork(filename);*/
                        break;
                    case 2:

                        break;
                    case 3:

                        break;
                    case 4:

                        break;
                    case 5:

                        break;
                    case 6:

                        break;
                    case 7:

                        break;
                    case 8:

                        break;
                    case 9:
                        System.out.print("Filename: ");
                        sc.nextLine();
                        filename = sc.next();
                        /*"""tree""" = loadNetwork(filename);*/
                        break;
                }
            }
            catch (IllegalArgumentException e1)
            {
                System.out.println("Error: " + e1.getMessage());
            }
        } while (cmd != 0);
    }

  /*Display general usage information to user about running program, including
    a description of the two modes and their flags*/
    private static void usageInfo()
    {
        //TODO user information print statements
    }
}
