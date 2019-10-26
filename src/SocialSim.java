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

 /* Main menu for the interactive testing environment */
    private static void menu()
    { //TODO do rigorous testing on menus to check for buffer issues
        Scanner sc = new Scanner(System.in);
        Network network = new Network();
        String filename;
        double probLike, probFollow;
        int cmd = -1;

        do
        {
            try
            {
                System.out.println("\n[1] Load Network\n[2] Set Probabilities" +
                    "\n[3] Node Operations\n[4] Edge Operations\n[5] New Post" +
                    "\n[6] Display Network\n[7] Display Statistics" +
                    "\n[8] Update (Run Timestep)\n[9] Save Network\n[0] " +
                    "Exit\n");
                cmd = sc.nextInt();
                switch (cmd)
                {
                    case 1: //Load
                        System.out.print("Filename: ");
                        sc.nextLine();
                        filename = sc.next();
                        network = IO.loadNetwork(filename);
                        break;
                    case 2: //Probabilities
                        System.out.println("Like Probability: ");
                        sc.nextLine();
                        probLike = sc.nextDouble();
                        network.setProbLike(probLike);

                        System.out.println("Follow Probability: ");
                        sc.nextLine();
                        probFollow = sc.nextDouble();
                        network.setProbLike(probFollow);
                        break;
                    case 3: //Node Ops
                        network = nodeOperations(network);
                        break;
                    case 4: //Edge Ops

                        break;
                    case 5: //New Post

                        break;
                    case 6: //Display Network
                        network.displayAsList();
                        break;
                    case 7: //Display Stats
                        //temp
                        System.out.println("Nodes: " +
                                           network.getVertexCount());
                        System.out.println("Edges: " + network.getEdgeCount());
                        break;
                    case 8: //Run Timestep

                        break;
                    case 9: //Save
                        System.out.print("Filename: ");
                        sc.nextLine();
                        filename = sc.next();
                        IO.saveNetwork(filename, network);
                        break;
                    default:
                        if (cmd != 0)
                        {
                            System.out.println("Error: Invalid selection");
                        }
                        break;
                }
            }
            catch (IllegalArgumentException e1)
            {
                System.out.println(e1.getMessage());
            }
        } while (cmd != 0);
    }

 /* Sub-menu for node operations */
    private static Network nodeOperations(Network network)
    {
        Scanner sc = new Scanner(System.in);
        String name = "";
        int cmd;

        System.out.println("[1] Find Node\n[2] Insert Node\n[3] Delete " +
            "Node\n[0] Back\n");
        cmd = sc.nextInt();
        sc.nextLine();
        try
        {
            switch (cmd)
            {
                case 1:
                    System.out.print("Node/Person Name: ");
                    name = sc.nextLine().trim(); //Remove excess whitespace
                    network.findNode(name);
                    break;
                case 2:
                    System.out.print("Node/Person Name: ");
                    name = sc.nextLine().trim(); //Remove excess whitespace
                    Person newNode = new Person(name);
                    network.addVertex(name, newNode);
                    System.out.println("'" + name + "' was successfully added" +
                        " to the network");
                    break;
                case 3:
                    System.out.print("Node/Person Name: ");
                    name = sc.nextLine().trim(); //Remove excess whitespace
                    network.removeVertex(name);
                    System.out.println("'" + name + "' was successfully" +
                        " removed from the network");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Error: Invalid selection");
                    break;
            }
        }
        catch (IllegalArgumentException e1)
        {
            System.out.println("Error: Node '" + name + "' already exists");
        }
//        catch (NoSuchElementException e2)
//        {
//            System.out.println("Error: Could not find node '" + name + "'");
//        }
        return network;
    }

 /* Display general usage information to user about running program, including
    a description of the two modes and their flags */
    private static void usageInfo()
    {
        //TODO user information print statements
    }
}
